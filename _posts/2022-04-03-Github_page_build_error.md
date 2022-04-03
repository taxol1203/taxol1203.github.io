---
title: "Github Page Build error 해결"
last_modified_at: 2022-04-03 T11:16:00-05:00
toc: true
toc_sticky: true
header:
categories:
  - Technology
tags:
  - Trouble Shooting
---

> Jekyll

# Github Page Build error 해결

## 오류 상황

Jekyll 깃허브 페이지에 Post를 올리는 과정에서 다음과 같이 Build error가 발생하였다.

<img src="/images/Tech/Something/20220403/untiled_1.PNG" width="40%" height="40%">

## 해결

바로 해당 Build 로그 기록을 확인하여 오류 원인을 확인하였다.

```
{% raw %}
github-pages 225 | Error:  Liquid syntax error (line 79): Variable '{{2,2,5,4}' was not properly terminated with regexp: /\}\}/
{% endraw %}
```

{% raw %}
알고리즘 풀이 내용 중 2차원 배열을 선언하는 과정에서 `{{ }}`가 있었고, 이것이 문제가 되었다.
{% endraw %}

해당 내용을 제거하기만 하면 해결되지만, 원인을 조금 더 파악해 보았다.

### liquid

`Liquid`는 Ruby 기반의 오픈소스 HTML 템플릿 언어이다.  
{% raw %}
따라서, Liquid가 `{{ }}`를 인식하여 해당 오류가 발생한 것이다.
{% endraw %}

[Liquid Exception: Liquid syntax error](https://github.com/jekyll/jekyll/issues/5458)

```
Wrap that whole line with {% raw %}…{% endraw %} to let Liquid know not to try to parse it
```

따라서, 문제가 되는 코드에 {% raw %}…{% endraw %}를 사용하여 해결하였다.

```java
{% raw %}
int[][] queries = new int[][] {{2,2,5,4},{3,3,6,6},{5,1,6,3}};
{% endraw %}
```
