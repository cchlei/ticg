
function doSearch() {
	$('#dg').datagrid('load',{  
        searchVal: $("#searchVal").searchbox("getValue")+":"+$("#searchVal").searchbox("getName")
    }); 
}
$(function(){
	
	obj={
	    editRow:undefined,
	    currows:undefined,
		 //添加一行
		add:function(){
			$("#save,#redo").show();
			 if(this.editRow==undefined){
					$("#dg").datagrid('insertRow',{
						index:0,
						row:{
							
						}
					});
				//将第一行设置为可编辑状态
				$("#dg").datagrid('beginEdit',0);
				this.editRow = 0;//不会同时添加2个
			  }
		},
	    save: function(){
	    	var rows = $("#dg").datagrid('endEdit',this.editRow);
	    },
	    redo:function(){
	    	$("#save,#redo").hide();
	    	this.editRow=undefined;
	    	//取消时回滚
	    	$("#dg").datagrid('rejectChanges');
		},
		edit:function(){
	     	var rows =	$("#dg").datagrid('getSelections');
	     	if(rows.length==1){
	     		 if(obj.editRow!=undefined){//保存
					  $("#dg").datagrid('endEdit',this.editRow);
				  }
				  //编辑
				  if(obj.editRow==undefined){
					  var index = $("#dg").datagrid('getRowIndex',rows[0]);
					  $("#save,#redo").show();
					  $("#dg").datagrid('beginEdit',index);//打开对应行的编辑模式
					  this.editRow = index;
					  $("#dg").datagrid('unselectRow',index);//取消对应行的选择--清空选中状态
				  }
	     	}else{
	     		$.messager.alert('警告',"请选择一行数据进行修改！",'warning');
	     	}
			
		},
		del:function(){
			var rows =	$("#dg").datagrid('getSelections');
	     	if(rows.length>0){
	     		$.messager.confirm('确定操作',"您确定要删除这一行吗？",function(flag){
	     			if(flag){
	     				var ids =[];
	     				for(var i=0;i<rows.length;i++){
	     					ids.push(rows[i].id);
	     				}
	     				/*alert(ids.join(','));*/
	     				$.ajax({
	     					type:'POST',
	     					url:ctx+'/menu/delmenu',
	     					data:{
	     						ids: JSON.stringify(ids.join(','))
	     					},
	     				    beforeSend:function(){
	     				    	$("#dg").datagrid('loading');//显示载入
	     				    },
	     				    success:function(data){
	     				    	if(data){
	     				    		$("#dg").datagrid('loaded');//隐藏载入
	     				    		$("#dg").datagrid('load');//刷新页面
	     				    		$("#dg").datagrid('unselectAll');//刷新页面
	     				    		$.messager.show({
	     				    			title:'提示',
	     				    			msg:data.msg
	     				    		});
	     				    	}
	     				    	
	     				    }
	     				});
	     			}
	     			
	     		});
	     		
	     	}else{
	     		$.messager.alert('警告',"请选择一行数据进行删除！",'info');
	     	}
		}
	
	};
	$("#dg").datagrid({
		  width:'100%',
		  height:'100%',
		  title:'菜单管理',
		  sortOrder:'desc',
          pagination:true, 
		  fitColumns:true,
		  rownumbers:true,
		  singleSelect:true,//因为要处理菜单的操作，必须单选
		  striped:true,
		  collapsible:true,
		  toolbar:'#toolbar',
		  method:'post',
		  pageSize:20,
		  url:ctx+'/menu/list/'+systemid,
		  onAfterEdit:function(rowIndex,rowData,changes){
			$("#save,#redo").hide();
		    obj.editRow = undefined;
		   // console.info(rowData);
			var inserted = $('#dg').datagrid('getChanges', 'inserted');
			var updated = $('#dg').datagrid('getChanges', 'updated');
			var url1 = info =  '';
			var boo = false;
			//新增菜单
			if (inserted.length > 0) {
				url1 = ctx+'/menu/addmenu/'+systemid;
				info = '新增';
				boo = true;
			}
			
			//修改菜单
			if (updated.length > 0) {
				url1 =  ctx+'/menu/addmenu/'+systemid; 
				info = '修改';
				boo = true;
			}
			 console.info(rowData);
			if(boo){//如果修改后提交
				$.ajax({
					type : 'POST',
					url : url1,
					dataType: 'json',
					data : {
						name : rowData.name,
						code : rowData.code,
						url : rowData.url,
						id : rowData.id,
					},
					beforeSend : function () {
						$('#dg').datagrid('loading');
//						checkName();
					},
					success : function (data) {
						if (data) {
							$('#dg').datagrid('loaded');
							$('#dg').datagrid('load');
							$('#dg').datagrid('unselectAll');
							$.messager.show({
								title : '提示',
								msg : data.msg,
							});
							obj.editRow = undefined;
						}
					},
				});
			}else{
				var rows = $("#dg").datagrid('endEdit',this.editRow);
			}
			
		  },
		  onDblClickRow:function(rowIndex,rowData){//双击修改
			  if(obj.editRow!=undefined){//保存
				  $("#dg").datagrid('endEdit',obj.editRow);
			  }
			  //编辑
			  if(obj.editRow==undefined){
				  $("#save,#redo").show();
				  $("#dg").datagrid('beginEdit',rowIndex);
				  obj.editRow = rowIndex;
			  }
		  },
		  columns:[[
					{
					    field:'id',
					    width:50,
					    checkbox:true
					   
					},
					 {
				        field:'name',
				        width:50,
				        title:'名称',
				        editor:{
					    	type:'validatebox',
					    	options:{
					    		required:true,
					    		validtype:'length[1,5]',
					    	}
					    }
		            }
					,
					 {
				        field:'code',
				        width:50,
				        title:'编码',
				        editor:{
					    	type:'validatebox',
					    	options:{
					    		required:true
					    	}
					    }
		            }
					,
					 {
				        field:'url',
				        width:50,
				        title:'URL',
				        editor:{
					    	type:'validatebox',
					    	options:{
					    		required:true
					    	}
					    }
		            }
		  ]]
		  
    });
	
});
/*****************关于操作部分**************************/

