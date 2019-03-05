package ch.openclassrooms.enyo1.mynews.view;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.Multimedium;
import ch.openclassrooms.enyo1.mynews.models.topStories.Result;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;

public class NYTimesArticleViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.fragment_item_title)
    TextView textView;
    @BindView(R.id.fragment_item_image)
    ImageView imageView;

    public NYTimesArticleViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void updateWithTopStoriesResult(NYTimesArticle article, RequestManager glide){

        textView.setText(article.getTitle());

        glide.load(article.getImageURL()).into(imageView);

        Log.i("TAG"," url value : "+article.getDate());

    }
}