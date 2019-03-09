package ch.openclassrooms.enyo1.mynews.utils;


import ch.openclassrooms.enyo1.mynews.models.articleSearch.ArticleSearch;
import ch.openclassrooms.enyo1.mynews.models.mostPopular.MostPopularArticle;
import ch.openclassrooms.enyo1.mynews.models.topStories.TopStories;
import retrofit2.Retrofit;
import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NYTimesService {

    // Top Stories API
    @GET("svc/topstories/v2/{section}.json")
    Observable<TopStories> getTopStories(@Path("section") String section ,
                                         @Query("api-key") String apiKey);

   // @GET("svc/mostpopular/v2/emailed/7.json")
   @GET("svc/mostpopular/v2/mostshared/all-sections/30.json")

    Observable<MostPopularArticle>getMostPopArticle(@Query("api-key")String apiKey);



    // Article Search API
    @GET("svc/search/v2/articlesearch.json")
    Observable<ArticleSearch> getArticleSearch(@Query("api-key") String apiKey);



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