//新增或修改处理
function saveOrUpdate(){
	var row=$('#dg').datagrid("getSelected"); 
	console.log(row);
 	$('#fm').form('submit', {   
 		url:ctx+"/authc/addauthc/"+row.id,
 	    onSubmit: function(){
 	    	return $(this).form('validate');
 	    },   
 	    success:function(result){
			var res = eval('(' + result + ')');
			if (res.status==0){
				$('#dlg').dialog('close');
				$('#dgAuthc').datagrid({url:ctx+'/authc/list/'+row.id});
				$('#dgAuthc').datagrid('reload');
				$.messager.show({title: '提示', msg: res.msg });
			} else {
				$.messager.show({title: '提示', msg: res.msg });
			}
 	    }   
 	});  
}


function clearForm(){
    $('#fm').form('clear');
    $("input[name='status'][value='1']").prop("checked",true);
}

//弹出新增框
function addAuthc(){
	$('#dlg').dialog('open').dialog('setTitle', '新增操作');
	$('#fm').form('clear');
	$("input[name='status'][value='1']").prop("checked",true);
}

//弹出修改窗口并赋值
function editAuthc(){
	var row=$('#dg').datagrid("getSelected"); 
	var ahthcrow=$('#dgAuthc').datagrid("getSelected"); 
	if (ahthcrow){
		console.log(row);
		console.log(ahthcrow);
		$('#dlg').dialog('open').dialog('setTitle', '编辑操作');
		$('#fm').form('clear');
		$('#fm').form('load',ahthcrow);
	    $("input[name='status'][value="+ahthcrow.status+"]").prop("checked",true);
	   // $('#fm').form();
	//	$.post(ctx+"/authc/addauthc/"+row.id,'json');
		
	}else{
		$.messager.show({
			title: '提示',
			msg: "请选择要编辑的操作！"
		});
	}
}
//删除
function  delAuthc(){
	var row=$('#dgAuthc').datagrid("getSelected"); 
	if (row){
		$.messager.confirm('提示','确定删除此操作?',function(r){
			if (r){
				$.post(ctx+"/authc/delauthc",{id:row.id},function(res){
					if (res.status==0){
						$('#dgAuthc').datagrid('reload');
						$.messager.show({title: '提示',msg: res.msg});
					} else {
						$.messager.show({title: '提示',msg: res.msg });
					}
				},'json');
			}
		});
	}else{
		$.messager.show({title: '提示',msg: "请选择要删除的操作！"});
	}
}
