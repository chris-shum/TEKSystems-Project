
package app.com.showme.android.verizon.models.photo_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dates {

    @SerializedName("posted")
    @Expose
    private String posted;
    @SerializedName("taken")
    @Expose
    private String taken;
    @SerializedName("takengranularity")
    @Expose
    private int takengranularity;
    @SerializedName("takenunknown")
    @Expose
    private String takenunknown;
    @SerializedName("lastupdate")
    @Expose
    private String lastupdate;

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public String getTaken() {
        return taken;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }

    public int getTakengranularity() {
        return takengranularity;
    }

    public void setTakengranularity(int takengranularity) {
        this.takengranularity = takengranularity;
    }

    public String getTakenunknown() {
        return takenunknown;
    }

    public void setTakenunknown(String takenunknown) {
        this.takenunknown = takenunknown;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

}
