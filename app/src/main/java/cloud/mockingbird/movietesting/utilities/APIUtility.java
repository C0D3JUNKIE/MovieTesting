package cloud.mockingbird.movietesting.utilities;

import android.util.Log;

import cloud.mockingbird.movietesting.BuildConfig;
import cloud.mockingbird.movietesting.retrofit.RetrofitClientInstance;
import cloud.mockingbird.movietesting.interfaces.APIService;

public class APIUtility {

  private static final String LOG_TAG = APIUtility.class.getSimpleName();
  public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
  public static final String DEFAULT_URL = BASE_URL;
  public static final String KEY_PARAM = "?api_key=";
  private static final String LANG_PARAM = "en-US";
  private static final String PAGE_PARAM = "1";
  public static final String APIKEY = BuildConfig.API_KEY;
  private static final String IMAGE_PATH = "http://image.tmdb.org/t/p/";
  public static final String TRAILER_PATH = "/videos";
  public static final String REVIEW_PATH = "/reviews";

  public static final String POPULAR_MOVIE_PATH = DEFAULT_URL + "popular" + KEY_PARAM + APIKEY;
  public static final String TOPRATED_MOVIE_PATH = DEFAULT_URL + "top_rated" + KEY_PARAM + APIKEY;

  public static final String YOUTUBE_APP = "vnd.youtube:";
  public static final String TRAILER_YOUTUBE_PATH = "https://www.youtube.com/watch?v=";
  public static final String TRAILER_IMAGE_PATH = "https://img.youtube.com/vi/";
  public static final String TRAILER_IMAGE_PARAM = "/1.jpg";




  public static APIService getAPIService(){
    Log.d(LOG_TAG, "****    getAPIService: Retrofit call to client instance and api service    *****");
    return RetrofitClientInstance.getRetrofitInstance(DEFAULT_URL).create(APIService.class);
  }

}
