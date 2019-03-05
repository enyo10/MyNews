package ch.openclassrooms.enyo1.mynews.view;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;

import java.util.List;

import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;

public class NYTimesArticleAdapter extends RecyclerView.Adapter<NYTimesArticleViewHolder> {
    // For data

    private List<NYTimesArticle> mNYTimesArticles;
    // The glide to hold the image.
    private RequestManager mRequestManager;

    // Constructor

    public NYTimesArticleAdapter(List<NYTimesArticle>resultList, RequestManager glide){
        this.mNYTimesArticles=resultList;
        this.mRequestManager=glide;

    }


    @NonNull
    @Override
    public NYTimesArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Create view holder and inflate it xml layout
        Context context=viewGroup.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.fragment_article_item,viewGroup,false);

        return new NYTimesArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NYTimesArticleViewHolder topStoriesViewHolder, int i) {
        topStoriesViewHolder.updateWithTopStoriesResult(this.mNYTimesArticles.get(i),this.mRequestManager);

    }

    @Override
    public int getItemCount() {
        return this.mNYTimesArticles.size();
    }
}
