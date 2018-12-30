package cloud.mockingbird.movietesting;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import cloud.mockingbird.movietesting.adapters.MoviePosterAdapter;
import cloud.mockingbird.movietesting.data.MoviePreferences;
import cloud.mockingbird.movietesting.utilities.JsonUtility;
import cloud.mockingbird.movietesting.utilities.NetworkUtility;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.MoviePosterAdapterOnClickHandler {

  private static final String TAG = MainActivity.class.getSimpleName();

  public static final int TEXT_INDEX_ID = 1;
  public static final int IMAGE_INDEX_ID = 5;

  private MoviePosterAdapter moviePosterAdapter;
  private RecyclerView recyclerView;
  private TextView errorMessageDisplay;
  private ProgressBar loadingIndicator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Stetho.initializeWithDefaults(this);
    setContentView(R.layout.activity_main);

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

    //Tie the adapter to the views
    moviePosterAdapter = new MoviePosterAdapter(this);
    recyclerView.setAdapter(moviePosterAdapter);

    loadingIndicator = findViewById(R.id.pb_loading_indicator);
    loadMovies();
  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  public void onClick(String[] moviePosterSelected) {
    Context context = this;
    Class destinationClass = DetailActivity.class;
    Intent intentToStartDetailActivity = new Intent(context, destinationClass);
    intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, moviePosterSelected);
    startActivity(intentToStartDetailActivity);
  }

  protected void showMovies(){
    errorMessageDisplay.setVisibility(View.INVISIBLE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  protected void loadMovies(){
    showMovies();
    String selectedSort = MoviePreferences.getSortPreferred();
    new FetchMovies().execute(selectedSort);
  }

  protected void showErrorMessage(){
    recyclerView.setVisibility(View.INVISIBLE);
    errorMessageDisplay.setVisibility(View.VISIBLE);
  }

  public class FetchMovies extends AsyncTask<String, Void, String[][]>{

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String[][] strings) {
      loadingIndicator.setVisibility(View.INVISIBLE);
      if(strings != null){
        showMovies();
        moviePosterAdapter.setMoviePosterData(strings);
      }else{
        showErrorMessage();
      }

    }

    @Override
    protected String[][] doInBackground(String... strings) {
      if (strings.length == 0) {
        return null;
      }
      String params = strings[0];
      URL movieURL = NetworkUtility.buildUrl(MainActivity.this, params);
      try{
        String jsonResponse = NetworkUtility.getResponseFromHttpURL(movieURL);
        String[][] jsonMovieData = JsonUtility.getMoviePosterValuesFromJson(jsonResponse);
        return jsonMovieData;
      }catch(Exception e){
        e.printStackTrace();
        return null;
      }
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.action_by_rating:
        new FetchMovies().execute(MoviePreferences.PREF_SORT_RATING);
        return true;
      case R.id.action_by_popularity:
        new FetchMovies().execute(MoviePreferences.PREF_SORT_POPULARITY);
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
