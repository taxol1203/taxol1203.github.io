---
title: "SWEA 1210 - Ladder1"
last_modified_at: 2021-02-02 T16:12:00-05:00
toc: true
toc_sticky: true
header:
  teaser: /images/swea.png
categories: 
  - codingTest
tags:
  - SWEA
---

> Java

1210 번 - Ladder1
=============
 
## 문제
100 x 100 크기의 2차원 배열로 주어진 사다리에 대해서, 지정된 도착점에 대응되는 출발점 X를 반환하는 코드를 작성하라

[문제 출처](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV14ABYKADACFAYh)  
[<img src="/images/swea.png" width="60%" height="60%">](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV14ABYKADACFAYh)  

## 접근 방법

## 구현 

## 코드
```java
import java.io.*;
import java.util.*;

class Solution {
	public static void main(String []args) throws Exception {  
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	int result = 0;
    	StringTokenizer st;
    	// ------------ 초기값 세팅 -------------- 
    	
    	int ladder[][] = new int[100][100];
    	boolean visited[][] = new boolean[100][100]; 
    	
    	for(int idx = 1; idx <= 10; idx++) {
    		int tc = stoi(br.readLine());
    		
    		for(int i = 0; i < 100; i++) {
    			st = new StringTokenizer(br.readLine());
    			for(int j = 0; j < 100; j++) {
    				ladder[i][j] = stoi(st.nextToken());
    			}
    		}
    		// 한번 확인 
    		visited = new boolean[100][100];
    		
    		// 도착 지점 검색
    		int y = 99, x = 0;		
    		for(int i = 0; i < 100; i++)
    			if(ladder[y][i] == 2) {
    				x = i;
    				break;
    			}
    		// 시작점 거꾸로 올라가기 시작
    		while(y != 0) {			// 도착 지점으로 가면 종료
    			// 왼쪽으로 갈 수 있는 길이 있으며, 처음 방문한 사다리면 이동한다.
    			if(x - 1 != -1 && ladder[y][x - 1] == 1 && !visited[y][x - 1]) {	
    				x--;
    				visited[y][x] = true;
    				continue;
    			}
    			// 오른쪽으로 갈 수 있는 길이 있으며, 처음 방문한 사다리면 이동한다.
    			else if(x + 1 != 100 && ladder[y][x + 1] == 1 && !visited[y][x + 1]) {
    				x++;
    				visited[y][x] = true;
    				continue;
    			}
    			// 오른쪽, 위쪽으로 갈 길이 없으면 위로 이동
    			else {
    				y--;
    				visited[y][x] = true;
    			}
    		}
    		result = x;
    		
    		System.out.println("#" + idx + " " + result);	    //출력
    	}
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


### 개선할 점