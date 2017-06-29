(function(w){
	var resourceKey = ${resourceKey};
	/**
	 * 工具栏的权限过滤
	 * @param tools
	 * @returns
	 */
	function authToolBar(tools) {
		var toolbars = [];

		$.each(tools, function(key, btn) {
			if (resourceKey.indexOf(key) != -1) {
				toolbars.push(btn);
			}
		});
		return toolbars;
	}
	w.authToolBar = authToolBar;
})(window);