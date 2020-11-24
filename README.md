# WormHole服务网关管理平台

## 相关快速链接

- [管理台操作说明](https://github.com/deathearth/wormhole/wikis/管理台操作说明)

- [C端对接网关及签名说明](https://github.com/deathearth/wormhole/wikis/c端对接网关及签名说明)

- [回调接口使用说明](https://github.com/deathearth/wormhole/wikis/回调接口使用说明)

- [多环境配置使用说明](https://github.com/deathearth/wormhole/wikis/多环境配置使用说明)

- [网关错误码说明](https://github.com/deathearth/wormhole/wikis/网关错误码说明)

- [WormHole更新说明](https://github.com/deathearth/wormhole/wikis/wormhole更新说明)

> 配置好即可运行
>> GitHub地址：https://github.com/deathearth/wormhole  
>> Gitee地址：https://gitee.com/deathearth/wormhole

### 目录
* <a herf="1">平台背景</a>
* <a herf="2">平台介绍</a>
* <a herf="3">平台特性</a>
* <a herf="4">平台技术结构</a> 
* <a herf="5">平台组件逻辑</a>
* <a herf="6">平台项目结构</a>
* <a herf="7">平台环境要求</a>
* <a herf="8">平台运行说明</a>
* <a herf="9">交流使用</a>

## <a name="1">平台背景</a>

公司原系统有多个业务服务，拆分多个web服务项目和dubbo服务。  
对于C端：不同终端在调用时，需要配置对应的环境IP/域名等，如果有新的服务还需要额外增加配置。  
对于S端：不仅需要关注业务服务的编写，还需要保持和C端的亲密接触。web层和dubbo服务层的编织过于复杂，非常不利于后期的拆分或调整。  
基于以上的情况，所以开始做一个网关项目，统一接口的访问入口，并提供接口的管理。有效的隔离了C端与S端。  
感谢同事的支持、帮助!!！

## <a name="2">平台介绍</a>

&nbsp;&nbsp;&nbsp;&nbsp;WormHole是“虫洞”意思。物理界解释为连接黑洞和白洞的时空隧道。它的结构和计算机网络中的网关类似，作为连接两个世界的枢纽。各管各，不用关心中间做了什么。如下：
<div align="center"><img src="/img/1.gif" width = "300" /></div>


&nbsp;&nbsp;&nbsp;&nbsp;WormHole是一个简单、易用的api管理平台。目的是为了降低后端服务开发与前端调用的耦合性。通过WormHole这一层使整个项目的开发协作更加完善。客户端开发人员从管理平台查找需要的接口信息进行调用，服务端开发人员定义好接口后同步到管理平台中，管理平台可以统一对接口的访问设置等。

<div align="left"><img src="/img/1.jpg" width = "800" /></div>


>> WormHole有两个版本，一个是基于SpringMVC框架、一个是基于原版改造为SpringBoot版本的。

## <a name="3">平台特性</a>
*  网关支持Dubbo服务
*  完善的授权管理（接口权限、web操作权限等）
*  安全机制(验签、IP名单设置)
*  流控机制(sentinel限流、熔断、隔离)
*  处理流程、可插拔机制(组件化配置)
*  支持mock功能，设定预期结果加快联调进度
*  支持接口的快速录入测试功能
*  有完善的用户、角色管理，使用shiro框架
*  接口的有效管理，跨环境策略
*  平台的扩展性，可按需调整



## <a name="4">平台技术结构</a>

<div align="left"><img src="/img/2.png" width = "800" /></div>

> 注：server层是指具体的dubbo服务提供方，框架、插件根据实际需求处理

>> WormHole的SpringBoot版本是 将SpringMVC、Shiro、JSP、Velocity、Servlet等组件与SpringBoot进行整合。部分配置还有优化空间，可按需调整



## <a name="5">平台组件逻辑</a>

<div align="left"><img src="/img/3.png" width = "800" /></div>


<div align="left"><img src="/img/4.png" width = "800" /></div>



*  contextProcessor         存储上下文信息
*  ipProcessor              IP黑白名单设置
*  userAgentProcessor       浏览器用户代理
*  paramProcessor           参数检查验证
*  callbackParamProcessor   回调接口参数检查验证
*  loginProcessor           token信息检查
*  flowControlProcessor     流控限制
*  signProcessor            签名验证
*  authProcessor            权限验证
*  mockProcessor            mock逻辑处理
*  dubboProcessor           泛化调用dubbo服务


<br>
## <a name="6">平台项目结构</a>

#### 1、maven项目结构

    wormehole

        / — wormehole-api         【管理台接口、实体类、传输对象、异常等的定义】

        / — wormehole-domain      【客户端信息、用户token、result对象等的定义】

        / — wormehole-web         【管理台的页面实现、接口实现业务逻辑】

#### 2、项目目录结构

wormehole-api

	/ — com.kaistart.gateway

		/ — api.service         【网关逻辑相关的所有接口定义】

		/ — domain              【网关逻辑相关的实体对象】

		/ — dto                 【业务传输对象】

		/ — exception           【自定义异常类、返回码】

		/ — support             【自定义公共应对象】

			/ — json            【自定义公共响应对象】

			/ — page            【自定义公共分页对象】

			/ — proto           【自定义公共接口】

		/ — tool                【日期转换工具类】



wormehole-domain

	| — com.kaistart.gateway.domain

		| — pojo                【网关的客户端对象、token信息】

		| — response            【网关层的异常、结构定义】



wormehole-web 【JAVA类、配置部分】


	src/main/java

	| — com.kaistart            【主包名】

		| — auth                【管理台核心包】

			| — controller      【管理台用户、角色、权限、资源等控制层】

			| — domain          【管理台相关实体类】

			| — interceptor     【管理台拦截器，url、权限等处理】

			| — mapper          【管理台相关的mapper对象】

			| — service         【管理台相关的service对象与实现】

		| — gateway             【网关核心包】

			| — common          【网关业务公共包】

				| — cache       【缓存工具类】

				| — exception   【网关异常类】	

				| — http        【http请求工具类,支持get请求传body】

				| — util        【工具类，zk,md5,executor】

			| — controller      【网关业务控制层】

			| — config          【网关的必要配置】

			| — dubbo           【网关的签名算法及对外服务】

			| — mgr             【网关的管理台处理】

				| — mapper      【网关相关的mapper对象】

				| — service     【网关相关的service定义及实现】

			| — processor       【网关逻辑的核心组件】

			| — servlet         【网关接口请求的servlet地址】
			
		| — init                【web.xml中部分配置移动到这里，20190622调整】

	src/main/resources

	| — mybatis                 【mybatis相关xml文件】

		| — auth                【控制台核心功能相关】

		| — mgr                 【网关功能相关】

	| — spring                  【核心配置文件】

	| — velocity                【velocity编码配置】

	| — log4j.xml               【日志文件】



	webapp

	| — common                  【公共页面】

	| — velocity                【velocity核心资源】

		| — config              【配置】

		| — image               【图片资源】

		| — js                  【核心js相关】

		| — components          【公用js组件】

		| — pages               【对应页面的js引用文件】

		| — utils               【分页等工具类】

		| — xxx.js              【框架相关js】

	| — lib                     【引用的三方插件资源】

	| — style                   【引用的css样式表】

	| — WEB-INF         

		| — templates           【页面结构】

			| — demo            【样例】

			| — layout          【页面框架布局】	

			| — pages           【管理台页面包】

				| — auth        【用户、角色、权限相关】

				| — gateway     【网关相关页面】

				| — system      【系统查看页面】

				| — zookeeper   【zk相关页面】

		| — web.xml             【核心web.xml配置页面】

	| — 401.jsp                 【过渡页面】

	| — index.jsp               【主页】

	| — login.jsp               【登录页面】
	

> SpringBoot版本的配置文件会少一点。  约定大于配置，应该还有较多优化空间。


## <a name="7">平台环境要求</a>
1. JDK1.8
2. Disconf配套环境【已移除配置】
3. Zookeeper
4. Tomcat容器等
5. Mysql数据库

> SpringBoot版本不需要tomcat,

## <a name="8">平台运行说明</a>

* 下载项目
* 处理以下几个核心配置

	* /gateway.sql 									
	【初始化数据库信息】
	* /resources/config.properties 					
	【修改zk、redis、mysql等的连接】
	* /com/kaistart/gateway/config/MgrConfig.java 	
	【修改不同环境的ip信息，这里这样写主要为了简化使用，可按需迁移到配置中心去】
	* gateway-web的 Context root 为 “/”
* 部署到tomcat容器进行启动, 记得将webcontext-root 改为 '/'
	
	* 正常流程如下，但不限于
	* 访问 http://127.0.0.1:8080/, 输入 admin/123456   或  root/123456
	* 创建网关用户；
	* 创建网关应用；
	* 创建网关接口分组；
	* 创建dubbo服务端接口，并暴露服务；
	* 新增并录入dubbo服务接口的基本信息；
	* 将接口或者接口分组授权给应用；
	* 使用接口测试功能调试所需要用到的接口，验证基本功能；
	* 如果接口需要登录，请在redis中创建usertoken_【uid】为键的值，值对象为com/kaistart/gateway/domain/pojo/UserToken.java(注：可根据需求调整UserToken对象)
	* 如果接口通过测试，客户端可以对接接口；
	* 最简单测试的是无鉴权、无登录类型的接口；
	* 如果需要验签可以根据签名规则进行签名处理；
	* 回调型接口有且只有一个参数，必须为java.util.Map类型，可以查看文档规则说明
	* 个别业务按照需求可扩展或调整
	* 注:这里目前只支持dubbo泛化调用，不支持http协议。
	

> 运行的时候，DEBUG模式查看日志，比较容易处理问题。

## <a name="9">交流使用</a>

### WX:		deathearth / 15858249942
### EMAIL:	353479460@qq.com






****

## 20190228  
* WormHole 独立项目改造完成 

## 20190304  
* gitlab文档处理基本完成

## 20190305  
* WormHole项目 SpringBoot版本改造完成

## 20190622
* 使用servlet 3.0之后的异步特性，配合线程池，增加吞吐量
* 部分web.xml配置，改为注解或类加载方式
* 简单使用lamada表达式处理

