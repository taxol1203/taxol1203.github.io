---
title: "백준 17264 - I AM IRONMAN"
last_modified_at: 2024-10-31 T11:16:00-05:00
toc: true
toc_sticky: true
header:
  teaser: /images/baekjoon.png
categories:
  - codingTest
tags:
  - Baek Joon
  - MAP
---

> Java

# 17264 번 - I AM IRONMAN

## 문제

[<img src="/images/baekjoon.png" width="40%" height="40%">](https://www.acmicpc.net/problem/17264)

## 접근 방법

HashMap을 사용하여, 
- 이길 수 있는 경우 W, 
- 무조건 지는 경우 L
- 결과를 알 수 없는 경우
를 확인한다.

점수의 경우 0점보다 떨어질 수 없으므로, 
점수 차감 시 **3항 연산자**를 활용하여 점수가 0보다 떨어지지 않는지 체크하였다.

## 코드

```java
import java.util.*;
import java.io.*;
public class Main {
    static int n,m;
    static int W, L, G;
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = stoi(stk.nextToken());
        m = stoi(stk.nextToken());
        stk = new StringTokenizer(br.readLine());
        W = stoi(stk.nextToken());
        L = stoi(stk.nextToken());
        G = stoi(stk.nextToken());

        Map<String, String> map = new HashMap<>();
        for(int i = 0; i < m; i++) {
            stk = new StringTokenizer(br.readLine());
            map.put(stk.nextToken(), stk.nextToken());
        }

        int score = 0;
        String curP;
        boolean isWin = false;
        for(int i = 0; i < n; i++) {
            curP = br.readLine();
            if(map.containsKey(curP)) {
                if(map.get(curP).equals("W")) {
                    score += W;
                }
                else {
                    score = score - L < 0 ? 0 : score - L;
                }
            } else {
                score = score - L < 0 ? 0 : score - L;
            }

            if(score >= G) {
                isWin = true;
                break;
            }
        }

        if(isWin) {
            System.out.println("I AM NOT IRONMAN!!");
        } else {
            System.out.println("I AM IRONMAN!!");
        }
    }

    public static int stoi(String str){
        return Integer.parseInt(str);
    }
}

```
