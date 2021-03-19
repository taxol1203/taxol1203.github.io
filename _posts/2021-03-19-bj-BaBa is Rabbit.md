---
title: "백준 17839 - Baba is Rabbit"
last_modified_at: 2021-03-19 T23:27:00-05:00
toc: true
toc_sticky: true
header:
  teaser: /images/baekjoon.png
categories: 
  - codingTest
tags:
  - DFS
  - Baek Joon
---

> Java

17839 번 - Baba is Rabbit
=============
 
## 문제

n개의 명령이 있다.  
명령의 형태는 p is q 의 형태이며, p, q는 사물이다.  
두 사물 p, q에 대해 p is q 라는 명령은 사물 p를 사물 q로 바꾼다.  
어떤 사물 p에 대해 적용할 수 있는 명령이 두 가지 이상이면, 그 중 아무거나 하나 골라서 적용할 수 있다.  
그리고 **어떤 사물 p에 명령을 한 번 이상 적용한 결과로 다시 p가 나오는 경우는 없다.**  

게임 초기에 설정된 명령들이 주어졌을 때, Baba에 명령을 적용하여 어떤 사물로 만들 수 있는지 구해보자.  

[<img src="/images/baekjoon.png" width="40%" height="40%">](https://www.acmicpc.net/problem/17839)  

## 접근 방법
위 문제를 해석해보면,  
```
Baba is B
B is C
B is D
D is E
E is B
```  
가 있다고 하자,  
1. 그렇다면 처음 Baba에서 부터 시작하여, B로 이동한다.  
2. B에서 C,D 둘다 갈 수 있으므로, 각각 가는 경우를 확인한다.  
3. 그리고 is를 통해 도착한 B,C,D,E를 저장하여 사전 순으로 출력한다.  

여기서 주의할 점은, p에 적용한 결과가 다시 p로 나올 수 없다는 것인데 이 말은,  
위의 예에서 E is B를 수행하였을 때 다시 B is C 그리고 B is D로 가게 된다.  
4. 따라서, 한번 방문한 문자열은 다시 방문하지 않게 visited 자료구조를 만든다.  

## 구현
우선 key value가 명확하므로 map을 사용하였다.  
B is C 그리고 B is D 처럼, q가 2개 이상인 경우를 고려하여 `Map<String, List<String>>`으로 명령을 담았다.  
이후 명령이 수행 될때마다 나오는 결과를 `Set<String>`에 담아, 중복되는 값이 들어오지 않게 한다.  

마지막으로 사전순으로 정렬을 해야하는데,  
Set을 List로 변경하고 `List a = ArrayList(Set)`  
Collection.sort()를 사용하여 간단히 문자열을 정렬하였다.  

## 코드
```java
import java.util.*;
import java.io.*;

public class Main {
	static int n;
	static Set<String> result;
	static Map<String, List<String>> baba;
	static Map<String, Integer> vis;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer stk;
    	n = stoi(br.readLine());
    	
    	baba = new HashMap<>();
    	vis = new HashMap<>();
    	String p,q;
    	for(int i = 0; i < n; i++) {
    		stk = new StringTokenizer(br.readLine());
    		p = stk.nextToken();
    		stk.nextToken();
    		q = stk.nextToken();
    		if(!baba.containsKey(p)) {
    			List<String> qq = new ArrayList<>();
    			qq.add(q);
    			baba.put(p, qq);
    			vis.put(p, 0);
    		}
    		else {
    			baba.get(p).add(q);
    		}
    	}
    	
    	result = new HashSet<String>();
    	
    	Recur("Baba");
    	// 정렬을 위해 Set을 List로 변환
    	List<String> sortStr = new ArrayList<String>(result);
    	Collections.sort(sortStr);		// 사전 순으로 정렬
    	
    	for(String qq : sortStr)
    		System.out.println(qq);
    	
    	br.close();
	}
	// 반복 시작
	static void Recur(String p) {
		if(!baba.containsKey(p))	// 마지막 노드일 경우
			return;
		if(vis.get(p) != 0)			// 한번 방문한 노드일 경우
			return;
		
		vis.put(p, vis.get(p) + 1);	// 방문 횟수 체크, 여기선 1로 바뀐다.
		
		for(String qq : baba.get(p)) {
			result.add(qq);			// 결과를 저장한다.  
			Recur(qq);				// 다음 명령을 수행
		}
	}
	
	static int stoi(String str) {
    	return Integer.parseInt(str);
    }
}
```

## 총평
### 난이도 
⭐⭐⭐⭐★
### 후기
먼저 출력을 주어진 조건대로 하지 않고, sysout(sortStr)를 하는 실수를 하였다.  
그리고 문제를 이번에도 제대로 이해하기에 꽤나 큰 시간이 걸렸으며,  
한번 방문한 노드는 다시 방문하지 않는다는 조건을 고려하지 못하였다.  
### 개선할 점
주어진 조건을 무조건 주석에 써, 이후에 잊지 않게 해야한다.  

<!-- ★
<img src="/images/codingTest/bj/문제번호.PNG" width="40%" height="40%">  

-->