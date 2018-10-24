# Swagger配置文档

### 快速使用
您可以轻松地在dips中引入Swagger:   

- 在`pom.xml`中引入以下依赖:

```xml
		<dependency>
			<groupId>com.cloud</groupId>
			<artifactId>dips-common-swagger</artifactId>
		</dependency>
```

- 在应用主类中增加`@EnabledipsSwagger2`注解

```java
@EnabledipsSwagger2
@EnableFeignClients
@SpringCloudApplication
public class dipsAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(dipsAdminApplication.class, args);
	}
}
```

只需以上两步，就能产生当前工程中Spring MVC加载的请求映射所形成的文档。如需要个性化的定制，请看下文。

**注意:**
- 配置中的鉴权作用域`scope`必须是数据库`sys_oauth_client_details`表的`scope`字段里的内容的一个子集，否则发起Oauth2.0请求时会直接失败。
- 默认情况下Swagger映射Spring MVC中所有的请求,这样的请求包含了排除了Spring Boot默认的监控和异常信息处理路径,通常不是我们想要的。因此提供两种解决方案，任选其一即可。
- 我们可以使用`swagger.base-path`来指定所有需要生成文档的请求路径基础规则，然后再利用`swagger.exclude-path`来剔除部分我们不需要的。
我们可以这样设置：

```yaml
swagger:
  base-path: /**
  exclude-path: 
    - /actuator/**
    - /error
```

上面的配置将解析所有除了`/actuator`开始以及spring boot自带`/error`请求路径，这样，就排除了Spring Boot默认的监控和异常信息处理路径。   
- 除了以上的方法,我们同样可以通过配置包扫描的方式，扫描指定包下的类生成API文档。
我们可以这样设置：

```yaml
swagger:
  base-package: com.cloud.dips.admin.controller
```
这样，Swagger只会生成对应包下的API文档，这样，自然也就排除了Spring Boot默认的监控和异常信息处理路径。
### 如何在dips Swagger中OAuth2.0 授权

#### 增加客户端

默认对所有终端进行验证码校验，但是swagger 模拟的时候不需要。

- 直接操作**sys_oauth_client_details**表

```sql
INSERT INTO `dips`.`sys_oauth_client_details` (
	`authorities`,
	`authorized_grant_types`,
	`web_server_redirect_uri`,
	`scope`,
	`additional_information`,
	`autoapprove`,
	`resource_ids`,
	`refresh_token_validity`,
	`client_secret`,
	`client_id`,
	`access_token_validity`
)
VALUES
	(
		NULL,
		'password,refresh_token',
		NULL,
		'server',
		NULL,
		'true',
		NULL,
		NULL,
		'test',
		'test',
		NULL
	);
```

#### 过滤指定客户端

dips-gateway-dev.yml

```yml
# 不校验验证码终端
ignore:
  clients:
    - test
```

