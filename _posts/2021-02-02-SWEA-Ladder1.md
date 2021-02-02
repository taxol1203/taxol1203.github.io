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

[<img src="/images/swea.png" width="80%" height="80%">](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV14ABYKADACFAYh)  

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


### 개선할 점
