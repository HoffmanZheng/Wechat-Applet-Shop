# 后端接口规范



#### /api/register

POST  {"tel":"13912345678"} 

这个接口会向用户下发一个手机验证码

若一切正常（若用户不存在，则新建用户），返回 200 OK

如果 JSON 格式不对，则返回 400 Bad Request



#### /api/login

POST {"tel":"13912345678", "code":"123456"} 

成功返回 302 跳转到指定页面

用户名密码不对返回 403 Forbidden

用户不存在 返回 404 Not Found



#### /api/logout

#### /api/status

查询登录状态，已登录返回 200 OK

{

​	login:true

​	user: {

​		id : 123

​		name : zhangsan

​		avatarUrl : "https://image.."

​	}

}

没有登录返回

{

​	login : false

}



User

id name tel avatarUrl createdAt updatedAt