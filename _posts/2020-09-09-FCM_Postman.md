---
title: "FCM과 PostMan으로 쉽게 Push알람 보내기"
date: 2020-09-09 17:45:28 -0400
categories: 
  - Technology
tags:
  - FCM
  - PostMan
---

FCM Push알람을 PostMan을 통하여 쉽게 보내기
===========

## Android 앱 FCM 설정

안녕하세요. 이 포스트는 FireBase가입, 프로젝트 생성, Android 앱에 Firebase 추가가 완료된 상태라고 전제를 둔 상황에서 작성한 게시글입니다.
앞서말한 자세한 방법은 [플랫폼 개발팀 기술 블로그]("https://team-platform.tistory.com/17")에서 #2 ~ #3 단계에서 정말 자세히 나와있습니다. 참고하셔서 작성하시고,

<img src="/images/FCM_PostMan/photo1.PNG">

 의 테스트가 완료 된 이후에 제 포스트를 봐주세요!

## 목표

저의 목표는 FCM 서버에서 클라이언트 기기로 메세지를 송신하는 것 까지는 성공하였지만 더이상 진전이 없어, subscribe를 통하여 여러 디바이스에 각각 푸시알람을 보내는 것입니다.

## PostMan 설정

먼저 PostMan을 받으셔야겠죠? [PostMan]("https://www.postman.com/downloads/")에서 받아줍시당!

이후 로그인까지 완료하면, 왼쪽 상단의 NEW! 를 클릭

<img src="/images/FCM_PostMan/photo2.png">

Requset를 눌러줍시다.

<img src="/images/FCM_PostMan/photo3.png">

원하시는 이름과 그룹을 설정해 주시고

<img src="/images/FCM_PostMan/photo4.png">

이제 이 화면에 도달합니다!

<img src="/images/FCM_PostMan/photo5.png">

먼저 이 PostMan 서버가 HTTP 요청을 하려면 FCM 서버로 연결해야겠죠? 따라서
```
https://fcm.googleapis.com/fcm/send
```
을 넣어주시고 FCM 서버에 보내기 위해 POST로 바꾸어 줍니다.
이후 Header를 설정해줘야하는데.. 
Authorization에 Firebase 서버 key를 등록해주시고,
Content-Type에 ```application/json```을 넣어주세요!

Firebase 서버 key는 
`Firebase 콘솔 -> 톱니바퀴 -> 프로젝트 설정 -> 클라우딩 메세지에 있습니당

<img src="/images/FCM_PostMan/photo7.png">

이후 앞선 과정을 완료하면 이렇게 됩니다

<img src="/images/FCM_PostMan/photo6.png">

## 주제 구독 설정

이제 각 앱 마다 주제를 구독을 설정해 주는 작업을 합시다.
간단히 아래와 같이 MainActivity에 작성해주면, 이 앱은 weather를 구독하게 됩니다.

```java
FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Topic weather is subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Topic weather subscribing failed";
                        }
                        Log.d("FCMTest", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
```

간단히

```java
FirebaseMessaging.getInstance().subscribeToTopic("weather");
```

처럼 해주셔도 됩니다.

### PostMan으로 전송

이제 PostMan 서버에서 (앱서버) FCM서버로 메세지를 보내봅시다!.

Body에 들어가 Raw 그리고 Json으로 설정한 후 

```json
{
  "to": "/topics/weather",
  "notification": {
    "title": "Breaking News",
    "body": "New news story available."
  }
}
```

다음과 같이 작성하시면 됩니다!
```json
"to": "/topics/weather"
``` 
라는 것은 weather라고 구독한 앱에 메세지를 전송하겠다는 것이며,
```json
"notification": {
    "title": "Breaking News",
    "body": "New news story available."
  }
  ```
이것은 푸시 메세지를 설정해 줍니다. title과 body를 써주는 것이죠.

이제 `send`를 해봅시다. 될까요?
아 이전에 애플리케이션을 빌드하고 설치 해주셔야하는거 잊지마세요!

<img src="/images/FCM_PostMan/photo8.png">

<img src="/images/FCM_PostMan/photo10.png">

넵! 제대로 왔네요!!

이를 통해 PostMan으로 Weather가 구독된 애플리케이션을 FCM 서버에 메세지를 전송하라고 보내, 실제로 수신했다는걸 보았습니다.

만약 `weather`와 `sunny` 모두 동시에 구독 된 앱만 메세지를 보내고 싶으면 어떻게 할까요?

방법은 
```json
{
  "condition": "'weather' in topics && 'sunny' in topics",
  "notification": {
    "title": "Breaking News",
    "body": "New news story available."
  }
}
```
입니다.

"to" 매개변수는 하나의 구독만 보냈지만
"condition" 매개변수를 통해 논리식을 지정 할 수 있어요!

이외에도 다양한 매개변수를 설정하거나, 밑의 결과 출력이 의미하는 것은 
[Firebase공식문서](https://firebase.google.com/docs/cloud-messaging/http-server-ref?authuser=0)에 자세히 나와있습니다!

## 결론

이상으로 PostMan으로 FCM을 이용해 보았습니다.
많이 부족하지만 읽어주셔서 감사드립니다.