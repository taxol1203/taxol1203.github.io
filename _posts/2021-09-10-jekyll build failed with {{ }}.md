---
title: "The variable `{{` on line  in `` was not properly closed with `}}"
date: 2021-09-10 17:45:28 -0400
categories: 
  - Technology
tags:
---

> jekyll

jupyter: no module named 해결법
===========

## 문제

깃허브 블로그 post가 안올라가서 에러 메일을 확인하였더니,  

```
The page build failed for the `master` branch with the following error:

The variable `{{1, 1}` on line 24 in `_posts/2021-09-10-pg-프로그래머스 세팅 문제.md` was not properly closed with `}}`. For more information, see https://docs.github.com/github/working-with-github-pages/troubleshooting-jekyll-build-errors-for-github-pages-sites#tag-not-properly-terminated.

For information on troubleshooting Jekyll see:

https://docs.github.com/articles/troubleshooting-jekyll-builds
```

라고 한다.

코드를 찾아보니 2차원 배열을 입력하기 위해 넣은 코드가 문제였다.  

```java
int[][] v = {{1, 1}, {2, 2}, {1, 2}};
```

## 결론

`{{` `}}`를 코드 내에서도 쓰면 안되는 듯 하다.  
아마 지킬 문법이라 그런가?  

## 참고
[지킬 트러블 슈팅 문서](https://docs.github.com/en/pages/setting-up-a-github-pages-site-with-jekyll/troubleshooting-jekyll-build-errors-for-github-pages-sites#tag-not-properly-terminated)