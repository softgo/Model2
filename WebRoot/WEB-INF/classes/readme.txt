一 . 本集合框架特色：

1. 使用了：common-pool-1.6.jar ; common-dbcp-1.4.jar ; 使用dbcp来连接数据库，而不是使用jdbc来连接,在spring-application.xml 中有体现...

2. 添加了request ，respone ，session 的获取方式来取值,已经验证，request是可以使用的,在 界面上有展示..

3. 可以控制到按钮的操作,对于没有使用权限的用户，是不可以操作按钮的.

4.分页的特色. 本框架所支持的分页为最全分页：实现了分页显示；去某一特定页;以及页面显示特性.

5. 自动生成代码功能.待续.

6.导入和导出excel功能.待续.

7.动态修改配置文件并让其使用起效.... 动态修改了预警设置...

8.全国联动的设置.




二 . 使用代码生成工具注意点：
1.使用 com.bruce.gogo.codeutil.CodeProductorUtil 实现代码生成的操作。
2.在 configXml 下的 mybatis.xml 加入对应的实体别名。
3.在 configXml 下的 spring-application.xml 的 mapperLocations 下加入对应的 *-mapper.xml 文件。
4.在 configXml 下的 spring-servlet.xml 中加入对应的 <context:component-scan base-package="com.*.controller"/> 扫描类.

