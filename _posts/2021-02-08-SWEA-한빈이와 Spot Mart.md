---
title: "SWEA 9229 - 한빈이와 Spot Mart"
last_modified_at: 2021-02-08 T17:30:00-05:00
toc: true
toc_sticky: true
header:
  teaser: /images/swea.png
categories: 
  - codingTest
tags:
  - DFS
  - SWEA
---

> Java

9229 번 - 한빈이와 Spot Mart
=============
 
## 문제
한빈이는 퇴근길에 스팟마트에 들러 과자 두 봉지를 사서 양 손에 하나씩 들고 가려고 한다.  
스팟마트에는 N개의 과자 봉지가 있으며, 각 과자 봉지는 ai그램의 무게를 가진다.  
배가 많이 고픈 한빈이는 최대한 양이 많은 (무게가 많이 나가는) 과자 봉지를 고르고 싶으나,  
과자 두 봉지의 무게가 M 그램을 초과하면 무거워서 과자를 들고 다닐 수 없다.  
한빈이가 들고 다닐수 있는 과자들의 최대 무게 합을 출력하라. 한빈이는 과자를 “정확히” 두 봉지 사야 함에 유의하라.

[<img src="/images/swea.png" width="60%" height="60%">](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AW8Wj7cqbY0DFAXN)  

## 접근 방법
조합을 통하여 n개의 과자 중 2개를 뽑아 무게의 합을 구한다.  

## 구현
DFS를 통하여 조합을 구현한다.  
DFS의 인자로 뽑은 개수와, 시작 인덱스를 제공한다.  
시작 인덱스를 두어, [1,1]와 같은 중복되는 경우를 방지한다.  
뽑은 개수가 2개면 무게의 합을 구한다.  
이 값이 M 미만일 경우 기존 결과 값과 비교하여 더 크면 결과 값으로 저장한다.  

## 코드
```java
import java.io.*;
import java.util.*;

class Solution {
	static int snack[], sel[], n, m, result;
	public static void main(String []args) throws Exception {  
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer stk;
    	int tc = stoi(br.readLine());
    	
    	for(int idx = 1; idx <= tc; idx++) {
    		stk = new StringTokenizer(br.readLine());
    		n = stoi(stk.nextToken());
    		m = stoi(stk.nextToken());
    		result = -1;
    		snack = new int[n];
    		sel = new int[2];
    		stk = new StringTokenizer(br.readLine());
    		for(int i = 0; i < n; i++) {
    			snack[i] = stoi(stk.nextToken());
    		}
    		DFS(0, 0);
    		
    		System.out.println("#" + idx + " " + result);
    	}
    	
	}
	static void DFS(int lv, int start) {
		if(lv == 2) {		// 2개를 뽑으면
			int sum = 0;
			for(int i = 0; i < 2; i++) {
				sum += sel[i];
			}
			if(sum > m)		// m 보다 크면 종료한다.
				return;
			result = Math.max(sum, result);		// 최대값으로 갱신
			return;
		}
		for(int i = start; i < n; i++) {	
			sel[lv] = snack[i];
			DFS(lv+1, i + 1);
		}
		return;
	}
	
	static int stoi(String str) {
    	return Integer.parseInt(str);
    }
}
```

## 총평
### 난이도
⭐★★★★
### 후기


### 개선할 점
