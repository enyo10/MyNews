package ch.openclassrooms.enyo1.mynews.view;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.models.topStories.Result;

public class TopStoriesViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.fragment_top_stories_item_title)
    TextView textView;

    public TopStoriesViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void updateWithTopStoriesResult(Result result){
        textView.setText(result.getByline());

        Log.i("TAG"," result item set");

    }
}
