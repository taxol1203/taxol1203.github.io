---
title: "Spring Boot Maven CA CERT 등록"
last_modified_at: 2022-03-30 T11:16:00-05:00
toc: true
toc_sticky: true
header:
categories:
  - Technology
tags:
  - Spring
  - Trouble Shooting
---

# Spring Boot Maven CA CERT 등록

## 오류 상황

Spring & Maven을 사용해 프로젝트를 init하는 과정에서 Maven install을 사용하였는데,  
다음과 같은 오류가 발생하였다.

```
[ERROR] Failed to execute goal on project titan: Could not resolve dependencies
for project lottechem.ess:titan:war:1.0.0-BUILD-SNAPSHOT:
Failed to collect dependencies at org.springframework:spring-core:jar:3.1.0.M1:
Failed to read artifact descriptor for org.springframework:spring-core:jar:3.1.0.M1:
Could not transfer artifact org.springframework:spring-core:pom:3.1.0.M1
from/to egovframe ([https://repo1.maven.apache.org/maven2/](https://repo.maven.apache.org/maven2/)):
java.lang.RuntimeException: Unexpected error:
java.security.InvalidAlgorithmParameterException:
the trustAnchors parameter must be non-empty -> [Help 1]
```

여기서 주목해야할 것은,

```
java.lang.RuntimeException: Unexpected error:
java.security.InvalidAlgorithmParameterException:
the trustAnchors parameter must be non-empty
```

이 오류이다.

Maven 인증서가 없어, 해당 오류가 발생하였다고 판단하여 인증서를 로컬에 저장하고 등록해보자.

## 해결

1. 문제가 되는 메이븐 저장소에 접속한다.  
   [https://repo1.maven.apache.org/maven2/](https://repo.maven.apache.org/maven2/) 에 접속

  <img src="/images/Tech/Spring/20220330/Untitled.png" width="50%" height="50%">

2. 해당 페이지 상단에 자물쇠 클릭한다. (Chrome 기준이다. )  
   <img src="/images/Tech/Spring/20220330/Untitled 1.png" width="50%" height="50%">

3. 인증서 복사  
   <img src="/images/Tech/Spring/20220330/Untitled 2.png" width="50%" height="50%">

4. base 64 인코딩  
   <img src="/images/Tech/Spring/20220330/Untitled 3.png" width="50%" height="50%">

5. keystore 등록  
   <img src="/images/Tech/Spring/20220330/Untitled 4.png" width="50%" height="50%">

```
keytool -import -file D:\lottechem\maven\repo.cer -keystore D:\lottechem\repoKeystore
```

주의 : jre가 설치되어있는 경로로 가서, 해당 .cer 파일을 통해 keystore을 등록한다.

6. 파일 확인  
   <img src="/images/Tech/Spring/20220330/Untitled 5.png" width="50%" height="50%">  
   방금 만든 Keystore를 확인한다.

7. maven 실행  
   <img src="/images/Tech/Spring/20220330/Untitled 6.png" width="50%" height="50%">

   D:\lottechem\tools\apache-maven-3.5.0-bin\bin\mvn clean install "-Djavax.net.ssl.trustStore=D:\lottechem\maven\repoKeystore"  
   주의 : pom.xml이 있는 경로로 가서 maven을 실행 한다. 여기서 인자를 방금 만든 keyStore를 적용한다.

8. 빌드

  <img src="/images/Tech/Spring/20220330/Untitled 7.png" width="50%" height="50%">

성공!
