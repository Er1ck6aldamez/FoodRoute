package com.example.hd.foodroute_v1;

/**
 * Created by hd on 25/9/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import Clases.AdaptadorExpandible;
import Clases.ChildInfo;
import Clases.GroupInfo;
import cz.msebera.android.httpclient.Header;

public class TbRestaurantes extends Fragment{

    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    private AdaptadorExpandible listAdapter;
    private ExpandableListView simpleExpandableListView;
    private ProgressBar prgCircular;

    private SwipeRefreshLayout swipeContainer;

    private boolean recargar=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tbrestaurantes, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeContainer = (SwipeRefreshLayout)getView().findViewById(R.id.contenedor);
        //get reference of the ExpandableListView
        simpleExpandableListView = (ExpandableListView)getView().findViewById(R.id.simpleExpandableListView);
        prgCircular = (ProgressBar)getView().findViewById(R.id.prgCircular);

        if(savedInstanceState==null) {
            // add data for displaying in expandable list view
            loadData();
            // create the adapter by passing your ArrayList data
            listAdapter = new AdaptadorExpandible(getActivity().getApplicationContext(),deptList);
        }else{
            deptList=(ArrayList<GroupInfo>) savedInstanceState.getSerializable("listRestaurantes");
            listAdapter = new AdaptadorExpandible(getActivity().getApplicationContext(),deptList);

            prgCircular.setVisibility(View.INVISIBLE);
            simpleExpandableListView.setVisibility(View.VISIBLE);
        }


        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(listAdapter);

        //expand all the Groups
        //expandAll();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                deptList=new ArrayList<GroupInfo>();
                loadData();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // setOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //get the child info
                ChildInfo detailInfo =  headerInfo.getProductList().get(childPosition);
                //display it or do something with it
                DetalleRestaurante.Datos = detailInfo.getName();
                Intent rest=new Intent(getActivity().getApplicationContext(),DetalleRestaurante.class);
                startActivity(rest);
                /*Toast.makeText(getActivity().getApplicationContext(), " Clicked on :: " + headerInfo.getName()
                        + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();*/
                return false;
            }
        });

        // setOnGroupClickListener listener for group heading click
        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //display it or do something with it

                return false;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("listRestaurantes", deptList);
        super.onSaveInstanceState(outState);
    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.collapseGroup(i);
        }
    }

    //load some initial data into out list
    private void loadData(){

        AsyncHttpClient client=new AsyncHttpClient();
        client.get("https://foodroute.000webhostapp.com/proyecto/obtener_restaurantes_por_esp.php?especialidad=Comida a la carta", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONObject json=new JSONObject(new String(responseBody));
                        JSONArray jsonArray=json.getJSONArray("restaurantes");
                        //sug=new Sugerencias[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            addProduct("Comida a la carta",jsonArray.getJSONObject(i).getString("Nombre"));
                        }
                        listAdapter.notifyDataSetChanged();
                        recargar=true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Error al cargar lista",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                addProduct("Comida a la carta","Error al cargar lista");
                listAdapter.notifyDataSetChanged();
                prgCircular.setVisibility(View.INVISIBLE);
                //simpleExpandableListView.setVisibility(View.VISIBLE);recargar=true;
                recargar=true;
            }
        });

        client.get("https://foodroute.000webhostapp.com/proyecto/obtener_restaurantes_por_esp.php?especialidad=Comida mexicana", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONObject json=new JSONObject(new String(responseBody));
                        JSONArray jsonArray=json.getJSONArray("restaurantes");
                        //sug=new Sugerencias[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            addProduct("Comida mexicana",jsonArray.getJSONObject(i).getString("Nombre"));
                        }
                        listAdapter.notifyDataSetChanged();
                        recargar=true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Error al cargar lista",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                addProduct("Comida mexicana","Error al cargar lista");
                listAdapter.notifyDataSetChanged();
                prgCircular.setVisibility(View.INVISIBLE);
                //simpleExpandableListView.setVisibility(View.VISIBLE);
                recargar=true;
            }
        });

        client.get("https://foodroute.000webhostapp.com/proyecto/obtener_restaurantes_por_esp.php?especialidad=Comida a la vista", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONObject json=new JSONObject(new String(responseBody));
                        JSONArray jsonArray=json.getJSONArray("restaurantes");
                        //sug=new Sugerencias[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            addProduct("Comida a la vista",jsonArray.getJSONObject(i).getString("Nombre"));
                        }
                        listAdapter.notifyDataSetChanged();
                        recargar=true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Error al cargar lista",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                addProduct("Comida a la vista","Error al cargar lista");
                listAdapter.notifyDataSetChanged();
                prgCircular.setVisibility(View.INVISIBLE);
                //simpleExpandableListView.setVisibility(View.VISIBLE);
                recargar=true;
            }
        });

        client.get("https://foodroute.000webhostapp.com/proyecto/obtener_restaurantes_por_esp.php?especialidad=Comida a la parrilla", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONObject json=new JSONObject(new String(responseBody));
                        JSONArray jsonArray=json.getJSONArray("restaurantes");
                        //sug=new Sugerencias[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            addProduct("Comida a la parrilla",jsonArray.getJSONObject(i).getString("Nombre"));
                        }
                        listAdapter.notifyDataSetChanged();
                        recargar=true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Error al cargar lista",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                addProduct("Comida a la parrilla","Error al cargar lista");
                listAdapter.notifyDataSetChanged();
                prgCircular.setVisibility(View.INVISIBLE);
                //simpleExpandableListView.setVisibility(View.VISIBLE);
                recargar=true;
            }
        });

        client.get("https://foodroute.000webhostapp.com/proyecto/obtener_restaurantes_por_esp.php?especialidad=Comida tradicional", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONObject json=new JSONObject(new String(responseBody));
                        JSONArray jsonArray=json.getJSONArray("restaurantes");
                        //sug=new Sugerencias[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            addProduct("Comida tradicional",jsonArray.getJSONObject(i).getString("Nombre"));
                        }
                        listAdapter.notifyDataSetChanged();
                        recargar=true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Error al cargar lista",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                addProduct("Comida tradicional","Error al cargar lista");
                listAdapter.notifyDataSetChanged();
                prgCircular.setVisibility(View.INVISIBLE);
                //simpleExpandableListView.setVisibility(View.VISIBLE);
                recargar=true;
            }
        });

        client.get("https://foodroute.000webhostapp.com/proyecto/obtener_restaurantes_por_esp.php?especialidad=Comida rapida", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    prgCircular.setVisibility(View.INVISIBLE);
                    simpleExpandableListView.setVisibility(View.VISIBLE);
                    try {
                        JSONObject json=new JSONObject(new String(responseBody));
                        JSONArray jsonArray=json.getJSONArray("restaurantes");
                        //sug=new Sugerencias[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            addProduct("Comida rápida",jsonArray.getJSONObject(i).getString("Nombre"));
                        }
                        listAdapter.notifyDataSetChanged();
                        recargar=true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Error al cargar lista",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                addProduct("Comida rápida","Error al cargar lista");
                listAdapter.notifyDataSetChanged();
                prgCircular.setVisibility(View.INVISIBLE);
                simpleExpandableListView.setVisibility(View.VISIBLE);
                recargar=true;
            }
        });

        /*addProduct("Comida mexicana","PolyMorphism");
        addProduct("Comida mexicana","Collections");

        addProduct("Comida a la vista","PolyMorphism");
        addProduct("Comida a la vista","Collections");*/
        swipeContainer.setRefreshing(false);
    }

    //here we maintain our products in various departments
    private int addProduct(String department, String product){

        int groupPosition = 0;

        //check the hash map if the group already exists
        GroupInfo headerInfo = subjects.get(department);
        //add the group if doesn't exists
        if(headerInfo == null){
            headerInfo = new GroupInfo();
            headerInfo.setName(department);
            subjects.put(department, headerInfo);
            deptList.add(headerInfo);
        }

        //get the children for the group
        if(recargar) {
            headerInfo.getProductList().clear();
            recargar=false;
        }
        ArrayList<ChildInfo> productList = headerInfo.getProductList();
        //size of the children list
        int listSize = productList.size();
        //add to the counter
        listSize++;

        //create a new child and add that to the group
        ChildInfo detailInfo = new ChildInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);

        //find the group position inside the list
        groupPosition = deptList.indexOf(headerInfo);

        return groupPosition;
    }
}
