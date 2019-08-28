chao-cloud-micro: springcloud nacos+seata+mybatis-plus  
=====

<p>
  <a href="https://github.com/996icu/996.ICU/blob/master/LICENSE">
    <img alt="996icu" src="https://img.shields.io/badge/license-NPL%20(The%20996%20Prohibited%20License)-blue.svg">
  </a>
  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>
</p>

------

以 spring-cloud 为基础，集成feign、nacos、seata、mybatis-plus 整合单机版分布式事务

	chao-cloud-micro
			│
		    ├─api 		//feign接口
		    ├─gateway 		//微服务网关  8000
		    ├─provider 		//服务提供者 8001
		    └─consumer		//服务消费者 8002
		
------  

## Step1-导入sql（mysql）

- 新建数据源 test，导入 consumer/resources/test.sql
- 新建数据源 seata，导入 seata/resources/seata.sql

## Step2-配置nacos（[nacos@安装手册](https://nacos.io/zh-cn/docs/quick-start.html)）

##### 1.配置jvm增加启动参数（请新增chao-cloud命名空间）

```
-Dspring.cloud.nacos.config.server-addr=   #你的nacos服务地址 
-Dspring.cloud.nacos.config.namespace=  #你的nacos命名空间（这里是 chao-cloud命名空间  的那个随机字符串）
```

##### 2.在nacos新增命名空间 chao-cloud 生成以下5个配置

- Data ID：chao-cloud-ext-feign.yaml 		 
- Group：chao-cloud

```
hystrix.command.default.execution.timeout.enabled: true
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds: ${ribbon.ReadTimeout}

ribbon.ReadTimeout: 120000
ribbon.ConnectTimeout: ${ribbon.ReadTimeout}

chao:
  cloud:
    feign:
      request:
        read-timeout: 120
        connect-timeout: 120
        write-timeout: 120
        keep-alive-duration: 5 #分钟
        max-idle-connections: 10

feign:
  httpclient:
    enabled: false
  hystrix:
    enabled: true
  okhttp:
    enabled: true
  compression:
    request:
      enabled: true
      mime-types:
      - text/xml 
      - application/xml 
      - application/json
      min-request-size: 2048
    response:
      enabled: true
```

- Data ID：chao-cloud-ext-mysql.yaml		 
- Group：chao-cloud

```
mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
    log-impl:  com.chao.cloud.common.extra.mybatis.log.Slf4jLogImpl
```

- Data ID：chao-cloud-ext-gateway.yaml		 
- Group：chao-cloud

```
server:
  port: 8000

spring:
  cloud:
    nacos:
      discovery:
        server-addr: ${spring.cloud.nacos.config.server-addr}
        namespace: ${spring.cloud.nacos.config.namespace} 
```

- Data ID：chao-cloud-ext-provider.yaml  		 
- Group：chao-cloud

```
server:
  port: 8001

spring:
  cloud:
    nacos:
      discovery:
        server-addr: ${spring.cloud.nacos.config.server-addr}
        namespace: ${spring.cloud.nacos.config.namespace}
  datasource:
    url: jdbc:mysql://127.0.0.1/seata?useSSL=false&useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      validation-query: SELECT 1
      test-on-borrow: true
      connection-init-sqls: SET NAMES utf8mb4;
      filter:
        wall:
          enabled: false
```

- Data ID：chao-cloud-ext-consumer.yaml  		
- Group：chao-cloud

```
server:
  port: 8002

spring:
  cloud:
    nacos:
      discovery:
        server-addr: ${spring.cloud.nacos.config.server-addr}
        namespace: ${spring.cloud.nacos.config.namespace}
  datasource:
    url: jdbc:mysql://127.0.0.1/test?useSSL=false&useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      validation-query: SELECT 1
      test-on-borrow: true
      connection-init-sqls: SET NAMES utf8mb4;
      filter:
        wall:
          enabled: false
```

## Step3-配置seata

##### 1.在 [Seata Release](https://github.com/seata/seata/releases) 下载相应版本的 Seata Server 并解压
##### 2.修改 `conf/registry.conf` 配置，将 type 改为 `nacos`

```
registry {
  type = "nacos"

  nacos {
    serverAddr = "你的nacos服务地址"
    namespace = "public"
    cluster = "default"
  }
}

config {
  type = "nacos"

  nacos {
    serverAddr = "你的nacos服务地址"
    namespace = "public"
    cluster = "default"
  }
}
```
##### 3.修改 `conf/nacos-config.txt`配置

修改 `service.vgroup_mapping`为自己应用对应的名称；如果有多个服务，添加相应的配置

如 

