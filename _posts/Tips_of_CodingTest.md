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

### 문자열 분리 split

```java
String[] str = br.readLine().split("\\."); // .으로 분리
```

.은 메타데이터 이므로 \\를 붙혀야 분리가 된다.

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

## Map

기본 사용법 - String key에 따라 개수 세기

```java
Map<String, Integer> map = new HashMap<>();

if(map.containsKey(extend))
    map.put(extend, map.get(extend) + 1);
else
    map.put(extend, 1);
```

Key 사전 순으로 정렬

```java
List<Entry<String, Integer>> entry = new ArrayList<>(map.entrySet());
Collections.sort(entry, (o1, o2)->{
    return o1.getKey().compareTo(o2.getKey());
});
```

## 자료형 변환

### int to char

```java
char value_char = (char)(value_int +'0');
```

### int to String

```java
int from = 123;
String to = Integer.toString(from);
```

### String to int

```java
String from = "123";
int to = Integer.parseInt(from);
```

## GCD, LCM

```java
int max = Math.max(n, m);
int min = Math.min(n, m);
int gcd = GCD(max,min);
int lcm = (n * m) / gcd;

static int GCD(int max, int min) {
    if(min == 0)
        return max;
    else
        return GCD(min, max % min);
}
```

## Priority Queue

예시 코드는, 두 수의 절대 값이 작은 순으로 정렬한다.  
만약 같으면 그 수가 작은 순으로 정렬

```java
PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2)->{
    int a = Math.abs(o1);
    int b = Math.abs(o2);
    int comp = Integer.compare(a, b);
    if(comp == 0)
        return Integer.compare(o1, o2);
    else
        return comp;
});
```

## 공부 법

알고리즘 공부법

1. 한 알고리즘을 공부할때는 기본을 이해하고 응용으로 바로 가지말고 난이도를 천천히 늘려가며 문제를 많이 풀어봐야한다

2. 설계와 구현을 동시에 하지 말고, 설계를 종이에 다 한 뒤 구현을 해야한다.

3. 매일 2문제식 풀자

- 난이도가 쉽던, 어렵던 매일 2문제씩

4. 문제를 풀다 막히면?

- 접근 조차 못하겠다 싶으면, 풀이를 보지 말고 더 쉬운 문제를 풀자
- 길어도 1~2시간 내에는 고민이 끝나야한다.(사실 집중하고 30분만 고민하고 안풀리면 넘어가야함)

5. 기업 별 평균 코테 종류

- Dijkstra
- Sliding Window / Two point
- Greedy / Heap
- DP 기초
- DFS / BFS 중급
- Union-Find / MST 기초
