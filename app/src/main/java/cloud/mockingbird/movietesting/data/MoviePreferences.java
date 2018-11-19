package cloud.mockingbird.movietesting.data;

public class MoviePreferences {

  public static final String PREF_SORT_POPULARITY = "popular";
  public static final String PREF_SORT_RATING = "top_rated";
  public static final String DEFAULT_SORT = PREF_SORT_POPULARITY;

  public static String getSortPreferred(){
    return DEFAULT_SORT;
  }

}
