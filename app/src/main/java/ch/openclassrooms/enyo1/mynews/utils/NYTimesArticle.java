package ch.openclassrooms.enyo1.mynews.utils;

/**
 * This class to represent a NewYorkTimes articles. This class will keep track of the most attribute of an article.
 */
public class NYTimesArticle implements Comparable<NYTimesArticle> {


    String mDate;               // It's created date of the news
    String mSection;            // It's the "section > subSection" label
    String mTitle;              // It's Title of the news
    String mArticlesURL;            // It's the Url of the news page
    String mImageURL;           // It's the Url where the image is
    boolean mEverRead = false;  // Indicator of ever read article ( not read by default )

    // Default Constructor
    public NYTimesArticle() {}

    public NYTimesArticle(String title, String imageURL, String newsURL, String date, String section) {
        this.mTitle = title;
        this.mArticlesURL = newsURL;
        this.mImageURL = imageURL;
        this.mDate = date;
        this.mSection = section;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        this.mSection = section;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

    public String getURL() {
        return mArticlesURL;
    }

    public void setURL(String newsURL) {
        this.mArticlesURL = newsURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }



    public boolean isEverRead() {
        return mEverRead;
    }

    public void setEverRead(boolean everRead) {
        mEverRead = everRead;
    }

    @Override
    public int compareTo(NYTimesArticle o) {
        return this.mDate.compareTo(o.mDate);
    }
}
