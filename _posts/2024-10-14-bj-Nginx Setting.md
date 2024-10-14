---
title: "Nginx 실행 오류 트러블슈팅"
last_modified_at: 2024-10-14 T11:16:00-05:00
toc: true
toc_sticky: true
categories:
  - Technology
tags:
---

> Nginx
===========

# 10816 번 - 숫자 카드 2

## 문제

[<img src="/images/baekjoon.png" width="40%" height="40%">](https://www.acmicpc.net/problem/10816)

## 접근 방법

HashMap을 사용하여 해결하였다.
getOrDefault() 을 사용하여 카드 개수를 세었다.

getOrDefault(put) : 찾는 키가 존재한다면 찾는 키의 값을 반환하고, 없다면 기본 값을 반환

## 코드

```java
import java.io.*;
import java.util.*;

public class Main {
    static int n, m;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = stoi(br.readLine());
        
        StringTokenizer stk = new StringTokenizer(br.readLine());
        int num;
        HashSet<Integer> set = new HashSet<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        while(stk.hasMoreTokens()) {
            num = stoi(stk.nextToken());
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        m = stoi(br.readLine());

        StringBuilder sb = new StringBuilder();
        stk = new StringTokenizer(br.readLine());
        while(stk.hasMoreTokens()) {
            num = stoi(stk.nextToken());
            sb.append(map.getOrDefault(num, 0)).append(" ");
        }
        
        System.out.println(sb.toString());

        return ;
    }

    static int stoi(String s){
        return Integer.parseInt(s);
    }
}
```
