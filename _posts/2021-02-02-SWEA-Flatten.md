---
title: "SWEA 1208 - Flatten"
last_modified_at: 2021-02-02 T14:41:00-05:00
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

1208 번 - Flatten
=============
 
## 문제
한 쪽 벽면에 다음과 같이 노란색 상자들이 쌓여 있다.

높은 곳의 상자를 낮은 곳에 옮기는 방식으로 최고점과 최저점의 간격을 줄이는 작업을 평탄화라고 한다.

평탄화를 모두 수행하고 나면, 가장 높은 곳과 가장 낮은 곳의 차이가 최대 1 이내가 된다.

평탄화 작업을 위해서 상자를 옮기는 작업 횟수에 제한이 걸려있을 때, 제한된 횟수만큼 옮기는 작업을 한 후 최고점과 최저점의 차이를 반환하는 프로그램을 작성하시오. 

[문제 출처](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV139KOaABgCFAYh)  

## 접근 방법

## 구현 

## 코드
```java
import java.io.*;
import java.util.*;

class Solution {
	public static void main(String []args) throws Exception {  
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	int tc = 10;
    	StringTokenizer st;
    	int[] arr = new int[100];
    	for(int idx = 1; idx <= tc; idx++) {
    		int dump = stoi(br.readLine());
    		st = new StringTokenizer(br.readLine());
    		for(int i = 0; i < 100; i++) {
    			arr[i] =stoi(st.nextToken());
    		}
    		
    		int diff = 0;
    		Arrays.sort(arr);
    		
    		while(dump-- != 0){
    			int maxI = 50, minI = 49;
    			for(int i = 99; i >= 51; i--) {
    				if(arr[i - 1] < arr[i]) {
    					maxI = i;
    					break;
    				}
    			}
    			for(int i = 0; i < 49; i++) {
    				if(arr[i] < arr[i + 1]) {
    					minI = i;
    					break;
    				}
    			}	
    			arr[maxI]--;
    			arr[minI]++;
    			diff = arr[maxI] - arr[minI];
    			if(diff == 0)
    				break;
    		}
    		Arrays.sort(arr);
    		diff = arr[99] - arr[0];
    		System.out.println("#" + idx + " " + diff);
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
