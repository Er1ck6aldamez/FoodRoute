package Clases;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Choche on 13/10/2017.
 */

public class ChildInfo implements Parcelable{
    private String sequence = "";//ID del restaurante
    private String name = "";
    private String latitud = "";
    private String longitud = "";

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public static final Parcelable.Creator<ChildInfo> CREATOR
            = new Parcelable.Creator<ChildInfo>() {
        public ChildInfo createFromParcel(Parcel in) {
            return new ChildInfo(in);
        }

        public ChildInfo[] newArray(int size) {
            return new ChildInfo[size];
        }
    };
    public ChildInfo(){

    }

    public ChildInfo(Parcel in){
        readFromParcel(in);
    }
    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sequence);
        dest.writeString(name);
        dest.writeString(latitud);
        dest.writeString(longitud);
    }
    private void readFromParcel(Parcel in){
        sequence=in.readString();
        name=in.readString();
        latitud=in.readString();
        longitud=in.readString();
    }
}
