package cloud.mockingbird.movietesting.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieReviewResults implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("results")
    @Expose
    private ArrayList<MovieReview> results;

    @SerializedName("total_pages")
    @Expose
    private Integer total_pages;

    @SerializedName("total_results")
    @Expose
    private Integer total_results;

    public MovieReviewResults(Integer id, Integer page, ArrayList<MovieReview> results, Integer total_page, Integer total_results) {
        this.id = id;
        this.page = page;
        this.results = results;
        this.total_pages = total_page;
        this.total_results = total_results;
    }

    public MovieReviewResults(Parcel source){

        id = source.readInt();
        page = source.readInt();
        results = source.createTypedArrayList(MovieReview.CREATOR);
        total_pages = source.readInt();
        total_results = source.readInt();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<MovieReview> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieReview> results) {
        this.results = results;
    }

    public Integer getTotal_page() {
        return total_pages;
    }

    public void setTotal_page(Integer total_page) {
        this.total_pages = total_page;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    public static final Parcelable.Creator<MovieReviewResults> CREATOR = new Parcelable.Creator<MovieReviewResults>(){
        @Override
        public MovieReviewResults createFromParcel(Parcel source) {
            return new MovieReviewResults(source);
        }

        @Override
        public MovieReviewResults[] newArray(int size) {
            return new MovieReviewResults[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(page);
        dest.writeList(results);
        dest.writeInt(total_pages);
        dest.writeInt(total_results);
    }
}
