package cloud.mockingbird.movietesting.retrofit;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    //Private class variables
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    //Private Constructor? is this even needed?
    private RetrofitClientInstance(){ }

    //Retrofit get method
    public static Retrofit getRetrofitInstance(String baseUrl){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
