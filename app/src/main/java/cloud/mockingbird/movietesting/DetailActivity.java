package cloud.mockingbird.movietesting;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

  private static final String imageURL = "https://image.tmdb.org/t/p/w185";

  private String[] movie;

  private ImageView movieImage;


  private TextView movieTitle;
  private TextView movieReleaseDate;
  private TextView movieRating;
  private TextView movieDescription;

  /**
   *
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    movieImage = (ImageView) findViewById(R.id.iv_movie_poster_image);

    movieTitle = (TextView) findViewById(R.id.tv_movie_title);
    movieReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
    movieRating = (TextView) findViewById(R.id.tv_movie_vote_average);
    movieDescription = (TextView) findViewById(R.id.tv_movie_plot);

    //id[0], title[1], plot[2], language[3], date[4], image[5], vote[6], rating[7]
    Intent intent = getIntent();
    if(intent != null){
      if(intent.hasExtra(Intent.EXTRA_TEXT)){
        movie = intent.getStringArrayExtra(Intent.EXTRA_TEXT);
        movieTitle.setText(movie[1]);
//        movieImage.setImageResource(Integer.parseInt(movie[5]));
        movieReleaseDate.setText(movie[4]);
        movieRating.setText(movie[6]);
        movieDescription.setText(movie[2]);
        Picasso.get().load(imageURL + movie[5]).into(movieImage);
      }
    }

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



}
