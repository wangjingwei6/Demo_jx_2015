package com.qnmlgb.receiver;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.ninexiu.wjw.R;
import com.qnmlgb.activity.MainUIActivity;
import com.qnmlgb.util.Utils;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RemoteViews;

@SuppressLint("NewApi")
public class PushDemoReceiver extends BroadcastReceiver {

    /**
     * 应用未启�? 个推 service已经被唤�?保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @SuppressWarnings("deprecation")
	@Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");

                String taskid = bundle.getString("taskid");
                Log.e("CalendarViewDemo","taskid == "+taskid);
                String messageid = bundle.getString("messageid");
                Log.e("CalendarViewDemo","messageid == "+messageid);
                
                // smartPush第三方回执调用接口，actionid范围�?0000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90002);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));
                Log.e("CalendarViewDemo","第三方回执接口调用" + (result ? "成功" : "失败"));

                if (payload != null) {
                    String data = new String(payload);
                    Log.e("CalendarViewDemo", "receiver payload : " + data);
                    
           //wjw------------------------------------------------------------
                    Intent tomainActivity = new Intent(context,MainUIActivity.class);
                    tomainActivity.putExtra("isPush","true");
                    PendingIntent pendingforMainIntent = PendingIntent.getActivity(context, 0, tomainActivity,
							PendingIntent.FLAG_UPDATE_CURRENT);
                    
                    WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
            		NotificationManager manager =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                    final int version = android.os.Build.VERSION.SDK_INT;
                    Bitmap small_bitmap  = BitmapFactory.decodeResource(context.getResources(), R.drawable.cat);
                    Bitmap notiLargeBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.push_dog);
                    
                    if (version >= 16) { //4.1.0以上   	API 16+
						RemoteViews remoteViews = new RemoteViews(context.getPackageName(),	R.layout.ns_notification_item);
						remoteViews.setImageViewBitmap(R.id.noti_large_image,notiLargeBitmap);
						remoteViews.setTextViewText(R.id.noti_title1, "@王丽欣");
						remoteViews.setTextViewText(R.id.noti_content1,data);
						remoteViews.setImageViewBitmap(R.id.noti_image1, small_bitmap);
					
						// long totalMilliSeconds = System.currentTimeMillis();\
						remoteViews.setTextViewText(R.id.noti_time, Utils.ShowTime(System.currentTimeMillis()/1000, remoteViews));
						
						Notification notification = new NotificationCompat.Builder(context)
								.setTicker("@王丽欣")
								.setSmallIcon(R.drawable.cat)
								.setContentTitle("王丽欣不是好人")
								.setContentText(data)
								.setAutoCancel(true)
								.setPriority(Notification.PRIORITY_MAX)
								.build();
								// .setWhen(System.currentTimeMillis())
						
						notification.bigContentView = remoteViews;
						notification.contentIntent = pendingforMainIntent;
						
						// 推送是否开启铃声
						notification.defaults |= Notification.DEFAULT_SOUND;

						// 推送是否开启震动
						notification.defaults |= Notification.DEFAULT_VIBRATE;
						
						
						/*newVersionOutsideNotification.contentIntent=pendingforMainIntent;
						newVersionOutsideNotification.contentView=remoteViews;*/
						//newVersionOutsideNotification.bigContentView = remoteViews;
						//newVersionOutsideNotification.contentIntent = pendingforMainIntent;
						//managerOut.notify(0,newVersionOutsideNotification);
						manager.notify(0,notification);
						 
					} else if(version>11&&version<16){//4.1.1以下  2.3以上   API Level 11-16
							
						Notification.Builder builder = new Notification.Builder(context)  
					            .setAutoCancel(true)  
					            .setContentTitle("王丽欣不是好人")  
					            .setContentText(data)  
					            .setContentIntent(pendingforMainIntent)  
					            .setSmallIcon(R.drawable.cat)  
					            .setWhen(System.currentTimeMillis())  
					            .setOngoing(true);  
					Notification notification = builder.getNotification();  
					manager.notify(0,notification);
						
					}else{ //旧版本  2.3以下  API11以下      Android 6.0不做兼容 
//						Notification notification = new Notification(R.drawable.cat,
//								"王丽欣不是好人", System.currentTimeMillis());
//						notification.tickerText = "@王丽欣";
//						notification.setLatestEventInfo(context, "王丽欣不是好人", data,pendingforMainIntent);
//						notification.flags = Notification.FLAG_AUTO_CANCEL;
//						
//						// 推送是否开启铃声
//						notification.defaults |= Notification.DEFAULT_SOUND;
//						notification.defaults |= Notification.DEFAULT_VIBRATE;
//						manager.notify(0,notification);
						
						
						//所以还是使用 2.3以上
						Notification.Builder builder = new Notification.Builder(context)  
					            .setAutoCancel(true)  
					            .setContentTitle("王丽欣不是好人")  
					            .setContentText(data)  
					            .setContentIntent(pendingforMainIntent)  
					            .setSmallIcon(R.drawable.cat)  
					            .setWhen(System.currentTimeMillis())  
					            .setOngoing(true);  
					Notification notification = builder.getNotification();  
					// 推送是否开启铃声
					notification.defaults |= Notification.DEFAULT_SOUND;

					// 推送是否开启震动
					notification.defaults |= Notification.DEFAULT_VIBRATE;
					
					manager.notify(0,notification);
					}
                    
                    payloadData.append(data);
                    payloadData.append("\n");

                /*    if (GetuiSdkDemoActivity.tLogView != null) {
                        GetuiSdkDemoActivity.tLogView.append(data + "\n");
                    }*/
                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后�?过用户帐号查找CID进行消息推�?
                String cid = bundle.getString("clientid");
                
                Log.e("CalendarViewDemo", "clientId=="+cid);
           /*     if (GetuiSdkDemoActivity.tView != null) {
                    GetuiSdkDemoActivity.tView.setText(cid);
                }*/
                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 * 
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }

}
