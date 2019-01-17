package cloud.mockingbird.movietesting.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MovieDbHelpter extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";
    public static final String TABLE_NAME = "movies";
    public static final int DATABASE_VERSION = 1;

    public MovieDbHelpter(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public MovieDbHelpter(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                MovieDbContract.MovieEntity.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDbContract.MovieEntity.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieDbContract.MovieEntity.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieDbContract.MovieEntity.COLUMN_RELEASE_DATE + " TEXT, " +
                MovieDbContract.MovieEntity.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MovieDbContract.MovieEntity.COLUMN_DESCRIPTION + " TEXT, " +
                MovieDbContract.MovieEntity.COLUMN_POSTER + " TEXT" + ");";
        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
