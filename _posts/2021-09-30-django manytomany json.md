---
title: "Djagno ManyToMany 관계의 model을 직렬화 하여 하나의 Json으로 응답하기"
date: 2021-09-30 17:45:28 -0400
categories: 
  - Technology
tags:
  - Django
---

> python

Djagno ManyToMany 관계의 model을 직렬화 하여 하나의 Json으로 응답
===========

## 서론
ManyToMany 관계로 이어진 두 Genre와 Movie 모델이 있습니다.

### model.py
```python
class Movie(models.Model):
    movie_id = models.CharField(max_length=10, default='', unique=True)
    original_title = models.CharField(max_length=100)
    overview = models.TextField()
    release_date = models.DateField()
    poster_path = models.CharField(max_length=40)    # https://image.tmdb.org/t/p/original/[poster_path]
    genres = models.ManyToManyField('Genre')    # many to many로 genre와 연결하였다. related_name은 자동으로 'genres'


class Genre(models.Model):
    genre_id = models.IntegerField()
    genre_name = models.CharField(max_length=20)
```

만약 Movie Data를 Get 할 때, Genre Data와 함께 직렬화하여 Json으로 응답하고자 합니다.

## serializers.py

ManyToMany 관계의 데이터 모델은, 직렬화 하는 과정에서 합칠 수 있습니다.

```python
class GenreSerializer(serializers.ModelSerializer):
    class Meta:
        model = Genre
        fields = '__all__'


class MovieSerializer(serializers.ModelSerializer):
    genres = GenreSerializer(read_only=True, many=True) # 장르 데이터도 직렬화, many=True는 여러 genre가 들어온다는 뜻.

    class Meta:
        model = Movie
        fields = '__all__'
        read_only_fields = ('genres',)      # 장르 데이터도 함께 json으로 변환하여 제공한다.
```

## views.py

마지막으로 movie data를 반환하면 movie json 내부에 연결된 genre 데이터도 함께 응답합니다.

```python
@api_view(['GET'])
def get_movie(request, pk):
    movie = get_object_or_404(Movie, id=pk)
    serializer = MovieSerializer(movie)
    return Response(serializer.data)
```

## 결과

```json
{
  "id": 69848,
  "movie_id": "140",
  "original_title": "La mala educación",
  "overview": "28살의 감독 ‘엔리케’ 앞에 어느 날 어린 시절 신학교 친구였던 ‘이나시오’가 배우가 되어 나타난다. 재회한 기쁨도 잠시, 이제부터 자신을 앙겔(천사)이라고 불러 달라는 이나시오가 낯설게만 느껴지는 엔리케. 이나시오는 자신들의 어린 시절과 당시 그들에게 ‘나쁜 교육’을 행한 마놀로 신부를 향한 증오와 복수, 음모와 살인에 관해 쓴 ‘방문객’이란 시나리오를 엔리케에게 건네는데…",
  "release_date": "2004-03-19",
  "poster_path": "/gUsYU7sw32Vs3wSxlJZWUesj1FK.jpg",
  "genres": [
    {
      "id": 43,
      "genre_id": 80,
      "genre_name": "Crime"
    },
    {
      "id": 45,
      "genre_id": 18,
      "genre_name": "Drama"
    },
    {
      "id": 51,
      "genre_id": 9648,
      "genre_name": "Mystery"
    },
    {
      "id": 52,
      "genre_id": 10749,
      "genre_name": "Romance"
    },
    {
      "id": 55,
      "genre_id": 53,
      "genre_name": "Thriller"
    }
  ],
}
```


## References
[StackOverflow](https://darrengwon.tistory.com/480)  