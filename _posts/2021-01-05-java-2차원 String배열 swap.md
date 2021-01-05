---
title: "자바 2차원 String배열 swap"
date: 2021-01-04 22:31:00 -0400
categories: 
  - Language
tags:
  - Java
  - String
---

> Java 

String 배열 Swap
=============
 
## 서론
[사탕게임](https://www.acmicpc.net/problem/3085)을 풀다보니 2차원 String 배열을 입력 받아, 각 배열의 값 char들을 swap하여야 하는 경우가 있었다.  


## 코드
```java
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	static int n;
	static String[] candy;

	public static void main(String[] args) throws IOException {

		// 2차원 String 배열 입력
    	BufferedReader bf = new BufferedReader(new InputStreamReader(System.in)); //선언
    	n = Integer.parseInt(bf.readLine());
    	candy = new String[n];
    	for(int i = 0; i < n; i++) {
    		candy[i] = bf.readLine();
    	}

    	//가로를 기준으로 인접한 2개의 사탕을 교환한다.
    	for(int i = 0; i < n; i++) {
    		for(int j = 0; j < n - 1; j++) {
    			candy = swap(candy, i, j , i , j + 1);
    			
    			candy = swap(candy, i, j , i , j + 1);
    		}
    	}
    	//세로를 기준으로 인접한 2개의 사탕을 교환한다.
    	for(int i = 0; i < n - 1; i++) {
    		for(int j = 0; j < n; j++) {
    			candy = swap(candy, i, j , i + 1, j);
    			
    			candy = swap(candy, i, j , i + 1, j);
    		}
    	}
    	
	}
	// swap을 진행하는 함수. c1과 c2는 각각 해당 char가 있는 좌표
	public static String[] swap(String[] temp, int c1_y, int c1_x , int c2_y, int c2_x) {	
		if(c1_y == c2_y) {
			StringBuilder chStr = new StringBuilder(temp[c1_y]);
	        char tmp = temp[c1_y].charAt(c1_x);
	        chStr.setCharAt(c1_x, temp[c2_y].charAt(c2_x));
	        chStr.setCharAt(c2_x, tmp);
	        temp[c1_y] = chStr.toString();
		}
		else {
			StringBuilder chStr1 = new StringBuilder(temp[c1_y]);
			StringBuilder chStr2 = new StringBuilder(temp[c2_y]);
			char tmp = temp[c1_y].charAt(c1_x);
			chStr1.setCharAt(c1_x, temp[c2_y].charAt(c2_x));
			chStr2.setCharAt(c2_x, tmp);
			temp[c1_y] = chStr1.toString();
			temp[c2_y] = chStr2.toString();
		}
        return temp;
    }
```
