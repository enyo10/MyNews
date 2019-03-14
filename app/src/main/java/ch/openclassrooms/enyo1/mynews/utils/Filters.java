package ch.openclassrooms.enyo1.mynews.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class model the list of the filter to pass to the request in NYTimesStream.
 */
public class Filters implements Serializable {
    private String mKeyWords;

    private Map<String ,String> mSelectedValues;
    private Map<String,String>mFilters;

    public Filters(){
        this.mSelectedValues=new HashMap<>();
        this.mFilters=new HashMap<>();
        this.mKeyWords="";
    }

    public Map<String, String> getSelectedValues() {
        return mSelectedValues;
    }

    public void setSelectedValues(Map<String, String> selectedValues) {
        mSelectedValues = selectedValues;
    }

    public void setKeyWord(String keyWords) {
        this.mKeyWords = keyWords;
    }

    public String getKeyWords() {
        return mKeyWords;
    }

    /**
     * Add a value to the map. The value of key, is the same as value.
     * @param value,
     *        the value to add.
     */
    public void addValue(String value){
        if(!mSelectedValues.containsKey(value))
        this.mSelectedValues.put(value,value);
    }

    /**
     * This method to remove a value from the list.
     * @param key,
     *      remove the value with key key.
     */
    public void removeValue(String key){
        if(mSelectedValues.containsKey(key)){
            mSelectedValues.remove(key);
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
        mFilters.put("fq", toString());

        return mFilters;
    }

    @Override
    public String toString() {
        String v="news_desk:(";
        StringBuilder builder = new StringBuilder(v);
        for(String a:this.getSelectedValues().values()){
            builder.append("\""+a+"\" ");
        }
        builder.append(")");

        return builder.toString();
    }
}
