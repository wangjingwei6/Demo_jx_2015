package com.qnmlgb.login;

/**
 *   登录注册实现接口
 * @author wangjingwei
 *
 */
public interface UserOperator {

	/**
	 *   登录接口
	 * @param userName 用户名
	 * @param passWord 密码
	 * @return
	 */
	public int userLogin(String userName,String passWord);
	
	
	/**
	 *  注册接口
	 * @param user 封装的数据bean
	 */
	public boolean regeist(UserBean user);
	
}
