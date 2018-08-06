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
    group: live #你的环境，各环境不同
</#if>

<#if @useMybatis@ == true>
# TODO 改成【测试环境】的数据库配置
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
</#if>