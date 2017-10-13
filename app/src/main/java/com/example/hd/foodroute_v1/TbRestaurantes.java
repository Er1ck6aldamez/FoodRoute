package com.example.hd.foodroute_v1;

/**
 * Created by hd on 25/9/2017.
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import Clases.AdapterRestaurantes;
import Clases.Item;

public class TbRestaurantes extends Fragment{


    private AdapterRestaurantes adaptador;
    private ArrayList<Item> item;
    private ListView lvItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tbrestaurantes, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        item = new ArrayList<>();
        adaptador = new AdapterRestaurantes(getActivity().getApplicationContext(),item);
        lvItems = (ListView)getView().findViewById(R.id.lv_items);



        AdapterRestaurantes adapter = getAdapter();

        lvItems.setAdapter(adapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdapterRestaurantes adapter = (AdapterRestaurantes) parent.getAdapter();


                Item item = (Item) adapter.getItem(position);
                if(item != null){
                    if(item.isExpanded){
                        item.isExpanded = false;

                    }else{
                        item.isExpanded = true;
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });

    }


    private AdapterRestaurantes getAdapter(){

        List<Item> items = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            Item item = new Item();
            item.title = "Title Item " + i;
            item.description = "Description for Title Item "+ i;
            item.isExpanded = false;

            items.add(item);
        }

        return new AdapterRestaurantes(getContext(),items);
    }

}
