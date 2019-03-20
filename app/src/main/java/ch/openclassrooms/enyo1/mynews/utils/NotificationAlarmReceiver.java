package ch.openclassrooms.enyo1.mynews.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.ArticleSearch;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.Doc;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

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
                        Log.i("TAG","Article search result size : " +nytArticles.getResponse().getDocs().size());
                        Doc doc =nytArticles.getResponse().getDocs().get(9);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG","aie, error in Search Result activity: "  +Log.getStackTraceString(e));

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * This method to send the notification.
     */
    protected void sendNotification(){

        String title = "MyNews make you a little hello";
        String notificationDescription = "Today, "+ " number of found" +" articles match your expectations.";

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
                  //  .setSmallIcon(R.drawable.ic_fiber_new_black_24dp)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .build();
            notificationManagerNew.notify(1, notification);
        }else{
            Log.d("TAG", "onReceive: Other Android Version");
            Notification notification = new Notification.Builder(mContext)
                    .setContentTitle(title)
                    .setStyle(new Notification.BigTextStyle().bigText(notificationDescription))
                    .setContentText(notificationDescription)
                   // .setSmallIcon(R.drawable.ic_fiber_new_black_24dp)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .build();
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);
        }
    }


}
