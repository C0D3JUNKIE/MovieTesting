package cloud.mockingbird.movietesting;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import cloud.mockingbird.movietesting.adapters.MoviePosterAdapter;
import cloud.mockingbird.movietesting.data.MoviePreferences;
import cloud.mockingbird.movietesting.interfaces.APIService;
import cloud.mockingbird.movietesting.model.MoviePoster;
import cloud.mockingbird.movietesting.model.MoviePosterResults;
import cloud.mockingbird.movietesting.utilities.APIUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.MoviePosterAdapterOnClickHandler {

  private static final String TAG = MainActivity.class.getSimpleName();

  //Class variables
  public static final int TEXT_INDEX_ID = 1;
  public static final int IMAGE_INDEX_ID = 5;

  //Local variables
  private List<MoviePoster> movies;
  private MoviePosterAdapter moviePosterAdapter;
  private RecyclerView recyclerView;
  private TextView errorMessageDisplay;
  private ProgressBar loadingIndicator;
  private APIService apiService;
  private GridLayoutManager layoutManager;
  private Parcelable moviePosterState;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Stetho.initializeWithDefaults(this);
    setContentView(R.layout.activity_main);

    //Declaration and initialization of API Service
    apiService = APIUtility.getAPIService();

    //Tie recyclerView, errorText, and progressBar to the xml entity.
    recyclerView = (RecyclerView) findViewById(R.id.rv_movie_posters);
    errorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
    loadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

    //Setting up layoutManager params.
    int recyclerViewOrientation = GridLayoutManager.VERTICAL;
    boolean shouldReversLayout = false;

    //Declare and initialize layoutManager.
    GridLayoutManager layoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.span), recyclerViewOrientation,
        shouldReversLayout);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setHasFixedSize(true);

//    LoaderManager.LoaderCallbacks<Cursor> callback = MainActivity.this;

    //Tie the adapter to the views
//    movies = new ArrayList<>();
//    moviePosterAdapter = new MoviePosterAdapter(movies,this);
    moviePosterAdapter = new MoviePosterAdapter(new ArrayList<MoviePoster>(0), this);
//    moviePosterAdapter.setMoviePosterData(movies);
    recyclerView.setAdapter(moviePosterAdapter);

    loadingIndicator = findViewById(R.id.pb_loading_indicator);
    if(savedInstanceState != null){
        movies = savedInstanceState.getParcelableArrayList("movieList");
    }
    if(movies != null){
        moviePosterAdapter.setMoviePosterData(movies);
    }else{
        loadMovies();
    }

  }

  //Lifecycle support methods
  @Override
  protected void onStart() {
    super.onStart();
  }

  //Lifecycle support methods
  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  //Lifecycle support methods
  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
//    outState.putParcelable("movieList", layoutManager.onSaveInstanceState());
      outState.putParcelableArrayList("movieList", (ArrayList<? extends Parcelable>) movies);
  }

  //Lifecycle support methods
  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    moviePosterState = savedInstanceState.getParcelable("movieList");
  }

  //onClick implementation for AdapterOnClickHandler

  @Override
  public void onClick(int moviePosterSelected) {
    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
    intent.putExtra("moviePoster", movies.get(moviePosterSelected));
    startActivity(intent);
  }

//  @Override
//  public void onClick(String[] moviePosterSelected) {
//    Context context = this;
//    Class destinationClass = DetailActivity.class;
//    Intent intentToStartDetailActivity = new Intent(context, destinationClass);
//    intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, moviePosterSelected);
//    startActivity(intentToStartDetailActivity);
//  }

  /**
   * Displays movie data in recycler view
   */
  protected void showMovies(){
    errorMessageDisplay.setVisibility(View.INVISIBLE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  /**
   * Calls showMovies method and gets specified sort and creates nested class object.
   */
  protected void loadMovies(){
    showMovies();
    String selectedSort = MoviePreferences.getSortPreferred();
//    new FetchMovies().execute(selectedSort);
    fetchMovies();
  }

  /**
   * Displays error message
   */
  protected void showErrorMessage(){
    recyclerView.setVisibility(View.INVISIBLE);
    errorMessageDisplay.setVisibility(View.VISIBLE);
  }

  /**
   *
   *
   */
  private void fetchMovies() {
    apiService.getPopularMoviePosters().enqueue(new Callback<MoviePosterResults>() {
      @Override
      public void onResponse(Call<MoviePosterResults> call, Response<MoviePosterResults> response) {
        if(response.isSuccessful()) {
          Log.d(TAG, "onResponse: Call from API had a successful response.");
          movies = response.body().getResults();
          moviePosterAdapter.setMoviePosterData(movies);
          recyclerView.setAdapter(moviePosterAdapter);
          Log.d(TAG, "onResponse: RecyclerView set");
        }else{
          Log.d(TAG, "onResponse: ERROR from API Call response.");
        }  
      }

      @Override
      public void onFailure(Call<MoviePosterResults> call, Throwable t) {

      }
    });
  }

  private void fetchMoviesByRating() {
    apiService.getTopRatedMoviePosters().enqueue(new Callback<MoviePosterResults>() {
      @Override
      public void onResponse(Call<MoviePosterResults> call, Response<MoviePosterResults> response) {
        if(response.isSuccessful()) {
          Log.d(TAG, "onResponse: Call from API had a successful response.");
          movies = response.body().getResults();
          moviePosterAdapter.setMoviePosterData(movies);
          recyclerView.setAdapter(moviePosterAdapter);
        }else{
          Log.d(TAG, "onResponse: ERROR from API Call response.");
        }
      }

      @Override
      public void onFailure(Call<MoviePosterResults> call, Throwable t) {

      }
    });
  }

//  public class FetchMovies extends AsyncTask<String, Void, String[][]>{
//
//    @Override
//    protected void onPreExecute() {
//      super.onPreExecute();
//      loadingIndicator.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    protected void onPostExecute(String[][] strings) {
//      loadingIndicator.setVisibility(View.INVISIBLE);
//      if(strings != null){
//        showMovies();
//        moviePosterAdapter.setMoviePosterData(strings);
//      }else{
//        showErrorMessage();
//      }
//
//    }
//
//    @Override
//    protected String[][] doInBackground(String... strings) {
//      if (strings.length == 0) {
//        return null;
//      }
//      String params = strings[0];
//      URL movieURL = APIUtility.buildUrl(MainActivity.this, params);
//      try{
//        String jsonResponse = APIUtility.getResponseFromHttpURL(movieURL);
//        String[][] jsonMovieData = JsonUtility.getMoviePosterValuesFromJson(jsonResponse);
//        return jsonMovieData;
//      }catch(Exception e){
//        e.printStackTrace();
//        return null;
//      }
//    }
//
//  }

  private boolean networkConnection(){
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    return networkInfo != null && networkInfo.isConnected();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.action_by_rating:
        fetchMoviesByRating();
//        new FetchMovies().execute(MoviePreferences.PREF_SORT_RATING);
        return true;
      case R.id.action_by_popularity:
//        new FetchMovies().execute(MoviePreferences.PREF_SORT_POPULARITY);
        fetchMovies();
        return true;
      case R.id.action_refresh:
        moviePosterAdapter.setMoviePosterData(null);
        loadMovies();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

}
