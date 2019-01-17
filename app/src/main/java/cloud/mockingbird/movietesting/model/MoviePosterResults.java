package cloud.mockingbird.movietesting.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviePosterResults implements Parcelable {

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    @SerializedName("results")
    @Expose
    private List<MoviePoster> results;

    public MoviePosterResults(){}

    public MoviePosterResults(Integer page, Integer totalResults, Integer totalPages, List<MoviePoster> results) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public MoviePosterResults(Parcel source){
        page = source.readInt();
        totalResults = source.readInt();
        totalPages = source.readInt();
        results = source.createTypedArrayList(MoviePoster.CREATOR);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<MoviePoster> getResults() {
        return results;
    }

    public void setResults(List<MoviePoster> results) {
        this.results = results;
    }

    public static final Parcelable.Creator<MoviePosterResults> CREATOR = new Parcelable.Creator<MoviePosterResults>(){

        @Override
        public MoviePosterResults createFromParcel(Parcel source) {
            return new MoviePosterResults(source);
        }

        @Override
        public MoviePosterResults[] newArray(int size) {
            return new MoviePosterResults[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(page);
        dest.writeInt(totalResults);
        dest.writeInt(totalPages);
        dest.writeList(results);

    }

}
