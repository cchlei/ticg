<!DOCTYPE html><html style="height: 100%">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<script type="text/javascript">


var allotDialog = undefined;//弹出框
var subData = undefined; //子系统列表
var menuData = undefined; //菜单列表
var authcData = undefined; //操作列表

var roleData; //角色列表
var temp = undefined; //是否打开编辑状态
var roleid = -200;

var flag = false; //判断是修改角色or添加角色
var check = false; //添加角色是否校验通过

$(function() {
	$("#treeOrz").tree({
		url:ctx+"/organization/orgs",
		onSelect:function(node){
			//重新加载数据
			//$("#searchVal").searchbox('clear');
			roleData.datagrid({url:ctx+'/role/list/'+node.id});
			roleData.datagrid('unselectAll');
			$("#oid").val(node.id);
		}
	});
	//yanpeng
	roleData = $("#roleData").datagrid({
		url : ctx + '/role/list/'+$("#oid").val(),
		method : 'post',
		title : '角色列表',
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40 ],
		border : false,
		fitColumns : true,
		columns : [ [ {
			title : 'ID',
			field : 'id',
			width : '5%',
			checkbox : true
		}, {
			title : '角色名称',
			field : 'rolename',
			width : 200,
			editor: {
				type : 'validatebox',
				options:{
					field : 'rolename',
					required:true,
					validType:['length[2,20]']
                }
			}
		}, {
			title : '组织名称',
			field : 'orgname',
			width : 200
		} ] ],
		toolbar : $("#toolbar"),
		onAfterEdit:function(rowIndex,rowData,changes){
			var testIndex = temp;
			temp=undefined;
			var inserted = roleData.datagrid('getChanges', 'inserted');
			if(changes.rolename==null&&changes.orgname==null){
				return false;
			}
			var url =  ctx+'/role/edit';
			$.ajax({
				type : 'POST',
				url : url,
				dataType: 'json',
				data : {
					rolename : rowData.rolename.trim(),
					oid : $("#oid").val(),
					id : rowData.id
				},
				beforeSend : function () {
					roleData.datagrid('loading');
				},
				success : function (data) {
					if (data) {
						roleData.datagrid('loaded');
						roleData.datagrid('unselectAll');
						msg(data.msg);
						if(data.status==1){
							roleData.datagrid('rejectChanges');
						}else{
							roleData.datagrid('acceptChanges');
						}
					}
				},
			});
		  },
		  onDblClickRow: function (rowIndex, rowData) {
                //双击开启编辑行
                if (temp != undefined) {
                    roleData.datagrid("endEdit", temp);
                }
                if (temp == undefined) {
                	roleData.datagrid("beginEdit", rowIndex);
                	temp = rowIndex;
                }
           }
	});
	
	
	allotDialog = $("#allotDialog").dialog({
		modal:true,
		toolbar:$("#toolbars")
	});
	allotDialog.dialog('close');
	
});


function add(){
	if(temp==undefined){
		temp = 0;
		roleData.datagrid('insertRow',{
			index: 0,	// index start with 0
			row: {
				rolename: ''
			}
		});
		roleData.datagrid('beginEdit',0);
	}else{
		msg('请先保存正在编辑的数据');
	}
}
function del(){
	if(temp!=undefined){
		roleData.datagrid('endEdit',temp);
		temp=undefined;
	}
	var selectRows = roleData.datagrid("getSelections");
	if (selectRows.length < 1) {
       	msg("请选择要删除的行");
        return;
    }
	$.messager.confirm("确认消息", "确定要删除所选记录吗？", function (isDelete) {
        if (isDelete) {
            var strIds = "";
            for (var i = 0; i < selectRows.length; i++) {
                strIds += selectRows[i].id + ",";
            }
            strIds = strIds.substr(0, strIds.length - 1);
            $.ajax({
				type : 'POST',
				url : ctx+'/role/del',
				dataType: 'json',
				data : {
					id : strIds
				},
				beforeSend : function () {
					roleData.datagrid('loading');
				},
				success : function (data) {
					if (data) {
						roleData.datagrid('loaded');
						roleData.datagrid('reload');
						roleData.datagrid('unselectAll');
						msg(data.msg);
					}
				},
			});
        }
    });
}

var rolesname  = '';

//分配菜单方法
function edit(){
	var row = roleData.datagrid('getSelections');
	if(row.length==1){
		if(row[0].id!=null){
			roleid = row[0].id;
			rolesname = row[0].rolename;
			$("#searchVals").val(rolesname);
		}
		flag = true;
	}else if(row.length>1){
		msg('只能选择一行');
		return false;
	}else{
		flag = false;
	}
	allotDialog.dialog('open');
	subData=$("#subData").datagrid({
		url:ctx+'/subsystem/lists/'+$("#oid").val(),
		queryParams: {rid:roleid},
		fitColumns:true,
		singleSelect:true,
		method: 'post',
		columns:[[{
			title:'ID',
			field: 'id',
			width: '5%',
			checkbox:true
		},{
			title:'子系统',
			field: 'name',
			width: '94.5%'
		}]],
		onSelect:function(index,row){
			menuData=$("#menuData").datagrid({
				url:ctx+'/menu/menus/'+row.id,
				queryParams: {rid:roleid},
				fitColumns:true,
				selectOnCheck:false,
				singleSelect:true,
				method: 'post',
				idField:'id',
				columns:[[{
					title:'ID',
					field: 'id',
					width: '5%',
					checkbox:true
				},{
					title:'菜单名称',
					field: 'name',
					width: '20%'
				},{
					title:'操作名称',
					field: 'authcs',
					width: '75%'
				}]],
				onLoadSuccess:function(data){
					$.each(data.rows,function(n,obj) {
						if(obj.ck){
							menuData.datagrid('checkRow',n);
						}
					});
				},
				onUncheck:function(index,row){
					var allChecks = 0;
					$("[name='authc"+row.id+"']:checkbox").each(function(){
		        		if($(this).is(":checked")){
		        			allChecks++;
		        		}
		        	});
					info(allChecks);
					if(allChecks>0){
						msg('底下有操作，请先去掉勾选的操作');
						menuData.datagrid('checkRow',index);
					}
				}
			});
		},
        onBeforeSelect:function(index,row){
     	   if(menuData){
     		   menuData.datagrid('uncheckAll');
     	   }
     	   return true;
        },
		onLoadSuccess:function(data){
			$.each(data.rows,function(n,obj) {
				if(obj.ck){
					subData.datagrid('checkRow',n);
				}
			});
		}
	});
}


