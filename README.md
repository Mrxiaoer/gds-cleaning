### 写在最前
**如果想快速部署dips-cloud，请完全参考本篇文档，如果有个性化的修改（例如：oauth2配置、token 个性化需求），请参考本篇运行起来以后，自行修改。循序渐进**

### 一、项目下载
git ssh://git@115.233.227.46:7081/home/githouse/dips-cloud

### 二、配置数据库
版本： mysql5.7+

### 三、dips-cloud配置修改
dips/dips-config/src/main/resources/config/application-dev.yml

# redis 相关
spring:
  redis:
    password:
    host: localhost
    
dips/dips-config/src/main/resources/config/dips-auth-dev.yml  
dips/dips-config/src/main/resources/config/dips-upms-dev.yml
dips/dips-config/src/main/resources/config/dips-mpms-dev.yml

# 数据源
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.jdbc.Driver
    username: root
    password: root
    url: jdbc:mysql://127.0.0.1:3309/dips?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false
```


### 四、dips-ui：

```
git clone git@gitlab.com:gomlFE/dips.git
npm run dev
```
请确保启动顺序
1.eureka   
2.config  
3.gateway  
4.auth  
5.upms 
6.mpms 
