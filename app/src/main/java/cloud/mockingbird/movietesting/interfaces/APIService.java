package cloud.mockingbird.movietesting.interfaces;

import static cloud.mockingbird.movietesting.utilities.APIUtility.POPULAR_MOVIE_PATH;
import static cloud.mockingbird.movietesting.utilities.APIUtility.TOPRATED_MOVIE_PATH;

import cloud.mockingbird.movietesting.model.MoviePosterResults;
import cloud.mockingbird.movietesting.model.MovieReviewResults;
import cloud.mockingbird.movietesting.model.MovieTrailerResults;
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

    //Get Trailer Details
    @GET
    Call<MovieTrailerResults> getMoviePosterTrailers(@Url String trailerUrl);

    //Get Review Details
    @GET
    Call<MovieReviewResults> getMoviePosterReviews(@Url String reviewUrl);

}
