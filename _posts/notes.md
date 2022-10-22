## 정리할 내용

https://github.com/backtony/Backend_Interview_for_Beginner/blob/master/Java.md
에서 정리하자

1. Wrapper Class

이점.

- 각 객체에 대한 자신의 상태를 객체 스스로 관리할 수 있다.  
  User 클래스에 나이를 저장하는 값을 int 기본타입이 아닌 Wrapper class로 저장하면,
  유효성 검증을 User 클래스가 아닌 나이 Int Wrapper Class가 검증할 수 있어 책임이 분리 할 수 있다.
  [이점1](https://tecoble.techcourse.co.kr/post/2020-05-29-wrap-primitive-type/)

2. Collection에서 Wrapper Class를 사용하는 이유

ArrayList 등과 같은 Collection의 데이터 구조는 기본 타입이 아닌 객체만 저장하게 되고, Wrapper class를 사용하여 자동박싱/언박싱이 일어난다.

3. static

4. 전역 변수에 static을 쓰는 이유

5. main에 static을 쓰는 이유

## 11st

### solr

자바 언어로 구현 된 오픈소스 기반의 검색 플랫폼

그리고 제가 사용해 본 solr는 search기능과 indexing기능이 아주 잘 되어있는 또다른 DB라는 생각이 들었습니다.
solr에도 DB처럼 쿼리, index, table, column이 있고 이것들은 DB의 개념과 유사하게 사용됩니다.

https://raspberrypi98.tistory.com/m/93

## 백엔드 정리

김재의 의견
다음 회의 전까지 각자 고민해봐요

## RDB 선택 (MySQL, PostgreSQL ...)

RDB를 어떤 것을 선택하는 지는 어떻게 위치 데이터를 저장할 것인가에 달려있다.  
위치데이터는 정형 데이터이지만 대량 입력이 주로 되므로 로그성 데이터인 비 정형으로도 볼 수 있다.

> 연산가능하면 정형 데이터이며, 형태가 있으나 연산가능하지 않으면 반정형 데이터에 속한다(로그, SNS)

### Mysql vs PostgreSql

Mysql은 단순한 읽기에 강점을 보이고, PostgreSql은 대규모 데이터 베이스와 복잡한 쿼리를 사용할 때 강점을 보인다.  
![image](https://user-images.githubusercontent.com/38308337/194551491-5ad0b6db-3bf5-4f01-9ab7-4556c98ee918.png)
[우아한 형제](https://techblog.woowahan.com/6550/)
[Integraate](https://www.integrate.io/ko/blog/postgresql-vs-mysql-the-critical-differences-ko/)

### 그 외

MSA를 사용할 시, DB를 분리하여  
러닝 기록 서비스에서는 Nosql, 그 외 데이터는 RDB를 사용하는 방안도 존재

## Spring

- 서버 프레임워크는 스프링을 사용하게 될텐데 스프링에서 꼭 사용했으면 하는 기술이 있는지? (Jpa, QueryDSL, Webflux, Spring cloud ...)
  저는 Kotlin을 써서 개발하는데도 큰 도전이라 생각되어,  
  Kotlin, jpa 더 하고자하면 MSA를 도전하고싶다.

### QueryDSL

https://tecoble.techcourse.co.kr/post/2021-08-08-basic-querydsl/

쭉 읽어 봤는데, JPA 사용할 줄 알면 충분히 사용 가능하다고 봅니다. 강추

### [WebFlux + R2DBC] VS [WebMVC+JPA]

GET API 성능을 비교시, WebFlux+R2DBC가 WebMVC+JPA 대비 480% 향상되었음을 확인
[WebFlux+R2DBC 와 WebMVC+JPA 성능 대결](https://www.manty.co.kr/bbs/detail/develop?id=198&scroll=comment)

성능면으로만 보면 WebFlux를 쓰는 것이 맞을 수 있지만, 반레도 존재

```
Webflux 프로젝트의 비지니스 로직들이 모두 Async + NonBlocking 으로 되어있다면 빠를 것. (DB connector, 외부 API 호출 등)
하지만 하나라도 Sync or Blocking 된 부분이 있거나, CPU 를 많이 쓰는 코드가 들어있다면 MVC보다 느릴 것으로 예상

2. blocking 로직이 들어가더라도 MVC 보다 빠를까?
느리다. blocking 이 하나라도 들어가면 MVC가 더 빠르다.
```

https://blog.naver.com/joebak/222008524688

Spring MVC의 blocking과 WebFlux의 Non blocking
https://pearlluck.tistory.com/726

Kotlin과의 조합은 WebFlux가 좋지만,  
JPA도 개인적으로 프로젝트를 수행하며 익히고 싶은 생각이 크다.  
또한 아직 WebFlux 보다는 Spring MVC가 It 업계에서 많이 사용되는 것 같음(뇌피셜)

3. 그 외에 꼭 경험해봤으면 하는 기술이 있는지?

- AWS, GCP, Azure 가 제공해주는 특성 기술들

### 서버, DB 모니터링

### 부하 테스트

nGrinder vs Jmeter
Jmeter가 오래되었고, nGrinder가 네이버에서 만들었네요,,
둘 중 사용하기 쉬운거 쓰면 될듯?

### MSA

MSA를 번거롭고 귀찮은 작업이지만 요즘 기업들은 기본적으로 MSA를 선호하므로 시도해 보는 것을 좋다고 생각

MSA 를 동기로 할 시 부하가 생겨 WEBFLUX를 도입하면 좋다는 이야기가 있네요
https://ok4u.tistory.com/238

## 나중에 공부하고 정리 할 것

- java의 collections 시간복잡도
- collection의 generic은 항상 reference type만 들어가는 이유
