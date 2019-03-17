package ch.openclassrooms.enyo1.mynews.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class model the list of the filter to pass to the request in NYTimesStream.
 */
public class Filters implements Serializable {
    private String mKeyWords="";
    private String mBeginDate="";
    private String mEndDate="";

    private Map<String ,String> mSelectedCategory;
    private Map<String,String>mFilters;

    public Filters(){
        this.mSelectedCategory=new HashMap<>();
        this.mFilters=new HashMap<>();

    }

    public Map<String, String> getSelectedValues() {
        return mSelectedCategory;
    }

    public void setSelectedValues(Map<String, String> selectedValues) {
        mSelectedCategory = selectedValues;
    }

    public void setKeyWord(String keyWords) {
        this.mKeyWords = keyWords;
    }

    public String getKeyWords() {
        return mKeyWords;
    }

    public void setKeyWords(String keyWords) {
        mKeyWords = keyWords;
    }

    public String getBeginDate() {
        return mBeginDate;
    }

    public void setBeginDate(String beginDate) {
        mBeginDate = beginDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public void addBeginDate(String beginDate) {
        mBeginDate = beginDate;
    }


    public void addEndDate(String endDate) {
        mEndDate = endDate;
    }

    public void setFilters(Map<String, String> filters) {
        mFilters = filters;
    }

    /**
     * Add a value to the map. The value of key, is the same as value.
     * @param value,
     *        the value to add.
     */
    public void addSelectedCategory(String value){
        if(!mSelectedCategory.containsKey(value))
        this.mSelectedCategory.put(value,value);
    }

    /**
     * This method to remove a value from the list.
     * @param key,
     *      remove the value with key key.
     */
    public void removeSelectedCategory(String key){
        if(mSelectedCategory.containsKey(key)){
            mSelectedCategory.remove(key);
        }
    }


    /**
     * This method return a map of new york article search query format.
     * @return Map<String,String>filters,
     *
     */
    public Map<String, String> getFilters() {
        // -- Results are sorted by newest to oldest
        mFilters.put("sort", "newest");
        // -- Query key words.
        if (this.getKeyWords() !="") mFilters.put("q", getKeyWords());
        //-- Put selected category.
        if(this.mSelectedCategory.size()!=0) mFilters.put("fq", formatSelectedCategory());
        //-- Put the begin date.
        if(this.getBeginDate()!="")  mFilters.put("begin_date",getBeginDate());
        //-- Put the begin date.
        if(this.getEndDate()!="")mFilters.put("end_date",getEndDate());

        return mFilters;
    }

    private String formatSelectedCategory(){
        String v="news_desk:(";
        StringBuilder builder = new StringBuilder(v);
        for(String a:this.getSelectedValues().values()){
            builder.append("\""+a+"\" ");
        }
        builder.append(")");

        return builder.toString();
    }


}
