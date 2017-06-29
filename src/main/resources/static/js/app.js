$(function() {
	$.ajaxSetup({
		statusCode : {
			401 : function() {
				$.messager.alert("权限提醒","您没有权限访问此资源！");
			}
		}
	});
	// 绑定菜单事件
	$(".crm-menu").on('click','li',function() {
		if (!$(this).hasClass('selected')) {
			// 获取center对应的panel对象
			var center = $("body").layout("panel", "center");
			// 刷新center区域
			center.panel("refresh", this.dataset.url);
			// 选中状态
			$(this).siblings('.selected').toggleClass().end().addClass('selected');
		}
	});
});