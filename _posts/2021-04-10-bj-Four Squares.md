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

[<img src="/images/baekjoon.png" width="40%" height="40%">](https://www.acmicpc.net/problem/17626)    

## 접근 방법

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
    	dp = new int[n+1];
    	
    	pow = new ArrayList<>();
    	
    	for(int i = 1; i <= Math.sqrt(n); i++) {
    		pow.add(i * i);
    	}
    	
    	Arrays.fill(dp,5);
    	dp[0] = 0;
    	for(int i = 1; i <= n; i++) {
    		for(int num : pow) {
    			if(i - num >= 0) {
    				dp[i] = Math.min(dp[i - num] + 1, dp[i]);
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