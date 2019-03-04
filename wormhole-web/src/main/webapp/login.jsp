<%@page language="java" pageEncoding="utf-8" %>
<% String path = request.getContextPath();  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>WormHole-方便、易用的dubbo服务网关管理平台</title>
<link rel="shortcut icon"  href="<%=path %>/velocity/image/favicon.ico" type="image/x-icon" />
<!--  background: #4992FF; 
background-image: url(../velocity/image/login-background.png);
    background-size:100% 100%;  
    -->
<style>
  * {
    font-family: -apple-system, "Helvetica Neue", Helvetica, Arial, "PingFang SC", "Hiragino Sans GB", "WenQuanYi Micro Hei", "Microsoft Yahei", sans-serif;
  }
  html, body {
    height: 100%;
    width: 100%;
    margin: 0;
    padding: 0;
  }
  .background {
    position: fixed;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    /*background: #4992FF; */
    z-index: -1;
    background-image: url('/velocity/image/login-background.png');
    background-size:100% 100%;  
  }

  .login-page {
    color: #fff;
    position: absolute;
    background: rgba(1, 1, 1, .5);
    border-radius: 5px;
    height: 380px;
    width: 520px;
    top: 50%;
    left: 50%;
    margin-left: -260px;
    margin-top: -200px;
  }
  .login-page .title,
  .login-page .login-box {
    text-align: center;
  }
  .login-page .title > h1 {
    margin-top: 40px;
    font-weight: normal;
  }
  .login-page .login-box {
    width: 80%;
    margin: 50px auto 0;
  }
  .login-page .login-box .form-group {
    margin: 35px 0 8px;
    position: relative;
  }
  .login-page .login-box .form-control {
    width: 100%;
    height: 30px;
    line-height: 30px;
    border: solid rgba(255, 255, 255, .4);
    border-width: 0 0 2px 0;
    outline: none;
    background-color: transparent;
    color: #eee;
    padding: 4px 8px; 
    font-size: 14px;
    transition: border-color .3s ease-out;
    box-shadow: none; /* 去除firefox表单验证的红色边框 */
  }
  /* webkit的自动填充背景色强制显示，用hack的方式取消其样式 */
  .login-page .login-box input.form-control:-webkit-autofill {
    transition-delay: 99999s;
    transition: color 99999s ease-in, background 99999s ease-in;
  }
  .login-page .login-box .form-control:focus {
    border-color: #E9F0FF;
  }
  .login-page .login-box .form-control ~ .placeholder {
    position: absolute;
    left: 8px;
    top: -15px;
    color: #999;
    transition: transform .3s ease-out;
    transform: translateY(25px);
    cursor: text;
  }
  .login-page .login-box .form-control:focus ~ .placeholder {
    color: #E9F0FF;
  } 
  .login-page .login-box .form-control.not-empty ~ .placeholder,
  .login-page .login-box .form-control:focus ~ .placeholder {
    transform: translateX(-3px) translateY(0px) scale(.9);
  }
  .btn-login {
    color: #eee;
    background-color: rgba(255,255,255, .3);
    padding: 8px 35px;
    border-radius: 3px;
    border: none;
    word-spacing: 1rem;
    text-align: center;
    cursor: pointer;
    transition: background .3s ease-in-out;
    margin-top: 50px;
  }
  .btn-login:hover {
    background-color: rgba(255,255,255, .2);
  }
  .error-box {
    display: none;
    left: 20%;
    width: 60%;
    padding: 10px 0;
    background-color: rgba(1, 1, 1, .7);
    text-align: center;
    line-height: 30px;
    border-radius: 25px;
    margin: auto;
    color: #f33d19;
    position: absolute;
    top: 30%;
    z-index: 9;
    opacity: 1;
    transition: opacity 1.5s ease-out;
  }
  .error-box.fade-out {
    opacity: 0;
  }
</style>
</head>
<body class="background">
  <div class="login-page">
    <div id="error-box" class="error-box">${error}</div>
    <div class="title">
      <h1>WormHole</h1>
    </div>
    <div class="login-box">
      <form name="form" method="post" action="/auth/authc">
        <div class="form-group username">
          <input type="text" class="form-control" id="username" name="username" required>
          <label class="placeholder" for="username">用户名</label>
        </div>
        <div class="form-group password">
          <input type="password" class="form-control" id="password" name="password" required>
          <label class="placeholder" for="password">密码</label>
        </div>
        <button type="submit" class="btn-login">登 录</button>
      </form>
    </div>
  </div>
  <script>
    function changeEmptyStatus (element) {
      if (element.value != '') {
        element.classList.add('not-empty');
      } else {
        element.classList.remove('not-empty');
      }
    }
    function watchInputChange (element) {
      changeEmptyStatus(element);
      element.addEventListener('blur', function () {
        changeEmptyStatus(element);
      });
      element.addEventListener('change', function () {
        changeEmptyStatus(element);
      });
    }

    var inputs = document.getElementsByClassName('form-control');
    for (var i = 0; i < inputs.length; i++) {
      var element = inputs[i];
      watchInputChange(element);
    }

    var errorBox = document.getElementById('error-box');
    if (errorBox.innerText != '') {
      errorBox.style.display = 'block';
      setTimeout(function () {
        errorBox.classList.add('fade-out');
        setTimeout(function () {
          errorBox.style.display = 'none';
        }, 1600);
      }, 1000);
    }
    
</script>
</body>
</html>