
package app.com.showme.android.verizon.models.photo_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Editability {

    @SerializedName("cancomment")
    @Expose
    private int cancomment;
    @SerializedName("canaddmeta")
    @Expose
    private int canaddmeta;

    public int getCancomment() {
        return cancomment;
    }

    public void setCancomment(int cancomment) {
        this.cancomment = cancomment;
    }

    public int getCanaddmeta() {
        return canaddmeta;
    }

    public void setCanaddmeta(int canaddmeta) {
        this.canaddmeta = canaddmeta;
    }

}
