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

public class TopStoriesViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.fragment_top_stories_item_title)
    TextView textView;
    @BindView(R.id.fragment_top_stories_item_image)
    ImageView imageView;

    public TopStoriesViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void updateWithTopStoriesResult(Result result, RequestManager glide){

        textView.setText(result.getTitle());

        glide.load(result.getMultimedia().get(1).getUrl()).into(imageView);

        Log.i("TAG"," url value : "+result.getMultimedia().get(0).getUrl());

    }
}
