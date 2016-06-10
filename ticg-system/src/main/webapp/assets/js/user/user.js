var path = ctx +"/user";

var treeOrg ;

var datagridUser ;

var datagridRole ;

var win;

//管理员所属机构
var sysUserOrgid ;

var orgid;

var userid;
$(function(){
	sysUserOrgid = $("#oid").val();
	$("#searchVal").css("display","none");
	if(sysUserOrgid==0){
		initTree();
	}else{		
		initDataGrid(sysUserOrgid);
	}
});

/**
 * 加载组织结构树
 */
function initTree(){
	treeOrg = $("#treeOrz").tree({
		url:ctx+"/organization/orgs",
		onSelect:function(node){
			orgid = node.id;
			if(!datagridUser){
				initDataGrid(orgid);
			}
			datagridUser.datagrid('load',{
				organizationid:orgid,
				searchVal: $('#searchValhidden').val()
			});
		}
	});
}

/**
 * 用户列表加载
 */

function initDataGrid(organid){
	$("#welcome").css("display","none");
	datagridUser=$("#dg").datagrid({
		height : '100%',
		width: '100%',
		url: path+'/userlist',
		idField:"id",
		title : '用户管理',
		rownumbers:true,
		pageSize : 20,
		pagination : true,
		fitColumns:true,
		sortOrder : 'desc',
		queryParams:{
			organizationid:organid
		},
		onSelect:function(index,row){
			userid = row.id;
		},
		toolbar: [
		          {
		        	  text:'新增',
		        	  iconCls:'icon-add',
		        	  handler:function(){
		        		  addOne(organid);
		        	  }
		          },
		          {
		        	  text:'修改',
		        	  iconCls:'icon-edit',
		        	  handler:function(){
		        		  modfied(organid);
		        	  }
		          },
		          {
		        	  text:'删除',
		        	  iconCls:'icon-remove',
		        	  handler:function(){
		        		  deleteOne();
		        	  }
		          },
		          {
		        	  text:'分配系统角色',
		        	  iconCls:'icon-man',
		        	  handler:function(){
		        		  var roleurl = ctx+"/userrole";
		        		  var type="Role";
		        		  initWin(roleurl,type,organid);
		        	  }
		          },
		          {
		        	  text:'分配共享角色',
		        	  iconCls:'icon-man',
		        	  handler:function(){
		        		  var roleurl = ctx+"/userShareRole"
		        		  var type="shareRole";
		        		  initWin(roleurl,type,organid);
		        	  }
		          }
		          ],
		          striped: true,
		          singleSelect:true,
		          collapsible :true,
		          columns: [[
		                     {field:'username',title:'用户名',sortable:true,width:295},
		                     {field:'email',title:'电子邮件',sortable:true,width:295},
		                     {field:'mobile',title:'联系电话',sortable:true,width:295},
		                     {field:'registTime',title:'注册时间',sortable:true,width:295}
		                     ]],
		                     onLoadSuccess : function(data,index){
		                    	 if(!data){
		                    		 $(this).datagrid('loadData', { total: 0, rows: [] });
		                    	 }
		                     }
	});
	var menuVal = "<div data-options=name:'all'>全部</div>"
		$('#mm').html($('#mm').html()+menuVal);
	var fields =  $('#dg').datagrid('getColumnFields');
	for(var i=0; i<fields.length-1; i++){
		var opts = $('#dg').datagrid('getColumnOption', fields[i]);
		if(opts.title=="username"){
			var muit = "<div data-options=name:'"+  fields[i] +"'>"+ opts.title +"</div>";
			$('#mm').html($('#mm').html()+muit);
		}
	}
	$('#searchVal').searchbox({  
		menu:'#mm',
		searcher:function(value,name){
			var v= name+':'+value;
			doSearch(v,organid);
		},
		prompt:'输出查询条件'
	});
	var serechbox_td = $("<td></td>").append($('.searchbox '));
	var serchVal_td =  $("<td></td>").append($('#searchVal'));
	datagridUser.parents(".datagrid:first").find(".datagrid-toolbar table td:last").after(serechbox_td).after(serchVal_td);

}
/**
 * 初始化window
 */
