package cloud.mockingbird.movietesting.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieTrailerResults implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("results")
    @Expose
    private List<MovieTrailer> results;

    public MovieTrailerResults(Integer id, List<MovieTrailer> results) {
        this.id = id;
        this.results = results;
    }

    public MovieTrailerResults(Parcel source){
        id = source.readInt();
        results = source.createTypedArrayList(MovieTrailer.CREATOR);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieTrailer> getResults() {
        return results;
    }

    public void setResults(List<MovieTrailer> results) {
        this.results = results;
    }

    public static final Parcelable.Creator<MovieTrailerResults> CREATOR = new Parcelable.Creator<MovieTrailerResults>(){
        @Override
        public MovieTrailerResults createFromParcel(Parcel source) {
            return new MovieTrailerResults(source);
        }

        @Override
        public MovieTrailerResults[] newArray(int size) {
            return new MovieTrailerResults[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeList(results);
    }

}
