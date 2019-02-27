package ch.openclassrooms.enyo1.mynews.utils;


import java.util.Map;

import ch.openclassrooms.enyo1.mynews.models.articleSearch.ArticleSearch;
import ch.openclassrooms.enyo1.mynews.models.topStories.TopStories;
import retrofit2.Retrofit;
import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface NYTimesService {

    // Top Stories API
    @GET("svc/topstories/v2/{section}.json")
    Observable<TopStories> getTopStories(@Path("section") String section ,
                                         @Query("api-key") String apiKey);


   /* @GET("svc/search/v2/articlesearch.json")
    Observable<Response> loadArticles(@Query("api-key") String key,
                                      @Query("q") String query,
                                      @Query("page") String pNum,
                                      @Query("sort") String sOrder,
                                      @Query("begin_date") String beginDate,
                                      @Query("fq") String newsDesk);
*/

    // Article Search API
    @GET("svc/search/v2/articlesearch.json")
    Observable<ArticleSearch> getArticleSearch(@Query("api-key") String apiKey,
                                               @QueryMap Map<String,String> filters);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
