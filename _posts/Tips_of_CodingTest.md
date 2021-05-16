---
title: "코딩 테스트 정리"
last_modified_at: 2021-05-16 T15:30:00-05:00
toc: true
toc_sticky: true
header:
  teaser: /images/baekjoon.png
categories:
  - CodingTest
tags:
---

> Java

## String

## 순열과 조합

https://coding-factory.tistory.com/606
[조합 계산기](https://ko.numberempire.com/combinatorialcalculator.php)

### 순열

순열이란 서로 다른 n개중에 r개를 선택하는 경우의 수를 의미 (순서 상관 있음)  
nPr = n! / (n - r)!

대략 13Pr 정도까지 순열을 쓸 수 있는 최대인 듯.

```java
void DFS(int lv){
    if(lv == n){
        return;
    }
    for(int i = 0; i < n; i++){
        if(!vis[i]){                // 만약 vis 체크를 안하면 중복 순열
            vis[i] = true;
            sel[lv] = i;
            DFS(lv + 1);
            vis[i] = false;
        }
    }
}
```

### 조합

조합이란 서로 다른 n개중에 r개를 선택하는 경우의 수를 의미 (순서 상관 없음)
n! / (n - r)! x r!

대략 29C14 정도가 7천만

```java
void DFS(int lv, int start){
    if(lv == m){
        return;
    }
    for(int i = start; i < n; i++){
        sel[lv] = i;
        DFS(lv + 1, i + 1);         // 만약 DFS(lv + 1, i) 이면 중복 조합이다.
    }
}
```

### n개 뽑았다 안 뽑았다 하는 것

모든 경우를 한번씩 보는 것
2^n

```java
void DFS(int lv) {
    if(lv == n) {
        for(int i = 0; i < n; i++) {
            if(sel[i])
                System.out.print(i);
        }
        System.out.println();
        return;
    }

    sel[lv] = true;
    DFS(lv + 1);

    sel[lv] = false;
    DFS(lv + 1);
}
```
