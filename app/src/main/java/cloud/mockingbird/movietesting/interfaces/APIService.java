package cloud.mockingbird.movietesting.interfaces;

import cloud.mockingbird.movietesting.model.MoviePoster;
import cloud.mockingbird.movietesting.utilities.NetworkUtility;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

//Interface for creating Retrofit endpoints
public interface APIService {

    //Get Popular Movies
    @GET(NetworkUtility.POPULAR_URL);
    Call<MoviePosterResults> getPopularMoviePosters();

    //Get Top Rated Movies
    @GET(NetworkUtility.TOP_RATED_URL);
    Call<MoviePosterResults> getTopRatedMoviePosters();

    //Get Trailers for Details
    @GET
    Call<MoviePosterResults> getMoviePosterTrailers(@Url String trailerUrl);

    //Get
    @GET
    Call<MoviePosterResults> getMoviePosterReviews(@Url String reviewUrl);

}
