package com.qnmlgb.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
	
	/**
	 * 将输入流转换为字符串
	 * @param inputStream
	 * @return
	 */
	public static String convertStream2String(InputStream inputStream){
	
		//封装输出流
		
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		
		byte[] buffer = new byte[512];
		int len = 0;
		try {
			while((len = inputStream.read(buffer))!=-1){
				bao.write(buffer,0,len);
			}
			
			inputStream.close();
			
			return new String(bao.toByteArray());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
