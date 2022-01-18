---
title: "jupyter no module named 해결법"
date: 2021-08-30 17:45:28 -0400
categories: 
  - Technology
tags:
  - Django
---

> Django

Django - jupyter: no module named 해결법
===========2021-09-28-bj-django_json_to_model

## 문제
Jupyter를 실행하고 import를 하는 과정에서 제목과 같은 오류가 발생하였다.  

```python
import json
import pandas as pd # <- 오류 발생, 왤까?
import os
import shutil
```

찾아보니, 가상 환경에는 쥬피터가 설치되어 있지 않아 전체 디렉토리에 깔려있는 쥬피터를 찾아 실행한 것 이였다.  

이에따라, 먼저 가상 환경을 동작하고

```
socure (가상환경폴더)/Scripts/activate
```

가상환경 내에서 쥬피터를 설치해 주었다.  

```
pip install notebook
```

따라서, 가상환경에서 pip 리스트를 확인하고 없으면 쥬피터를 깔아주자, 그러면 import가 가능하다.

```
pip list
```

## 결론

이리저리 해결책을 찾아보다가 지인의 도움으로 해결하였다.  

혹시 나와 같은 가상환경에는 모듈이 설치되었는데, import를 할 수 없는 경우가 생긴다면 이를 참고하였으면 한다.  