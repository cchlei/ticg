<!DOCTYPE html><html style="height: 100%">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<title>共享角色列表</title>
<script type="text/javascript">
function doSearch(val) {
	$('#dg').datagrid('load',{  
		searchVal: $("#searchVal").searchbox("getValue")+":"+$("#searchVal").searchbox("getName")
    });
}
//清除数据
function clear(){
	$('#tt').tree('reload');
	$('#roleid').val("");
	$('#rolename').val("");
	var tab = $('#tabs');
	var tabs = tab.tabs('tabs');
    for(var i=tabs.length-1; i>=0; i--){
    	var s = tab.tabs('getTabIndex',tabs[i]);
    	tab.tabs('close',s);
    }
}
//给子系统赋值
function loadSubsystem(){
	$('#subData').datagrid({url:ctx+'/subsystem/sharelists/'+${oid},queryParams: {srid:$('#roleid').val()},"onLoadSuccess":function(data){
		var haschecked = false;
		$.each(data.rows,function(i,e){
			if(e.ck){
				$('#subData').datagrid('checkRow',i);
				//如果有选中的默认加载第一个子系统的菜单及操作
				if(!haschecked){
					$('#subData').datagrid('selectRow',i);
					haschecked.true;					
				}
			}
		})
	}});
}
//新增弹出框
function openNew(){
	clear();
	loadSubsystem();
    $('#dlg').dialog('open').dialog('setTitle', '新增');
}
//编辑弹出框并赋值
function loadDatas(){
	clear();
	var row = $("#dg").datagrid('getSelections');
	if(row.length > 1){
		$.messager.show({title: '提示',msg: "只能选择一行数据进行编辑!"});
		return;
	}else if(row.length ==1){//修改
		//加载数据
		$('#roleid').val(row[0].id);
		$('#rolename').val(row[0].rolename);
		//给tree赋值
		$.ajax({
			url:ctx+'/sharerole/getShareroleOrg',
			data:{
				srid :$("#roleid").val()
			},
			method:'post',
			dataType:'json',
			success : function (res) {
				if (res.status==0){
					Array.prototype.contains = function(item){
						return RegExp(item).test(this);
					};
		            var root = $('#tt').tree('getRoot');
		            var nodes = $('#tt').tree('getChildren',root);
		            for(var i=0; i<nodes.length; i++){
		            	if(res.data.contains(nodes[i].id)){
							var node = $('#tt').tree('find', nodes[i].id);
							$('#tt').tree('check', node.target);
		            	}
		            }
				}
			}
		});
	}
	loadSubsystem();
	$('#dlg').dialog('open').dialog('setTitle', '编辑');
}
//加载菜单及操作
function loadAuthc(index,row){//rowid子系统id
	if($('#menuData'+index).length>0){//存在切换tab
		$('#tabs').tabs('select',index);// title or index of tab panel.
	}else{
		var tabHtml='<div style="height: 100%;">'+
						'<table id="menuData'+index+'" class="easyui-datagrid" style="height: 100%;" sortOrder="desc" fitColumns="true" striped="true"'+
						'data-options="method:\'post\',selectOnCheck:false,singleSelect:true,onUncheck:emptyAuthc">'+
							'<thead>'+
								'<tr>'+
									'<th field="id" width="5px" checkbox="true">编号</th>'+
									'<th field="name" width="10px">菜单</th>'+
									'<th field="authcs" width="100px">操作</th>'+
								'</tr>'+
							'</thead>'+
						'</table>'+
					'</div>';
		$('#tabs').tabs('add',{
			index: index,
			title: row.name,
			content: tabHtml
		});
		//更换menudata的数据
		$('#menuData'+index).datagrid({url:ctx+'/menu/sharemenus/'+row.id,queryParams: {srid:$('#roleid').val()},"onLoadSuccess":function(data){
			$.each(data.rows,function(i,e){
				if(e.ck){
					$('#menuData'+index).datagrid('checkRow',i);
				}
			})
		}});
	}
	var tab = $('#tabs');
	$.each(tab.tabs('tabs'),function(k,v) {
		var s = tab.tabs('getTabIndex',v);
	});
}
// 取消子系统选项清空	当前	子系统菜单及操作
function emptyMenu(index,row){
	var menudata = $('#menuData'+index);
	menudata.datagrid('uncheckAll');
	menudata.parent().find('[name^=authc]:checkbox').removeAttr('checked');
	$('#tabs').tabs('select',index);//切换
}
// 取消菜单选项清空子系统
function emptyAuthc(index,row){
	$("[name='authc"+row.id+"']:checkbox").removeAttr('checked');
}
// 保存
function saveOrUpdate(){
	if($('#rolename').val().trim()==''){
		$.messager.show({title: '提示',msg: "请填写角色名称！"});
		return;
	}
	var subids = '';
	var subs = $('#subData').datagrid('getChecked');
	if(subs.length < 1){
		$.messager.show({title: '提示',msg: "请选择子系统！"});
		return;
	}
    for (var i = 0; i < subs.length; i++) {
    	subids += subs[i].id + ",";
    }
    subids = subids.substr(0, subids.length - 1);
    var oids = '';
	var nodes = $('#tt').tree('getChecked');
	if(nodes.length < 1){
		$.messager.show({title: '提示',msg: "请选择共享组织机构！"});
		return;
	}
    for(var i=0; i<nodes.length; i++){
        if (oids != '') oids += ',';
        oids += nodes[i].id;
    }
	var menus = '';
	var menuDgs = $('[id^=menuData]')//所有menu数据gride
	$.each(menuDgs,function(k,v) {
		var temobj = $(v).datagrid('getChecked');
		if(temobj.length!=0){
		    for(var i=0; i<temobj.length; i++){
		        if (menus != '') menus += ',';
		        menus += temobj[i].id;
		    }
		}
	});
	var authcs = '';
	$("[name^=authc]:checkbox").each(function(){
		if($(this).is(":checked")){
			 if (authcs != '') authcs += ',';
			 authcs += $(this).val();
		}
	});
	$.ajax({
		url:ctx+'/sharerole/saveShareRole',
		data:{
			roleid :$("#roleid").val(),
			rolename :$("#rolename").val(),
			menus:menus,
			authcs:authcs,
			subids:subids,
			oid:${oid},
			oids:oids
		},
		method:'post',
		dataType:'json',
		success : function (res) {
			if (res.status==0){
				$('#dg').datagrid('reload');
				$('#dlg').dialog('close');
				$.messager.show({title: '提示',msg: "操作成功！"});
			} else {
				$.messager.show({title: '提示',msg: res.msg});
			}
		}
	});
}
//删除
function del(){
	var selectRows = $('#dg').datagrid("getSelections");
	if (selectRows.length < 1) {
		$.messager.show({title: '提示',msg: "请选择要删除的数据！"});
        return;
    }
	$.messager.confirm("确认消息", "确定要删除所选数据吗？", function (isDelete) {
        if (isDelete) {
            var strIds = "";
            for (var i = 0; i < selectRows.length; i++) {
                strIds += selectRows[i].id + ",";
            }
            strIds = strIds.substr(0, strIds.length - 1);
			$.post(ctx+"/sharerole/del",{id:strIds},function(res){
				if (res.status==0){
					$('#dg').datagrid('reload');
					$.messager.show({title: '提示',msg: "删除成功！"});
				} else {
					$.messager.show({title: '提示',msg: res.msg });
				}
			},'json');
        }
    });
}
</script>
</head>
<body style="height: 100%">
    <div style="width: 100%;height: 100%;">
    <table id="dg" class="easyui-datagrid" title="共享角色管理" style="height: 100%;" toolbar="#toolbar"
		sortOrder="desc" pagination="true" fitColumns="true" striped="true" pageSize="20"
		url="${ctx}/sharerole/list/${oid}" data-options="method:'post'">
		<thead>
			<tr>
            	<th field="id" width="50px" checkbox="true">编号</th>
				<th field="rolename" width="50px">名称</th>
				<th field="orgname" width="50px">所属组织机构</th>
			</tr> 
		</thead> 
	</table>
	</div>
	<div id="toolbar" style="width: 100%;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openNew()">新增</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="loadDatas()">编辑</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
		<input id="searchVal" class="easyui-searchbox" data-options="prompt:'请输入查询条件',menu:'#mm',searcher:function(value,name){doSearch()}" style="width: 300px;vertical-align: bottom;"></input>
		<div id="mm" style="width:100px">
			<div data-options="name:'rolename'">名称</div>
		</div>
	</div>
	
	<!-- 弹出对话框 -->
	<div id="dlg" style="top: 100px;height:50%;width: 90%;" closed="true" class="easyui-dialog" data-options="
	iconCls:'icon-save',resizable:true,modal:true,buttons: [{text:'提交', iconCls:'icon-ok', handler:saveOrUpdate}]">
		<div style="width: 20%;height:100%;float: left;">
			<input type="hidden" name="roleid" id= "roleid"/>
			 角色名称：<input name="rolename" id= "rolename" style="width: 60%;"/></p>
        	<ul id="tt" class="easyui-tree" data-options="url:'${ctx}/organization/orgs',method:'get',animate:true,checkbox:true,cascadeCheck:false"></ul>
		</div>
			<div style="width: 20%;height:100%;float: left;">
			    <table id="subData" class="easyui-datagrid" style="height: 100%;" sortOrder="desc" fitColumns="true"
			    striped="true" data-options="method:'post',selectOnCheck:false,singleSelect:true,onSelect:loadAuthc,onUncheck:emptyMenu"> 
					<thead>
						<tr>
			            	<th field="id" width="50px" checkbox="true">编号</th>
							<th field="name" width="50px">子系统</th>
						</tr> 
					</thead> 
				</table>
			</div>
		<div id="tabs" class="easyui-tabs" style="height:100%"></div>
	</div>
</body>
</html>