<!DOCTYPE HTML><html class="framePage">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
<meta charset="UTF-8"/>
<%@ include file="/assets/global.jsp"%>
    <script>
    function init() {  
        $("#username").focus();
    }

    function login() {
    	$.ajax({
    		url : ctx+"/login",
    		type : "post",
    		data : {username:$("#username").val(),password:$("#password").val(),yzm:$("#yzm").val()},
//     		dataType: "text",
    		success : function(data) {
    			var res = eval("("+data+")");
    			if (res.result == "success") {
<%--     				window.location.href="<%=basePath%>loginController/redirectLogin"; --%>
    			}else{
    				if(res.msg=="密码错误"){
    					$("#inputPassword").val("");
    					$("#inputPassword").focus();
    				}
<%--     				$("#yzmimg").attr("src","<%=basePath%>culturalVillage.images?d="+Math.random()); --%>
    				alert(res.msg);
    			}
    		}
    	});
    }
    </script>

</head>
<body onload="init()">
    <div id="panel">
        <i class="logo"></i>
        <i class="sname"></i>
        <form action="${ctx}/login" method="post" >
            <i>账　号：</i> <input class="tx" name="username" type="text" id="username" value="admin" maxlength="20"/><br/>
            <i>密　码：</i> <input class="tx" name="password" type="password" id="password" value="www.trgis.com:)" maxlength="50"/> <br/>
            <i>　　　　</i> 
            			 <input type="submit" class="btn btn-primary btn-lg btn-block" value="登录"></input>
<!--             			 <input type="submit" onclick="javascript:login();return false;" name="btnSubmit" value=" 登 录 " id="btnSubmit" style="width: 72px;"/> -->
        </form>
    </div>
</body>
</html>