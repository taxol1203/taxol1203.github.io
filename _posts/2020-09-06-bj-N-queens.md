---
title: "N-queens"
date: 2020-09-06 15:32:00 -0400
toc: true
toc_sticky: true
header:
  teaser: /images/baekjoon.png
categories: 
  - codingTest
tags:
  - Back Tracking
  - Brute Force
---

> C++

2468번 - 안전영역
=============
 
## 문제
n*n 체스판 위에 동일한 행, 열, 대각선 상에 있지 않는 n개의 체스 말을 놓을 수 있는 지 출력하는 문제

백트래킹 공부를 위해 [출처](https://ict-nroo.tistory.com/50)의 사이트를 보며 알고리즘을 짜보았습니다.
공부를 위해서는 윗 블로그 분이 훨~씬 정리 잘해놓으셨으니 저기로 가셔유

## 접근 방법 및 구현
queen을 놓을 좌표를 1차원 배열을 통하여 key, value 느낌으로 구현하였다.
(1,1)~ (1,4)까지 탐색하며
(1,1) 에서 (2,1) ~ (2,4) 이렇게 한 단계씩 DFS로 탐색을 하는데!
promising이란 함수를 통하여 더 이상 탐색 할 지 말지 결정한다.
이를 통해 Back Tracking을 구현하여 탐색 범위를 획기적으로 줄인다.

백트래킹의 제일 중요한 점은 깊이 우선 탐색을 하다, 유망성을 판단하는 것이다!
이는 
```
boolean back trackin(현재 위치)
	if non-promising
		return false;
	else if 목표 지점에 도달 하였을 때
		return true;
	else
		상태 공간 트리의 현재 위치에서 자식 노드들을 순회한다. 
```

## 코드 
```c++
#include <iostream>
#include <vector>
#include <cstdlib>

using namespace std;

int n;
int* col;
bool MyQueen(int);
bool promising(int);
void PrintQueen();
int main() {

	ios::sync_with_stdio(false);
	cin.tie(NULL);
	cin >> n;
	//편의성을 위해 y축 1에서 시작하기 위해 실제 size를 1늘린다.
	col = new int[n + 1];

	MyQueen(0);

	return 0;
}

bool MyQueen(int level) {
	//들어온 좌표 위치가 유망한지(앞선 queen과 충돌을 하는지) 확인하며
	//충돌하면 false를 반환하여 x를 한단계 올린다.
	if (!promising(level)) {
		return false;
	}
	//n개의 queen이 모두 놓이면
	else if (level == n) {
		PrintQueen();
		//true를 반환하여 함수를 빠져나가도록 만든다.
		//만약 false 이면 다시 다음 i값으로 새로 함수를 시작한다.
		return true;
	}
	for (int i = 1; i <= n; i++) {
		col[level + 1] = i;
		if (MyQueen(level + 1)) {
			//true를 반환하면 함수를 빠져나가도록 만든다.
			return true;
		}
	}
	return false;
}

//유망한지, 유망하지 않은 지 확인한다.
//현재 해당하는 level의 아래 값들이, 즉 만약 3번째 행에서 값이 1, 2 번째 행의 queen이 충돌을 일으키지 않는 가를 확인한다.
bool promising(int level) {
	for (int i = 1; i < level; i++) {
		//만약 x 좌표 값이 같으면 queen 충돌이므로 false
		if (col[i] == col[level])
			return false;
		//각 좌표간의 y값의 차이와 x값의 차이가 같으면 대각선이므로 충돌
		else if(level - i == abs(col[i] - col[level]))
			return false;
	}
	return true;
	
}

void PrintQueen() {
	for (int i = 1; i <= n; i++) {
		cout << "(" << i << "," << col[i] << ")" << "\n";
	}
}
```

## 후기 및 개선할 점

후기:
백준의 Back Tracking문제를 풀기 위해 이론을 공부하였다.
이 전에 stack을 통하여 풀려고 노력을 하였으나 실패하였다. 이유는,
level 3까지는 탐색한다 하지만 3에서 더 이상 갈 곳이 없으므로 1로 돌아와야 하는데 이 과정에서 visitied가 level1 단계로 돌아오지 못하는 문제를 해결하지 못하였다.