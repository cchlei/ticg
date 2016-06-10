
var path = ctx+"/subsystem";
function doSearch() {
	$('#dg').datagrid('load',{  
        searchVal: $("#searchVal").searchbox("getValue")+":"+$("#searchVal").searchbox("getName")
    }); 
}
//添加
function add(){
	var url= ctx+"/subsystem/addsys";
	var serv= "add";
	createDialog(url,serv,null);
}
//修改
function edit(){
	var selected = checkSelectRow();
	if(selected){
		var url= ctx+"/subsystem/addsys";  
		var serv= "edit";
		createDialog(url,serv,selected);
	}
	
}


function createDialog(url,serv,selected){
	cleanDialog();
	var title=(serv=='add')?'添加子系统':'修改子系统';
    $("#add").dialog({
    	title:title,
    	width: 500,    
        height: 400,  
        top: 115,
        left:350,
        closed: false,    
        cache: false,
        model:true,
        resizable:true,
        closable:false, 
        buttons:[
                 {
                 text:'保存',
                 iconCls:"icon-ok",
                 handler:function(){
                	 submitForm(url);
                 }
                 },
				{
                text:'取消',
                iconCls:"icon-cancel",
                handler:function(){
                	$("#add").dialog('close');
                }
				}
                 ]
    	
    });
    if(selected){		
		$("#addfm #name").val(selected.name);
		$("#addfm #id").val(selected.id);
		$("#addfm #code").val(selected.code);
		$("#addfm #url").val(selected.url);
		$("#addfm #database").val(selected.database);
		$("#addfm #description").val(selected.description);
		$("#addfm #createtime").val(selected.createtime);
		$("input[name='status'][value="+selected.status+"]").prop("checked",true);
	}
    $("#add").css("display","block");
}


	
/**
 * 表单提交
 * @param url
 */
function submitForm(url){
	
	var form = $("#addfm").form({
		url:url,
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(result){
				var res=eval('('+result+')');
				if(res.errorMsg){
					$.messager.show({
						title:"错误",
						msg:res.msg
					});
				}else{
					$("#add").dialog('close');
    				$('#dg').datagrid('reload');
    				$.messager.show({title:"提示",msg:res.msg});
				}
			} 
		
	});
	form.submit();

}
function doSearch(value) {
	$('#searchValhidden').val(value);
	$('#dg').datagrid('load',{  	
        searchVal: $('#searchValhidden').val()
    }); 
}

/**
 * 清除窗口内容
 */
function cleanDialog(){
	$("#addfm").form("clear");
    $("input[name='status'][value='1']").prop("checked",true);
	$("#dialog").dialog('refresh');
}


function del(){
	var url = path+"/delsys"
	if(checkSelectRow()){
		var row=$('#dg').datagrid("getSelected"); 
		if (row){
			$.messager.confirm('提示','确定要删除所选子系统?',function(r){
				if (r){
					$.post(url, {id:row.id}, function(res){
						if (res.status==0){
							$('#dg').datagrid('reload');	
							$.messager.show({title: '提示', msg: res.msg });
						} else {
							$.messager.show({title: '提示', msg: res.msg });
						}
					});
				}
			});
		}
		
	}
}

function checkSelectRow(){
	var row = $("#dg").datagrid('getSelected');
	if(row){
		return row;
	}else{
		$.messager.alert('警告 ','所选数据为空','warning');
		return null;
	}



}


