#起步

基于JDK1.8哦。用了一些新特性，所以最好是使用最新版本的jdk

##### 修改数据源配置
	在application.properties中配好自己的数据源。否则启动会失败！

##### 初始化数据

data.sql为初始化数据SQL脚本。

首次启动后，会自动创建表。表建好后，再执行data.sql导入数据。


本项目基于Spring Boot进行构建，如果你还不熟悉Spring Boot建议先移步下面网址，先学习一下Spring Boot基本使用
`https://bbs.jeasyuicn.com/thread-19.htm`

#最后欢迎大家关注我的新论坛，多多支持
`https://bbs.jeasyuicn.com/`

另外一个分支，前端页面js采用了requirejs进行模块化加载，加载方式更合理。

http://git.oschina.net/gson/easyui-rbac/tree/feature/senior/

#### 案例在线演示：

[案例在线演示](http://crud.jeasyuicn.com/)

##### 项目截图

![输入图片说明](https://git.oschina.net/uploads/images/2017/0629/172822_51c49f23_82.jpeg "在这里输入图片标题")
![输入图片说明](https://git.oschina.net/uploads/images/2017/0629/172839_4dfed0a7_82.png "在这里输入图片标题")
![输入图片说明](https://git.oschina.net/uploads/images/2017/0629/172850_c9e792c7_82.png "在这里输入图片标题")
![输入图片说明](https://git.oschina.net/uploads/images/2017/0629/172858_0532ae19_82.png "在这里输入图片标题")

##### 权限逻辑
![输入图片说明](https://git.oschina.net/uploads/images/2017/0703/095959_d15a9894_82.jpeg "在这里输入图片标题")