---
title: "백준 17626 - Four Squares"
last_modified_at: 2021-04-10 T11:46:00-05:00
toc: true
toc_sticky: true
header:
  teaser: /images/baekjoon.png
categories: 
  - codingTest
tags:
  - DP
  - Baek Joon
---
> Java

17626 번 - Four Squares
=============
 
## 문제
자연수 n이 주어질 때, n을 최소 개수의 제곱수 합으로 표현하는 컴퓨터 프로그램을 작성하시오.  
여기서, 1 ≤ n ≤ 50,000이다.  

[<img src="/images/baekjoon.png" width="40%" height="40%">](https://www.acmicpc.net/problem/17626)    

## 접근 방법
DP를 사용하여 해결한 문제였다.  
각 인덱스가 수를 나타내고, 값이 해당하는 값까지 오는 연산 수를 담을 1차원 배열을 만든다.  

연산이 가능한 수는 제곱수이므로, n보다 작은 제곱수들을 미리 다 구한다.  

이제 1부터 시작하여, n까지 각 수를 탐색하는데, i와 각 제곱수들을 빼보고 이 값이 0보다 크다면, 현재 i - 제곱수에서 i로 연산 한번을 하여 올 수 있다는 뜻으므로, 연산 수의 최솟값을 갱신한다.  

n의 수까지 도달하면, n까지 도달하는데 걸리는 최소 제곱수 합 연산 수를 구할 수 있다.  

## 코드
```java
import java.util.*;
import java.io.*;

public class Main {
	
	static int n, result = Integer.MAX_VALUE;
	static int[] dp;
	static List<Integer> pow;
    public static void main(String []args) throws IOException {        
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	n = stoi(br.readLine());
    	dp = new int[n+1];				// 인덱스는 수, 값은 연산 수
    	
    	pow = new ArrayList<>();
    	
    	for(int i = 1; i <= Math.sqrt(n); i++) {	// 연산이 가능한 제곱 수들을 다 모아놓는다.
    		pow.add(i * i);
    	}
    	
    	Arrays.fill(dp,5);
    	dp[0] = 0;
    	for(int i = 1; i <= n; i++) {
    		for(int num : pow) {
    			if(i - num >= 0) {	//	 현재 수는 해당 제곱 수로 더할 수 있으면
    				dp[i] = Math.min(dp[i - num] + 1, dp[i]);	// 최소 연산 수로 갱신
    			}
    			else
    				break;
    		}
    	}
    	
    	System.out.println(dp[n]);

    	br.close();
    }

    static int stoi(String str) {
    	return Integer.parseInt(str);
    }
}
```

## 총평
### 후기

### 개선할 점
없습니다.

<!-- ★
<img src="/images/codingTest/bj/문제번호.PNG" width="40%" height="40%">  

-->