<!DOCTYPE html><html style="height: 100%">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<title>菜单资源列表</title>
<script type="text/javascript" src="${ctx }/assets/js/subsystem/menulist.js"></script>
</head>
<script type="text/javascript">
var systemid =${systemid};
function doSearchAuthc() {
	$('#dgAuthc').datagrid('load',{  
        searchVal: $("#searchValAuthc").searchbox("getValue")+":"+$("#searchValAuthc").searchbox("getName")
    }); 
}
//弹出操作框
function openAuthc(){
	var row=$('#dg').datagrid("getSelected"); 
	if (row){
		$('#dlgAuthc').dialog('open').dialog('setTitle', '操作管理');
		$('#dgAuthc').datagrid({url:'${ctx}/authc/list/'+row.id});
	}else{
		$.messager.show({	// show error message
			title: '提示',
			msg: "请选择要注册操作的菜单！"
		});
	}
}
</script>
<body style="height: 100%">
<div id="dg" class="dg">
</div>
<div id="toolbar">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="obj.add()">新增</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.edit()">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.del()">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="obj.save()" style="display:none;" id="save">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="obj.redo()"style="display:none;" id="redo">取消编辑</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openAuthc()">操作管理</a>
		
    	<input id="searchVal" class="easyui-searchbox" data-options="prompt:'请输入查询条件',menu:'#mm',searcher:function(value,name){doSearch()}"style="width: 300px;vertical-align: bottom;"></input>
	    <div id="mm" style="width: 120px">
	        <div data-options="name:'all'">全部</div>
			<div data-options="name:'name'">名称</div>
			<div data-options="name:'code'">编码</div>
			<div data-options="name:'url'">URL</div>
		</div>
		<input type="hidden" id="searchVal"> 
</div>
<!-- begin 操作隐藏窗口 -->
	<div id="dlgAuthc" class="easyui-dialog" style="width: 770px;padding: 0px 0px" closed="true" modal=true data-options="height:500">
		<table id="dgAuthc" class="easyui-datagrid" toolbar="#toolbarV" style="height: 100%"
		sortOrder="desc" pagination="true" fitColumns="true" singleSelect="true" striped="true" pageSize="20"
		url="" data-options="singleSelect:true,collapsible:true,method:'post'">
		<thead>
			<tr>
				<th field="id"  width="50px" checkbox="true">全选</th>
				<th field="name" width="50px">名称</th>
				<th field="code" width="50px">编码</th>
				<th field="note" width="50px">备注</th>
			</tr>
		</thead>
		</table>
		<div id="toolbarV" style="width: 100%;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addAuthc()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editAuthc()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="delAuthc()">删除</a>
			<input style="height: 25px;" id="searchValAuthc" class="easyui-searchbox" data-options="prompt:'请输入查询条件',menu:'#mm',searcher:function(value,name){doSearchAuthc(value+':'+name)}"style="width: 300px;vertical-align: bottom;"></input>
			<div id="mm" style="width: 120px">
				<div data-options="name:'all'">全部</div>
			    <div data-options="name:'name'">名称</div>
			    <div data-options="name:'code'">编码</div>
			    <div data-options="name:'url'">URL</div>
			</div>
		</div>
	</div>
<!-- end 操作隐藏窗口 -->

<!-- BEGIN新增操作隐藏窗口 -->
	<div id="dlg" class="easyui-dialog" style="top:200px;" closed="true"  data-options="iconCls:'icon-save',resizable:true,modal:true,
	buttons: [{text:'提交', iconCls:'icon-ok', handler:saveOrUpdate},{text:'重置', handler:clearForm}]">
	<form id="fm" method="post">
        <input type="hidden" id="id" name="id"/>
           <table cellpadding="5">
            	<tr>
                   <td>名称:</td>
                   <td><input style="width: 300px;" class="easyui-validatebox textbox" id ="name" name="name" 
                   data-options="required:true,validType:'length[2,20]'" onkeyup="value=value.replace(/\s|input|script/g,'')"
                   onpaste="value=value.replace(/\s|input|script/g,'')" oncontextmenu = "value=value.replace(/\s|input|script/g,'')"></input></td>
               </tr>
			   <tr>
                   <td>编码:</td>
                   <td>
                    <input style="width:300px;" class="easyui-validatebox textbox" id ="code" name="code" 
                   data-options="required:true,validType:'length[2,20]'" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"
                   onpaste="value=value.replace(/\s|input|script/g,'')" oncontextmenu = "value=value.replace(/\s|input|script/g,'')"></input>
                   </td>
               </tr>
               <tr>
                   <td>状态:</td>
                   <td>
						<input name="status" type="radio" checked value="1"><label >启用</label>
						<input name="status" type="radio" value="0"><label >禁用</label>
                   </td>
               </tr>      
               <tr>
                   <td>备注:</td>
                   <td><textarea style="width: 300px;height: 60px;white-space:normal;" class="easyui-validatebox textbox" name="note" maxlength="200"></textarea></td>
               </tr>
           </table>
	</form>
	</div>
	<!-- END新增操作隐藏窗口 -->
</body>
</html>