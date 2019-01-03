package cloud.mockingbird.movietesting.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import cloud.mockingbird.movietesting.BuildConfig;
import cloud.mockingbird.movietesting.R;
import cloud.mockingbird.movietesting.RetrofitClientInstance;
import cloud.mockingbird.movietesting.interfaces.APIService;
import retrofit2.Retrofit;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtility {

  private static final String LOG_TAG = NetworkUtility.class.getSimpleName();
  private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
  private static final String DEFAULT_URL = BASE_URL;
  private static final String KEY_PARAM = "?api_key=";
  private static final String LANG_PARAM = "en-US";
  private static final String PAGE_PARAM = "1";
  private static final String APIKEY = BuildConfig.API_KEY;
  private static final String IMAGE_PATH = "http://image.tmdb.org/t/p/";
  private static final String TRAILER_PATH = "/videos";
  private static final String REVIEW_PATH = "/reviews";

  public static final String POPULAR_MOVIE_PATH = DEFAULT_URL + "popular" + KEY_PARAM + APIKEY;
  public static final String TOPRATED_MOVIE_PATH = DEFAULT_URL + "top_rated" + KEY_PARAM + APIKEY;

  public static APIService getAPIService(){
    Log.d(LOG_TAG, "****    getAPIService: Retrofit call to Base Url    *****");
    return RetrofitClientInstance.getRetrofitInstance(DEFAULT_URL).create(APIService.class);
  }

//  /**
//   * Please note these methods are cookie cutter methods from Google/Udacity's Sunshine Project.
//   *
//   * @param context
//   * @param params
//   * @return URL
//   */
//  public static URL buildUrl(Context context, String params){
//
//    Uri builtUri = Uri.parse(DEFAULT_URL)
//        .buildUpon()
//        .appendPath(params)
//        .appendQueryParameter(KEY_PARAM, APIKEY)
//        .appendQueryParameter("language", LANG_PARAM)
//        .appendQueryParameter("page", PAGE_PARAM)
//        .build();
//    URL url = null;
//
//    try{
//      url = new URL(builtUri.toString());
//    }catch(MalformedURLException e){
//      e.printStackTrace();
//    }
//
//    Log.v(LOG_TAG, "Built URI " + url);
//
//    return url;
//
//  }
//
//  /**
//   * Please note these methods are cookie cutter methods from Google/Udacity's Sunshine Project.
//   *
//   * @param url  URL to fetch response from
//   * @return Contents of HTTP response
//   * @throws IOException For network response
//   */
//  public static String getResponseFromHttpURL(URL url) throws IOException {
//
//    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//    try{
//      InputStream in = urlConnection.getInputStream();
//      Scanner scanner = new Scanner(in);
//      scanner.useDelimiter("\\A");
//
//      if(scanner.hasNext()){
//        return scanner.next();
//      }else{
//        return null;
//      }
//    }finally{
//      urlConnection.disconnect();
//    }
//
//  }

}
