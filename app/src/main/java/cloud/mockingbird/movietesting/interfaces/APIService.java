package cloud.mockingbird.movietesting.interfaces;

import static cloud.mockingbird.movietesting.utilities.NetworkUtility.POPULAR_MOVIE_PATH;
import static cloud.mockingbird.movietesting.utilities.NetworkUtility.TOPRATED_MOVIE_PATH;

import cloud.mockingbird.movietesting.model.MoviePosterResults;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Url;

//Interface for creating Retrofit endpoints
public interface APIService {

    //Get Popular Movies
    @GET(POPULAR_MOVIE_PATH)
    Call<MoviePosterResults> getPopularMoviePosters();

    //Get Top Rated Movies
    @GET(TOPRATED_MOVIE_PATH)
    Call<MoviePosterResults> getTopRatedMoviePosters();

    //Get Trailers for Details
    @GET
    Call<MoviePosterResults> getMoviePosterTrailers(@Url String trailerUrl);

    //Get
    @GET
    Call<MoviePosterResults> getMoviePosterReviews(@Url String reviewUrl);

}
