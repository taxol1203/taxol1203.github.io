---
title: "SWEA 1210 - Flatten"
last_modified_at: 2021-02-02 T16:12:00-05:00
toc: true
toc_sticky: true
header:
  teaser: /images/swea.png
categories: 
  - codingTest
tags:
  - Sort
  - SWEA
---

> Java

1210 번 - Flatten
=============
 
## 문제
100 x 100 크기의 2차원 배열로 주어진 사다리에 대해서, 지정된 도착점에 대응되는 출발점 X를 반환하는 코드를 작성하라

[![문제 출처]<img src="/images/swea.png" width="40%" height="40%">](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV14ABYKADACFAYh)  

## 접근 방법

## 구현 

## 코드
```java
import java.io.*;
import java.util.*;

class Solution {
	public static void main(String []args) throws Exception {  
		//System.setIn(new FileInputStream("res/input.txt"));	//제출 할 때 주석해야함
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	int result = 0;
    	StringTokenizer st;
    	//int tc = stoi(br.readLine());
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
    		visited = new boolean[100][100];
    		
    		int y = 99, x = 0;
    		for(int i = 0; i < 100; i++)
    			if(ladder[y][i] == 2) {
    				x = i;
    				break;
    			}
    		while(y != 0) {
    			if(x - 1 != -1 && ladder[y][x - 1] == 1 && !visited[y][x - 1]) {
    				x--;
    				visited[y][x] = true;
    				continue;
    			}
    			else if(x + 1 != 100 && ladder[y][x + 1] == 1 && !visited[y][x + 1]) {
    				x++;
    				visited[y][x] = true;
    				continue;
    			}
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
단순히 정렬과 탐색으로 풀 수 있는 문제를, 평균을 이용하는 등 머리를 굴리려다 오히려 고생한 문제.  

### 개선할 점
1. 처음 정렬을 하고 
2. 양 끝 값을 1씩 증감을 시킨 후
3. 다시 정렬을 하여 반복하는 간단한 방법도 있다.  
하지만 위는 매번 정렬을 해야하므로 나의 코드보다 더 시간이 오래 걸리니 정신승리를 한다.  