//角色列表保存按钮
function save(){
	roleData.datagrid('endEdit',temp);
}

//保存分配菜单
function saveMenu(){
	var subid = undefined;
	var sub = subData.datagrid('getChecked');
	
	//如果选择菜单，但是没有选择子系统
	if(sub.length==0){
		if(menuData){
			var menuTemp = menuData.datagrid('getChecked');
			if(menuTemp.length>0){
				msg('请选择分配菜单的子系统');
				return false;
			}
		}
	}else{
		subid = sub[0].id;
	}
	doSearchs();//校验角色名，添加修改都要校验
	if(!check){
		return false;
	}
	var parms = '';
	if(menuData){
		var allChecks = 0;
		var sumChecks = 0;
		var curRows = menuData.datagrid('getRows');
		if(curRows.length!=0){
	    	$.each(curRows,function(n,obj) {
	        	$("[name='authc"+obj.id+"']:checkbox").each(function(){
	        		if($(this).is(":checked")){
	        			allChecks++;
	        		}
	        	});
	        });
		}
		var temobj = menuData.datagrid('getChecked');
		if(temobj.length!=0){
	    	$.each(temobj,function(n,obj) {
	    		parms += obj.id;
	        	$("[name='authc"+obj.id+"']:checkbox").each(function(){
	        		if($(this).is(":checked")){
	        			parms += ',';
	        			parms += $(this).val();
	        			sumChecks++;
	        		}
	        	});
	    		parms += '-';
	        });
	    	parms = parms.substr(0, parms.length - 1);
		}
		if(allChecks!=sumChecks){
			msg('选择操作后请选中菜单，否则会导致数据丢失');
			return false;
		}
	}
	
	$.ajax({
		url:ctx+'/role/addMenu/',
		data:{
			ids:parms,
			subid:subid,
			rid:roleid,
			oid:$("#oid").val(),
			rolname:$("#searchVals").val()
		},
		method:'post',
    	beforeSend : function () {
			roleData.datagrid('loading');
		},
		success : function (data) {
			if (data) {
				msg(data.msg);
				if(data.status==0){
					closeDialog();
					roleData.datagrid('reload');
				}
			}
		}
	});
}


	function closeDialog() {
		allotDialog.dialog('close');
	}
	//查询
	function doSearch(obj) {
		roleData.datagrid({
			url : ctx + '/role/list/' + $("#oid").val(),
			queryParams : {
				searchVal : $("#searchVal").val()
			}
		});
	}

	//添加角色校验
	function doSearchs(obj) {
		if ($("#searchVals").val().trim() == '') {
			msg('角色名称为空');
			return false;
		}
		$.ajax({
			url : ctx + '/role/check',
			data : {
				checks : $("#searchVals").val(),
				oid : $("#oid").val(),
				rid : roleid
			},
			async : false,
			method : 'post',
			beforeSend : function() {
				roleData.datagrid('loading');
			},
			success : function(data) {
				if (data) {
					roleData.datagrid('loaded');
					roleData.datagrid('reload');
					roleData.datagrid('unselectAll');
					if (data.status == 0) {
						check = true;
						if (roleid != undefined) {
							msg('角色校验完成');
						} else {
							msg(data.msg);
						}
					} else {
						check = false;
						msg(data.msg);
					}
				}
			}
		});
	}

	function msg(mesg) {
		$.messager.show({
			title : "操作提示",
			msg : mesg,
			showType : 'slide',
			timeout : 4500
		});
	}
	function info(obj) {
		console.info(obj);
	}
</script>
</head>
<body style="height: 100%">
<div class="easyui-layout" style="width: 100%;height: 100%;">
	<div data-options="region:'west',title:'组织机构',split:true" style="width:200px;"> 
    	<ul id="treeOrz"></ul>
    </div>
    <div data-options="region:'center'">
    	<div id="roleData" style="height: 100%;"></div>
	</div>
		<div id="toolbar" style="width: 100%;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="save()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">分配菜单</a>
			<input id="searchVal" class="easyui-searchbox" data-options="prompt:'请输入查询条件',searcher:function(){doSearch()}"style="width: 300px;vertical-align: bottom;"></input>
			<div id="mm" style="width:100px">
				<div data-options="name:'rolename'">名称</div>
			</div>
		</div>
	</div>
	</div>
		<div id="toolbars" style="width: 100%;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveMenu()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeDialog()">取消</a>
			<input id="searchVals" placeholder="请输入角色名称" style="width: 100px;vertical-align: bottom;"></input>
		</div>
	</div>
	<!-- 弹出对话框 -->
	<div id="allotDialog" title="分配菜单" style="width:80%;height:70%;">
		<div style="width: 20%;float: left;">
				<div id="subData"></div>
		</div>
		<div>
				<div id="menuData"></div>
		</div>
	</div>
	<input type="hidden" id="oid" value="${oid }"><!-- 组织机构id -->
</body>
</html>