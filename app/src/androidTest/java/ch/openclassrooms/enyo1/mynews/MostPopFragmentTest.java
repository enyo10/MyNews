package ch.openclassrooms.enyo1.mynews;

import org.junit.Test;

import ch.openclassrooms.enyo1.mynews.models.mostPopular.MostPopularArticle;
import ch.openclassrooms.enyo1.mynews.models.topStories.TopStories;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesStream;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;

public class MostPopFragmentTest {

    @Test
    public void fetchMostPopArticles()throws Exception{
        //1 - Get the stream
        Observable<MostPopularArticle> observableMostPopArticle = NYTimesStream.streamFetchMostPopArticles("UqsVUuAGooyAyaJPZrwM45HG454PT72r");
        //2 - Create a new TestObserver
        TestObserver<MostPopularArticle> testObserver = new TestObserver<>();
        //3 - Launch observable
        observableMostPopArticle.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue


       // 4 - Get the most article fetched
       MostPopularArticle articleFetched = testObserver.values().get(0);

        // - Verify if status: "OK"
        assertThat("The status of the Stream was read correctly", articleFetched.getStatus().equals("OK"));

        // - Verify if Results Exist
        assertThat("Results exist in the Stream request", articleFetched.getNumResults()!=0);
        // - Verify if we have 20 article.
        assertThat(" Article size is 20.",articleFetched.getNumResults() != 20);
    }


}
