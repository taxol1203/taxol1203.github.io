---
title: "Could not autowire. No beans of '' type found_Test Class"
date: 2022-06-06 17:45:28 -0400
categories:
  - Technology
tags:
  - Spring Boot
  - Trouble Shooting
---

> Java

# Could not autowire. No beans of '' type found.

========

## 문제

Test Class를 작성하는 도중, autowired로 가져올 bean 객체를 찾을 수 없다는 에러가 발생하였다.

<img src="/images/Tech/Spring/20220606/1.png" width="50%" height="50%">

문제가 발생하는 해당 TempRepository에 `@Repository` 어노테이션은 등록 된 상황이다.

## 해결

문제의 원인은 Test class의 위치가 `@SpringBootApplication` 애노테이션이 붙은 클래스가 존재하는 패키지의 하위 패키지에 테스트를 둬야 한다는 원칙을 어긴 것이었다.

<img src="/images/Tech/Spring/20220606/2.png" width="50%" height="50%">

현재 `temp` 테스트 클래스 폴더는 위치상 `core.user` 패키지 밖에 있다.  
또한, `@SpringBootApplication` 애노테이션이 붙은 클래스가 존재하는 패키지는 core.user 내부에 있으므로 `temp` 패키지는 `@SpringBootApplication`의 `@ComponentScan` 의 결과를 얻어 올 수 없었다.

따라서, `temp` 패키지의 위치를 `core.user` 패키지 아래에 놔두어 해결하였다.

## References

<!-- ★
<img src="/images/Tech/Django/silk.png" width="40%" height="40%">

-->
