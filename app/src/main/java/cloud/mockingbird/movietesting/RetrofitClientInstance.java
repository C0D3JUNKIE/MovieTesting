package cloud.mockingbird.movietesting;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    //Private class variables
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";

    //Private Constructor? is this even needed?
    private RetrofitClientInstance(){ }

    //Retrofit get method
    public static Retrofit getRetrofitInstance(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
