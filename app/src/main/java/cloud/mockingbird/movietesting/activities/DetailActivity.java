package cloud.mockingbird.movietesting.activities;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cloud.mockingbird.movietesting.R;
import cloud.mockingbird.movietesting.adapters.MoviePosterAdapter;
import cloud.mockingbird.movietesting.adapters.MovieReviewAdapter;
import cloud.mockingbird.movietesting.adapters.MovieTrailerAdapter;
import cloud.mockingbird.movietesting.data.MovieDbContract;
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
  private boolean favorite = false;
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
  private ProgressBar progressBar;
  private FloatingActionButton fabButton;
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

    progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
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
    fabButton = (FloatingActionButton) findViewById(R.id.button_favorite);

//    if(getIntent() != null && getIntent().hasExtra("movie")){
//      moviePoster = getIntent().getParcelableExtra("movie");
//    }
//
//    if(moviePoster != null){
//
//    }

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
    toggleFavorite();

    movieTrailerAdapter = new MovieTrailerAdapter(new ArrayList<MovieTrailer>(0), this, this);
    trailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    trailerRecyclerView.setAdapter(movieTrailerAdapter);
    trailerRecyclerView.setLayoutManager(trailerLayoutManager);
    trailerRecyclerView.setHasFixedSize(true);

    movieReviewAdapter = new MovieReviewAdapter(new ArrayList<MovieReview>(0), this);
    reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    reviewRecyclerView.setAdapter(movieReviewAdapter);
    reviewRecyclerView.setLayoutManager(reviewLayoutManager);
    reviewRecyclerView.setHasFixedSize(true);

    fetchTrailers();
    fetchReviews();

//    new queryDb().execute();
//
//    fabButton.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        toggleFavorite();
//      }
//    });

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


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    MenuItem menuItem = menu.findItem(R.id.action_refresh);
    return super.onCreateOptionsMenu(menu);
  }

  private void toggleFavorite(){
    if(!favorite){
      fabButton.setImageResource(R.drawable.ic_heart_outline);
    }else{
      fabButton.setImageResource(R.drawable.ic_heart);
    }
  }

  private void fetchTrailers(){

    String trailerUrl = APIUtility.DEFAULT_URL + movieId + APIUtility.TRAILER_PATH + APIUtility.KEY_PARAM + APIUtility.APIKEY;
    apiService.getMoviePosterTrailers(trailerUrl).enqueue(new Callback<MovieTrailerResults>() {
      @Override
      public void onResponse(Call<MovieTrailerResults> call, Response<MovieTrailerResults> response) {
        if(response.body() != null){
          trailers = response.body().getResults();
          movieTrailerAdapter.setTrailerData(trailers);
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
          movieReviewAdapter.notifyDataSetChanged();
        }
      }

      @Override
      public void onFailure(Call<MovieReviewResults> call, Throwable t) {

      }
    });
  }

  private void insert2Db(){
    favorite =  true;
    String id = moviePoster.getMovieId();
    String title = moviePoster.getMovieTitle();
    String release_date = moviePoster.getMovieReleaseDate();
    String vote_average = moviePoster.getMovieRating();
    String description = moviePoster.getMovieDescription();
    String poster_path = moviePoster.getMovieImagePath();

    ContentValues contentValues = new ContentValues();
    contentValues.put(MovieDbContract.MovieEntity.COLUMN_MOVIE_ID, id);
    contentValues.put(MovieDbContract.MovieEntity.COLUMN_TITLE, title);
    contentValues.put(MovieDbContract.MovieEntity.COLUMN_RELEASE_DATE, release_date);
    contentValues.put(MovieDbContract.MovieEntity.COLUMN_VOTE_AVERAGE, vote_average);
    contentValues.put(MovieDbContract.MovieEntity.COLUMN_DESCRIPTION, description);
    contentValues.put(MovieDbContract.MovieEntity.COLUMN_POSTER, poster_path);

    Uri insertUri = getContentResolver().insert(MovieDbContract.MovieEntity.BUILT_URI, contentValues);

    if(insertUri == null){
      String failed = String.format(getResources().getString(R.string.db_insert_error));
      Toast.makeText(this, failed, Toast.LENGTH_SHORT).show();
    }else{
      String confirmation = String.format(getResources().getString(R.string.db_insert_confirmation));
      Toast.makeText(this, confirmation, Toast.LENGTH_SHORT).show();
    }

  }

  private void deleteFromDb(){
    favorite = false;
    Uri deleteUri = MovieDbContract.BASE_CONTENT_URI.buildUpon().appendPath(MovieDbContract.FAVORITE_PATH).appendPath(movieId).build();

    int row = getContentResolver().delete(deleteUri, null, null);
    if(row == 0){
      String failed = String.format(getResources().getString(R.string.db_delete_error));
      Toast.makeText(this, failed, Toast.LENGTH_SHORT).show();
    }else{
      String confirmation = String.format(getResources().getString(R.string.db_delete_confirmation));
      Toast.makeText(this, confirmation, Toast.LENGTH_SHORT).show();
    }

  }

  private class queryDb extends AsyncTask<Void, Void, Cursor>{
    @Override
    protected void onPostExecute(Cursor cursor) {
      super.onPostExecute(cursor);
      if (cursor.getCount() > 0) {
        favorite=true;
        toggleFavorite();
      }else{
        favorite=false;
        toggleFavorite();
      }
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
      Uri uri = MovieDbContract.MovieEntity.BUILT_URI;
      String selected = MovieDbContract.MovieEntity.COLUMN_MOVIE_ID + "=?";
      String[] args = new String[]{movieId};
      return getContentResolver().query(uri, null, selected, args, null);
    }
  }


}
