package ch.openclassrooms.enyo1.mynews;

import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;


import ch.openclassrooms.enyo1.mynews.models.topStories.TopStories;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesStream;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)

public class TopStoriesFragmentTest {

    @Test
    public void fetchTopStoriesTest() {
        //1 - Get the stream
        Observable<TopStories> observableTopStories = NYTimesStream.streamFetchTopStories("UqsVUuAGooyAyaJPZrwM45HG454PT72r","sports");
        //2 - Create a new TestObserver
        TestObserver<TopStories> testObserver = new TestObserver<>();
        //3 - Launch observable
        observableTopStories.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        // 4 - Get the top stories fetched
        TopStories topStoriesFetched = testObserver.values().get(0);

        // - Verify if status: "OK"
        assertThat("The status of the Stream was read correctly", topStoriesFetched.getStatus().equals("OK"));
        // - Verify if section: "sports"
        assertThat("The section of the Stream was read correctly", topStoriesFetched.getSection().equals("sports"));
        // - Verify if Results Exist
        assertThat("Results exist in the Stream request", topStoriesFetched.getNumResults()!=0);

    }
}
