
Vue + SpringBoot实现的博客系统

线上地址：<a target="_blank" href="https://www.9420666.xyz">小宇博客</a>


# 效果图

## 首页

![image](https://github.com/cn2k/qiuq_blog/blob/main/blog-img/index.png)

## 登录页
![image](https://github.com/cn2k/qiuq_blog/blob/main/blog-img/login.png)

## 注册页
![image](https://github.com/cn2k/qiuq_blog/blob/main/blog-img/register.png)

## 文章分类-标签、详情
![image](https://github.com/cn2k/qiuq_blog/blob/main/blog-img/category.png)

![image](https://github.com/cn2k/qiuq_blog/blob/main/blog-img/tags.png)

## 文章归档
![image](https://github.com/cn2k/qiuq_blog/blob/main/blog-img/guidang.png)

## 写文章
![image](https://github.com/cn2k/qiuq_blog/blob/main/blog-img/write.png)

## 编辑文章
![image](https://github.com/cn2k/qiuq_blog/blob/main/blog-img/edit.png)

## 文章详情
![image](https://github.com/cn2k/qiuq_blog/blob/main/blog-img/details.png)

## 评论
![image](https://github.com/cn2k/qiuq_blog/blob/main/blog-img/comments.png)

# 技术

## 前端  blog-app

- Vue
- Vue-router
- Vuex
- ElementUI
- mavon-editor
- lodash
- axios
- Webpack

## 后端  blog-parent

- SpringBoot
- Shiro
- Jpa
- Redis
- Fastjson
- Druid
- MySQL
- Maven

# 实现功能

## 整体

- 用户：登录 注册 退出
- 首页：文章列表、最热标签、最新文章、最热文章，日志，留言板
- 文章分类-标签：列表、详情
- 文章归档
- 文章：写文章、文章详情。编辑文章
- 评论：文章添加评论 对评论回复
- 文章列表滑动分页

## 后端
- 用户、文章、文章分类、标签和评论 增删改查api接口
- 基于token权限控制
- Redis存储Session
- 全局异常处理
- 操作日志记录

# 待实现功能
- 评论的分页 点赞
- 第三方登录
- 后台管理系统
- ..........

# 运行

将项目clone到本地

## 方式一 前后分离（开发方式）
## 后端配置
#  运行SpringBoot项目
1. 将blog-parent导入到IDE工具中
2. 将blog.sql导入MySQL数据库
3. 打开Redis数据库
4. resources/application.properties 修改MySQL、Redis连接
5. 修改七牛云文件存储信息,com/kabu/blog/utils/QiniuUtils.java，修改application.yml信息
6. 运行,访问：http://localhost:8888

## 前端配置
1. 按方式一运行blog-api，提供api数据接口
2. 打开命令行
	> cd blog-app

	> npm install

	> npm run dev

3. 访问：http://localhost:8080
4. 修改blog-app/src 下的文件进行开发
5. npm run build 生成最终静态文件




