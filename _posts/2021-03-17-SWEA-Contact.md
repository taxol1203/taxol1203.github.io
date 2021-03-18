---
title: "SWEA 1238 - Contact "
last_modified_at: 2021-03-17 T15:16:00-05:00
toc: true
toc_sticky: true
header:
  teaser: /images/swea.png
categories: 
  - codingTest
tags:
  - BFS
  - SWEA
---

> Java

1238 번 - Contact
=============
 
## 문제
방향이 있는 그래프가 주어진다.  
주어진 시작 점으로 부터 시작ㅎ아ㅕ 가능한 모든 점을 탐색한다.  
각 시점마다 이동을 하여, 더 이상 갈 수 없을때 마지막 시점의 점들 중에서 가장 큰 수를 구한다.  

[<img src="/images/swea.png" width="60%" height="60%">](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV4suNtaXFEDFAUf)  

## 접근 방법
BFS로 해결하였다.  
한 시점에 갈 수 있는 모든 점을 별도의 List에 저장하고, 시점이 바뀔때 마다 초기화 시켜준다.  
마지막 queue의 값이 없다면, 더 이상 갈 수 있는 점이 없으므로 이전 마지막 시점에 방문했던 점들 중에서 가장 큰 수를 구하였다.  

## 코드
```java
import java.io.*;
import java.util.*;

class Solution {
	static int result = 0, n, start, size;
	static boolean[][] arr;
	static boolean[] vis;
	public static void main(String []args) throws Exception {  
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer stk;
    	int tc = 10;
    	
    	for(int tidx = 1; tidx <= tc; tidx++) {
    		stk = new StringTokenizer(br.readLine());
    		n = stoi(stk.nextToken());
    		start = stoi(stk.nextToken());
    		size = 0;
    		
    		int from, to;
    		stk = new StringTokenizer(br.readLine());
    		arr = new boolean[101][101];
    		vis = new boolean[101];
			// 간선을 표현한다.
    		for(int i = 0; i < n / 2; i++) {
    			from = stoi(stk.nextToken());
    			to = stoi(stk.nextToken());
    			arr[from][to] = true;
    			size = Math.max(size, from);	// 정점의 최대 크기
    			size = Math.max(size, to);
    		}
    		
    		result = bfs();
    		
    		System.out.println("#" + tidx + " " + result);
    	}
    	
	}
	
	static int bfs() {
		Queue<Integer> q = new LinkedList<>();
		List<Integer> lastN = new ArrayList<>();	// 마지막 시점에 방문했던 점
		q.add(start);
		int curN;
		int cnt = 1;
		int newLoop = cnt;
		while(!q.isEmpty()) {
			lastN = new ArrayList<>();
			cnt = 0;
			// 시점
			while(newLoop-- != 0) {
				curN = q.poll();
				lastN.add(curN);
				
				for(int i = 1; i <= size; i++) {
					if(arr[curN][i] && !vis[i]) {
						cnt++;
						q.add(i);
						vis[i] = true;
					}
				}
			}
			
			newLoop = cnt;
		}
		
		Collections.sort(lastN);
		return lastN.get(lastN.size() - 1);
	}
	
	static int stoi(String str) {
    	return Integer.parseInt(str);
    }
}
```


## 총평
### 난이도
⭐⭐★★★
### 후기
최악의 컨디션에서 어찌어찌 버텼다.
### 개선할 점
