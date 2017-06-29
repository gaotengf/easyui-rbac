#起步

##### 修改数据源配置
	在application.properties中配好自己的数据源。否则启动会失败！

##### 初始化数据

data.sql为初始化数据SQL脚本。

首次启动后，会自动创建表。表建好后，再执行data.sql导入数据。