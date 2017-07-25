package com.qnmlgb.service;

import com.ninexiu.wjw.R;
import com.qnmlgb.application.SysApplication;

import android.app.Service;  
import android.content.Intent;  
import android.media.MediaPlayer;  
import android.os.IBinder;  
  
public class MediaPlayerService extends Service {  
    private MediaPlayer mp;  
    private int musicCount;
   
    @Override  
    public void onCreate() {  
    	musicCount = SysApplication.musicIndex;
        // TODO Auto-generated method stub  
        // 初始化音乐资源  
        try {  
            System.out.println("create player");  
            // 创建MediaPlayer对象  
            mp = new MediaPlayer(); 
            int id;
            if(musicCount!=2){
            	id = R.raw.ljz;
            }else{
            	id =R.raw.ahm;
            }
            mp = MediaPlayer.create(MediaPlayerService.this, id);  
            SysApplication.musicIndex++;
//             mp.prepare();  
        } catch (IllegalStateException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        super.onCreate();  
    }  
  
    @Override  
    public void onStart(Intent intent, int startId) {  
        // TODO Auto-generated method stub  
        // 开始播放音乐  
        mp.start();  
        // 音乐播放完毕的事件处理  
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {  
  
            public void onCompletion(MediaPlayer mp) {  
                // TODO Auto-generated method stub  
                // 循环播放  
                try {  
                    mp.start();  
                } catch (IllegalStateException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
        });  
        // 播放音乐时发生错误的事件处理  
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {  
  
            public boolean onError(MediaPlayer mp, int what, int extra) {  
                // TODO Auto-generated method stub  
                // 释放资源  
                try {  
                    mp.release();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
  
                return false;  
            }  
        });  
  
        super.onStart(intent, startId);  
    }  
  
    @Override  
    public void onDestroy() {  
        // TODO Auto-generated method stub  
        // 服务停止时停止播放音乐并释放资源  
        mp.stop();  
        mp.release();  
        super.onDestroy();  
    }  
  
    @Override  
    public IBinder onBind(Intent intent) {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
}  
