package Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.hd.foodroute_v1.R;

import java.util.ArrayList;

/**
 * Created by Choche on 13/10/2017.
 */

public class AdaptadorExpandible extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<GroupInfo> deptList;

    public AdaptadorExpandible(Context context, ArrayList<GroupInfo> deptList) {
        this.context = context;
        this.deptList = deptList;
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ChildInfo> productList = deptList.get(groupPosition).getProductList();
        return productList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ChildInfo> productList = deptList.get(groupPosition).getProductList();
        return productList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupInfo headerInfo = (GroupInfo) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.group_items, null);
        }

        TextView heading = (TextView) convertView.findViewById(R.id.heading);
        heading.setText(headerInfo.getName().trim());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildInfo detailInfo = (ChildInfo) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_items, null);
        }
        TextView sequence = (TextView) convertView.findViewById(R.id.sequence);
        sequence.setText(detailInfo.getSequence().trim() + ". ");
        TextView childItem = (TextView) convertView.findViewById(R.id.childItem);
        childItem.setText(detailInfo.getName().trim());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
