---
title: "[SpringBoot + Kotlin] KaKao & Google OAuth Login web service"
last_modified_at: 2023-01-18 T11:44:00-05:00
toc: true
toc_sticky: true
header:
  teaser: /images/effective_Java.png
categories:
  - Technology
tags:
---

> [SpringBoot + Kotlin] KaKao & Google OAuth Login web service

## 사전 설명

KaKao와 Google의 Oauth 기능을 사용하여 로그인을 구현하고 사용자 정보를 DB에 저장하는 것 까지 해보겠습니다.

이 포스트는 Spring web server로 Oauth의 로그인 창을 띄우고 access token을 발급, 회원 정보를 가져오는 모든 기능을 수행하는 것입니다.

프론트에서 access token을 받아 Oauth를 통해 로그인을 처리하는 것은 다음 포스트에서 진행 될 예정입니다.

## 사전 작업

구글과 카카오의 Oauth api을 사용하기 위해서는 각 서비스의 OAuth 서비스 등록이 필요합니다.  
해당 과정은 [해당 포스트](https://deeplify.dev/back-end/spring/oauth2-social-login#%EA%B5%AC%EA%B8%80-oauth-%EC%84%9C%EB%B9%84%EC%8A%A4-%EB%93%B1%EB%A1%9D)에 잘 정리되어 있어 구글과 카카오 Oauth 서비스 등록을 참고하시길 바라겠습니다.

그 이외에 참고할 정보는 다음과 같습니다.

### 구글

리다이렉트 URL 세팅이 필요합니다.  
제가 개발할 당시, port 번호로 인하여 인증이 안되는 오류가 발생하여 리디렉션 URL에 포트가 있는 정보와 없는 정보 전부 넣어주었습니다.  
<img src="/images/Tech/Spring/20230118/google_redirect.png" width="50%" height="50%">

### 카카오

동의 항목은 닉네임과 이메일을 선택하였습니다.  
해당 동의항목 외 다른 동의항목을 선택하면 `CustomOAuth2Provider`의 `scope()`에 추가가 필요합니다.

<img src="/images/Tech/Spring/20230118/kakao_agree.png" width="50%" height="50%">

카카오의 리다이렉트 URL도 다음과 같이 세팅 해줍니다.

<img src="/images/Tech/Spring/20230118/kakao_redirect.png" width="50%" height="50%">

## 코드

프로젝트 구조는 다음과 같습니다.

<img src="/images/Tech/Spring/20230118/project_structure.png" width="50%" height="50%">

### build.gradle.kt

디펜던시는 다음과 같이 세팅합니다.

```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    // db
    runtimeOnly("com.h2database:h2")
    implementation("org.postgresql:postgresql:42.5.1")
    runtimeOnly("org.postgresql:postgresql") // 추가
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
}
```

### SecurityConfig

해당 파일에서는 스프링 시큐리티 설정과 OAuth2 기능을 시큐리티에 적용하는 기능이 작성되어 있습니다.

```kotlin
package com.oauth.example.oauth.authorizationserver

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.filter.CharacterEncodingFilter

/**
 * 스프링 시큐리티 관련 설정입니다.
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Autowired private val customOAuth2AccountService: CustomOAuth2AccountService
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {

        http
            // OAuth2 적용 관련 설정입니다.
            .addFilterAt(CharacterEncodingFilter(), CsrfFilter::class.java)
            .csrf().disable()
            // URI 접근과 관련된 설정입니다.
            .authorizeRequests()
            // 아래 url으로만 접근 가능하도록 세팅
            .antMatchers("/", "/login/**", "/oauth2/**", "/images/**", "/api/login/**").permitAll()
            .anyRequest().authenticated()

            // Iframe 사용 허용합니다.
            .and()
            .headers().frameOptions().disable()

            // 인증되지 않은 사용자를 원하는 페이지로 이동시킵니다.
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))

            // 로그인 인증 후 이동 페이지 설정입니다.
            .and()
            .formLogin()
            .successForwardUrl("/welcome")

            // 로그아웃과 관련한 설정입니다.
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)

            .and()
            .oauth2Login()
            .defaultSuccessUrl("/login/complelte")
            .userInfoEndpoint() // userInfo Endpoint, 즉 로그인 성공 후에 관하여 설정
            .userService(customOAuth2AccountService) // 로그인 성공후에 사용할 Service 등록

    }

    /**
     * OAuth2 설정입니다.
     */
    @Bean
    fun clientRegistrationRepository(oAuth2ClientProperties: OAuth2ClientProperties,
    ): InMemoryClientRegistrationRepository {

        // 소셜 설정 등록
        val registrations = oAuth2ClientProperties.registration.keys
            .map { getRegistration(oAuth2ClientProperties, it) }
            .filter { it != null }
            .toMutableList()

        registrations.add(
            CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId("put client id")   // 카카오 oauth의 client id (restful id)
                .clientSecret("put secret") // 카카오 oauth의 secret pw
                .jwkSetUri("temp")
                .build());

        return InMemoryClientRegistrationRepository(registrations)
    }

    // 공통 소셜 설정을 호출합니다.
    private fun getRegistration(clientProperties: OAuth2ClientProperties, client: String): ClientRegistration? {
        val registration = clientProperties.registration[client]
        return when(client) {
            "google" -> CommonOAuth2Provider.GOOGLE.getBuilder(client)
                .clientId(registration?.clientId)
                .clientSecret(registration?.clientSecret)
                .scope("email", "profile")
                .build()
            else -> null
        }
    }
}
```

## 결과

## 전체 코드

마지막으로 서비스를 구현한 전체 프로젝트 입니다.
[OAuthLogin Example](https://github.com/taxol1203/OAuthLoginExample)

## 출처

[[Spring boot + Kotlin] OAuth2를 이용한 Social Login](https://blog.naver.com/PostView.naver?blogId=anytimedebug&logNo=221396422266&categoryNo=28)  
[[Spring Boot] OAuth2 소셜 로그인 가이드 (구글, 페이스북, 네이버, 카카오)](https://deeplify.dev/back-end/spring/oauth2-social-login)
