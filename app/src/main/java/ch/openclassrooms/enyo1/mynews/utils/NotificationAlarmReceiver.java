package ch.openclassrooms.enyo1.mynews.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.openclassrooms.enyo1.mynews.models.articleSearch.ArticleSearch;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.Doc;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationAlarmReceiver extends BroadcastReceiver {
    private Doc mDoc;

    @Override
    public void onReceive(Context context, Intent intent) {
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
}
