package cloud.mockingbird.movietesting.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cloud.mockingbird.movietesting.adapters.MovieFavoriteAdapter;
import cloud.mockingbird.movietesting.adapters.MoviePosterAdapter;
import cloud.mockingbird.movietesting.model.MoviePoster;
import cloud.mockingbird.movietesting.R;

public class FavoriteActivity extends AppCompatActivity {

    public static final String TAG = FavoriteActivity.class.getSimpleName();

    private List<MoviePoster> favoriteMovies;
    private MovieFavoriteAdapter movieFavoriteAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = (RecyclerView) findViewById(R.id.rv_favorite_movies);
        int recyclerViewOrientation = LinearLayout.VERTICAL;
        boolean shouldReverseLayout = false;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, recyclerViewOrientation, shouldReverseLayout);

        movieFavoriteAdapter = new MoviePosterAdapter(new ArrayList<MovieFavoriteAdapter>(0),this);
        recyclerView.setAdapter(movieFavoriteAdapter);
    }
}
