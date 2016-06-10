var path = ctx + "/organization";
var flag;
// 添加组织机构
function add() {
	var row = $("#orgTree").treegrid('getSelected');
	if (row) {
		var url = ctx + "/organization/addOrganization";
		var serv = "add";
		createDialog(url, serv, row);
	} else {
		$.messager.alert('警告 ', '请先选择父级组织机构', 'warning');
		return null;
	}
}
// 修改组织机构
function edit() {
	var selected = checkSelectRow();
	if (selected) {
		var url = ctx + "/organization/editOrganization";
		var serv = "edit";
		createDialog(url, serv, selected);
	}

}

function createDialog(url, serv, selected) {
	cleanDialog();
	var title = (serv == 'add') ? '添加组织机构' : '修改组织机构';
	$("#add").dialog({
		title : title,
		width : 800,
		height : 400,
		top : 115,
		left : 200,
		closed : false,
		cache : false,
		model : true,
		resizable : true,
		closable : false,
		buttons : [ {
			text : '保存',
			iconCls : "icon-ok",
			handler : function() {
				submitForm(url);
			}
		}, {
			text : '取消',
			iconCls : "icon-cancel",
			handler : function() {
				$("#add").dialog('close');
			}
		} ]

	});

	if (serv == 'edit') {
		$("#addfm #orgnazationName").val(selected.orgnazationName);
		$("#addfm #id").val(selected.id);
		$("#addfm #address").val(selected.address);
		$("#addfm #phone").val(selected.phone);
		$("#addfm #email").val(selected.email);
		$("#addfm #person").val(selected.person);
		$("#addfm #parentId").val(selected._parentId);
		$("#addfm #d").datebox('setValue', selected.date);
	} else if (serv == 'add') {
		$("#addfm #parentId").val(selected.id);
	}
	$("#add").css("display", "block");
}
// 删除组织机构
function deleteOrgnization() {
	var url = path + "/deleteOrganization";
	var checkDeleteurl = path + "/checkDeleteOrganization";
	var row = checkSelectRow();
	if (row) {
		if ((row._parentId == undefined)) {
			$.messager.alert('警告 ', '此节点不能被删除!', 'warning');
		} else if (row.children.length != '0') {
			$.messager.alert('警告 ', '此节点下还有子节点，请先删除子节点!', 'warning');
		} else {
			$.post(checkDeleteurl, {
				orgid : row.id
			}, function(res) {
				if (res.status == 1) {
					$.messager.alert('警告 ', res.msg, 'warning');
				} else {
					$.messager.confirm('提示', '确定要删除所选组织机构?', function(r) {
						if (r) {
							$.post(url, {
								id : row.id
							}, function(res) {
								if (res.status == 0) {
									$('#orgTree').treegrid('reload');
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
		}

	}

}
// 是否选中一行
function checkSelectRow() {
	var row = $("#orgTree").treegrid('getSelected');
	if (row) {
		return row;
	} else {
		$.messager.alert('警告 ', '所选数据为空', 'warning');
		return null;
	}
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
	var form = $("#addfm").form({
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
				$('#orgTree').treegrid('reload');
				$.messager.show({
					title : "提示",
					msg : res.msg
				});
			}
		}

	});
	form.submit();
}
