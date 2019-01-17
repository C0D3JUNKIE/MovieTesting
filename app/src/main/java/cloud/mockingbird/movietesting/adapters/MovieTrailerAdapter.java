package cloud.mockingbird.movietesting.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cloud.mockingbird.movietesting.model.MovieTrailer;
import cloud.mockingbird.movietesting.utilities.APIUtility;
import cloud.mockingbird.movietesting.R;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerAdapterViewHolder> {

    private static final String TAG = MovieTrailerAdapter.class.getSimpleName();

    private List<MovieTrailer> movieTrailerData;
    private final MoviePosterAdapter.MoviePosterAdapterOnClickHandler clickHandler;
    private Context context;

   public interface MovieTrailerAdapterOnClickHandler{
       void onClick(int movieTrailerSelected);
   }

//    public MovieTrailerAdapter(List<MovieTrailer> movieTrailerData, MoviePosterAdapter.MoviePosterAdapterOnClickHandler clickHandler) {
//        this.movieTrailerData = movieTrailerData;
//        this.clickHandler = clickHandler;
//    }

    public MovieTrailerAdapter(List<MovieTrailer> movies, MoviePosterAdapter.MoviePosterAdapterOnClickHandler clickHandler, Context context){
       this.movieTrailerData = movies;
       this.clickHandler = clickHandler;
       this.context = context;
    }

    public class MovieTrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

       public final TextView movieTrailerTitle;
       public final ImageView movieTrailerImage;

       public MovieTrailerAdapterViewHolder(@NonNull View itemView){
           super(itemView);
           movieTrailerTitle = itemView.findViewById(R.id.tv_movie_trailer_text);
           movieTrailerImage = itemView.findViewById(R.id.iv_movie_trailer_image);
           itemView.setOnClickListener(this);
        }

       @Override
       public void onClick(View v) {
         int adapterPosition = getAdapterPosition();
         clickHandler.onClick(adapterPosition);
       }
    }


    @NonNull
    @Override
    public MovieTrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int position){
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachImmediately);
        return new MovieTrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerAdapterViewHolder movieTrailerAdapterViewHolder, int i) {
        MovieTrailer trailer = movieTrailerData.get(i);
        String trailerTitle = trailer.getName();
        String trailerId = trailer.getKey();
        Uri uri = Uri.parse(APIUtility.TRAILER_IMAGE_PATH + trailerId + APIUtility.TRAILER_IMAGE_PARAM);
        movieTrailerAdapterViewHolder.movieTrailerTitle.setText(trailerTitle);
        Picasso.get()
                .load(uri)
                .into(movieTrailerAdapterViewHolder.movieTrailerImage);
    }

    @Override
    public int getItemCount() {
        if(null == movieTrailerData){
            return 0;
        }
        return movieTrailerData.size();
    }

    public void setTrailerData(List<MovieTrailer> trailerData){
        movieTrailerData = trailerData;
        notifyDataSetChanged();
    }
}
