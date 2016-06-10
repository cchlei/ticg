<!DOCTYPE html><html style="height: 100%">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<title>系统账号管理列表</title>
<script type="text/javascript">
function doSearch(val) {
	$('#dg').datagrid('load',{  
		searchVal: $("#searchVal").searchbox("getValue")+":"+$("#searchVal").searchbox("getName")
    }); 
}
//弹出修改窗口并赋值
function editOne(){
	var row=$('#dg').datagrid("getSelected"); 
	if (row){
		$('#dlg').dialog('open').dialog('setTitle', '编辑系统账户');
		$('#fm').form('clear');
		$('#fm').form('load',row);
		$('#roleid').combotree('setValue',  row.sysrole.id==""?"请选择":row.sysrole.id);
		$('#orgid').combotree('setValue',  row.organization.id==""?"请选择":row.organization.id); 
	}else{
		$.messager.show({
			title: '提示',
			msg: "请选择要编辑的账户！"
		});
	}
}
//删除
function removeOne(){
	var row=$('#dg').datagrid("getSelected"); 
	if (row){
		$.messager.confirm('提示','确定删除此账户?',function(r){
			if (r){
				$.post(ctx+"/systemUser/delUser",{id:row.id},function(res){
					if (res.status==0){
						$('#dg').datagrid('reload');
						$.messager.show({title: '提示',msg: "删除成功！"});
					} else {
						$.messager.show({title: '提示',msg: "删除失败！"});
					}
				},'json');
			}
		});
	}else{
		$.messager.show({
			title: '提示',
			msg: "请选择要删除的账户！"
		});
	}
}
//弹出重置密码窗口
function resetPwd(){
	var row=$('#dg').datagrid("getSelected"); 
	if (row){
		$.messager.confirm('提示','确定要重置此账户的登录密码?',function(r){
			if (r){
				$.post(ctx+"/systemUser/resetPwd",{id:row.id},function(res){
					if (res.status==0){
						$('#dg').datagrid('reload');
						$.messager.show({title: '提示',msg: "重置密码成功，新密码为123456，请尽快登录修改！"});
					} else {
						$.messager.show({title: '提示',msg: "重置密码失败！"});
					}
				},'json');
			}
		});
	}else{
		$.messager.show({	// show error message
			title: '提示',
			msg: "请选择要重置密码的账户！"
		});
	}
}
//弹出新增框
function addOne(){
	$('#dlg').dialog('open').dialog('setTitle', '新增系统账户');
	$('#fm').form('clear');
}
//新增或修改处理
function saveOrUpdate(){
	$('#fm').form('submit',{
		url: '${ctx }/systemUser/add',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(result){
			var res = eval('(' + result + ')');
			if (res.status==0){
				$('#dg').datagrid('reload');
				$('#dlg').dialog('close');
				$.messager.show({title: '提示', msg: "操作成功" });
			} else {
				if(res.msg=="nameUnique"){
					res.msg = "用户名已存在!";
				}
				$.messager.show({title: '提示', msg: res.msg });
			}
		}
	});
}
</script>
</head>
<body style="height: 100%">
    <div style="width: 100%;height: 100%;">
    <table id="dg" class="easyui-datagrid" title="系统账号管理" style="height: 100%;" toolbar="#toolbar"
		sortOrder="desc" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true" striped="true" pageSize="20"
		url="${ctx }/systemUser/list" data-options="singleSelect:true,collapsible:true,method:'post'">
		<thead>
			<tr>
				<th field="username" width="50px">用户名</th>
				<th field="rolename" width="50px">角色</th>
				<th field="organizationname" width="50px">组织机构<span style="color: red;">（此项为空者为系统超管）</span></th>
			</tr>
		</thead>
	</table>
	</div>
	<div id="toolbar" style="width: 100%;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addOne()">新增</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editOne()">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="resetPwd()">重置密码</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeOne()">删除</a>
		<input id="searchVal" class="easyui-searchbox" data-options="prompt:'请输入查询条件',menu:'#mm',searcher:function(value,name){doSearch(value+':'+name)}"style="width: 300px;vertical-align: bottom;"></input>
		<div id="mm" style="width: 120px">
			<div data-options="name:'all'">全部</div>
			<div data-options="name:'username'">用户名</div>
			<div data-options="name:'rolename'">角色</div>
			<div data-options="name:'organizationname'">组织机构</div>
		</div>
	</div>
	<!-- 	BEGIN新增隐藏窗口 -->
	<div id="dlg" closed="true" class="easyui-dialog" data-options="iconCls:'icon-save',resizable:true,modal:true,
	buttons: [{text:'提交', iconCls:'icon-ok', handler:saveOrUpdate},
	{text:'重置', handler:function(){ javascript:$('#fm').form('clear');}}]">
	<form id="fm" method="post">
	<input type="hidden" name="id" id ="id"/>
		<table border="0" cellpadding="0" cellspacing="5">
			<tr>
				<td align="center">账号:</td>
				<td>
					<input id="username" name="username" class="easyui-validatebox textbox" style="width: 198px;"
					data-options="required:true,validType:'length[6,20]'"
					onkeyup="value=value.replace(/[^A-Za-z0-9]/g,'')" onpaste="value=value.replace(/[^A-Za-z0-9]/g,'')"
					oncontextmenu = "value=value.replace(/[^A-Za-z0-9]/g,'')" />
				</td>
			</tr>
			<tr>
				<td align="center">角色:</td>
                <td>
					<input style="width:200px;" id="roleid" name="roleid" class="easyui-combotree" value='请选择'
    				data-options="url:'${ctx }/systemRole/getRolesToChoice',required:true" style="width:180px;">
                </td>
			</tr>
			<tr>
				<td align="center">组织机构:</td>
                <td>
					<input style="width:200px;" id="orgid" name="orgid" class="easyui-combotree" value='请选择'
    				data-options="url:'${ctx }/systemRole/getOrgsToChoice'" style="width:180px;">
                </td>
			</tr>
		</table>
	</form>
	</div>
	<!-- END新增隐藏窗口 -->
</body>
</html>