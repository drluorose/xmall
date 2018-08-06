<#if @useDubbo@ == true>
spring:
  dubbo:
    application:
      name: adx-exchange #配你的应用编码，比如adx-exchange
      logger: slf4j #统一配slf4j
      owner: mayong #配你项目责任人的名字，禁止用中文
      organization: wsd-java #配你的部门代码，禁止用中文
    zkRegistry:
      address: zookeeper://10.1.51.39:2181?backup=10.1.51.40:2181,10.1.51.41:2181 #zookeeper地址
    dubboProtocol:
      name: dubbo # 统一用dubbo协议
      port: 43800 #必须用指定端口段内的端口，并进行登记
      dispatcher: message #只有请求响应消息派发到线程池，其余连接断开事件、心跳消息等直接在IO线程上处理
    monitor:
      protocol: registry #从注册中心获取监控上报服务地址
    version: 1.0.0 #  你的服务版本
    group: dev #你的环境，各环境不同
</#if>

<#if @useMybatis@ == true>
# TODO 改成【联调环境】的数据库配置
---
spring:
  datasource:
    druid:
      url: jdbc:mysql://@databaseIp@:@databasePort@/@databaseName@
      username: @databaseUsername@
      password: @databasePassword@
      driver-class-name: com.mysql.jdbc.Driver
      validationQuery: "select 1"
      filter:
        stat:
          enabled: true
        config:
          enabled: true
        encoding:
          enabled: true
</#if>

<#if @useOa@ == true>
<#noparse>
# OA鉴权相关地址文档：http://doc.dz11.com/ddse/preview/share/d342d26f681b0c97d942?sid=30
# 注意：这里接的是新OA，而不是老OA；并且，本代码暂时不支持数据权限
# 申请接OA，可以找王军帮忙开
# 申请接入权限系统，可以找吴启华帮忙开
#OA配置
OAService:
  serviceDomainOuter: https://oasso.douyucdn.cn
  serviceDomain: http://wsd-oasso-java-srv.bj01
  cpsDomain: https://dymcps.douyucdn.cn
  clientId: #TODO 填上你自己的应用id
  clientSecret:  #TODO 填上你自己的应用密钥
  redirectUrl: ${OAService.cpsDomain}/login/callback
  grantType: authorization_code
  authorizeUrl: ${OAService.serviceDomainOuter}/oauth2/authorize
  accessTokenUrl: ${OAService.serviceDomain}/oauth2/accessToken
  userInfoUrl: ${OAService.serviceDomain}/oauth2/profile
  oaLogoutUrl: ${OAService.serviceDomainOuter}/logout
  logoutUrl: ${OAService.cpsDomain}/logout/callback
  checkUserPermitUrl: ${OAService.serviceDomain}/api/authorapi/checkuserpermit
  enabled: true

# 权限系统配置
permit:
  pms:
    enabled: true
    rootCode: root # 上报的根节点的代码，推荐使用：root
    checkLevel: module # 权限检查级别：一共2个级别，分别是项目级（project）、功能模块级（module），默认为功能模块级（module）
    checkRule: gentle # 权限检查模式：一共2个模式，分别是严格模式（strict）、宽容模式（gentle），默认为严格模式strict
    publicKey: #TODO 填上你自己在权限系统的应用公钥
    privateKey: # TODO 填上你自己在权限系统的应用私钥
    uploadUrl: http://wsd-permit-php-srv.bj01/api/permission/report # 上报权限点接口地址
    downloadUrl: http://wsd-permit-php-srv.bj01/api/permission/get # 获取权限接口地址
    packageToScan: com.douyu.wsd.cps.admin.facade.controller # 自动扫描权限注解@PmsKey的扫描范围
    interceptorPathPatterns: /api/** # 执行权限校验的路径
    report:
    uid: 1 # 进行权限上报的uid
    cacheExpireAfterWrite: 20000 # 权限的本地的缓存时间，单位是毫秒
</#noparse>
</#if>