$(function() {
	var dg = $("#member_dg");
	var searcherTpl = $("#member_searcher_tpl");

	// 使用edatagrid，需要而外导入edatagrid扩展
	dg.edatagrid({
		url : '/system/member/list',
		saveUrl : '/system/member/save',
		updateUrl : '/system/member/update',
		destroyUrl : '/system/member/delete',
		emptyMsg : "还未创建用户",
		idField : "id",
		fit : true,
		rownumbers : true,
		fitColumns : true,
		border : false,
		pagination : true,
		ignore:['roles'],
		pageSize : 30,
		columns : [ [ {
			field : 'realName',
			title : '姓名',
			width : 30,
			editor : {
				type : 'validatebox',
				options : {
					required : true
				}
			}
		},{
			field : 'gender',
			title : '性别',
			width : 30,
			editor : {
				type : 'combobox',
				options : {
					data : [
						{text:'男',value:"BOY"},
						{text:'女',value:"GIRL"}
					],
					editable:false,
					required : true,
					panelHeight:'auto'
				}
			},
			formatter:function(val){
				return {"BOY":"男","GIRL":"女"}[val];
			}
		},{
			field : 'userName',
			title : '账号',
			width : 30,
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : 'userName'
				}
			}
		},{
			field : 'telephone',
			title : '电话',
			width : 50,
			editor : {
				type : 'validatebox',
				options : {
					required : true
				}
			}
		},{
			field : 'email',
			title : '邮箱',
			width : 50,
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : 'email'
				}
			}
		},{
			field : 'hiredate',
			title : '入职日期',
			width : 50,
			editor : {
				type : 'datebox',
				options : {
					editable : false
				}
			}
		},{
			field : 'roles',
			title : '角色',
			width : 80,
			formatter:function(val,row,index){
				var roleList = [];
				$.each(val,function(){
					roleList.push(this.roleName);
				});
				return roleList.join(',');
			}
		}, {
			field : 'status',
			title : '状态',
			width : 20,
			align : 'center',
			editor : {
				type : 'checkbox',
				options : {
					on : true,
					off : false
				}
			},
			formatter : function(val, row) {
				return val ? "可用" : "禁用";
			}
		} ] ],
		toolbar : authToolBar({
			"member-create" : {
				iconCls : 'fa fa-plus-square',
				text : "创建",
				handler : function() {
					dg.edatagrid('addRow');
				}
			},
			"member-save" : {
				iconCls : 'fa fa-save',
				text : "保存",
				handler : function() {
					dg.edatagrid('saveRow');
				}
			},
			"member-delete" : {
				iconCls : 'fa fa-trash',
				text : "删除",
				handler : function() {
					dg.edatagrid('destroyRow');
				}
			}
		},{
			iconCls : 'fa fa-mail-reply',
			text : "取消",
			handler : function() {
				dg.edatagrid('cancelRow');
			}
		},{
			iconCls : 'fa fa-refresh',
			text : "刷新",
			handler : function() {
				dg.edatagrid('reload');
			}
		}),
		onError : function(index, data) {
			// 操作请求发送错误
			console.error(data);
		}
	});

	var toolbar = dg.datagrid("getPanel").find('.datagrid-toolbar');

	toolbar.prepend(searcherTpl.html());

	$("#member_searcher").searchbox({
		searcher : function(value, name) {
			if (value) {
				dg.datagrid("load", {
					userName : value
				})
			} else {
				dg.datagrid("load", {})
			}
		},
		prompt : '请输入用户名'
	});
});