package cloud.mockingbird.movietesting.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviePoster implements Parcelable {

  @SerializedName("id")
  @Expose
  private String movieId;

  @SerializedName("title")
  @Expose
  private String movieTitle;

  @SerializedName("release_date")
  @Expose
  private String movieReleaseDate;

  @SerializedName("vote_average")
  private String movieRating;
  @SerializedName("overview")
  @Expose
  private String movieDescription;

  @SerializedName("poster_path")
  @Expose
  private String movieImagePath;

  //No Arg Constructor
  public MoviePoster(){}

  //Constructor
  public MoviePoster(String id, String title, String releaseDate, String rating, String description, String imagePath){
    movieId = id;
    movieTitle = title;
    movieReleaseDate = releaseDate;
    movieRating = rating;
    movieDescription = description;
    movieImagePath = imagePath;
  }

  //Parcel Constructor
  private MoviePoster(Parcel source){

    movieId = source.readString();
    movieTitle = source.readString();
    movieReleaseDate = source.readString();
    movieRating = source.readString();
    movieDescription = source.readString();
    movieImagePath = source.readString();

  }


  //Getters and Setters for POJO
  public String getMovieId() {
    return movieId;
  }

  public void setMovieId(String movieId) {
    this.movieId = movieId;
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

    @SuppressWarnings({"unchecked"})
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
    parcel.writeString(movieId);
    parcel.writeString(movieTitle);
    parcel.writeString(movieReleaseDate);
    parcel.writeString(movieRating);
    parcel.writeString(movieDescription);
    parcel.writeString(movieImagePath);

  };

}


