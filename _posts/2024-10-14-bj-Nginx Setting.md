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

nginx 를 설치하고, 실행이 잘 안되는 것 같아 테스트를 위해 확인해보았다.

### 확인 과정
**확인 방법 1.**
```
ps -ef | grep nginx
```
결과 : 
```
503 99528 95752   0  2:40PM ttys005    0:00.00 grep nginx
```
-> 찾아보니, nginx가 실행되었다면 nginx 관련 프로세스가 노출되어야하는데, 
여기서는 grep 프로세스만 노출됨 (즉 nginx 실행 실패)

**확인 방법2. sudo brew services list 로 조회**

- > nginx error 256 root /Library/LaunchDaemons/homebrew.mxcl.nginx.plist

으로 실행 안됨

<img src="/images/Tech/Nginx/first/1.png">

원인 :

https://4sii.tistory.com/625

https://bobcares.com/blog/mac-brew-nginx-error-256/

" nginx -t

로 원인파악을 해보니, crt, key 인증 파일들을 확인하지 못하여 오류가 발생한 것이다.

```

nginx: [alert] could not open error log file: open() "/opt/homebrew/var/log/nginx/error.log" failed (13: Permission denied)

2024/10/08 15:31:22 [emerg] 11941#0: cannot load certificate key "/opt/homebrew/etc/nginx/ssl/planbo.crt.key": BIO_new_file() failed (SSL: error:80000002:system library::No such file or directory:calling fopen(/opt/homebrew/etc/nginx/ssl/planbo.crt.key, r) error:10000080:BIO routines::no such file)

nginx: configuration file /opt/homebrew/etc/nginx/nginx.conf test failed

```
<img src="/images/Tech/Nginx/first/2.png">


- > nginx.conf 파일의 인증서 경로를 정상적으로 연결하니 문제없이 실행 됨

sudo brew services list

<img src="/images/Tech/Nginx/first/3.png">


ps -ef | grep nginx

<img src="/images/Tech/Nginx/first/4.png">
