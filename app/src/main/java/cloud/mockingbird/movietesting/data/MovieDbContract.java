package cloud.mockingbird.movietesting.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieDbContract {

    public static final String AUTHORITY = "cloud.mockingbird.movietesting";
    public static final String FAVORITE_PATH = "favorites";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private MovieDbContract(){}

    public static final class MovieEntity implements BaseColumns{
        public static final Uri BUILT_URI = BASE_CONTENT_URI.buildUpon().appendPath(FAVORITE_PATH).build();
        public static final String MOVIE_LIST = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + FAVORITE_PATH;
        public static final String MOVIE_INDIVIDUAL = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + FAVORITE_PATH;
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_ID = "_ID";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_POSTER = "movie_poster";
    }

}
