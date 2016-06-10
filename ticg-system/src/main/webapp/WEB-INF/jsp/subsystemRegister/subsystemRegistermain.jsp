<!DOCTYPE html>
<html style="height: 100%">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<title>子系统注册</title>
<script type="text/javascript" src="${ctx}/assets/js/subsystemRegister/subsystemRegister.js"></script>
</head>
<body class="easyui-layout" style="width: 100%"> 
    <div data-options="region:'west',title:'组织机构',split:true" style="width:20%;"> 
    	<ul id="treeOrz" >
    	</ul>
    </div>
    <!-- 用户列表 -->   
    <div data-options="region:'center'" style="padding:5px;background:#eee;width: 80%" >
    	<table id="dg" style="width: 100%">
    	</table>
    </div>  
    <div id="add" hidden="hidden" data-options="iconCls:'icon-save',resizable:true,modal:true,buttons: [{text:'提交', iconCls:'icon-ok', handler:function(){ $('#add').dialog('create');}},
		{text:'取消', handler:function(){ $('#add').dialog('close');}}]">
	<form id="addfm" method="post" style="width: 100%;height: 100%;"  >
		<div class="easyui-panel"  style="position: relative;" data-options="fit:true">
			<table cellpadding="3" border="0" style="width: 100%;height:100%;font-size:14px;font-family:'微软雅黑' ;text-align:center;">
				<tr>
					<td>请选择子系统(可多选):</td>
					<td>
					<input id="subsystem" name="subsystem" class="easyui-combobox"
					    style="width:250px;height:25px"
						  data-options="valueField:'id',textField:'name',required:true, editable:false, multiple:true"
						/>
					</td>
				</tr>
				<tr>	
					<td>开始使用日期:</td>
					<td>
						<input id="startDate" name="startDate" data-options="editable:false" type="text" class="easyui-datebox" required="required" style="width:250px;height:25px">
					</td>	
				</tr>
				<tr>	
					<td>结束使用日期:</td>
					<td>
						<input id="endDate" name="endDate" data-options="editable:false" type="text" class="easyui-datebox" required="required" style="width:250px;height:25px">
					</td>	
				</tr>
				<tr>
					<td>备注:</td>
					<td><input id="remark" name="remark" class="easyui-validatebox textbox"
					    style="width:250px;height:25px"
						data-options="required:true,validType:['length[2,20]']"
						/></td>
				</tr>
						<input id="id" name="id" class="easyui-validatebox textbox"  style="width:250px;height:25px" required="true" type="hidden">					
			</table>
		</div>
	</form>
</div> 
</body>
</html>