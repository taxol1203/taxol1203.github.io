---
title: "백준 13023 - ABCDE"
last_modified_at: 2022-03-26 T11:16:00-05:00
toc: true
toc_sticky: true
header:
  teaser: /images/baekjoon.png
categories:
  - codingTest
tags:
  - Baek Joon
  - DFS
---

> Java

# 13023 번 - ABCDE

## 문제

[<img src="/images/baekjoon.png" width="40%" height="40%">](https://www.acmicpc.net/problem/13023)

## 접근 방법

문제를 이해하기 어려웠다.  
문제의 조건을 읽으면 다음과 같은데,

```
A는 B와 친구다.
B는 C와 친구다.
C는 D와 친구다.
D는 E와 친구다.
```

이말은 즉슨, A -> B -> C -> D -> E 까지 차례로 5명이 관계를 가지는 경우가 있는지를 확인하면 된다.

즉, 그래프의 관점으로 보았을 때, 첫번재 노드부터 깊이가 5까지 내려갈 수 있는지 판단하면 된다.  
이것은 DFS를 통해 해결하였다.

주의해야할 점은 사람의 수 N이 (5 ≤ N ≤ 2000)으로 꽤 크다.  
DFS를 통해 타고 내려가면 2000^3까지 나올 수 있으므로 시간초과가 유발된다.

친구 관계의 수 M은 최대 2000까지 이므로, 친구의 관계를 나타낼 때 2차원 int 배열이 아닌,  
ArrayList를 사용한다.

이를 사용하면, 현재 자신과 이어진 친구를 탐색할 때 최대 n명을 탐색하는 것이 아닌 것보다 훨씬 적은 수의 사람만 탐색하면 된다.

```java
for(int nextN : friend.get(curN)) { // curN과 친구 관계를 가진 사람만 가져옴
  ...
}
```

```java
for(int nextN = 0; nextN < N; nextN++) { // 이의 경우 N이 2000이면 시간초과가 발생한다.
  ...
}
```

## 코드

```java
import java.util.*;
import java.io.*;

public class Main {
	static int n, m, result;
	static ArrayList<ArrayList<Integer>> friend;
	static boolean[] vis;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stk = new StringTokenizer(br.readLine());
    	n = stoi(stk.nextToken());	// 사람의 수
    	m = stoi(stk.nextToken());	// 관계의 수

    	int a,b;
    	friend = new ArrayList<>();
    	for(int i = 0; i < n; i++) {
    		friend.add(new ArrayList<>());
    	}

    	for(int i = 0; i < m; i++) {
    		stk = new StringTokenizer(br.readLine());
    		a = stoi(stk.nextToken());
    		b = stoi(stk.nextToken());

    		friend.get(a).add(b);
    		friend.get(b).add(a);
    	}

    	for(int i = 0; i < n; i++) {
    		vis = new boolean[n];
    		if(DFS(i,0)) {
    			System.out.println(1);
    			return;
    		}
    	}

    	System.out.println(0);
    	br.close();
	}

	private static boolean DFS(int curN, int depth) {
		if(depth == 4) {
			return true;
		}
		vis[curN] = true;

		for(int nextN : friend.get(curN)) {
			if(!vis[nextN]) {
				if(DFS(nextN, depth + 1))
					return true;
				vis[nextN] = false;
			}
		}

		return false;
	}

	static int stoi(String str) {
    	return Integer.parseInt(str);
    }
}
```

## 총평

### 후기

### 개선할 점
