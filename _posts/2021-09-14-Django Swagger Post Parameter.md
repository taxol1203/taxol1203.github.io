---
title: "Django Swagger Post Parameter 없음"
date: 2021-09-14 17:45:28 -0400
categories: 
  - Technology
tags:
  - Django
---

> python

Django Swagger Post Parameter 없는 현상 해결법
===========

## 문제
Swagger의 Post에 Parameters를 넣는 곳이 없다.  

<img src="/images/Tech/Django/post_1.png" width="100%" height="100%">  

현재 코드는 다음과 같다.  

```python
class AlbumView(APIView):

    def get(self, request, format=None):
        albums = Album.objects.all()
        serializer = AlbumSerializer(albums, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = AlbumSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
```

찾아보니,  GenericApiView를 사용하면 model과 Swagger의 post parameter 연동이 가능하다고 되어있다.  

[출처 : StackOverflow](https://stackoverflow.com/questions/41104615/how-can-i-specify-the-parameter-for-post-requests-while-using-apiview-with-djang)

따라서 GenericApiView를 적용하였다.  

```python
class AlbumView(GenericAPIView):    # GenericAPIView로 변경

    queryset = Album.objects.all()      # Generic Api View는 반드시 포함 해야한다.
    serializer_class = AlbumSerializer  # 추가한다

    def get(self, request, format=None):
        albums = Album.objects.all()
        serializer = AlbumSerializer(albums, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = AlbumSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
```

<img src="/images/Tech/Django/post_2.png" width="100%" height="100%">  

성공