function initWin(roleurl,type,organid){
	if(!checkSelectRow()){
		return;
	}
	var title=(type=='Role')?'分配系统角色':'分配共享角色';
	var option = {};
	initOptions(option,type);
	$('#win').window({   
		title:title,
		width:"600",    
		height:"400",    
		modal:true,
		onOpen:function(){
			datagridRole = $("#roleDg").datagrid({
				height : '100%',
				width: '100%',
				url: roleurl+"/find",
				rownumbers:true,
				pageSize : 50,
				pagination : true,
				sortOrder : 'desc',
				checkOnSelect:true,
				queryParams:{
					userid:userid,
					orgid:organid
				},
				onLoadSuccess:function(data){
					var rowData = data.rows;
					$.each(rowData, function (idx, val) {
						if (val.isCheck) {
							datagridRole.datagrid("selectRow", idx);
						}
					});
				},
				toolbar: [
				          {
				        	  text:'保存',
				        	  iconCls:'icon-save',
				        	  handler:function(){
				        		  var registurl=roleurl+"/sync";
				        		  saveRoleRegist(registurl,type);
				        	  }
				          },
				          {
				        	  text:'取消',
				        	  iconCls:'icon-cancel',
				        	  handler:function(){
				        		  $('#win').window('close');
				        	  }
				          },
				          {
				        	  text:'查看详细',
				        	  iconCls:'icon-more',
				        	  handler:function(){
				        		  alert('预留按钮!');
				        	  }
				          }
				          ],
				          striped: true,
				          collapsible:true,
				          columns:option.columns
			});
		}
	});

	var row = datagridUser.datagrid('getSelected');
	$("#userid").val(row.id);
	$("#win").css("display","block");
}
/**
 * 动态生成datagrid列
 * @param options
 * @param type
 */
function initOptions(option,type){
	if(type=="Role"){
		option.columns=[[
		                 {field:'isCheck',checkbox:true,title:'是否分配'},
		                 {field:'rolename',title:'角色名称',
		                	 sortable:true,width:300,
		                	 formatter:function(value,rec,index){   //格式化函数添加一个操作列							
		                		 if(rec.role){
		                			 return rec.role.rolename;
		                		 }		   
		                	 }  
		                 }
		                 ]];
	}
	if(type=="shareRole"){
		option.columns=[[
		                 {field:'isCheck',checkbox:true,title:'是否分配'},
		                 {field:'rolename',title:'角色名称',
		                	 sortable:true,width:300,
		                	 formatter:function(value,rec,index){   //格式化函数添加一个操作列							
		                		 if(rec.shareRole){
		                			 return rec.shareRole.rolename;
		                		 }		   
		                	 }  
		                 }
		                 ]];
	}
}

function checkSelectRow(){
	var row = datagridUser.datagrid('getSelected');
	if(row){
		return true;
	}else{
		$.messager.alert('警告 ','亲，能先选择一条数据么？','warning');
		return false;
	}
}

function addOne(organid){
	var url= path+"/registUser";
	var serv= "add";
	createDialog(url,serv,organid);
}

/**
 * 初始化用户编辑对话框
 */
function createDialog(url,serv,orgid){
	var title=(serv=='add')?'添加用户':'修改用户';
	$("#dialog").dialog({
		title:title,
		width: 400,    
		height: 200,    
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
		        		 cleanDialog();
		        	 }
		         }
		         ]

	});
	$("input[name='orgid'").val(orgid);
	$("#dialog").css("display","block");
}

/**
 * 清除窗口内容
 */
function cleanDialog(){
	$("#fm").form("clear");
	$("#dialog").dialog('refresh');
	$("#dialog").dialog('close');
}

function modfied(organid){
	if(!checkSelectRow()){
		return;
	}
	var url= path+"/modfiyUser";
	var serv = "update";
	var row = datagridUser.datagrid('getSelected');
	createDialog(url,serv,organid);
	$("#fm").form("load",row);
}

function submitForm(url){
	var form = $("#fm").form({
		url:url,
		success:function(result){
			var res=eval('('+result+')');
			if(res.errorMsg){
				$.messager.show({
					title:"错误",
					msg:res.msg
				});
			}else{
				$("#dialog").dialog('close');
				$('#dg').datagrid('reload');
				$.messager.show({title:"提示",msg:res.msg});
			}
			cleanDialog();
		} 

	});
	form.submit();
}

function deleteOne(){
	var url = path+"/removeUser"
	if(checkSelectRow()){
		var row=$('#dg').datagrid("getSelected"); 
		if (row){
			$.messager.confirm('提示','确定要删除所选用户?',function(r){
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

/**
 *  搜索
 * @param value
 */
function doSearch(value,organid) {
	$('#searchValhidden').val(value);
	datagridUser.datagrid('load',{  	
		organizationid:organid,
		searchVal: $('#searchValhidden').val()
	}); 
}
/**
 * 保存分配角色
 */
function saveRoleRegist(registurl,type){
	var rows = datagridRole.datagrid('getSelections');
	if(type=='Role'){
		if(!rows){
			$.messager.alert('警告 ','必须给用户分配角色!','warning');
			return false;
		}
	}
	var roleArr = new Array();
	var userid =$("#userid").val();
	if(rows && rows.length!=0){
		for(var k=0; k<rows.length; k++){
			var object = rows[k];
			if(type=='Role'){
				roleArr.push(object.role.id);
			}else{
				roleArr.push(object.shareRole.id);
			}
		}
	}else{
		roleArr.push(-1);
	}
	$.post(registurl,{'roles':roleArr,userid},function(data){
		if (data.status==0){	
			$.messager.show({title: '提示', msg: data.msg });
			datagridRole.datagrid('reload');
		} else {
			$.messager.show({title: '提示', msg: data.msg });
		}
	},"json");
}
