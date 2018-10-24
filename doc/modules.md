DIPS微服务基础版
dips-cloud
├── dips-ui -- 前端工程[8000]
├── dips-auth -- 统一认证授权服务提供[3000]
├── dips-common -- 系统公共模块
├ ├── dips-common-core -- 公共工具类核心包
├ ├── dips-common-log -- 日志服务
├ └── dips-common-security -- 安全工具类
├ └── dips-common-swagger -- Swagger Api文档生成
├── dips-config -- 配置中心[4001]
├── dips-eureka -- 服务注册与发现[1025]
├── dips-gateway -- 微服务网关[9999]
└── dips-visual -- 图形化模块
├ ├── dips-monitor -- 服务监控中心 [5001]
├ └── dips-code-gen -- 图形化代码生成[5003]
├──dips-modules -- 微服务模块
├ ├── dips-upms -- 通用用户权限管理模块 [5001]
├ ├── └── dips-upms-api -- 通用用户权限管理系统公共api模块
├ ├── └── dips-upms-biz -- 通用用户权限管理系统业务处理模块[4000]
├ └── dips-tms -- 智能标签管理模块[2010]
└── └── 更多模块正在开发中...