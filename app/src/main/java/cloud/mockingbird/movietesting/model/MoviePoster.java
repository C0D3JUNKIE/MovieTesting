package cloud.mockingbird.movietesting.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MoviePoster implements Parcelable {

  @SerializedName("title")
  private String movieTitle;
  @SerializedName("release_date")
  private String movieReleaseDate;
  @SerializedName("vote_average")
  private String movieRating;
  @SerializedName("overview")
  private String movieDescription;
  @SerializedName("poster_path")
  private String movieImagePath;

  public MoviePoster(String title, String releaseDate, String rating, String description, String imagePath){
    movieTitle = title;
    movieReleaseDate = releaseDate;
    movieRating = rating;
    movieDescription = description;
    movieImagePath = imagePath;
  }

  private MoviePoster(Parcel source){

    movieTitle = source.readString();
    movieReleaseDate = source.readString();
    movieRating = source.readString();
    movieDescription = source.readString();
    movieImagePath = source.readString();

  }


  public String getMovieTitle() {
    return movieTitle;
  }

  public void setMovieTitle(String movieTitle) {
    this.movieTitle = movieTitle;
  }

  public String getMovieReleaseDate() {
    return movieReleaseDate;
  }

  public void setMovieReleaseDate(String movieReleaseDate) {
    this.movieReleaseDate = movieReleaseDate;
  }

  public String getMovieRating() {
    return movieRating;
  }

  public void setMovieRating(String movieRating) {
    this.movieRating = movieRating;
  }

  public String getMovieDescription() {
    return movieDescription;
  }

  public void setMovieDescription(String movieDescription) {
    this.movieDescription = movieDescription;
  }

  public String getMovieImagePath() {
    return movieImagePath;
  }

  public void setMovieImagePath(String movieImagePath) {
    this.movieImagePath = movieImagePath;
  }


  public static final Parcelable.Creator<MoviePoster> CREATOR = new Parcelable.Creator<MoviePoster>() {

    @Override
    public MoviePoster createFromParcel(Parcel parcel) {
      return new MoviePoster(parcel);
    }

    @Override
    public MoviePoster[] newArray(int i) {
      return new MoviePoster[i];
    }

  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(movieTitle);
    parcel.writeString(movieReleaseDate);
    parcel.writeString(movieRating);
    parcel.writeString(movieDescription);
    parcel.writeString(movieImagePath);

  };

}


