package ch.openclassrooms.enyo1.mynews.view;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;

import java.util.List;

import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;

public class NYTimesArticleAdapter extends RecyclerView.Adapter<NYTimesArticleViewHolder> {
    private static String TAG = NYTimesArticleAdapter.class.getSimpleName();
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
    public void onBindViewHolder(@NonNull NYTimesArticleViewHolder articleViewHolder, int i) {
        articleViewHolder.updateWithArticles(this.mNYTimesArticles.get(i),this.mRequestManager);

    }

    @Override
    public int getItemCount() {
        return this.mNYTimesArticles.size();
    }

    /**
     * This method to return the item at position "position" on the recycler view.
     * @param position, the position
     * @return Object, the item to return.
     */
    public NYTimesArticle getItem(int position){
        return this.mNYTimesArticles.get(position);
    }
}
