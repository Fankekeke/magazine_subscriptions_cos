### 基于SpringBoot + Vue的在线杂志订阅系统.

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、
前端：Vue、Apex、Antd、Axios

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok


#### 前台启动方式
安装所需文件 yarn install 
运行 yarn run dev

#### 默认后台账户密码
[管理员]
admin
1234qwer

[用户]
fank
1234qwer

##### 管理员：
公告信息、用户管理、杂志内容模块、用户订阅管理、订阅价格管理、用户消息通知、历史阅读记录、订阅收益记录、收益统计、阅读统计、工单管理、样式管理、文章评论。

##### 用户：
用户注册登录、修改密码、订单管理、杂志订阅、在线阅读、杂志推荐、支付宝在线支付、工单提交、杂志评论，杂志点赞，杂志关注。

#### 项目截图
暂无

|  |  |
|---------------------|---------------------|
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/61304ff1-34d9-4a70-9aeb-3fb3c5e75ee0.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/2faa3f79-def7-4133-8a21-e9a93fc34cd1.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1990f439-1aa4-4db0-b7bb-7d705ce7ceec.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/f95e7ee1-5e7f-43b5-af81-f4f3c80279e4.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/953f706e-63d8-485d-b17b-5f8c8ebddd06.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/f3d0fe5c-6fdf-41af-b8ec-14f0d5e4afe6.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/920de269-5033-47c5-b41e-46336275004f.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/f1b1a9cd-5846-4cfe-aa58-fe7ba91bb5aa.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/097fc158-5a07-4928-9cb3-a487e6fdfc25.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/eeb32f06-e33e-4d83-82d8-73cd430cf7dd.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/96d7969b-e464-46f9-a3c8-a2ce819e2d20.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/d7d83a17-56fa-40ef-baf5-5f782dbc26da.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/68c9e1d7-540d-4797-a178-18e9f3fb76a6.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/bb2bd217-eb8b-4111-82fa-c88b32ec734f.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/8fdcbd2d-0ea6-469c-8532-f10b54da05dc.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/b0e4e2c0-1af3-4e73-ba22-eac7ea3bcae3.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/6b5c7bb2-0d22-44a3-8f71-d13723a4bddf.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/a9d9f7cb-07b9-484c-ae4a-ba17347281d7.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/4c9274d3-3b1e-43de-8069-7b936a7a683d.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/353434ba-cf34-4072-9c8c-62eca001aeb0.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/3ad234d1-5cd5-4633-8872-d5c9ce098901.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/work/936e9baf53eb9a217af4f89c616dc19.png) |


#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解😭`

> 1.项目纯原创，不做二手贩子 2.一次购买终身有效 3.项目讲解持续到答辩结束 4.非常负责的答辩指导 5.**黑奴价格**

> 项目部署调试不好包退！功能逻辑没讲明白包退！

#### 其它资源

[2025年-答辩顺利通过-客户评价🍜](https://berserker287.github.io/2025/06/18/2025%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2024年-答辩顺利通过-客户评价👻](https://berserker287.github.io/2024/06/06/2024%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2023年-答辩顺利通过-客户评价🐢](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年-答辩通过率100%-客户评价🐣](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)

#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

`网站建设、小程序、H5、APP、各种系统 选题+开题报告+任务书+程序定制+安装调试+项目讲解+论文+答辩PPT`

More info: [悲伤的橘子树](https://berserker287.github.io/)
