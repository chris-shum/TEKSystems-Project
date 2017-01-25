
package app.com.showme.android.verizon.models.photo_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usage {

    @SerializedName("candownload")
    @Expose
    private int candownload;
    @SerializedName("canblog")
    @Expose
    private int canblog;
    @SerializedName("canprint")
    @Expose
    private int canprint;
    @SerializedName("canshare")
    @Expose
    private int canshare;

    public int getCandownload() {
        return candownload;
    }

    public void setCandownload(int candownload) {
        this.candownload = candownload;
    }

    public int getCanblog() {
        return canblog;
    }

    public void setCanblog(int canblog) {
        this.canblog = canblog;
    }

    public int getCanprint() {
        return canprint;
    }

    public void setCanprint(int canprint) {
        this.canprint = canprint;
    }

    public int getCanshare() {
        return canshare;
    }

    public void setCanshare(int canshare) {
        this.canshare = canshare;
    }

}
