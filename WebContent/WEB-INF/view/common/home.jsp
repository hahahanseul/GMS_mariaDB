<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="${ctx}/resources/css/member.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="${js}/member.js"></script>
</head>
<body>
<div id="wrapper">
	<header>
		<h1 id="title">로그인</h1>
		<a id="go_main" href="${ctx}/WEB-INF/common/index.jsp">메인으로 가기</a><br>
 		<a id="db_tester" href="${ctx}/jdbc_test.jsp">DB 연결테스트</a>
	</header>
	<hr/>
	<div id="container">
		<form id="login_form" name="login_form">
			<img id="login_img" src="${img}/img_main.jpg" alt="" />
			<span id="login_id_txt">ID</span>
			<input type="text" id="login_id" name="login_id"/><br />
			<span id="login_pass">PASSWORD</span>
			<input type="text" id="login_pw" name="login_pw"/><br>
			<div id="login_btn_box">
				<input id="loginBtn" type="submit" value="LOGIN"></input>
				<input id="cancelBtn" type="reset" value="CANCEL"></input>
			</div>
			<input type="hidden" name="page" value="main"/>
			<input type="hidden" name="action" value="login"/>
		</form>
	</div>
		<footer>
		<div id="footer-box">
			<p>Posted by: Hansuru Kim</p>
	  		<p>Contact information: <a href="behanseul@naver.com">
	 		behanseul@naver.com</a></p>
	 		<a href="${ctx}/util/update_email.jsp">이메일 업데이트</a>
		</div>
	</footer>
</body>
<script>
member.init();
</script>
</html>