package ch.openclassrooms.enyo1.mynews.utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import ch.openclassrooms.enyo1.mynews.models.articleSearch.ArticleSearch;
import ch.openclassrooms.enyo1.mynews.models.topStories.TopStories;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTimesStream {


    public static Observable<TopStories>streamFetchTopStories(String apiKey,String section){
        NYTimesService topStoriesService =NYTimesService.retrofit.create(NYTimesService.class);
        return topStoriesService.getTopStories(section,apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(30,TimeUnit.SECONDS);
    }


    // NYTimes ArticleSearch STREAM
    public static Observable<ArticleSearch> streamFetchArticleSearch(String apiKey, Map<String,String> filters){
        NYTimesService articleSearchService = NYTimesService.retrofit.create(NYTimesService.class);
        return articleSearchService.getArticleSearch(apiKey,filters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(30, TimeUnit.SECONDS);
    }
}
