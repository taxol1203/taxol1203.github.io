---
title: "SWEA 1208 - Flatten"
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

1208 번 - Flatten
=============
 
## 문제
한 쪽 벽면에 다음과 같이 노란색 상자들이 쌓여 있다.

높은 곳의 상자를 낮은 곳에 옮기는 방식으로 최고점과 최저점의 간격을 줄이는 작업을 평탄화라고 한다.

평탄화를 모두 수행하고 나면, 가장 높은 곳과 가장 낮은 곳의 차이가 최대 1 이내가 된다.

평탄화 작업을 위해서 상자를 옮기는 작업 횟수에 제한이 걸려있을 때, 제한된 횟수만큼 옮기는 작업을 한 후 최고점과 최저점의 차이를 반환하는 프로그램을 작성하시오. 

[문제 출처](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV139KOaABgCFAYh)  

## 접근 방법
정렬과 탐색을 통하여 해결하였다.  

1. 상자들을 오름차순으로 정렬한다.
2. 제일 뒤에서 부터 탐색하여 제일 높은 상자의 위치를 찾는다.
3. 제일 앞에서 부터 탐색하여 제일 낮은 상자의 위치를 찾는다.
4. 높은 위치의 상자는 -1, 낮은 위치의 상자는 +1 하여 평탄화를 한다
5. 2번으로 돌아가 반복한다.  

## 구현 
Math.sort() 메소드를 이용하여 입력받은 상자들의 크기를 정렬한다.  
제일 뒤부터 제일 큰 수를 찾는다. 정렬 되어 있으므로, 자기보다 작은 수가 나오면, 현재 자신이 제일 큰 수이므로 바로 종료가 가능하다.  
제일 작은 수도 마찬가지로 위의 방법을 통해 찾는다.  
이후 두 크기를 수정하고 위 두 크기의 차이가 0이면, 완전 평탄화가 되었으므로 종료한다.  

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
    		Arrays.sort(arr);		// 1번 - 처음 정렬
    		
    		while(dump-- != 0){
    			int maxI = 50, minI = 49;
    			for(int i = 99; i >= 51; i--) {		//2번 제일 큰 수를 찾는다.
    				if(arr[i - 1] < arr[i]) {
    					maxI = i;
    					break;
    				}
    			}
    			for(int i = 0; i < 49; i++) {		// 3번 제일 작은 수를 찾는다.
    				if(arr[i] < arr[i + 1]) {
    					minI = i;
    					break;
    				}
    			}	
    			arr[maxI]--;						// 평탄화 작업
    			arr[minI]++;
    			diff = arr[maxI] - arr[minI];		
    			if(diff == 0)
    				break;
    		}
    		Arrays.sort(arr);						// 마지막으로 정렬을 하여 높이 차를 구한다.
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
단순히 정렬과 탐색으로 풀 수 있는 문제를, 평균을 이용하는 등 머리를 굴리려다 오히려 고생한 문제.  

### 개선할 점
1. 처음 정렬을 하고 
2. 양 끝 값을 1씩 증감을 시킨 후
3. 다시 정렬을 하여 반복하는 간단한 방법도 있다.  
하지만 위는 매번 정렬을 해야하므로 나의 코드보다 더 시간이 오래 걸리니 정신승리를 한다.  