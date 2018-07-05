
package io.github.stewilondanga.update_manager.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Update {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Update() {
    }

    /**
     * 
     * @param response
     */
    public Update(Response response) {
        super();
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getPushId(){
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
