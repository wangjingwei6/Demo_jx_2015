package com.qnmlgb.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.qnmlgb.util.FileUtil;

import android.text.TextUtils;

public class UserOperatorImpl implements UserOperator{

	private static File file = new File(FileUtil.SAVE_FILE_PATH+"/UserAccount.txt");
	private boolean flag = false;
	
	private static int CODE = 0; //默认是0  返回提示数据类型    1:登陆成功     2: 用户名不能为空  3:用户名错误  	4:密码不能为空	5:密码错误	6:用户名密码不能为空	7:用户名密码错误
	
	static {  //类创建而调用
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 登录接口实现类
	 */
	@Override
	public int userLogin(String userName, String passWord) {
		
		BufferedReader br = null;
		String oldUserName = null;
		String oldPassWord = null;
		
		
		try {
			StringBuffer sbf = new StringBuffer();
			br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while ((line = br.readLine())!=null){
				System.out.println("读取数据中...");
				sbf.append(line);
			}
			
			
			String []datas = sbf.toString().split("=");
			if(datas!=null&&datas.length>1){
				oldUserName = datas[0];
			    oldPassWord = datas[1];
			}else{
				return CODE = 6;
			}
		
			
//			if (datas[0].equals(userName) && datas[1].equals(passWord)) {
//				flag = true;
//				break;
//			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		//============================================================ 脑残判断开始
		
		
		if(TextUtils.equals(oldUserName,userName)&&TextUtils.equals(oldPassWord, passWord)){//登录成功
			CODE = 1;
		}else{ //错误提示
			
			
			
			if(TextUtils.isEmpty(userName)&&TextUtils.isEmpty(passWord)){ //用户名，密码都为空
				return CODE = 6;
			}else{ //其他
				
				if(!TextUtils.equals(oldUserName,userName)&&!TextUtils.equals(oldPassWord,passWord)
						&&!TextUtils.isEmpty(userName)&&!TextUtils.isEmpty(passWord)){  //用户名密码都不是正确 并且 两个都不为空
					return CODE = 7;
				}
				
				if(!TextUtils.equals(oldUserName,userName)){
					if(TextUtils.isEmpty(userName)){
						CODE = 2;
					}else if(TextUtils.isEmpty(passWord)){ //用户名不为空 ，密码为空
						CODE = 4;
					}else{
						CODE = 3;
					}
					
				}else if(!TextUtils.equals(oldPassWord, passWord)){
					if(TextUtils.isEmpty(passWord)){
						CODE = 4;
						
					}else {
						CODE = 5;
					}
				}else{
					CODE = 7;
				}
			}
			
			
		
	}
		
		return CODE;

}	
	
	/**
	 *  注册接口实现类
	 */
	@Override
	public boolean regeist(UserBean user) {
		
		//封装数据源
		StringBuffer sb = new StringBuffer();
		sb.append(user.getUserName()).append("=").append(user.getPassWord());
		
		BufferedWriter bf = null;
		try {
			bf =  new BufferedWriter(new FileWriter(file, false));
			bf.write(sb.toString());
			bf.newLine();
			bf.flush();
			flag = true;
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}finally {
			if(bf!=null){
				try {
					bf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return flag;
		
	}

}
