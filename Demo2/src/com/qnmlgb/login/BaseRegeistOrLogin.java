package com.qnmlgb.login;

/**
 *  注册登录接口类 
 * @author wangjingwei
 *
 */
public interface BaseRegeistOrLogin {
	 void regeistUser(String userName,String passWord);
	 void loginUser(String userName,String passWord);
}
