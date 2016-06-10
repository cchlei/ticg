var path = ctx + "/subsystemRegister";

var treeOrg;

var datagridUser;

var queryParam;

var selected;

$(function() {
	queryParam = {
		orgid : ""
	};

	initTree();
	initDataGrid(queryParam.orgid);
});

/**
 * 加载组织结构树
 */
function initTree() {
	treeOrg = $("#treeOrz").tree(
			{
				url : ctx + "/organization/orgs",
				onSelect : function(node) {
					queryParam.orgid = node.id;
					$('#subsystem').combobox(
							'reload',
							ctx + "/subsystemRegister/list?orgid="
									+ queryParam.orgid);
					initDataGrid(queryParam.orgid);
					selected = node;
				}

			});
}


function initDataGrid(orgId) {
	var datagridpath = ctx + '/subsystemRegister/findSubsystemRegisterById';
	if (orgId == "") {
		datagridpath = ctx + '/subsystemRegister/findAllSubsystemRegister';
		datagridUser = $("#dg").datagrid({
			height : '100%',
			width : '100%',
			url : datagridpath,
			idField : "id",
			title : '子系统注册列表',
			rownumbers : true,
			pageSize : 20,
			pagination : true,
			sortOrder : 'desc',
			queryParams : {
				organizationid : orgId,
				status : 0
			},
			striped : true,
			singleSelect : true,
			collapsible : true,
			columns : [ [ {
				field : 'organizationname',
				title : '组织机构名称',
				width : 150,
				sortable : true,
				formatter : function(value, row, index) {
					if (row.organization) {
						return row.organization.orgnazationName;
					}
				}
			}, {
				field : 'name',
				title : '子系统名称',
				width : 200,
				sortable : true,
				formatter : function(value, row, index) {
					if (row.organization) {
						return row.subsystem.name;
					}
				}
			}, {
				field : 'startDate',
				title : '开始使用时间',
				width : 200,
				sortable : true,

			}, {
				field : 'endDate',
				title : '结束使用时间',
				width : 200,
				sortable : true,

			}, {
				field : 'remark',
				title : '备注',
				width : 190,
				sortable : true,

			} ] ],

			onLoadSuccess : function(data, index) {
				if (!data) {
					$(this).datagrid('loadData', {
						total : 0,
						rows : []
					});
				}
			},
		});
	} else {
		datagridUser = $("#dg").datagrid({
			height : '100%',
			width : '100%',
			url : datagridpath,
			idField : "id",
			title : '子系统注册列表',
			rownumbers : true,
			pageSize : 20,
			pagination : true,
			sortOrder : 'desc',
			queryParams : {
				organizationid : orgId,
				status : 0
			},
			toolbar : [ {
				id : 'addSub',
				text : '新增',
				iconCls : 'icon-add',
				handler : function() {
					addSystem();
				}
			}, {
				id : 'deleteSub',
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					deleteSystem();
				}
			} ],
			striped : true,
			singleSelect : true,
			collapsible : true,
			columns : [ [ {
				field : 'organizationname',
				title : '组织机构名称',
				width : 150,
				sortable : true,
				formatter : function(value, row, index) {
					if (row.organization) {
						return row.organization.orgnazationName;
					}
				}
			}, {
				field : 'name',
				title : '子系统名称',
				width : 200,
				sortable : true,
				formatter : function(value, row, index) {
					if (row.organization) {
						return row.subsystem.name;
					}
				}
			}, {
				field : 'startDate',
				title : '开始使用时间',
				width : 200,
				sortable : true,

			}, {
				field : 'endDate',
				title : '结束使用时间',
				width : 200,
				sortable : true,

			}, {
				field : 'remark',
				title : '备注',
				width : 190,
				sortable : true,

			} ] ],

			onLoadSuccess : function(data, index) {
				if (!data) {
					$(this).datagrid('loadData', {
						total : 0,
						rows : []
					});
				}
			},
		});
	}
}

function addSystem() {
	var url = ctx + '/subsystemRegister/addSubsystemRegister';
	var serv = "add";
	createDialog(url, serv, selected);

}
function deleteSystem() {
	var url = ctx + "/subsystemRegister/deleteSubsystemRegister";
	var row = $("#dg").datagrid('getSelected');
	var checkDeleteurl = path + "/checkDeleteSubsystemRegister";
	if (row) {
		$.post(checkDeleteurl, {
			id : row.id
		}, function(res) {
			if (res.status == 1) {
				$.messager.alert('警告 ', res.msg, 'warning');
			} else {
				$.messager.confirm('提示', '确定要删除所选行?', function(r) {
					if (r) {
						$.post(url, {
							id : row.id
						}, function(res) {
							if (res.status == 0) {
								$('#dg').datagrid('reload');
								$('#subsystem').combobox(
										'reload',
										ctx + "/subsystemRegister/list?orgid="
												+ selected.id);
								$.messager.show({
									title : '提示',
									msg : res.msg
								});
							} else {
								$.messager.show({
									title : '提示',
									msg : res.msg
								});
							}
						});
					}
				});
			}
		});
	} else {
		$.messager.alert('警告 ', '所选数据为空', 'warning');
		return null;

	}
}

function createDialog(url, serv, selected) {
	cleanDialog();
	var title = (serv == 'add') ? '注册子系统' : '修改子系统和注册';
	$("#add").dialog({
		title : title,
		width : 800,
		height : 400,
		top : 115,
		left : 250,
		closed : false,
		cache : false,
		model : true,
		resizable : true,
		closable : false,
		buttons : [ {
			text : '保存',
			iconCls : "icon-ok",
			handler : function() {
				var startDate=$("#startDate").datebox('getValue');
				var endDate=$("#endDate").datebox('getValue');;
				if(startDate>=endDate){
					$.messager.alert('警告 ', '开始时间不能小于结束时间', 'warning');
				}else{
					submitForm(url);
				}
				
			}
		}, {
			text : '取消',
			iconCls : "icon-cancel",
			handler : function() {
				$("#add").dialog('close');
			}
		} ]

	});

	$("#addfm #id").val(selected.id);
	$('#startDate').datebox('setValue', selected.startDate);
	$('#endDate').datebox('setValue', selected.endDate);
	$("#add").css("display", "block");
}
/**
 * 清除窗口内容
 */
function cleanDialog() {
	$("#addfm").form("clear");
	$("#dialog").dialog('refresh');
}

/**
 * 表单提交
 * 
 * @param url
 */
function submitForm(url) {
	var form = $("#addfm").form(
			{
				url : url,
				success : function(result) {
					var res = eval('(' + result + ')');
					if (res.errorMsg) {
						$.messager.show({
							title : "错误",
							msg : res.msg
						});
					} else {
						$("#add").dialog('close');
						$('#dg').datagrid('reload');
						$('#subsystem').combobox(
								'reload',
								ctx + "/subsystemRegister/list?orgid="
										+ selected.id);
						$.messager.show({
							title : "提示",
							msg : res.msg
						});
					}
				}

			});
	form.submit();
}
