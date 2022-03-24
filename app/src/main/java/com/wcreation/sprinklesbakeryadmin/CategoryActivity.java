package com.wcreation.sprinklesbakeryadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wcreation.sprinklesbakeryadmin.Adapter.CatRecyclerAdapter;
import com.wcreation.sprinklesbakeryadmin.Model.Category;
import com.wcreation.sprinklesbakeryadmin.Model.Items;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    RecyclerView recyclerCat;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private List<Category> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        recyclerCat = findViewById(R.id.recyclerCat);

        categories = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerCat.setLayoutManager(linearLayoutManager);

        getCatogories();

    }

    private void getCatogories() {
        stringRequest = new StringRequest(Request.Method.GET, baseCatUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array  = new JSONArray(response);
                    for (int i=0; i< array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        int id = object.getInt("c_id");
                        String c_name = object.getString("c_name");
                        String c_desc = object.getString("c_desc");

                        Category categoriesModel = new Category(id, c_name, c_desc);
                        categories.add(categoriesModel);

                    }

                }catch (Exception e){
                    Toast.makeText(CategoryActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

                adapter = new CatRecyclerAdapter(CategoryActivity.this, categories);
                recyclerCat.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CategoryActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        Volley.newRequestQueue(CategoryActivity.this).add(stringRequest);
    }

    private String baseCatUrl() {
        return "https://dineshwayaman.com/final/getallcat.php";
    }
}