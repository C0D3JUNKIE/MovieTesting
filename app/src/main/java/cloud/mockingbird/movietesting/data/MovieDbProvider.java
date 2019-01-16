package cloud.mockingbird.movietesting.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieDbProvider extends ContentProvider {

    private MovieDbHelpter dbHelper;

    public static final int FAVORITES = 111;
    public static final int FAVORITES_BY_ID = 313;

    public static final UriMatcher URI_MATCHER = createUriMatcher();

    public static UriMatcher createUriMatcher(){
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MovieDbContract.AUTHORITY, MovieDbContract.FAVORITE_PATH, FAVORITES);
        matcher.addURI(MovieDbContract.AUTHORITY, MovieDbContract.FAVORITE_PATH + "/#", FAVORITES_BY_ID);
        return matcher;
    }

    @androidx.annotation.Nullable
    @Nullable
    @Override
    public Cursor query(@androidx.annotation.NonNull @NonNull Uri uri, @androidx.annotation.Nullable @Nullable String[] projection, @androidx.annotation.Nullable @Nullable String selection, @androidx.annotation.Nullable @Nullable String[] selectionArgs, @androidx.annotation.Nullable @Nullable String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        int matcher = URI_MATCHER.match(uri);
        Cursor cursor = null;
        switch(matcher){
            case FAVORITES:
                cursor = db.query(MovieDbContract.MovieEntity.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );
                break;
            case FAVORITES_BY_ID:
                cursor = db.query(MovieDbContract.MovieEntity.TABLE_NAME, projection, "movie_id=?", new String[]{id}, null, null, sortOrder );
                break;
            default:
                throw new UnsupportedOperationException("*****    INCORRECT DB URI     *******    = " + uri);
        }
        return cursor;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MovieDbHelpter(getContext());
        return true;
    }

    @androidx.annotation.Nullable
    @Nullable
    @Override
    public String getType(@androidx.annotation.NonNull @NonNull Uri uri) {
        return null;
    }

    @androidx.annotation.Nullable
    @Nullable
    @Override
    public Uri insert(@androidx.annotation.NonNull @NonNull Uri uri, @androidx.annotation.Nullable @Nullable ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int matcher = URI_MATCHER.match(uri);
        Uri returnedUri;
        switch (matcher){
            case FAVORITES:
                long id = db.insert(MovieDbContract.MovieEntity.TABLE_NAME, null, values);
                if(id > 0){
                    returnedUri = ContentUris.withAppendedId(MovieDbContract.MovieEntity.BUILT_URI, id);
                }else{
                    throw new SQLException("*****  SQL EXCEPTION:  INSERT FAILED    ***** " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("*****    INCORRECT DB URI     *******    = " + uri);
        }
        if(getContext() != null){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return returnedUri;
    }

    @Override
    public int delete(@androidx.annotation.NonNull @NonNull Uri uri, @androidx.annotation.Nullable @Nullable String selection, @androidx.annotation.Nullable @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int matcher = URI_MATCHER.match(uri);
        switch (matcher){
            case FAVORITES_BY_ID:
                String id = uri.getPathSegments().get(1);
                return db.delete(MovieDbContract.MovieEntity.TABLE_NAME, "movie_id=?", new String[]{id});
            default:
                throw new UnsupportedOperationException("*****    INCORRECT DB URI     *******    = " + uri);
        }

    }

    @Override
    public int update(@androidx.annotation.NonNull @NonNull Uri uri, @androidx.annotation.Nullable @Nullable ContentValues values, @androidx.annotation.Nullable @Nullable String selection, @androidx.annotation.Nullable @Nullable String[] selectionArgs) {
        return 0;
    }
}
