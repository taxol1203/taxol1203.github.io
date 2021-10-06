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

## views.py

특정 메서드를 profiling 하고 싶다면 데코레이터를 사용한다.  

```python
from silk.profiling.profiler import silk_profile

@api_view(['GET'])
@silk_profile(name='View get movie data')
def get_genre_rec_movies(self):
  # 내가 수행하고 싶은 무언가
```

그리고 읽을 수 있게 `setting.py`에도 설정을 추가한다.

```python
SILKY_PYTHON_PROFILER = True
SILKY_PYTHON_PROFILER_BINARY = True
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

<img src="/images/Tech/Django/silk1.png" width="100%" height="100%">  

보다싶이 api 요청을 잡았지만, 제대로 결과가 나오지 않는다.  

이는 2가지 문제가 있었으며 각각 해결하니 정상적으로 나왔다.  

- 장고 버젼 문제

지금까지 사용하였던 장고 버젼은 `3.2`였다.  
따라서, 장고 버젼을 `3.1`로 낮추었다.  

```
pip install "django<3.2"
```

[Silk shows 0 time for all queries?](https://github.com/jazzband/django-silk/issues/442#issuecomment-814920147)    

- profiling directory 경로 문제  
결과가 표시되지 않는 이유는, profile을 한 후 그 결과를 저장할 디렉토리 경로와 폴더가 지정되지 않아서 이다.  

따라서, `setting.py` 최 상단에 다음과 같이 추가하였다.  

```python
# silk 설정
SILKY_PYTHON_PROFILER = True
SILKY_PYTHON_PROFILER_BINARY = True
SILKY_PYTHON_PROFILER_RESULT_PATH = os.path.join(BASE_DIR, 'profiles') # 추가
```

이후 디렉토리 root위치에 profiles 폴더를 추가하여 결과를 저장한다.  

<img src="/images/Tech/Django/silk3.png" width="50%" height="50%">   

## 결과

4시간의 노력 끝에 성공했다.   

<img src="/images/Tech/Django/silk2.png" width="100%" height="100%">   

특정 메서드를 profiling도 가능하다.  

<img src="/images/Tech/Django/silk4.png" width="100%" height="100%">   

## References


<!-- ★
<img src="/images/Tech/Django/silk.png" width="40%" height="40%">

-->
