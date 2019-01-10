package cloud.mockingbird.movietesting;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;

import java.util.List;

import cloud.mockingbird.movietesting.interfaces.APIService;
import cloud.mockingbird.movietesting.model.MoviePoster;
import cloud.mockingbird.movietesting.model.MoviePosterResults;
import cloud.mockingbird.movietesting.utilities.NetworkUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

  private static final String imageURL = "https://image.tmdb.org/t/p/w500";
  private static final String TAG = DetailActivity.class.getSimpleName();

  private ImageView movieImage;

  private String movieId;

  private TextView movieTitle;
  private TextView movieReleaseDate;
  private TextView movieRating;
  private TextView movieDescription;
  private MoviePoster moviePoster;

  private  FloatingActionButton fabButton;

  private APIService apiService;

  private List<MoviePoster> trailers;
  private List<MoviePoster> reviews;

  /**
   *
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    Stetho.initializeWithDefaults(this);
    setContentView(R.layout.activity_detail);

    movieImage = (ImageView) findViewById(R.id.iv_movie_poster_image);
    movieTitle = (TextView) findViewById(R.id.tv_movie_title);
    movieReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
    movieRating = (TextView) findViewById(R.id.tv_movie_vote_average);
    movieDescription = (TextView) findViewById(R.id.tv_movie_plot);

    Intent intent = getIntent();
    MoviePoster poster = intent.getParcelableExtra("moviePoster");
    moviePoster = poster;

    movieTitle.setText(moviePoster.getMovieTitle());
    movieReleaseDate.setText(moviePoster.getMovieReleaseDate());
    movieRating.setText(moviePoster.getMovieRating());
    movieDescription.setText(moviePoster.getMovieDescription());

    Uri uri = Uri.parse(imageURL + moviePoster.getMovieImagePath());
    Picasso.get()
            .load(uri)
            .into(movieImage);

    fetchTrailers();
    fetchReviews();



  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {

  }

  /**
   *
   * @param menu
   * @return
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    MenuItem menuItem = menu.findItem(R.id.action_refresh);
    return super.onCreateOptionsMenu(menu);
  }

  private void fetchTrailers(){

    String trailerUrl = NetworkUtility.DEFAULT_URL + movieId + NetworkUtility.TRAILER_PATH + NetworkUtility.KEY_PARAM + NetworkUtility.APIKEY;
    apiService.getMoviePosterTrailers(trailerUrl).enqueue(new Callback<MoviePosterResults>() {
      @Override
      public void onResponse(Call<MoviePosterResults> call, Response<MoviePosterResults> response) {

          trailers = response.body().getResults();



      }

      @Override
      public void onFailure(Call<MoviePosterResults> call, Throwable t) {

      }
    });
  }

  private void fetchReviews(){
    String reviewUrl = NetworkUtility.DEFAULT_URL + movieId + NetworkUtility.REVIEW_PATH + NetworkUtility.KEY_PARAM + NetworkUtility.APIKEY;
    apiService.getMoviePosterReviews(reviewUrl).enqueue(new Callback<MoviePosterResults>() {
      @Override
      public void onResponse(Call<MoviePosterResults> call, Response<MoviePosterResults> response) {

      }

      @Override
      public void onFailure(Call<MoviePosterResults> call, Throwable t) {

      }
    });
  }



}
