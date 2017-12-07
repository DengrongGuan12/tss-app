# tss 后端接口定义及问题记录
## 用户服务

swagger 地址：http://192.168.1.100:1212/# 

用户服务接口文档地址：http://192.168.1.168:19944/v2/api-docs
### 注册

1. 发送注册验证码： http://192.168.1.100:1212/#/verify-code-controller/getRegisterVerifyCodeUsingGET
2. 注册： http://192.168.1.100:1212/#/user-controller/registerUsingPOST
account: 手机号 
username: username和realname

