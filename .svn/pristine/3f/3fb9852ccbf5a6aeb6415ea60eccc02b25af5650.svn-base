<!DOCTYPE html>
<html style="height: 100%">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<title>用户管理</title>
<script type="text/javascript" src="${ctx}/assets/js/user/user.js"></script>
</head>
<body class="easyui-layout">




	<input type="hidden" id="oid" value="${oid }">
	<!-- 组织机构id -->
	<div data-options="region:'west',title:'组织机构',split:true"
		style="width: 300px;">
		<ul id="treeOrz">
		</ul>
	</div>
	<!-- 用户列表 -->
	<div data-options="region:'center'"
		style="padding: 5px; background: #eee;">
		<div id="welcome">
	 <h1>请选择组织机构!</h1>
	</div>
		<table id="dg">
			<div id="toolbar" style="width: 100%;">
				<input type="hidden" id="searchValhidden" />
			</div>
			<input id="searchVal" />
			<div id="mm" style="width: 120px"></div>
		</table>
	</div>
	<!-- 添加用户 -->
	<div id="dialog" style="display: none">
		<form id="fm" method="post">
			<input type="hidden" name="orgid" /> <input type="hidden"
				name="organization.id" /> <input type="hidden" name="id" />
			<div class="fitem" style="margin: 10px 30px">
				<label>用户名称:</label> <input name="username" class="easyui-textbox"
					data-options="required:true,validType:'length[0,10]'"
					style="width: 150px" />
			</div>
			<div class="fitem" style="margin: 10px 30px">
				<label>用户邮件:</label> <input name="email" class="easyui-textbox"
					data-options="required:true,min:1,validType:'email'"
					style="width: 150px" />
			</div>
			<div class="fitem" style="margin: 10px 30px">
				<label>用户电话:</label> <input name="mobile" class="easyui-numberbox"
					data-options="required:true,min:1,validType:'mobile'"
					style="width: 150px" />
			</div>
		</form>
	</div>
	<!-- 分配用户角色window -->
	<div id="win" style="display: none;">
		<input id="userid" type="hidden" />
		<table id="roleDg">
		</table>
	</div>
</body>
</html>