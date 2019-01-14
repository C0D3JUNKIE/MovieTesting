package cloud.mockingbird.movietesting;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cloud.mockingbird.movietesting.adapters.MoviePosterAdapter;
import cloud.mockingbird.movietesting.adapters.MovieReviewAdapter;
import cloud.mockingbird.movietesting.adapters.MovieTrailerAdapter;
import cloud.mockingbird.movietesting.interfaces.APIService;
import cloud.mockingbird.movietesting.model.MoviePoster;
import cloud.mockingbird.movietesting.model.MovieReview;
import cloud.mockingbird.movietesting.model.MovieReviewResults;
import cloud.mockingbird.movietesting.model.MovieTrailer;
import cloud.mockingbird.movietesting.model.MovieTrailerResults;
import cloud.mockingbird.movietesting.utilities.APIUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements MovieTrailerAdapter.MovieTrailerAdapterOnClickHandler, MoviePosterAdapter.MoviePosterAdapterOnClickHandler {

  private static final String imageURL = "https://image.tmdb.org/t/p/w500";
  private static final String TAG = DetailActivity.class.getSimpleName();

  private ImageView movieImage;
  private ImageView movieTrailerImage;

  private String movieId;

  private TextView movieTitle;
  private TextView movieReleaseDate;
  private TextView movieRating;
  private TextView movieDescription;
  private TextView movieTrailerText;
  private TextView reviewAuthor;
  private TextView reviewContents;

  private MoviePoster moviePoster;
  private MovieTrailer movieTrailer;
  private MovieReview movieReview;

  private  FloatingActionButton fabButton;

  private APIService apiService;

  private List<MovieTrailer> trailers;
  private List<MovieReview> reviews;
  private MovieTrailerAdapter movieTrailerAdapter;
  private MovieReviewAdapter movieReviewAdapter;
  private RecyclerView trailerRecyclerView;
  private RecyclerView reviewRecyclerView;
  private LinearLayoutManager trailerLayoutManager;
  private LinearLayoutManager reviewLayoutManager;


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
    movieTrailerImage = (ImageView) findViewById(R.id.iv_movie_trailer_image);

    movieTitle = (TextView) findViewById(R.id.tv_movie_title);
    movieReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
    movieRating = (TextView) findViewById(R.id.tv_movie_vote_average);
    movieDescription = (TextView) findViewById(R.id.tv_movie_plot);
    reviewAuthor = (TextView) findViewById(R.id.tv_review_author);
    reviewContents = (TextView) findViewById(R.id.tv_review_content);

    trailerRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_reviews);
    reviewRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_trailers);

    Intent intent = getIntent();
    MoviePoster poster = intent.getParcelableExtra("moviePoster");
    moviePoster = poster;
    movieId = poster.getMovieId();
    movieTitle.setText(moviePoster.getMovieTitle());
    movieReleaseDate.setText(moviePoster.getMovieReleaseDate());
    movieRating.setText(moviePoster.getMovieRating());
    movieDescription.setText(moviePoster.getMovieDescription());

    Uri uri = Uri.parse(imageURL + moviePoster.getMovieImagePath());
      Picasso.get()
              .load(uri)
              .into(movieImage);

    apiService = APIUtility.getAPIService();

    movieTrailerAdapter = new MovieTrailerAdapter(new ArrayList<MovieTrailer>(0), this, this);
    trailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    trailerRecyclerView.setAdapter(movieTrailerAdapter);

    movieReviewAdapter = new MovieReviewAdapter(new ArrayList<MovieReview>(0), this);
    reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    reviewRecyclerView.setAdapter(movieReviewAdapter);


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

  @Override
  public void onClick(int clickedTrailer){
    MovieTrailer trailer = trailers.get(clickedTrailer);
    String trailerKey = trailer.getKey();
    Intent youTubeApp = new Intent(Intent.ACTION_VIEW, Uri.parse(APIUtility.YOUTUBE_APP + trailerKey));
    Intent youTube = new Intent(Intent.ACTION_VIEW, Uri.parse(APIUtility.TRAILER_YOUTUBE_PATH + trailerKey));

    try{
      startActivity(youTubeApp);
    }catch(Exception e){
      startActivity(youTube);
    }

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

    String trailerUrl = APIUtility.DEFAULT_URL + movieId + APIUtility.TRAILER_PATH + APIUtility.KEY_PARAM + APIUtility.APIKEY;
    apiService.getMoviePosterTrailers(trailerUrl).enqueue(new Callback<MovieTrailerResults>() {
      @Override
      public void onResponse(Call<MovieTrailerResults> call, Response<MovieTrailerResults> response) {
        if(response.body() != null){
          trailers = response.body().getResults();
          movieTrailerAdapter.setTrailerData(trailers);
//          getTrailers(trailers);
          trailerRecyclerView.setAdapter(movieTrailerAdapter);
          trailerRecyclerView.setLayoutManager(trailerLayoutManager);
          trailerRecyclerView.setHasFixedSize(true);
          movieTrailerAdapter.notifyDataSetChanged();

        }
      }

      @Override
      public void onFailure(Call<MovieTrailerResults> call, Throwable t) {

      }
    });
  }

  private void fetchReviews(){
    String reviewUrl = APIUtility.DEFAULT_URL + movieId + APIUtility.REVIEW_PATH + APIUtility.KEY_PARAM + APIUtility.APIKEY;
    apiService.getMoviePosterReviews(reviewUrl).enqueue(new Callback<MovieReviewResults>() {
      @Override
      public void onResponse(Call<MovieReviewResults> call, Response<MovieReviewResults> response) {
        if(response.body() != null){
          reviews = response.body().getResults();
          movieReviewAdapter.setMovieReviewData(reviews);
//          getReviews(reviews);
          reviewRecyclerView.setAdapter(movieReviewAdapter);
          reviewRecyclerView.setLayoutManager(reviewLayoutManager);
          reviewRecyclerView.setHasFixedSize(true);
          movieReviewAdapter.notifyDataSetChanged();
        }
      }

      @Override
      public void onFailure(Call<MovieReviewResults> call, Throwable t) {

      }
    });
  }

  private void getTrailers(List<MovieTrailer> trailers){
//    MovieTrailerAdapter adapter = new MovieTrailerAdapter(trailers, this, this);
//    trailerRecyclerView.setAdapter(adapter);
//    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
//    trailerRecyclerView.setLayoutManager(layoutManager);
//    trailerRecyclerView.setHasFixedSize(true);
//    adapter.notifyDataSetChanged();
  }

  private void getReviews(List<MovieReview> reviews){
//    MovieReviewAdapter adapter = new MovieReviewAdapter(this, reviews);
//    reviewRecyclerView.setAdapter(adapter);
//    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//    reviewRecyclerView.setLayoutManager(layoutManager);
//    adapter.notifyDataSetChanged();
  }


}
