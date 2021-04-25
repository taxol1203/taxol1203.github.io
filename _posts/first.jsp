// regist
<body>
	<%@ include file="/include/header.jsp"%>
	<h2>도서 등록</h2>
	<form action="main" method="post">
		<fieldset>
			<legend>도서 등록</legend>
				<input type="hidden" name="act" value="regist">
			<label for="isbn">도서번호</label> 
			<input type="text" id="isbn" name="isbn" value="111-222-3333"><br>
			 
			<label for="title">도서명</label>
			<input type="text" id="title" name="title" value="자바프로그래밍"><br>
			
			<label for="author">저자</label> 
			<input type="text" id="author" name="author" value="홍길동"><br>
			 
			<label for="price">가격</label>
			<input type="text" id="price" name="price" value="25000"><br>
			
			<label for="desc">설명</label><br>
			<textarea id="desc" name="desc">자바 프로그래밍을 위한 기본 문법을 담고 있다.</textarea> <br>
			
			<input type="submit" value="도서 등록" id="regist">
			<input type="reset" value="취소">
		</fieldset>
	</form>
</body>
<script>
	
</script>
</html>

----header----------------
<h1 id="title">SSAFY Shop</h1>
<div id="loginInfo">
	<c:if test="${empty loginUser }">
		<form method="post" action="${root }/main">
			<input type="hidden" name="act" value="login">
			<input type="text" name="id" placeholder="아이디">
			<input type="password" name="pass" placeholder="비밀번호">
			<input type="submit" value="login">
		</form>
	</c:if>
	<c:if test="${!empty loginUser}">
		<div>${loginUser.name}
			님 반갑습니다. <a href="main?act=logout">로그아웃</a>
		</div>
	</c:if>

</div>
<ul id="menu">
	<li><a href="${root }/main?act=list">도서 목록</a></li>
	<li><a href="${root }/main?act=registForm">도서 등록</a></li>
</ul>
<hr>

///-----------list

<body>
	<%@ include file="/include/header.jsp"%>
	<h2>도서 목록</h2>
	
	<form align="right" action="main" method="get">
		<input type="hidden" name="act" value="list"> 
		<select	name="searchField">
			<option value="LIST">전체</option>
			<option value="ISBN"
				<c:if test="${searchField == 'ISBN'}">selected</c:if>>도서번호</option>
		</select> 
		<input type="text" name="searchText" value="${searchText}"> 
		<input type="submit" value="검색">
	</form>
	
	<table>
		<thead>
			<tr>
				<th>번호</th>
				<th>ISBN</th>
				<th>도서명</th>
				<th>저자</th>
				<th>가격</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="book" items="${books}" varStatus="vs">
				<tr>
					<td>${vs.count}</td>
					<td>${book.isbn}</td>
					<td>${book.title}</td>
					<td>${book.author}</td>
					<td>${book.price}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="regist.jsp">추가등록</a>
	<a href="index.jsp">메인으로</a>
</body>

//// web xml

<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd" id="WebApp_ID" version="4.0">
  <display-name>ssafy_test</display-name>
  <welcome-file-list>
    <welcome-file>index.jsp</welcome-file>
  </welcome-file-list>
  <error-page>
    <error-code>500</error-code>
    <location>/error/500.jsp</location>
  </error-page>
</web-app>