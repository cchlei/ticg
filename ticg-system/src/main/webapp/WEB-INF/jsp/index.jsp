<!DOCTYPE HTML>
<html class="framePage">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>
<meta charset="UTF-8" />
<%@ include file="/assets/global.jsp"%>
<link rel="stylesheet" href="${ctx}/assets/libs/ztree/zTreeStyle.css" />
<link rel="stylesheet" href="${ctx}/assets/css/index.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/icon.css">
<script type="text/javascript"
	src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.min.js"></script>
<script src="${ctx}/assets/libs/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx }/assets/libs/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>

<script>
	//给左侧权限树赋值
	var zNodes = ${resultString};
</script>

<script src="${ctx}/assets/js/cco.js"></script>
<script type="text/javascript">
	
	function openPwd() {
		//$('#changeDlg').dialog('open');
		$("#changeDlg").dialog({
			title : "修改密码",
			width : 350,
			height : 225,
			top : 150,
			left : 500,
			closed : false,
			cache : false,
			model : true,
			resizable : true,
			closable : false,
			buttons : [ {
				text : '修改',
				iconCls : "icon-ok",
				handler : function() {
					submitForm();
				}
			}, {
				text : '取消',
				iconCls : "icon-cancel",
				handler : function() {
					$("#changeDlg").dialog('close');
				}
			} ]

		});
		$("#changeDlg").css("display", "block");
	}

	function submitForm() {
		debugger;
		 var originalPwd = document.getElementById("originalPwd").value;
		 var changePwd = document.getElementById("changePwd").value;
		var affirmPwd = document.getElementById("affirmPwd").value;
		jQuery("#username").val("${systemUser.username}"); 
		if(originalPwd == changePwd){
			//初始密码和修改密码相等
			$("#hint").html("初始密码和修改密码相同!").show(500).delay(2000).hide(500); 
			//清除数据
			//$("#changefm").form("clear");
			return;
		}else if(changePwd != affirmPwd){
			//修改密码和取认密码不等
			$("#hint").html("修改密码和确认密码不同!").show(500).delay(2000).hide(500);
			//$("#changefm").form("clear");
			return;
		}
		
 	
		var form = $("#changefm").form({
			url :"${ctx}/systemUser/changePwd",
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var res = eval('(' + result + ')');
				if (res.errorMsg) {
					$.messager.show({
						title : "错误",
						msg : res.msg
					});
				} else {
					$("#changeDlg").dialog('close');
					
					$.messager.show({
						title : "提示",
						msg : res.msg
					});
				}
				$("#changefm").form("clear");
			}

		});
		form.submit();
	}
	
</script>
</head>
<body>
	<div id="header">
		<i class="sname"
			style="vertical-align: middle; color: white; font-style: initial;">
			<h1>智慧社区云平台维护系统</h1>
		</i>
		<div class="tr">
			<span style="color: white;">欢迎您:${systemUser.username}</span> <a
				href="javascript:void(0)" onclick="openPwd()" class="zhuce">修改密码</a>
			<a href="${ctx}/logout" class="leave">安全退出</a>
		</div>
	</div>


	<table id="main">
		<tr>
			<td class="lnav">
				<div class="nvCont">
					<ul id="navi" class="ztree"></ul>
				</div>
			</td>
			<td class="spacer"></td>
			<td class="cont">
				<div id="infoDiv"></div>
				<div id="ifmCont"></div>
			</td>
		</tr>
	</table>

	<!-- 修改密码 -->
	<div id="changeDlg" closed="true" class="easyui-dialog"   
		data-options="iconCls:'icon-save',resizable:true,modal:true,buttons: [{text:'提交', iconCls:'icon-ok', handler:function(){ $('#changeDlg').dialog('create');}},
{text:'取消', handler:function(){ $('#changeDlg').dialog('close');}}]">
		<form id="changefm" method="post">
			<input type="hidden" name="username" id="username" value="${systemUser.username}"/>
			<table border="0" cellpadding="0" cellspacing="5">
				<tr>
					<td align="center">初始密码:</td>
					<td><input id="originalPwd" name="originalPwd" type="password"
						class="easyui-validatebox" style="width: 250px; height: 25px"
						data-options="required:true,validType:['length[2,20]']" /></td>
				</tr>
				<tr>
					<td align="center">修改密码:</td>
					<td><input id="changePwd" name="changePwd" type="password"
						class="easyui-validatebox" style="width: 250px; height: 25px"
						data-options="required:true,validType:['length[2,20]']" /></td>
				</tr>
				<tr>
					<td align="center">确认密码:</td>
					<td><input id="affirmPwd" name="affirmPwd" type="password"
						class="easyui-validatebox" style="width: 250px; height: 25px"
						data-options="required:true,validType:['length[2,20]']" /></td>
				</tr>
				<tr>
					<td align="center"></td>
					<td>
						<output id="hint" style="width: 250px; height: 25px"/>
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>

</html>