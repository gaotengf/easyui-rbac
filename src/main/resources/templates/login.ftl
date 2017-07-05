<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>CRM学员管理系统</title>
  <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<div class="logo_box">
  <h1>CRM学员管理系统</h1>
  <form action="login" method="post">
    <div class="input_outer">
      <span class="u_user"></span>
      <input required="required" name="userName" class="text" placeholder="输入用户名" type="text">
    </div>
    <div class="input_outer">
      <span class="us_uer"></span>
      <input required="required" placeholder="请输入密码" name="password" class="text" type="password">
    </div>
    <div class="mb2">
      <button class="act-but submit" style="color: #FFFFFF">登录</button>
    </div>
  <#if error??>
    <div style="text-align:center;padding: 10px;">${error}</div>
  </#if>

  </form>
</div>
</body>
</html>