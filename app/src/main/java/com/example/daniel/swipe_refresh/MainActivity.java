package com.example.daniel.swipe_refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mrecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    public  Metro_Recycler_Adapter metro_recycler_adapter;
    public static List<Metro_Cons> Metro_List = new ArrayList<>();
    private String url = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=55ec6d6e-dc5c-4268-a725-d04cc262172b&sort=Destination";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mrecyclerview=(RecyclerView)findViewById(R.id.Metro_Recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMetroInfo();
                metro_recycler_adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getMetroInfo();
    }

    public void SetRecyclerview(){
        metro_recycler_adapter = new Metro_Recycler_Adapter(Metro_List, MainActivity.this);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mrecyclerview.setItemAnimator(new DefaultItemAnimator());
        mrecyclerview.setAdapter(metro_recycler_adapter);
    }

    private void getMetroInfo() {

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        FakeX509TrustManager.allowAllSSL();//讓Volley可以使用Https請求
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //{"result":{"offset":0,"limit":10000,"count":25,"sort":"Destination","results":[{"_id":"1","Station":"士林站","Destination":"大安站","UpdateTime":"2017-04-10T20:59:27.577"},...}]}}
                            JSONArray info = response.getJSONObject("result").getJSONArray("results");
                            for(int i = 0; i < info.length();i++){
                                JSONObject jsonObject = info.getJSONObject(i);
                                //{"_id":"1","Station":"士林站","Destination":"大安站","UpdateTime":"2017-04-10T20:59:27.577"}
                                String time = jsonObject.getString("UpdateTime").substring(11,19);
                                String get_Station=jsonObject.getString("Station");
                                String get_Destination=jsonObject.getString("Destination");
                                Metro_Cons metro_cons=new Metro_Cons(get_Station, get_Destination, time);
                                Metro_List.add(metro_cons);
                            }
                            SetRecyclerview();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Get data error !"+error.toString(),Toast.LENGTH_LONG).show();
            }
        }
        );
        queue.add(jsonObjectRequest);
    }
}
