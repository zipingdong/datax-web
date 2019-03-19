## 主要功能
提供一个 web 系统，方便开发人员快速创建作业

## 示例
##### [首页](https://github.com/zipingdong/datax-web/blob/master/snapshot/index.png)
##### [配置](https://github.com/zipingdong/datax-web/blob/master/snapshot/index2.png)
##### [生成](https://github.com/zipingdong/datax-web/blob/master/snapshot/download.png)
##### [插件列表](https://github.com/zipingdong/datax-web/blob/master/snapshot/guide.png)
##### [插件配置手册](https://github.com/zipingdong/datax-web/blob/master/snapshot/markdown.png)

## 打包
使用 spring boot 开发，直接使用 gradle 打成 war 包即可

[新手](https://github.com/zipingdong/datax-web/blob/master/snapshot/war.png)

打包成功后的相对路径为 build/libs/datax-web-0.0.1.war

## 部署环境
系统：

CentOS Linux release 7.6.1810 (Core)

java：

java version "1.8.0_201"

Java(TM) SE Runtime Environment (build 1.8.0_201-b09)

Java HotSpot(TM) 64-Bit Server VM (build 25.201-b09, mixed mode)

tomcat：

apache-tomcat-9.0.16

## 环境变量
需要配置下面三个环境变量：

datax安装目录：

export DATAX_HOME=...

datax源码目录：

export DATAX_SRC_HOME=...

datax作业文件保存目录：

export DATAX_JSON_HOME=...
