# 🎉 欢迎来到 [KornZen的代码分享]！
## ✨ 项目简介
这是使用Spring Boot 3.2.0开发的一个简单的前后端分离项目,包含了用户管理、角色管理、菜单管理等功能。

## 📚 项目结构
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── kornzen
│   │   │           ├── config
│   │   │           ├── controller
│   │   │           ├── entity
│   │   │           ├── mapper
│   │   │           ├── service
│   │   │           ├── utils
│   │   │           └── Application.java
│   │   └── resources
│   │       ├── application.yml
│   │       ├── mapper
│   │       │   └── UserMapper.xml
│   │       └── static
│   └── test
├── pom.xml
└── README.md
```
## 🔧 技术栈
- 后端: Spring Boot 3.2.0
- 数据库: PostgreSQL 15.3
- 其他: Lombok, MyBatis, Spring, Redis, Swagger, etc.
- 开发工具: IntelliJ IDEA, Maven, GitHub, etc.
- 其他: Git, Docker, etc.
## 📝 项目说明
本项目是一个简单的前后端分离项目,包含了用户管理、角色管理、菜单管理等功能。
- 用户管理: 包括用户的增删改查,以及用户的登录、退出等功能。
- 角色管理: 包括角色的增删改查,以及角色的分配、取消等功能。
- 菜单管理: 包括菜单的增删改查,以及菜单的分配、取消等功能。
- 其他: 包括其他的功能,例如日志、权限、缓存等。

## 🚀 快速开始
1. 克隆本仓库到本地: `git clone URL_ADDRESSgit clone https://github.com/KornZen/springboot3.2.0.git`
2. 导入项目到IDEA中,并等待Maven下载依赖。
3. 配置数据库连接,修改`src/main/resources/application.yml`中的数据库连接信息。
4. 运行`Application.java`中的`PgsqlTestApplication`方法,启动项目。
5. 访问http://localhost:8080/swagger-ui/index查看API文档。

## 📝 注意事项
- 本项目使用了Lombok,请确保您的IDEA已经安装了Lombok插件。
- 本项目使用了MyBatis,请确保您的IDEA已经安装了MyBatis插件。
- 本项目使用了Swagger,请确保您的IDEA已经安装了Swagger插件。
- 本项目使用了Redis,请确保您已经安装了Redis。
- 本项目使用了Docker,请确保您的IDEA已经安装了Docker插件。
- 本项目使用了Git,请确保您已经安装了Git。
- 本项目使用了Maven,请确保您的IDEA已经安装了Maven插件。

## 🤝 贡献者
- [KornZen](URL_ADDRESS- [KornZen](https://github.com/KornZen) - 项目维护者
