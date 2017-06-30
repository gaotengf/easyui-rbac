<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>CRM</title>
  <!-- 导入主题样式文件 -->
  <link rel="stylesheet" href="/easyui/themes/gray/easyui.css">
  <!-- 图标 -->
  <link rel="stylesheet" href="/easyui/themes/icon.css">
  <link rel="stylesheet" href="/font-awesome/css/font-awesome.min.css">
  <!-- 项目公共样式 -->
  <link rel="stylesheet" href="/css/app.css">
  <!-- 第一脚本：jquery，必须是在第一位 -->
  <script src="/easyui/jquery.min.js" charset="utf-8"></script>
  <!-- easyui的核心 -->
  <script src="/easyui/jquery.easyui.min.js" charset="utf-8"></script>
  <!-- Easyui的扩展 -->
  <script src="/easyui/jquery.edatagrid.js" charset="utf-8"></script>
  <!-- Easyui的国际化 -->
  <script src="/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
  <!-- Easyui的bug修复包 -->
  <script src="/easyui/fixed.js" charset="utf-8"></script>
  <script src="/resource" charset="utf-8"></script>
  <!-- 项目资源 -->
  <script src="/js/app.js" charset="utf-8"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north'" style="height: 70px;overflow: hidden;padding: 0 10px;">
  <div class="user-info">
    <span class="item" id="public_change_info"><i class="fa fa-user"></i> ${s_member.realName}</span>
    <span class="item" id="public_change_password"><i class="fa fa-user"></i> 修改密码</span>
    <a class="item" href="/logout"><i class="fa fa-sign-out"></i> 注销</a>
  </div>
  <h1>CRM学员管理系统</h1>
</div>
<div title="菜单" data-options="region:'west',iconCls:'fa fa-list'" style="width: 200px">
  <div class="easyui-accordion" data-options="fit:true,border:false">
  <#list menus as menu>
	  <#if !menu.parent??>
        <div title="${menu.resName}" data-options="iconCls:'fa fa-cogs'">
          <ul class="crm-menu">
			  <#list menus as child>
				  <#if child.parent?? && child.parent.id == menu.id>
                    <li data-url="${child.menuUrl}">${child.resName}</li>
				  </#if>
			  </#list>
          </ul>
        </div>
	  </#if>
  </#list>
  </div>
</div>
<div data-options="region:'center',href:'/desktop'">

</div>
<div data-options="region:'south'" style="height:20px;text-align: center;line-height: 20px;overflow: hidden;">
  Copyright © 2017 CRM学员管理系统 v1.0 Powered by <a href="https://www.jeasyuicn.com">____′↘夏悸</a>
</div>
</body>
</html>