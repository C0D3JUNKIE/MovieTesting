package cloud.mockingbird.movietesting.adapters;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cloud.mockingbird.movietesting.model.MovieReview;
import cloud.mockingbird.movietesting.R;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewAdapterViewHolder> {

    private static final String TAG = MovieReviewAdapter.class.getSimpleName();

    private Context context;
    private List<MovieReview> movieReviewData;

//    public interface MovieReviewAdapterOnClickHandler{
//        void onClick(int movieReviewSelected);
//    }

    public MovieReviewAdapter(Context context, List<MovieReview> movies){
        this.context = context;
        this.movieReviewData = movies;
    }

//    public class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder{

        public final TextView reviewAuthor;
        public final TextView reviewText;

        public MovieReviewAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.reviewAuthor = itemView.findViewById(R.id.tv_review_author);
            this.reviewText = itemView.findViewById(R.id.tv_review_content);
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            int adapterPosition = getAdapterPosition();
//            clickHandler.onClick(adapterPosition);
//        }

    }

    @NonNull
    @Override
    public MovieReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem, viewGroup, attachToParentImmediately);
        return  new MovieReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewAdapterViewHolder holder, int i) {
        MovieReview movieReview = movieReviewData.get(i);
        String author = movieReview.getAuthor();
        String content = movieReview.getContent();
        holder.reviewAuthor.setText(author);
        holder.reviewText.setText(content);
    }

    @Override
    public int getItemCount() {
        if(null == movieReviewData){
            return 0;
        }
        return movieReviewData.size();
    }

    public void setMovieReviewData(List<MovieReview> reviewData){
        movieReviewData = reviewData;
        notifyDataSetChanged();
    }
}
