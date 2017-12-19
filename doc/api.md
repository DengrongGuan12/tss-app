# tss 后端接口定义及问题记录
swagger 地址：http://superid.org:18000/   
## 用户服务 user@2.0
### 注册  
1. 发送注册验证码： http://superid.org:18000/#/verify-code-controller/getRegisterVerifyCodeUsingGET
2. 注册： http://superid.org:18000/#/user-controller/registerUsingPOST  
account: 手机号  
username: username和realname

### 登录,参考目前的登录  
1. 登录 http://superid.org:18000/#/login-controller/loginUsingPOST     
2. 获取token http://superid.cn:18000/login/oauth/token?grant_type=password

### 个人中心
1. 获取个人信息  
~~原接口(user@2.0): http://superid.org:18000/#/user-controller/getUserInfoUsingGET~~  
现接口(tss@2.0): http://superid.org:18000/#/user-controller/getUserInfoUsingGET  
男：1 女：2 保密：0
2. 修改性别  
http://superid.org:18000/#/user-controller/editBaseUsingPOST 只传gender就行
3. 修改信息公开性  
http://superid.org:18000/#/user-controller/changePublicTypeUsingPOST
4. 修改密码  
http://superid.org:18000/#/user-controller/changePwdUsingPOST
5. 修改手机号绑定  
    - 获取验证码：http://superid.org:18000/#/verify-code-controller/getTokenChangeVerifyCodeUsingGET
    - 修改手机号：http://superid.org:18000/#/user-controller/changeMobileOrEmailUsingPOST
6. 修改头像(file@2.0)
    - 获取上传头像token: http://superid.org:18000/#/file-controller/userAvatarTokenUsingPOST  
    - 上传头像到OSS
    - 压缩头像: http://superid.org:18000/#/file-controller/condenseUserAvatarUsingPOST  

## 课程相关 tss@2.0  
### 获取我的课程列表(我的课程)  
http://superid.org:18000/#/course-controller/getMyCoursesUsingGET  
返回值为Map<String,Map<String,CourseSimple>>类型，可直接调用接口查看。

