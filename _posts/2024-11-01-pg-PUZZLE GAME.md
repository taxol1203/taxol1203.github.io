---
title: "[PCCP 기출문제] 2번 / 퍼즐 게임 챌린지"
last_modified_at: 2024-11-01 T11:16:00-05:00
toc: true
toc_sticky: true
header:
  teaser: /images/programmers.jpg
categories:
  - codingTest
tags:
  - Programmers
  - Binary Search
---

> Java

# [PCCP 기출문제] 2번 / 퍼즐 게임 챌린지

## 문제

[<img src="/images/programmers.jpg" width="40%" height="40%">](https://school.programmers.co.kr/learn/courses/30/lessons/340212?language=java#)

## 접근 방법

숙련도를 1에서부터 하나씩 올려서, 퍼즐을 해결하는 최소 숙련도를 구하는 방법으로, 
초기 테스트 케이스는 통과할 수 있다.

하지만 위 방법으로는 시간초과가 발생한다.
숙련도의 최대 : 100,000 * 퍼즐의 개수 300,000 = 30,000,000,000 (시간초과)
-> O(N * N)

따라서, 1에서 100,000 까지의 숙련도를 최대한 빨리 찾는 방법을 생각해야한다.
그 방법은 이진 탐색(binary Search) 알고리즘을 사용한다.

이진 탐색 알고리즘을 통해 퍼즐을 해결하는 최소 난이도를 빠르게 구한다.
-> O(N * log(N))

## 코드

```java
import java.util.*;

class Solution {
    int[] Diffs;
    int[] Times; 
    long Limit;
    
    public int solution(int[] diffs, int[] times, long limit) {
        Diffs = diffs;
        Times = times; 
        Limit = limit;
        
        // 최대 난이도
        int max = 0;
        for(int i : diffs) {
            max = Math.max(max, i);
        }
        
        int answer = max;
        // 숙련도는 1에서부터 최대난이도 까지 나올 수 있다
        int l = 1,r = max;
        while(l < r) {
            int mid = (l + r) / 2;
            boolean isSolved = cal(mid);
            // 문제가 해결된다면, 현재 난이도가 해결할 수 있는 최소 난이도라 가정
            if(isSolved) {      
                r = mid;
                answer = mid;
            }
            // 문제가 해결되지 않는다면, 남은 절반을 탐색 
            else {    
                l = mid + 1;
            }
        }
        
        return answer;
    }
    
    public boolean cal(int userDiff) {
        int size = Diffs.length;
        int diff = 1;
        int prevTime = 0;
        int curTime = Times[0];
        long totalTime = Times[0];
        
        // 문제 풀기 시작
        for(int i = 1; i < size; i++) {
            diff = Diffs[i];
            prevTime = Times[i - 1];
            curTime = Times[i];

            // 현재 난이도 보다, 어려울 때
            if(diff > userDiff) {
                totalTime += ((diff - userDiff) * (prevTime + curTime) + curTime);
            }
            // 쉬울 때
            else {
                totalTime += curTime;
            }
        }
        
        if(totalTime <= Limit) {
            return true;
        } else {
            return false;
        }
    }
}

```

## 코드 해석
각 케이스에서 나올 수 있는 가장 높은 난이도를 구한다 : max
이는 이진 탐색에서 `r` 으로 사용된다.

또한 최소로 나올 수 있는 숙련도 1 을 `l` 로 사용한다.

이후 숙련도를 구하기 위한 이진탐색을 시작하고,  
문제가 해결 가능한지 계산하는 함수를 별도로 만들어 각 숙련도 케이스마다 문제 해결여부를 확인한다.

