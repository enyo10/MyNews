package ch.openclassrooms.enyo1.mynews;

import android.util.Log;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import ch.openclassrooms.enyo1.mynews.models.articleSearch.ArticleSearch;
import ch.openclassrooms.enyo1.mynews.models.topStories.TopStories;
import ch.openclassrooms.enyo1.mynews.utils.Filters;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesStream;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class SearchResultActivityTest {

    @Test
    public void executeHttpRequestWithRetrofit() {
        // Create a map of search criteria.
        Map<String, String> query =  new HashMap<>();
        query.put("q", "Kennedy");
        query.put("fq", "news_desk:( Politics )");
        //1 - Get the stream
        Observable<ArticleSearch> observableArticleSearch = NYTimesStream.streamFetchArticlesSearch("UqsVUuAGooyAyaJPZrwM45HG454PT72r",query);
        //2 - Create a new TestObserver
        TestObserver<ArticleSearch> testObserver = new TestObserver<>();
        //3 - Launch observable
        observableArticleSearch.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue



        // Get list of news fetched
        ArticleSearch articleSearchFetched = testObserver.values().get(0);
        // Verify if status: "OK"
        assertThat("The status of the Stream was read correctly", articleSearchFetched.getStatus().equals("OK"));
        // Verify if Results Exist
        assertThat("Results exist in the Stream request", !articleSearchFetched.getResponse().equals(null));
        Log.i("TAG"," Search size "+ articleSearchFetched.getResponse().getDocs().size());

        assertTrue(articleSearchFetched.getResponse().getDocs().size()==10);
    }

    @Test
    public void convertToArticlesList() {
    }
}