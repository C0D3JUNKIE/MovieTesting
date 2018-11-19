package cloud.mockingbird.movietesting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterAdapterViewHolder>{

  private static final String MOVIEDB_IMAGE_URL = "https://image.tmdb.org/t/p/w780";

  private String[][] moviePosterData;

  final private MoviePosterAdapterOnClickHandler clickHandler;

  public interface MoviePosterAdapterOnClickHandler{
    void onClick(String[] moviePosterSelected);
  }

  public MoviePosterAdapter(MoviePosterAdapterOnClickHandler handler){
    clickHandler = handler;
  }

  public class MoviePosterAdapterViewHolder extends RecyclerView.ViewHolder implements
      OnClickListener{

    ImageView movieImage;
    TextView movieText;

    public MoviePosterAdapterViewHolder(@NonNull View itemView) {
      super(itemView);
      movieImage = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
      movieText = (TextView) itemView.findViewById(R.id.tv_movie_text);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      int adapterPosition = getAdapterPosition();
      clickHandler.onClick(moviePosterData[adapterPosition]);
    }

  }

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

  @Override
  public void onBindViewHolder(@NonNull MoviePosterAdapterViewHolder holder, int position) {
    String listedMovies[] = moviePosterData[position];
//    holder.movieText.setText(listedMovies[MainActivity.TEXT_INDEX_ID]);
    Picasso.get()
        .load(MOVIEDB_IMAGE_URL + listedMovies[5])
        .into(holder.movieImage);
    //Picasso.get().load(imageURL + movie[5]).into(movieImage);
  }

  @Override
  public int getItemCount() {
    if (null == moviePosterData) {
      return 0;
    }
    return moviePosterData.length;
  }

  public void setMoviePosterData(String[][] movieData){
    moviePosterData = movieData;
    notifyDataSetChanged();
  }

}
