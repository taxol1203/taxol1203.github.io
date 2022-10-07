## 정리할 내용

https://github.com/backtony/Backend_Interview_for_Beginner/blob/master/Java.md
에서 정리하자

1. Wrapper Class

2. Collection에서 Wrapper Class를 사용하는 이유

3. static

4. 전역 변수에 static을 쓰는 이유

5. main에 static을 쓰는 이유

## 백엔드 정리

1. DB 선택
   기본적으로 Rdbms를 쓰되, 위치데이터는 위도 경도를 저장하므로 rdbms를 사용하여도 좋다.
   단, 위치 데이터를 조회하는 성능을 고려하여 RDBMS를 정한다.
   -> 우리 비즈니스에 적합안 DB를 선정

2. 기술 선택
   MSA(spring cloud), WEBFLUX <-> jpa,

김재의 의견
다음 회의 전까지 각자 고민해봐요

1. RDB 선택 (MySQL, PostgreSQL ...)

- PostgreSQL 의 경우 geospatial 기능이 강력함(MySQL 과 PostgreSQL 의 자세한 성능 비교는 추후 문서로 정리 필요)
- 우리 서비스는 특성상 위치데이터를 다루는 경우가 많을 것
- 위치데이터를 어떤 데이터베이스에 저장할지에 앞서서 어떤 형태로 저장할지도 고민이 필요
- 위치데이터 하나하나마다 위,경도 뿐만 아니라 속도, 가속도 등 그 순간의 다양한 센서값이 포함될 것
- 한 번의 러닝이 보통 수백~수천개의 데이터를 생산하고 위치데이터의 저장과 조회는 러닝의 단위로 일어날 것이므로 저장, 조회의 단위는 단건보다는 대량으로 생각
- 또한 수정보다는 입력이 압도적으로 많을 것(이렇게보니 로그성 데이터의 성격이 짙다.)
- 위같은 위치데이터는 메인 DB 와는 따로 생각해도 좋을 듯 한데 어떤 DB 가 적합할지? (NoSQL 을 사용한다면 Mongo? ElasticSearch?)

2. Spring

- 서버 프레임워크는 스프링을 사용하게 될텐데 스프링에서 꼭 사용했으면 하는 기술이 있는지? (Jpa, QueryDSL, Webflux, Spring cloud ...)
- QueryDSL

- [WebFlux + R2DBC] VS [WebMVC+JPA]
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
- 서버, DB 모니터링
- 부하 테스트
- MSA

MSA를 번거롭고 귀찮은 작업이지만 요즘 기업들은 기본적으로 MSA를 선호하므로 시도해 보는 것을 좋다고 생각

MSA 를 동기로 할 시 부하가 생겨 WEBFLUX를 도입하면 좋다는 이야기가 있네요
https://ok4u.tistory.com/238


