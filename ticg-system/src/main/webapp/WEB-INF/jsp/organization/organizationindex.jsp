<!DOCTYPE html><html style="height: 100%">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<script type="text/javascript" src="${ctx }/assets/js/organization/organization.js"></script>
<title>组织机构管理</title>
</head>
<body style="height: 100%">
	<form id="org" method="post" style="height: 100%">
    <div style="width: 100%;height: 100%;">
    <table title="组织机构管理" class="easyui-treegrid" id="orgTree" style="height: 100%" toolbar="#toolbar"
			data-options="
				url: '${ctx }/organization/findRootOrganizations',
				method: 'get',
				rownumbers: true,
				idField: 'id',
				animate:true,
				lines:true,
				treeField: 'orgnazationName'
			">
		<thead>
			<tr>
				<th data-options="field:'orgnazationName'" width="220">名称</th>
				<th data-options="field:'address'" width="250" align="right">地址</th>
				<th data-options="field:'date'" width="150"> 创建日期</th>
				<th data-options="field:'email'" width="200"> 邮箱</th>
				<th data-options="field:'person'" width="200"> 联系人</th>
				<th data-options="field:'phone'" width="200"> 电话</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar" style="width: 100%;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">新增</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteOrgnization()">删除</a>
		<input type="hidden" id="searchVal"> 
	</div>
	</div>
</form>
<!-- 新增和编辑-->
<div id="add" data-options="iconCls:'icon-save',resizable:true,modal:true,buttons: [{text:'提交', iconCls:'icon-ok', handler:function(){ $('#add').dialog('create');}},
{text:'取消', handler:function(){ $('#add').dialog('close');}}]">
	<form id="addfm" method="post" style="width: 100%;height: 100%;"  >
		<div class="easyui-panel"  style="position: relative;" data-options="fit:true">
			<table cellpadding="3" border="0" style="width: 100%;height:100%;font-size:14px;font-family:'微软雅黑' ;text-align:center;">
				<tr>
					<td><input id="id" name="id" type="hidden"/></td>
				</tr>
				<tr>
					<td>组织机构名称:</td>
					<td><input id="orgnazationName" name="orgnazationName" class="easyui-validatebox textbox"
					    style="width:250px;height:25px"
						data-options="required:true,validType:['length[2,20]']"
						/></td>
				</tr>
				<tr>
					<td>地址:</td>
					<td><input id="address" name="address" class="easyui-validatebox textbox"
					    style="width:250px;height:25px"
						data-options="required:true,validType:['length[2,20]']"
						/></td>
				</tr>
				<tr>
					<td>邮箱:</td>
					<td>
                    <input id="email" name="email" class="easyui-validatebox textbox"  style="width:250px;height:25px" data-options="required:true,validType:'email'" />
                    </td>
                </tr>
				<tr>
					<td>联系人：</td>
					<td>
					<input id="person" name="person" required="true" class="easyui-validatebox textbox "
					    style="width:250px;height:25px" 
					    data-options="required:true,validType:['length[2,20]']"/>
					</td>
				</tr>
				<tr>	
					<td>电话:</td>
					<td>
					<input  id="phone" name="phone" required="true" class="easyui-validatebox textbox"
					    style="width:250px;height:25px" maxlength="255"/>
					</td>	
				</tr>
				<tr>	
					<td>创建日期:</td>
					<td>
						<input id="d" name="d" type="text" class="easyui-datebox" required="required" style="width:250px;height:25px" data-options="editable:false" >
					</td>	
				</tr>
    			<%-- <tr >	
					<td>父级组织机构:</td>
					<td>
						<input id="parentId" name="parentId" class="easyui-combotree"  style="width:250px;height:25px" required="true"
   							 data-options="valueField:'id',textField:'orgnazationName',url:'${ctx }/systemRole/getOrgsToChoice'">					
   					</td>	
				</tr> --%>
				<!-- <tr>	
					<td>父级组织机构:</td>
					<td>
						<input id="parentId" name="parentId" class="easyui-validatebox textbox"  style="width:250px;height:25px" required="true" type="hidden">					
   					</td>	
				</tr>  -->
						<input id="parentId" name="parentId" class="easyui-validatebox textbox"  style="width:250px;height:25px" required="true" type="hidden">					
			</table>
		</div>
	</form>
</div>
</body>
</html>
