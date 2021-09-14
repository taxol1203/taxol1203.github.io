---
title: "Django Swagger Post Parameter 없음"
date: 2021-09-14 17:45:28 -0400
categories: 
  - Technology
tags:
---

> python

Django Swagger Post Parameter 없는 현상 해결법
===========

## 문제
Swagger의 Post에 Parameters를 넣는 곳이 없다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/12da4544-f196-4937-8f43-7b89b399b390/Untitled.png)

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

찾아보니,  GenericApiView를 사용하면 model과 swagger post parameter 연동이 가능하다고 되어있다.  

[출처](https://stackoverflow.com/questions/41104615/how-can-i-specify-the-parameter-for-post-requests-while-using-apiview-with-djang)

따라서 GenericApiView를 적용하였다.

```python
class AlbumView(GenericAPIView):

    queryset = Album.objects.all()  # Generic Api View는 반드시 포함 해야함
    serializer_class = AlbumSerializer

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

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ab610ab7-0359-446d-a0c5-728b6dfc9917/Untitled.png)

성공

## 결론

이리저리 해결책을 찾아보다가 지인의 도움으로 해결하였다.  

혹시 나와 같은 가상환경에는 모듈이 설치되었는데, import를 할 수 없는 경우가 생긴다면 이를 참고하였으면 한다.  