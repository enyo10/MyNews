package ch.openclassrooms.enyo1.mynews.utils;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.controller.activities.NotificationsActivity;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.ArticleSearch;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.Doc;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationAlarmReceiver extends BroadcastReceiver {

    private Context mContext;
    private Doc mDoc;


    @Override
    public void onReceive(Context context, Intent intent) {
        mContext=context;
        Log.i("Receiver","Received info");

        executeHttpRequestWithRetrofit();


    }


    // Retrofit request.
    protected void executeHttpRequestWithRetrofit() {
        String key="UqsVUuAGooyAyaJPZrwM45HG454PT72r";
        Map<String, String> filters =new HashMap<>();

        Disposable disposable= NYTimesStream.streamFetchArticlesSearch(key,filters)
                .subscribeWith(new DisposableObserver<ArticleSearch>() {
                    @Override
                    public void onNext(ArticleSearch nytArticles) {

                        Log.i("TAG","Article search Downloading...");
                        Log.i("TAG","Article search in Notification result size : " +nytArticles.getResponse().getDocs().size());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG","aie, error in Search Result activity: "  +Log.getStackTraceString(e));

                    }

                    @Override
                    public void onComplete() {
                       sentNotification();

                    }
                });

        }

      /*  private void sendNotification(Context context){

            String title = "MyNews make you a little hello";
            String notificationDescription = "Today, "+ nbrArticleFound +" articles match your expectations.";
            if(nbrArticleFound>0) notificationDescription+=" Come quickly to consult them.";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d("TAG", "onReceive: Android 8.0 (API level 26) and higher");
                CharSequence name = "Channel";
                String channelDescription = "Channel Description";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("My Channel", name, importance);
                channel.setDescription(channelDescription);
                NotificationManager notificationManagerNew = mContext.getSystemService(NotificationManager.class);
                notificationManagerNew.createNotificationChannel(channel);
                Notification notification = new Notification.Builder(mContext,"My Channel")
                        .setContentTitle(title)
                        .setStyle(new Notification.BigTextStyle().bigText(notificationDescription))
                        .setContentText(notificationDescription)
                        .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .build();
                notificationManagerNew.notify(1, notification);
            }else{
                Log.d("TAG", "onReceive: Other Android Version");
                Notification notification = new Notification.Builder(mContext)
                        .setContentTitle(title)
                        .setStyle(new Notification.BigTextStyle().bigText(notificationDescription))
                        .setContentText(notificationDescription)
                        .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .build();
                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, notification);
            }
    }*/

      public void sentNotification(){

          NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "Chanel_id")
                  .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                  .setContentTitle("New article notification")
                  .setContentText(" Some new article are to read")
                  .setStyle(new NotificationCompat.BigTextStyle()
                          .bigText("Much longer text that cannot fit one line..."))
                  .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                  ;

          NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);

          // notificationId is a unique int for each notification that you must define
          notificationManager.notify(1, builder.build());


          Log.i("TAG","Notifications send");

      }





    /*public void showNotification() {
        PendingIntent pi = PendingIntent.getActivity(mContext, 0, new Intent(mContext, NotificationsActivity.class), 0);
        Resources r = mContext.getResources();
        Notification notification = new NotificationCompat.Builder(mContext,"")
                .setTicker("Hollo")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("New Articles")
                .setContentText("You have a new article to read.")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }*/


}
