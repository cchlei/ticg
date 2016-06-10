<!DOCTYPE html><html style="height: 100%">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<title>应用子系统列表</title>
<script type="text/javascript" src="${ctx }/assets/js/subsystem/sublist.js"></script>
</head>
<body style="height: 100%">
<form id="fm" method="post" style="height: 100%">
    <div style="width: 100%;height: 100%;">
    <table id="dg" class="easyui-datagrid" title="应用子系统管理" style="height: 100%;" toolbar="#toolbar"
		sortOrder="desc" pagination="true" fitColumns="true" singleSelect="true" striped="true" pageSize="20"
		url="${ctx}/subsystem/list" data-options="singleSelect:true,collapsible:true,method:'post'">
		<thead>
			<tr>
            	<th field="id" width="50px" checkbox="true">编号</th>
				<th field="name" width="50px">名称</th>
				<th field="code" width="50px">编码</th>
				<th field="database" width="50px">数据库标识</th>
				<th field="url" width="50px">URL</th>
				<th field="description" width="50px">描述</th>
				<th field="createTime" width="50px">注册时间</th>
				<th field="statusStr" width="50px" >是否启用</th>	
			</tr> 
		</thead> 
	</table>
	</div>
	<div id="toolbar" style="width: 100%;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">新增</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
		<input id="searchVal" class="easyui-searchbox" data-options="prompt:'请输入查询条件',menu:'#mm',searcher:function(value,name){doSearch()}"style="width: 300px;vertical-align: bottom;"></input>
		<div id="mm" style="width:100px">
			<div data-options="name:'all'">全部</div>
			<div data-options="name:'name'">名称</div>
			<div data-options="name:'code'">编码</div>
			<div data-options="name:'database'">数据库标识</div>
			<div data-options="name:'url'">URL</div>
		</div>
		<input type="hidden" id="searchVal"> 
	</div>
</form>
<!-- 添加资料隐藏窗口 -->
<div id="add" data-options="iconCls:'icon-save',resizable:true,modal:true,buttons: [{text:'提交', iconCls:'icon-ok', handler:function(){ $('#add').dialog('create');}},
{text:'取消', handler:function(){ $('#add').dialog('close');}}]">
	<form id="addfm" method="post" style="width: 100%;height: 100%;"  >
		<div class="easyui-panel"  style="position: relative;" data-options="fit:true">
			<table cellpadding="3" border="0" style="width: 100%;height:100%;font-size:14px;font-family:'微软雅黑' ;text-align:center;">
				<tr>
					<td><input id="id" name="id" type="hidden"/></td>
				</tr>
				<tr>
					<td style="width:50px;">子系统名称:</td>
					<td><input id="name" name="name" class="easyui-validatebox"
					    style="width:250px;height:25px"
						data-options="required:true,validType:['length[2,20]']"
						onkeyup="value=value.replace(/\s|input|script/g,'')"
                        onpaste="value=value.replace(/\s|input|script/g,'')"
                        oncontextmenu = "value=value.replace(/\s|input|script/g,'')"/></td>
				</tr>
				<tr>
					<td>编码:</td>
					<td>
                    <input id="code" name="code" class="easyui-validatebox"  style="width:250px;height:25px" data-options="required:true,validType:['length[2,20]']"
                    onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" onpaste="value=value.replace(/\s|input|script/g,'')" oncontextmenu = "value=value.replace(/\s|input|script/g,'')"/>
                    </td>
                </tr>
				<tr>
					<td>数据库标识：</td>
					<td>
					<input id="database" name="database" required="true" class="easyui-validatebox "
					    style="width:250px;height:25px" 
					    data-options="required:true,validType:['length[2,20]']"/>
					</td>
				</tr>
				<tr>	
					<td>URL:</td>
					<td>
					<input  id="url" name="url" required="true" class="easyui-validatebox"
					    style="width:250px;height:25px" maxlength="255"/>
					</td>	
				</tr>
				<tr>	
					 <td style="text-align:center;">是否启用:</td>
			         <td style="width:182px;height:25px;text-align:left;padding-left:58px;">
						<input name="status" type="radio" checked value="1"><label >启用</label>
						<input name="status" type="radio" value="0"><label >禁用</label>
                   </td>
				</tr>
				<tr>	
					<td>简 介:</td>
					<td>
					<textarea id="description"  cols="3" name="description" required="true" class="easyui-validatebox"
					    style="width:250px;height:40px" maxlength="100"></textarea>
					</td>
				</tr>
				
			</table>
		</div>
	</form>
</div>
</body>
</html>