```properties
service.vgroup_mapping.my_test_tx_group=default

#改为 

service.vgroup_mapping.chao-cloud-provider-fescar-service-group=default
service.vgroup_mapping.chao-cloud-consumer-fescar-service-group=default
```

也可以在 Nacos 配置页面添加，data-id 为 `service.vgroup_mapping.${YOUR_SERVICE_NAME}-fescar-service-group`, group 为 `SEATA_GROUP`， 如果不添加该配置，启动后会提示`no available server to connect` 

注意配置文件末尾有空行，需要删除，否则会提示失败，尽管实际上是成功的

##### 4.将 Seata 配置添加到 Nacos 中 

```bash
cd conf
sh nacos-config.sh localhost  #localhost 为你的nacos服务地址
```

成功后会提示

```bash
init nacos config finished, please start seata-server
```

在 Nacos 管理页面应该可以看到有 47-60（版本不同） 个 Group 为`SEATA_GROUP`的配置

##### 5.启动 Seata Server 

```bash
cd ..
sh ./bin/seata-server.sh -p 8091 -m file
```

启动后在 Nacos 的服务列表下面可以看到一个名为`serverAddr`的服务

##### 6.在nacos服务  public 命名空间下添加一个配置
- Data Id： registry.type
- Group： SEATA_GROUP
- 内容为  nacos  格式选txet 

## Step4-测试

#####  http://localhost:8000/chao-cloud-consumer/test?userId=1
#####  查看 seata 数据库的 order表 是否有数据，没有则正确


## 自定义 seata-nacos配置
- 项目的nacos配置和  seata的nacos配置可分开 ->请查看seata/conf/registry.conf 

```java
@EnableTxSeata

//yaml 配置
chao:
  cloud:
    tx:
      seata:
        server-addr:  # seata->nacos地址
        namespace: # seata->nacos命名空间  默认 空 
        cluster:   # seata->集群标识  默认 default
```

- 说明
  * 在启动类增加@EnableTxSeata  
  * 目前只支持 nacos+feign+seata

## 注意 

### TxSeataConfig 配置

这里是尤其需要注意的，Seata 是通过代理数据源实现事务分支，所以需要配置 `io.seata.rm.datasource.DataSourceProxy` 的 Bean，且是 `@Primary`默认的数据源，否则事务不会回滚，无法实现分布式事务 

```java
@Data
@ConfigurationProperties(EnableTxSeata.TX_SEATA_PREFIX)
public class TxSeataConfig {

	private String serverAddr;
	private String namespace;
	private String cluster;

	@Bean(initMethod = "init")
	@ConditionalOnMissingBean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DruidDataSource druidDataSource() {
		StaticLog.info("初始化-Seata-DruidDataSource");
		return new DruidDataSource();
	}

	@Primary
	@Bean
	@ConditionalOnMissingBean
	public DataSourceProxy dataSource(DataSource dataSource) {
		return new DataSourceProxy(dataSource);
	}

	@Bean
	@ConditionalOnClass(Feign.class)
	public TxSeataFeignProxy TxSeataProxy() {
		return new TxSeataFeignProxy();
	}
}
```

## 参考
- [nacos参考手册](https://nacos.io/en-us/docs/what-is-nacos.html)
- [helloworlde@seata部署](https://github.com/helloworlde/spring-cloud-alibaba-component/tree/master/cloud-seata-nacos)

------

## 版权

### Apache License Version 2.0  

- 如不特殊注明，所有模块都以此协议授权使用。
- 任何使用了chao-cloud-micro的全部或部分功能的项目、产品或文章等形式的成果必须显式注明chao-cloud-micro。

### NPL (The 996 Prohibited License)

- 不允许 996 工作制度企业使用该开源软件

### 其他版权方
- 实施上由个人维护，欢迎任何人与任何公司向本项目开源模块。
- 充分尊重所有版权方的贡献，本项目不占有用户贡献模块的版权。

### 鸣谢
感谢下列优秀开源项目：
- [nacos@配置注册中心](https://github.com/alibaba/nacos)  
- [seata@分布式事务](https://github.com/seata/seata)  
- [helloworlde@seata-demo](https://github.com/helloworlde/spring-cloud-alibaba-component)  
- [hutool-超级工具类](https://github.com/looly/hutool)  
- [lombok](https://github.com/rzwitserloot/lombok)  
- [mybatis-plus](https://github.com/baomidou/mybatis-plus)  
- [......](https://github.com/)  

感谢诸位用户的关注和使用，chao-cloud-micro并不完善，未来还恳求各位开源爱好者多多关照，提出宝贵意见。

作者 [@chaojunzi 1521515935@qq.com]

2019年8月28日
