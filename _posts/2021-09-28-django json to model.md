---
title: "Djagno ORM을 사용하여 json 파일을 database에 저장하기 with postgres"
date: 2021-08-30 17:45:28 -0400
categories: 
  - Technology
tags:
  - Django
---

> python

Djagno ORM을 사용하여 json 파일을 postgres database에 저장 
===========

## 문제
TMDB의 영화 데이터를 json 형태로 파싱하였다.
```json
[
    {
        "model": "movies.movie",
        "pk": 597891,
        "fields": {
            "poster_path": "/uQWgSRXeYRWCvGIX9LDNBW6XBYD.jpg",
            "title": "Kate",
            "original_title": "Kate",
            "original_language": "en",
            "overview": "After she's irreversibly poisoned, a ruthless criminal operative has less than 24 hours to exact revenge on her enemies and in the process forms an unexpected bond with the daughter of one of her past victims.",
            "adult": false,
            "popularity": 2247.437,
            "release_date": "2021-09-10",
            "genre_ids": [
                28,
                53
            ],
            "vote_average": 6.8,
            "vote_count": 404,
            "like_users": [],
            "provider": {
                "link": "https://www.themoviedb.org/movie/597891-kate/watch?locale=KR",
                "flatrate": [
                    {
                        "display_priority": 0,
                        "logo_path": "/9A1JSVmSxsyaBK4SUFsYVqbAYfW.jpg",
                        "provider_id": 8,
                        "provider_name": "Netflix"
                    }
                ]
            }
        }
    },
    {
        "model": "movies.movie",
        "pk": 436969,
        "fields": {
            "poster_path": "/kb4s0ML0iVZlG6wAKbbs9NAm6X.jpg",
            "title": "The Suicide Squad",
            "original_title": "The Suicide Squad",
            "original_language": "en",
            "overview": "Supervillains Harley Quinn, Bloodsport, Peacemaker and a collection of nutty cons at Belle Reve prison join the super-secret, super-shady Task Force X as they are dropped off at the remote, enemy-infused island of Corto Maltese.",
            "adult": false,
            "popularity": 2017.497,
            "release_date": "2021-07-28",
            "genre_ids": [
                28,
                12,
                14,
                35
            ],
            "vote_average": 7.9,
            "vote_count": 3978,
            "like_users": [],
            "provider": {
                "link": "https://www.themoviedb.org/movie/436969-the-suicide-squad/watch?locale=KR",
                "buy": [
                    {
                        "display_priority": 3,
                        "logo_path": "/p3Z12gKq2qvJaUOMeKNU2mzKVI9.jpg",
                        "provider_id": 3,
                        "provider_name": "Google Play Movies"
                    }
                ],
                "rent": [
                    {
                        "display_priority": 3,
                        "logo_path": "/p3Z12gKq2qvJaUOMeKNU2mzKVI9.jpg",
                        "provider_id": 3,
                        "provider_name": "Google Play Movies"
                    }
                ]
            }
        }
    },
]
```  

이 데이터를 Postgres DB의 `Movie` table에 저장 해본다.  

## model.py

우선 Movie talbe을 만들자

```python
class Movie(models.Model):
    movie_id = models.CharField(max_length=10, default='', unique=True)
    original_title = models.CharField(max_length=100)
    overview = models.TextField()
    release_date = models.DateField()
    poster_path = models.CharField(max_length=40) 
```

## views.py

1. json을 파일을 불러온다  
2. pd.json_normalize() 사용하여 json을 dataFrame으로 변환한다.  
3. df.iterrows() 메서드를 사용하여 row를 하나씩 방문하고, dictionary 접근으로 필요한 데이터를 `Movie` 테이블에 넣어준다.  

```python
def get_movie_data(self):
    # json 파일 불러옴
    with open('./rec_movie/data/movies.json', 'r') as f:
        data = json.loads(f.read())

    # dataFrame 변환
    df = pd.json_normalize(data)

    for idx, row in df.iterrows():
        # release_date와 poster_path가 없는 영화가 1개 있다.
        if row['fields.release_date'] == '' or row['fields.poster_path'] is None:
            continue
        # Movie table에 저장
        Movie.objects.create(movie_id=row['pk'], original_title=row['fields.original_title'],
                             overview=row['fields.overview'], release_date=row['fields.release_date'],
                             poster_path=row['fields.poster_path'])

    return HttpResponse('Success convert json to database')

```