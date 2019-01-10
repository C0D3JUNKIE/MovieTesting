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

import cloud.mockingbird.movietesting.MainActivity;
import cloud.mockingbird.movietesting.R;
import cloud.mockingbird.movietesting.model.MoviePoster;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterAdapterViewHolder> {

    //Class variables
    private static final String MOVIEDB_IMAGE_URL = "https://image.tmdb.org/t/p/w780";

    //Local variables
    private List<MoviePoster> moviePosterData;
    private final MoviePosterAdapterOnClickHandler clickHandler;

    //Nested interface for onClick implementation
    public interface MoviePosterAdapterOnClickHandler { void onClick(int moviePosterSelected);}

    //Adapter constructor

    public MoviePosterAdapter(List<MoviePoster> movies, MoviePosterAdapterOnClickHandler clickHandler) {
        this.moviePosterData = movies;
        this.clickHandler = clickHandler;
    }
    //Old Constructor
//    public MoviePosterAdapter(MoviePosterAdapterOnClickHandler handler) {
//        clickHandler = handler;
//    }

    /**
     * AdapterViewHolder Inner Class with super class RecyclerView.ViewHolder with onClickListener interface.
     */
    public class MoviePosterAdapterViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        //Local variables
        public final ImageView movieImage;
//        public final TextView movieText;

        //AdapterViewHolder constructor
        public MoviePosterAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.iv_movie_poster);
//            movieText = itemView.findViewById(R.id.tv_movie_text);
            itemView.setOnClickListener(this);
        }

        //Implementing onClickListeners onClick method
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            clickHandler.onClick(adapterPosition);
        }

    }

    //Implementing onCreateViewHolder from RecyclerView.Adapter
    @NonNull
    @Override
    public MoviePosterAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_poster_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, attachToParentImmediately);
        return new MoviePosterAdapterViewHolder(view);
    }

    //Implementing onBindViewHolder from RecyclerView.Adapter utilizing Picasso to display MoviePoster images.
    @Override
    public void onBindViewHolder(@NonNull MoviePosterAdapterViewHolder holder, int position) {
//        String listedMovies[] = moviePosterData[position];
//        holder.movieText.setText(listedMovies[MainActivity.TEXT_INDEX_ID]);

        MoviePoster movie = moviePosterData.get(position);
        Uri uri = Uri.parse(MOVIEDB_IMAGE_URL + movie.getMovieImagePath());
        Picasso.get().load(uri).into(holder.movieImage);
    }

    //Implementing getItemCount from RecyclerView.Adapter.
    @Override
    public int getItemCount() {
        if (null == moviePosterData) {
            return 0;
        }
        return moviePosterData.size();
    }

    /**
     * Setting movieData to class variable moviePosterData.
     */
    public void setMoviePosterData(List<MoviePoster> movieData) {
        moviePosterData = movieData;
        notifyDataSetChanged();
    }

}
