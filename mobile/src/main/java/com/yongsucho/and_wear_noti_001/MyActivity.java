package com.yongsucho.and_wear_noti_001;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MyActivity extends Activity implements View.OnClickListener{
    private final String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initLayout();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initLayout() {
        findViewById(R.id.btnNoti01).setOnClickListener(this);
        findViewById(R.id.btnNoti02).setOnClickListener(this);
        findViewById(R.id.btnNoti03).setOnClickListener(this);
        findViewById(R.id.btnNoti04).setOnClickListener(this);
        findViewById(R.id.btnNoti05).setOnClickListener(this);
        findViewById(R.id.btnNoti06).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnNoti01:
                sendNotification();
                break;
            case R.id.btnNoti02:
                sendNotification02();
                break;
            case R.id.btnNoti03:
                sendNotification03();
                break;
            case R.id.btnNoti04:
                sendNotificationAndReply();
                break;
            case R.id.btnNoti05:
                sendNotificationWithPages();
                break;
            case R.id.btnNoti06:
                sendNotificationWithGroup();
                break;
        }
    }

    /**
     * Wearable 용 Notification 생성
     */
    private void sendNotification(){
        Log.d(TAG, "sendNotification 01");

        int notification_id = 001;

        // Phone App 과 연결 부분
        Intent viewIntent = new Intent(this, MyActivity.class);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        // Icon 이 없으니 생성 안됨.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher).setContentIntent(viewPendingIntent)
                .setContentTitle("Notification").setContentText("Notification Test.");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notification_id, notificationBuilder.build());

    }

    /**
     * Intent Action Notification 예제
     */
    private void sendNotification02(){
        Log.d(TAG, "sendNotification 02");

        int notification_id = 002;

        // Map App 과 연결 부분
        String location  = "Seoul";
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent =
                PendingIntent.getActivity(this, 0, mapIntent, 0);

        // Phone App 과 연결 부분
        Intent viewIntent = new Intent(this, MyActivity.class);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        // Icon 이 없으니 생성 안됨.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher).setContentIntent(viewPendingIntent)
                .setContentTitle("Notification").setContentText("Notification Test.")
                .addAction(R.drawable.ic_launcher, "Map", mapPendingIntent);;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notification_id, notificationBuilder.build());

    }

    /**
     * Big Text Style Notification 예제
     */
    private void sendNotification03() {
        Log.d(TAG, "sendNotification 03");

        int notification_id = 003;

        NotificationCompat.BigTextStyle bitTextStyle = new NotificationCompat.BigTextStyle();
        bitTextStyle.bigText("adfasd asdfasdfa asdfa sdfasd fasdf asd fas df asd fa sdf a dsf asdf");

        // Phone App 과 연결 부분
        Intent viewIntent = new Intent(this, MyActivity.class);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        // Map App 과 연결 부분
        String location  = "Seoul";
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent =
                PendingIntent.getActivity(this, 0, mapIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.drawable.ic_launcher))
                        .setContentTitle("Title")
                        .setContentText("Contents")
                        .setContentIntent(viewPendingIntent)
                        .addAction(R.drawable.ic_launcher,
                                "Map", mapPendingIntent)
                        .setStyle(bitTextStyle);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notification_id, notificationBuilder.build());
    }
    private static final String EXTRA_VOICE_REPLY = "extra_voice_reply";
    /**
     * 음성 Reply Notification 예제
     */
    private void sendNotificationAndReply() {
        Log.d(TAG, "sendNotificationAndReply");

        int notification_id = 004;

        // Phone App 과 연결 부분
        Intent viewIntent = new Intent(this, MyActivity.class);
        PendingIntent replyPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Pre defined Reply Text
        String[] replyChoices = getResources().getStringArray(R.array.reply_choices);

        // 음성 Reply 관련 설정
        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel("REPLY")
                .setChoices(replyChoices)
                .build();

        // Create the reply action and add the remote input
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_launcher,
                        "REPLY", replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        // Icon 이 없으니 생성 안됨.
        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Title")
                        .setContentText("Contents")
                        .extend(new NotificationCompat.WearableExtender().addAction(action))
                        .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notification_id, notification);

    }

    /**
     * Resume 에서 음성 Reply 응답 처리
     */
    protected void onResume() {
        super.onResume();
        if (getIntent() != null) {
            Bundle inputResults = RemoteInput.getResultsFromIntent(getIntent());
            if (inputResults != null) {
                CharSequence replyText = inputResults.getCharSequence(EXTRA_VOICE_REPLY);
                if (replyText != null) {
                    Toast.makeText(this, TextUtils.concat("REPLY : ", replyText),
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * 멀티 페이지 Notification 예제
     */
    private void sendNotificationWithPages() {
        Log.d(TAG, "sendNotificationWithPages");

        int notification_id = 003;


        // Phone App 과 연결 부분
        Intent viewIntent = new Intent(this, MyActivity.class);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);


        // Create second page notification
        NotificationCompat.BigTextStyle bigTextStyle01 = new NotificationCompat.BigTextStyle();
        bigTextStyle01.setBigContentTitle("Big Contents1").bigText("adfasd asdfasdfa asdfa sdfasd fasdf asd fas df asd fa sdf a dsf asdf");
        Notification secondPageNotification =
                new NotificationCompat.Builder(this)
                        .setStyle(bigTextStyle01)
                        .build();

        // Create Third Page Notification
        NotificationCompat.BigTextStyle bigTextStyle02 = new NotificationCompat.BigTextStyle();
        bigTextStyle02.setBigContentTitle("Big Contents2").bigText("adfasd asdfasdfa asdfa sdfasd fasdf asd fas df asd fa sdf a dsf asdf");
        Notification thirdPageNotification =
                new NotificationCompat.Builder(this)
                        .setStyle(bigTextStyle02)
                        .build();

        // First Page Notification
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.drawable.ic_launcher))
                        .setContentTitle("Page 1")
                        .setContentText("Short message")
                        .setContentIntent(viewPendingIntent);

        // Add second page with wearable extender and extend the main notification
        Notification pagesNotification =
                new NotificationCompat.WearableExtender()
                        .addPage(secondPageNotification)
                        .addPage(thirdPageNotification)
                        .extend(notificationBuilder)
                        .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notification_id, pagesNotification);
    }

    // 그룹 관리를 위한 String KEY
    final static String GROUP_KEY_EXAM = "group_key_examples";
    private int messageCount = 0;

    /**
     * Notification 을 그룹으로 관리하기 위한 예제
     */
    private void sendNotificationWithGroup(){
        Log.d(TAG, "sendNotificationWithGroup "  + messageCount);

        int notification_id = 001;

        // Phone App 과 연결 부분
        Intent viewIntent = new Intent(this, MyActivity.class);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        // 그룹 관련 세팅을 추가
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher).setContentIntent(viewPendingIntent)
                .setContentTitle("Notification " + messageCount).setContentText("Notification Test." + messageCount)
                .setGroup(GROUP_KEY_EXAM);

        // 그룹으로 추가하기 위해서는 Notification ID 가 서로 달라야 함.
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notification_id + messageCount, notificationBuilder.build());

        messageCount++;
    }
}
