package ch.openclassrooms.enyo1.mynews.utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import ch.openclassrooms.enyo1.mynews.models.articleSearch.ArticleSearch;
import ch.openclassrooms.enyo1.mynews.models.mostPopular.MostPopularArticle;
import ch.openclassrooms.enyo1.mynews.models.topStories.TopStories;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTimesStream {

    /**
     * This method to retrieve the top stories
     * @param apiKey,
     *               the api key to retrieve the resources.
     * @param section,
     *               the section means the category on which the resource belong on.
     * @return A Stream,
     *               the stream of the top stories.
     */
    public static Observable<TopStories>streamFetchTopStories(String apiKey,String section){
        NYTimesService topStoriesService =NYTimesService.retrofit.create(NYTimesService.class);
        return topStoriesService.getTopStories(section,apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(15,TimeUnit.SECONDS);
    }

    /**
     * This method to retrieve a stream of the most popular articles.
     * @param apiKey, the api key to get the resources.
     * @return Articles, the popular articles.
     */
    public static Observable<MostPopularArticle>streamFetchMostPopArticles(String apiKey){
        NYTimesService mostPopArticleService =NYTimesService.retrofit.create(NYTimesService.class);
        return mostPopArticleService.getMostPopArticle(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(15,TimeUnit.SECONDS);


    }

    /**
     * This method to retrieve a stream of business articles.
     * @param keyword,
     *
     * @param apiKey
     * @return
     */
    public static Observable<ArticleSearch>streamFetchBusinessArticles(String keyword, String apiKey){

        NYTimesService businessArticleService=NYTimesService.retrofit.create(NYTimesService.class);
       return businessArticleService.getBusinessArticles(keyword,apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(15,TimeUnit.SECONDS);

    }

    /**
     * This method to retrieve a stream of articles according to the given filters.
     * @param apiKey,
     *        api-key
     * @param filter,
     *        a map of the filters.
     * @return Observable<ArticleSearch>,
     *        a stream of the articles.
     */
    public static Observable<ArticleSearch> streamFetchArticlesSearch(String apiKey,Map<String,String>filter){
        NYTimesService articleSearchService = NYTimesService.retrofit.create(NYTimesService.class);
        return articleSearchService.getArticleSearch(apiKey,filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(15, TimeUnit.SECONDS);
    }
}
