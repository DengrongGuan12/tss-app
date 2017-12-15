# tss 后端接口定义及问题记录
## 用户服务
swagger 地址：http://192.168.1.100:1212/#   
用户服务接口文档地址：http://192.168.1.168:19944/v2/api-docs
### 注册  
1. 发送注册验证码： http://192.168.1.100:1212/#/verify-code-controller/getRegisterVerifyCodeUsingGET
2. 注册： http://192.168.1.100:1212/#/user-controller/registerUsingPOST  
account: 手机号  
username: username和realname

### 登录,参考目前的登录  
1. 登录 http://192.168.1.100:1212/#/login-controller/loginUsingPOST     
2. 获取token http://superid.cn:18000/login/oauth/token?grant_type=password

### 个人中心
1. 获取个人信息（学号，院系年级？？）
http://192.168.1.100:1212/#/user-controller/getUserInfoUsingGET  
http://superid.org:18000/#/user-controller/getUserInfoUsingGET  
男：1 女：2 保密：0
2. 修改性别  
http://192.168.1.100:1212/#/user-controller/editBaseUsingPOST 只传gender就行
3. 修改信息公开性  
http://192.168.1.100:1212/#/user-controller/changePublicTypeUsingPOST
4. 修改密码  
http://192.168.1.100:1212/#/user-controller/changePwdUsingPOST
5. 修改手机号绑定  
    - 获取验证码：http://192.168.1.100:1212/#/verify-code-controller/getTokenChangeVerifyCodeUsingGET
    - 修改手机号：http://192.168.1.100:1212/#/user-controller/changeMobileOrEmailUsingPOST