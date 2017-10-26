package Clases;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Choche on 13/10/2017.
 */

public class GroupInfo implements Parcelable{
    private String name;
    private ArrayList<ChildInfo> list = new ArrayList<ChildInfo>();

    public static final Parcelable.Creator<GroupInfo> CREATOR
            = new Parcelable.Creator<GroupInfo>() {
        public GroupInfo createFromParcel(Parcel in) {
            return new GroupInfo(in);
        }

        public GroupInfo[] newArray(int size) {
            return new GroupInfo[size];
        }
    };
    public GroupInfo(){}

    public GroupInfo(Parcel in){
        readFromParcel(in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ChildInfo> getProductList() {
        return list;
    }

    public void setProductList(ArrayList<ChildInfo> productList) {
        this.list = productList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(list);
    }
    private void readFromParcel(Parcel in){
        name=in.readString();
        in.readTypedList(list,ChildInfo.CREATOR);
    }
}
