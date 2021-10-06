---
title: "Djagno Silk Profiler 적용 및 시행착오"
date: 2021-10-06 17:45:28 -0400
categories: 
  - Technology
tags:
  - Django
---

> python

Djagno Silk Profiler 적용 및 시행착오  
===========

## 서론
장고 API를 개발 후, 성능 측정을 위해 silk Profilier를 적용하였다.  
한번에 되었으면 좋았겠지만, 많은 시행착오를 겪어 차후 다른 개발자가 참고하도록 정리한다.

## 설치
설치는 간단하다.  
[Silk 공식 문서](https://github.com/jazzband/django-silk)에 따라 설치하면 된다.  

### pip install
먼저 silk 라이브러리를 받자.  
```
pip install django-silk
```

### setting.py
1. setting.py 파일 내부에 MIDDLEWARE에 다음과 같이 추가한다.  

```python
MIDDLEWARE = [
    # CORS
    'corsheaders.middleware.CorsMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',

    # silk middleware
    'silk.middleware.SilkyMiddleware',

    # 나머지 middleware들
    ---
]
```
`SilkyMiddleware`를 위치하는 장소가 중요하다.  

`SilkyMiddleware`는 기존 장고에 작성된 Middleware 뒤에, 그리고 새롭게 기입하는 Middleware 앞에 위치하여야 한다.  

```python
INSTALLED_APPS = (
    ...
    'silk'
)
```
그리고 silk라는 앱을 장고가 읽을 수 있도록 한다.  

## urls.py

Silk의 사용자 인터페이스를 연결하기 위해, url을 설정한다.  
여기서 말한 urls.py는 장고 프로젝트의 기본 urls.py에 존재하는 것이다.  
```python
urlpatterns = [
    # silk 적용
    path('silk/', include('silk.urls')),

    # 나머지 path들
    ~~~
]
```

접속하는 방법은 로컬 호스트 기준으로 다음과 같다.  
```
http://127.0.0.1:8000/silk/
```

## migrate
Silk의 migratinon을 실행한다.  
아마 makemigrations는 달라진게 없다고 생략해도 될 것이다.   
 
```
python manage.py makemigrations

python manage.py migrate

python manage.py collectstatic
```

## 사용

이제 설치는 끝났다.  

Silk 라이브러리를 사용하는 방법은 Django의 swagger나 직접 url로 들어가는 것이 아닌,   
**클라이언트에서 해당 장르의 api를 호출할 때** 그 intercept하여 Silk가 실행 시간을 기록한다.    

## 시행 착오

### 사용법 미숙지
처음 접한 문제는, 앞서 말한 localhost의 url과 swagger로만 api를 실행하여 Silk가 인식하지 못한 것이었다.  

이는 클라이언트를 실행하다 보니 우연히 기록이 되는 것을 확인하여 알 수 있었다.  

### 0ms

<img src="/images/Tech/Django/silk.png" width="100%" height="100%">  

보다싶이 api 요청을 잡았지만, 제대로 결과가 나오지 않는다.  



## References
[DRF serializers를 통해 장고 객체를 json으로 만들기](https://darrengwon.tistory.com/480)  

<!-- ★
<img src="/images/Tech/Django/silk.png" width="40%" height="40%">

-->
