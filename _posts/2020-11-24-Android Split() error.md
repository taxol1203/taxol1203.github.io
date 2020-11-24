---
title: "안드로이드 - 자바 split() 메소드 점 ( . ) 인식 불가."
date: 2020-11-24 17:45:28 -0400
categories: 
  - Technology
tags:
  - Java
  - Android
---

안드로이드 - 자바 split() 메소드 점 ( . ) 인식 불가.
===========

안드로이드를 개발 도중 이메일 string을 분할 해야하는 상황이 생겼습니다.  
하지만 aaa@google.com 중 google과 com을 **.**으로 분할 하려니 오류가 발생하였습니다.  

## 오류

``` java
String willSplit = "hello.hi";
String[] a = willSplit.split(".");
String front = a[0];              //오류 발생!!
String end = a[1];
Log.e("Check splited", "front: " + front);
Log.e("Check splited", "end: " + end);
```

`java.lang.ArrayIndexOutOfBoundsException: length=0; index=0` 와 같은 오류가 발생하였습니다.

## 이유 

[참고 사이트](https://stackoverflow.com/questions/7935858/the-split-method-in-java-does-not-work-on-a-dot)에 따르면,  
**.**은 split에서 인식이 안된다는 해답을 얻을 수 있습니다.

## 해결 방안 

split(".") 대신에 split("\\.")으로 변경하여 자바가 **.**을 인식하게 해줍니다.

``` java
String willSplit = "hello.hi";
String[] a = willSplit.split("\\.");
String front = a[0];              //오류 발생!!
String end = a[1];
Log.e("Check splited", "front: " + front);
Log.e("Check splited", "end: " + end);

```