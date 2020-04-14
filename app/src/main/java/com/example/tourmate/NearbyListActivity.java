package com.example.tourmate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tourmate.model.KeyValue;

import java.util.ArrayList;

public class NearbyListActivity extends AppCompatActivity {

    ArrayList<KeyValue> nearByTypeList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_list);
        getList(23.822350,90.365417,"restaurant");
    }

    public void getList(double lat,double lng,String type){
        //"&keyword="+keyword.getText().toString()+
        String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lng+"&radius=10000&type="+type+"&key=AIzaSyDsj__sDfjvf0gm9B6POx1PtHRrHf2D3KE";
        System.out.println(url);

    }



}
