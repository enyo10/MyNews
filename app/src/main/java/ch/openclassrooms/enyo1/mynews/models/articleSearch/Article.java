package ch.openclassrooms.enyo1.mynews.models.articleSearch;

import java.util.ArrayList;

public class Article {

    private String id;
    private Byline byline;
    private String document_type;
    private Headline headline;
    private ArrayList<Keyword> keywords;
    private ArrayList<Multimedium> multimedia;
    private String news_desk;
    private Integer  print_page;
    private String pub_date;
    private Integer score;
    private String snipper;
    private String source;
    private String  type_of_material;
    private String  uri;
    private String web_url;
    private Integer word_count;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Byline getByline() {
        return byline;
    }

    public void setByline(Byline byline) {
        this.byline = byline;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public ArrayList<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<Keyword> keywords) {
        this.keywords = keywords;
    }

    public ArrayList<Multimedium> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(ArrayList<Multimedium> multimedia) {
        this.multimedia = multimedia;
    }

    public String getNews_desk() {
        return news_desk;
    }

    public void setNews_desk(String news_desk) {
        this.news_desk = news_desk;
    }

    public Integer getPrint_page() {
        return print_page;
    }

    public void setPrint_page(Integer print_page) {
        this.print_page = print_page;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getSnipper() {
        return snipper;
    }

    public void setSnipper(String snipper) {
        this.snipper = snipper;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType_of_material() {
        return type_of_material;
    }

    public void setType_of_material(String type_of_material) {
        this.type_of_material = type_of_material;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public Integer getWord_count() {
        return word_count;
    }

    public void setWord_count(Integer word_count) {
        this.word_count = word_count;
    }
}
