package ch.openclassrooms.enyo1.mynews.utils;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.ArticleSearch;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class NotificationAlarmReceiver extends BroadcastReceiver {

    private Context mContext;
    private int mArticleNumber;
    private Filters mFilters=new Filters();


    @Override
    public void onReceive(Context context, Intent intent) {
        mContext=context;
        Log.i("Receiver","Received info");
       SharedPreferences preferences= mContext.getSharedPreferences("key",Context.MODE_PRIVATE);

       String searchWord =preferences.getString("editText","");
       String mapString=preferences.getString("selectedValue","");
       Log.i("TAG"," map string retrieve :"+mapString);
       mFilters.setKeyWord(searchWord);
       mFilters.setBeginDate(getDate());
        try {
            Map<String,String>m=Filters.jsonToMap(mapString);
            mFilters.setSelectedValues(m);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        executeHttpRequestWithRetrofit();

        /*if(getArticleNumber() > 0) {
           // sentNotification("You have " + mArticleNumber + " new article to read.");
            Log.i("TAG"," We have ......"+mArticleNumber +" new articles.");

        }*/

    }


    // Retrofit request.
    protected void executeHttpRequestWithRetrofit() {
        String key="UqsVUuAGooyAyaJPZrwM45HG454PT72r";
        Log.i("TAG"," Filter map "+mFilters.getFilters());


        Disposable disposable= NYTimesStream.streamFetchArticlesSearch(key,mFilters.getFilters())
                .subscribeWith(new DisposableObserver<ArticleSearch>() {
                    @Override
                    public void onNext(ArticleSearch nytArticles) {

                        Log.i("TAG","Article search Downloading...");
                        setArticleNumber(nytArticles.getResponse().getDocs().size());


                        Log.i("TAG","Article search in Notification result size : "+mArticleNumber);


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG","aie, error in Search Result activity: "  +Log.getStackTraceString(e));

                    }

                    @Override
                    public void onComplete() {
                        if(mArticleNumber > 0)
                        sendNotification("You have " + mArticleNumber + " new article to read.");

                    }
                });


        }



      public void sendNotification(String message){

          NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "Chanel_id")
                  .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                  .setContentTitle("New article notification")
                  .setContentText(" Some new article are to read")
                  .setStyle(new NotificationCompat.BigTextStyle()
                          .bigText(message))
                  .setPriority(NotificationCompat.PRIORITY_DEFAULT);

          NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);

          // notificationId is a unique int for each notification that you must define
          notificationManager.notify(1, builder.build());

          Log.i("TAG","Notifications send");

      }

    /**
     * This method to get the date of today in format yyyyMMdd
     * @return String, the date in String format.
     */
    protected String getDate(){
          String pattern = "yyyyMMdd";
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);

          return simpleDateFormat.format(new Date());

      }

    public int getArticleNumber() {
        return mArticleNumber;
    }

    public void setArticleNumber(int articleNumber) {
        mArticleNumber = articleNumber;
    }
}
