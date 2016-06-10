<!DOCTYPE html><html style="height: 100%">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/assets/css/roleMenuTree.css">
<title>角色管理列表</title>
<script type="text/javascript">
function doSearch(val) {
	$('#dg').datagrid('load',{  
		searchVal: $("#searchVal").searchbox("getValue")+":"+$("#searchVal").searchbox("getName")
    });
}
//重置
function clearForm(){
    $('#fm').form('clear');
    $("input[name='status'][value='1']").prop("checked",true);
    //清除checkBox
    $("#checkB").find("a:first").filter(".ed").click();
}
//弹出新增框
function addOne(){
	$('#dlg').dialog('open').dialog('setTitle', '新增系统角色');
	$('#fm').form('clear');
	$.post(ctx+"/systemRole/initMenu",{roleid:null},function(res){
		$("#checkB").empty();
		$("#checkB").checkTree(res.menulist);
	},'json');
	$("input[name='status'][value='1']").prop("checked",true);
}
//新增或修改处理
function saveOrUpdate(){
	$("#selectedPermissionIds").val($("#checkB").checkTree("getCheckData"));
 	$('#fm').form('submit', {   
 		url:"${ctx }/systemRole/doEditRole",
 	    onSubmit: function(){
 	    	if($("#checkB").checkTree("getCheckData")==""){
 	    		$.messager.show({title: '提示', msg: "请选择角色拥有的菜单！" });
 	    		return false;
 	    	}
 	    	return $(this).form('validate');
 	    },   
 	    success:function(result){
			var res = eval('(' + result + ')');
			if (res.status==0){
				$('#dg').datagrid('reload');
				$('#dlg').dialog('close');
				$.messager.show({title: '提示', msg: "操作成功" });
			} else {
				if(res.msg=="nameUnique"){
					res.msg = "角色名已存在!";
				}
				$.messager.show({title: '提示', msg: res.msg });
			}
 	    }   
 	});  
}
//弹出修改窗口并赋值
function editOne(){
	var row=$('#dg').datagrid("getSelected"); 
	if (row){
		$('#dlg').dialog('open').dialog('setTitle', '编辑系统角色');
		$('#fm').form('clear');
		$('#fm').form('load',row);
		$.post(ctx+"/systemRole/initMenu",{roleid:row.id},function(res){
			$("#checkB").empty();
			$("#checkB").checkTree(res.menulist);
		},'json');
	}else{
		$.messager.show({
			title: '提示',
			msg: "请选择要编辑的角色！"
		});
	}
}
//删除
function removeOne(){
	var row=$('#dg').datagrid("getSelected"); 
	if (row){
		$.messager.confirm('提示','确定删除此角色?',function(r){
			if (r){
				$.post(ctx+"/systemRole/delRole",{roleid:row.id},function(res){
					if (res.status==0){
						$('#dg').datagrid('reload');
						$.messager.show({title: '提示',msg: "删除成功！"});
					} else {
						$.messager.show({title: '提示',msg: res.msg });
					}
				},'json');
			}
		});
	}else{
		$.messager.show({title: '提示',msg: "请选择要删除的角色！"});
	}
}
</script>
</head>
<body>
<style>
	html,body{height:98.6%}
</style>
    <div style="width: 100%;height: 100%;">
    <table id="dg" class="easyui-datagrid" title="角色管理" style="height: 100%;" toolbar="#toolbar"
		sortOrder="desc" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true" striped="true" pageSize="20"
		url="${ctx }/systemRole/list" data-options="singleSelect:true,collapsible:true,method:'post'">
		<thead>
			<tr>
				<th field="rolename" width="50px">角色</th>
				<th field="remarks" width="50px">描述</th>
				<th field="statusStr" width="50px">状态</th>
			</tr>
		</thead>
	</table>
	</div>
	<div id="toolbar" style="width: 100%;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addOne()">新增</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editOne()">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeOne()">删除</a>
		<input id="searchVal" class="easyui-searchbox" data-options="prompt:'请输入查询条件',menu:'#mm',
		searcher:function(value,name){doSearch(value+':'+name)}" style="width:300px"></input>
		<div id="mm" style="width:120px">
			<div data-options="name:'all'">全部</div>
			<div data-options="name:'rolename'">角色</div>
			<div data-options="name:'remarks'">描述</div>
			<div data-options="name:'status'">状态</div>
		</div>
	</div>
	
	<!-- 	BEGIN新增隐藏窗口 -->
	<div id="dlg" style="top: 100px;" closed="true" class="easyui-dialog" data-options="iconCls:'icon-save',resizable:true,modal:true,
	buttons: [{text:'提交', iconCls:'icon-ok', handler:saveOrUpdate},{text:'重置', handler:clearForm}]">
	<form id="fm" method="post">
        <input type="hidden" id="id" name="id"/>
           <table cellpadding="5">
            	<tr>
                   <td>角色名:</td>
                   <td><input style="width: 300px;" class="easyui-validatebox textbox" id ="rolename" name="rolename" 
                   data-options="required:true,validType:'length[3,10]'" onkeyup="value=value.replace(/\s|input|script/g,'')"
                   onpaste="value=value.replace(/\s|input|script/g,'')" oncontextmenu = "value=value.replace(/\s|input|script/g,'')"></input></td>
               </tr>
			   <tr>
                   <td>描述:</td>
                   <td><textarea style="width: 300px;height: 60px;white-space:normal;" class="easyui-validatebox textbox" name="remarks" maxlength="200"></textarea></td>
               </tr>
               <tr>
                   <td>状态:</td>
                   <td>
						<input name="status" type="radio" checked value="1"><label >启用</label>
						<input name="status" type="radio" value="0"><label >禁用</label>
                   </td>
               </tr>
               <tr>
                   <td>菜单:</td>
                   <td>
                   <input type="hidden" id="selectedPermissionIds" name="selectedPermissionIds"/>
                   <div id="checkB"></div>
                   </td>
               </tr>
           </table>
	</form>
	</div>
	<!-- END新增隐藏窗口 -->
</body>
<script type="text/javascript" src="${ctx }/assets/js/system/roleMenuTree.js"></script>
</html>