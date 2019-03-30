package ch.openclassrooms.enyo1.mynews.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
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
        this.mFilters= new HashMap<>();


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
        mSelectedCategory.remove(key);
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
        if (!this.getKeyWords().equals("")) mFilters.put("q", getKeyWords());
        //-- Put selected category.
        if(this.mSelectedCategory.size()!=0) mFilters.put("fq", formatSelectedCategory());
        //-- Put the begin date.
        if(!this.getBeginDate().equals(""))  mFilters.put("begin_date",getBeginDate());
        //-- Put the begin date.
        if(!this.getEndDate().equals("")) mFilters.put("end_date",getEndDate());

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

    /**
     * To convert a string map to string.
     * @param stringMap,
     *        the map of string to convert.
     * @return string,
     *        the string to return.
     */
    public static String mapToJsonString(Map<String,String>stringMap){

        JSONObject jsonObject =new JSONObject(stringMap);
        return jsonObject.toString();
    }

    /**
     * Helper method to convert a json string to a Map<String,String> Object.
     * @param jsonString,
     *        The Json string to convert.
     * @return Map<String,String> , the result of the conversion.
     * @throws JSONException, an exception when the mapping fail.
     */
    public static Map<String, String>jsonToMap(String jsonString)throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        JSONObject jObject = new JSONObject(jsonString);
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            String value = jObject.getString(key);
            map.put(key, value);

        }
        return map;
    }


}
