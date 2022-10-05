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

3. 그 외에 꼭 경험해봤으면 하는 기술이 있는지?

- AWS, GCP, Azure 가 제공해주는 특성 기술들
- 서버, DB 모니터링
- 부하 테스트
- MSA
