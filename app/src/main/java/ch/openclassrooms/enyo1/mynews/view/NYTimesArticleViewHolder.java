package ch.openclassrooms.enyo1.mynews.view;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.utils.DateFormatter;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;

public class NYTimesArticleViewHolder extends RecyclerView.ViewHolder{
    private static final String TAG = NYTimesArticleViewHolder.class.getSimpleName();

    @BindView(R.id.fragment_item_title)
    TextView mTitle_textView;
    @BindView(R.id.fragment_item_image)
    ImageView mImageView;
    @BindView(R.id.fragment_item_date)TextView mDate_textView;
    @BindView(R.id.fragment_item_section)TextView mSection_textView;

    public  NYTimesArticleViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }


    protected void updateWithArticles(NYTimesArticle article, RequestManager glide){

        mTitle_textView.setText(article.getTitle());
        mDate_textView.setText(DateFormatter.formatDate(article.getDate()));
        mSection_textView.setText(article.getSection());
        glide.load(article.getImageURL()).into(mImageView);

        Log.i(TAG, " Article list update success");


    }
}
