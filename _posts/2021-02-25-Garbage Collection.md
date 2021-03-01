---
title: "Garbage Collection"
last_modified_at: 2021-02-25 T23:44:00-05:00
toc: true
toc_sticky: true
header:
  teaser: /images/effective_Java.png
categories: 
  - Technology
tags:
  - Java
---

> Garbage Collection

자바 CS 면접을 대비한 미니 세미나를 준비하는 겸, 나중에 복습 할때 참고하여 공부하기 위해 따로 정리하였다.  
이 글 내용은 좋은 글을 써주신 분들의 블로그 내용들을 취합한 것이므로 먼저 출처를 올리겠습니다.  

## 출처
[Java Garbage Collection](https://d2.naver.com/helloworld/1329)  
[[cs] java 면접 질문(기술면접 대비)](https://sujinhope.github.io/2019/12/09/CS-Java-%EB%A9%B4%EC%A0%91-%EC%A7%88%EB%AC%B8(%EA%B8%B0%EC%88%A0%EB%A9%B4%EC%A0%91-%EB%8C%80%EB%B9%84).html)  
[Java - Garbage Collection(GC,가비지 컬렉션) 란?](https://coding-start.tistory.com/206)  
[자바 메모리 관리 - 가비지 컬렉션](https://yaboong.github.io/java/2018/06/09/java-garbage-collection/)  
[Java 의 GC는 어떻게 동작하나?](https://mirinae312.github.io/develop/2018/06/04/jvm_gc.html)  
[[JVM] Garbage Collection Algorithms](https://medium.com/@joongwon/jvm-garbage-collection-algorithms-3869b7b0aa6f)  

## Garbage Collection 이란?
Garbage Collection 통칭 GC란 이미 할당된 메모리에서 더 이상 사용하지 않는 메모리를 해제하는 행동을 의미합니다.  
GC는 Java의 JVM에 의해 자동으로 실행되어, 더 이상 사용되지 않은 객체들을 점검하여 제거하는 아주 편리한 기능입니다.  
따라서 Java 프로그래머들에게는 C 혹은 C++처럼 메모리를 직접 관리해야 하는 언어와 비교하여, 부담이 줄어들게 되었습니다.  

## Garbage Collection의 대상
GC는 무엇을 메모리 해제를 할까요, GC는 Heap영역의 더 이상 쓰지 않는 new 연산자 등으로 생성된 객체(인스턴스)와 배열들의 자원을 회수합니다.  
```java
for (int i = 0; i < 10000; i++) {
  NewObject obj = new NewObject();  
  obj.doSomething();
}
```
위의 예에서 NewObject 객체는 Loop 내에서 생성되고, 사용되지만 Loop 밖에서는 더이상 사용할 일이 없어집니다.  
이런 객체들이 메모리를 계속 점유하고 있다면, 다른 코드를 실행하기 위한 메모리 자원은 지속적으로 줄어들기 때문에 GC는 위 자원들을 회수하여 한정된 메모리를 효율적으로 사용할 수 있게 해줍니다.  

일반 적으로 다음과 같은 경우가 GC의 대상이 됩니다.  
1. 모든 객체 참조가 null 인 경우  
2. 객체가 블럭 안에서 생성되고 블럭이 종료된 경우  
3. 부모 객체가 null이 된 경우, 자식 객체는 자동적으로 GC 대상이 된다.  
4. 객체가 Weak 참조만 가지고 있을 경우  
5. 객체가 Soft 참조이지만 메모리 부족이 발생한 경우  

### Unreachable Objects
Garbage Collection은 사용되지 않는 Object, 즉 더 이상 참조되지 않는 Object를 모으는 작업입니다.  
위에서 말한 GC의 대상들이겠죠.  
이 Object의 사용 여부는 어떻게 알까요 이는 Root Set과의 관계로 판단하게 되는데, 
[Root Set](https://medium.com/@joongwon/jvm-garbage-collection-algorithms-3869b7b0aa6f)에서 어떤 식으로든 참조 관계가 있다면 Reachable Object라고 하며 이를 현재 사용하고 있는 Object로 간주하며 그 밖의 Object는 Unreachable Object가 됩니다.  
따라서 GC는 이 Unreachable Objects들을 모으는 것이 되겠습니다.  

<img src="/images/Tech/GC/Unreachable Objects.png" width="50%" height="50%">  

그렇다면 Root Set이란 뭘까요? Root Set을 간단하게 설명하면 객체들 간에 참조 사슬의 시작점이라고 보면 됩니다.  

## stop-the-world
우선 GC는 Thread 입니다. GC가 수행 되기 위해선 현재 실행되고 있는 GC의 Thread를 제외한 나머지 쓰레드는 모두 작업을 멈춰야 합니다.  
이를 **stop-the-world**라고 합니다.  
어떤 GC 알고리즘을 사용하더라도 stop-the-world는 발생하게 되며, 앞으로 나올 GC 튜닝도 이 stop-the-world 시간을 줄이는 것입니다.  

## Weak generational hypothesis
Java에서는 개발자가 프로그램 코드로 메모리를 명시적으로 해제하지 않기 때문에 GC가 더 이상 필요 없는 (쓰레기) 객체를 찾아 지우는 작업을 합니다.  
Java의 GC는 두가지 가설 하에 만들어 졌습니다. 첫번째로는,  
```  
* 대부분의 객체는 금방 접근 불가능 상태(unreachable, garbage)가 된다.  
* 오래된 객체에서 젊은 객체로의 참조는 아주 적게 존재한다.  
```
첫번째 가설은 새로 생성된 객체는 금방 쓰임새가 없어져 Garbage가 되기 쉽다는 뜻입니다.  
두번째 가설은 Old 객체가 Young 객체를 참조할 일은 드물다라는 것인데, old 객체와 young 객체는 다음에서 설명 하겠습니다.  

## Young 영역과 Old 영역
위 가설에서는 `오래된 객체`라는 말이 나오는데, JVM에서는 이 오래됨(Old) 을 표현하기 위해 메모리를 크게 2개로 물리적 공간을 나누었습니다.  
이 둘로 나눈 공간이 Young 영역과 Old 영역입니다.  
<img src="/images/Tech/GC/Young&Old.png" width="50%" height="50%">  

### Young 영역
Young 영역은 새롭게 생성한 객체의 대부분이 여기에 위치하는 장소입니다.  
이 영역에 위치하는 객체들은 대부분 Unreachable Objects가 되며, 여기서 객체가 사라질때 Minor GC가 발생한다고 말합니다.  

Young 영역은 총 3개의 영역으로 나누어집니다.

* Eden 영역  
Eden Area은 객체 할당만을 위한 전용 공간입니다.
* Survivor 영역(2개)  
Survivor Area는 Minor GC 당시 살아남은 객체를 옮기는 공간입니다.  
Survivor 영역은 중 하나는 반드시 비어 있는 상태로 남아 있어야하며 둘의 영역은 독립적입니다.  

새롭게 생성 된 객체는 Young 영역에서 위치하며, 각 영역의 처리 절차를 순서를 따라 기술하면 다음과 같습니다.  
1. 새로 생성한 객체는 Eden 영역에 위치합니다.  
2. Eden 영역에서 GC가 발생한 후 살아남은 객체는 Survivor 영역 중 하나로 이동한다.  
3. Eden 영역에서 GC가 한번 더 발생하면 이미 살아남은 객체가 존재하는 Survivor 영역으로 객체가 계속 쌓인다.  
4. 하나의 Survivor 영역이 가득 차게 되면, 그 중에서 살아남은 객체를 비어있는 다른 Survivor 영역으로 이동한다. 이전의 Survivor 영역은 비운다.
5. 이 과정을 반복하다가 일정 GC 이상 횟수 만큼 살아남은 객체는 Old 영역으로 이동한다.  

<img src="/images/Tech/GC/JVMObjectLifecycle.png" width="70%" height="70%">  

*JVM 메모리 영역(Heap)내에서 객체의 이동*  
다음은 위 5개의 순서를 그림으로 나타낸 것입니다.  

### Old 영역
객체가 Unreachable Objects로 되지 않아, Young 영역에서 살아남은 객체가 여기로 복사됩니다.  
Young 영역보다 메모리는 크게 할당되며 GC가 적게 발생됩니다.  
Young 보다는 빈도수가 적지만, 데이터가 가득 차면 GC는 발생하게 되며, 이 영역에서 객체가 사라질 때 Major GC(혹은 Full GC)가 발생한다고 말합니다.  

## Card Table
Minor GC를 진행 할 때, Old 영역에 있는 객체가 Young 영역의 객체를 참조하는 경우 해당 객체는 GC의 대상이 되질 않습니다.  
따라서 매번 Old 영역에서 Young 영역으로의 참조를 확인해야 하는데, 이는 비효율적이므로 Old 영역에는 512바이트의 덩어리(chunk)로 되어 있는 카드 테이블(card table)이 존재합니다.  

카드 테이블에는 Old 영역에 있는 객체가 Young 영역의 객체를 참조할 때마다 정보가 표시됩니다.  
Young 영역의 GC를 실행할 때에는 Old 영역에 있는 모든 객체의 참조를 확인하지 않고, 이 카드 테이블만 뒤져서 GC 대상인지 식별합니다.

<img src="/images/Tech/GC/CardTable.png" width="50%" height="50%">  

## Permanent Generation
Permanent Generation 영역(이하 Perm 영역)은 Method Area라고도 합니다.  
객체나 억류(intern)된 문자열 정보를 저장하는 곳이며, 이 영역에서도 GC가 발생합니다.  
여기서 발생한 GC도 Major GC 횟수에 포함됩니다.  

## 정리
GC에 대해서 알아보았습니다.  
간단하게 정리 한다고 해도 내용이 길어졌는데 핵심은  
**Eden 영역에 최초로 객체가 만들어지고, Survivor 영역을 통해서 Old 영역으로 오래 살아남은 객체가 이동**을 기억하면 되겠습니다.  
감사합니다.  