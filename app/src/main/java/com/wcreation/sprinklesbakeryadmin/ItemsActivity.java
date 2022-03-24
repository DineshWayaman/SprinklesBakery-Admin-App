package com.wcreation.sprinklesbakeryadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wcreation.sprinklesbakeryadmin.Adapter.ItemsRecyclerAdapter;
import com.wcreation.sprinklesbakeryadmin.Model.Items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsActivity extends AppCompatActivity {

    RecyclerView recyclerItems;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    ProgressDialog progressDialog;
    Spinner spnCat;
    EditText edtIname, edtQty, edtPrice, edtIng, edtMsg;
    ImageView imgItemimg;
    AppCompatButton btnSaveItem, btnChooseImg;

    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private List<Items> items;
    ArrayList<String> category = new ArrayList<>();
    ArrayAdapter<String> catAdapter;
    FloatingActionButton fabAddNew;
    Dialog dialog;

    final int CODE_GALLERY_REQUEST =999;
    Bitmap bitmap;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        recyclerItems = findViewById(R.id.recyclerItems);
        fabAddNew = findViewById(R.id.fabAddNew);

        manager = new LinearLayoutManager(this);
        recyclerItems.setLayoutManager(manager);
        items = new ArrayList<>();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_new_items);



        spnCat = dialog.findViewById(R.id.spnCategory);
        edtIname = dialog.findViewById(R.id.edtItemName);
        edtQty = dialog.findViewById(R.id.edtQty);
        edtPrice = dialog.findViewById(R.id.edtPrice);
        edtIng = dialog.findViewById(R.id.edtIIng);
        edtMsg = dialog.findViewById(R.id.edtImessage);
        btnChooseImg = dialog.findViewById(R.id.btnChooseImg);
        btnSaveItem = dialog.findViewById(R.id.btnSaveItem);
        imgItemimg = dialog.findViewById(R.id.imgUpload);

        btnChooseImg.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(
                    ItemsActivity.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODE_GALLERY_REQUEST
            );
        });

        btnSaveItem.setOnClickListener(view -> {
            if (edtIname.getText().length()==0 || edtQty.getText().length()==0 || edtPrice.getText().length()==0 || edtMsg.getText().length()==0 || edtIng.getText().length()==0){
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            }else{
                saveItem(edtIname.getText().toString(), spnCat.getSelectedItem().toString(), edtQty.getText().toString(), edtPrice.getText().toString(), edtIng.getText().toString(), edtMsg.getText().toString(), imageToString(bitmap));
            }
        });


        getCategories();


        fabAddNew.setOnClickListener(view -> {
            dialog.show();
        });


        getItems();

    }

    private void saveItem(String name, String cat, String qty, String price, String ing, String msg, String img) {
        loading();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, additemurl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                dialog.dismiss();
                Toast.makeText(ItemsActivity.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ItemsActivity.this, "error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("name", name);
                params.put("cat", cat);
                params.put("qty", qty);
                params.put("price", price);
                params.put("ing", ing);
                params.put("msg", msg);
                params.put("image", img);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ItemsActivity.this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent =new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            }else{
                Toast.makeText(getApplicationContext(), "You don't have permission to access gallery!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgItemimg.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodedString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedString;
    }

    private void getCategories() {
        loading();
        stringRequest = new StringRequest(Request.Method.GET, baseCarUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array  = new JSONArray(response);
                    for (int i=0; i< array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        int id = object.getInt("c_id");
                        String c_name = object.getString("c_name");
                        String c_desc = object.getString("c_desc");

//                        Categories categoriesModel = new Categories(id, c_name, c_desc);
//                        categories.add(categoriesModel);
                        category.add(c_name);
                        catAdapter = new ArrayAdapter<>(ItemsActivity.this,
                                android.R.layout.simple_spinner_item, category);

                        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spnCat.setAdapter(catAdapter);
                        progressDialog.dismiss();
                    }

                }catch (Exception e){
                    progressDialog.dismiss();
                    Toast.makeText(ItemsActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ItemsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        Volley.newRequestQueue(ItemsActivity.this).add(stringRequest);
    }


    private void loading(){
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    private String baseCarUrl() {
        return "https://dineshwayaman.com/final/getallcat.php";
    }

    private String additemurl() {
        return "https://dineshwayaman.com/final/add-item.php";
    }


    private void getItems() {
        requestQueue  = Volley.newRequestQueue(ItemsActivity.this);
        stringRequest = new StringRequest(Request.Method.POST, baseUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray array = new JSONArray(response);
                    for (int i=0; i<array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        int item_id = object.getInt("i_id");
                        String i_name = object.getString("i_name");
                        String i_cat = object.getString("i_cat");
                        int i_qty = object.getInt("i_qty");
                        double i_price = object.getDouble("i_price");
                        String i_ingredients = object.getString("i_ingredients");
                        String i_message = object.getString("i_message");
                        String i_img = object.getString("i_img");

                        Items itemsList = new Items(item_id,i_name,i_cat,i_qty,i_price,i_ingredients,i_message,i_img);
                        items.add(itemsList);

                    }
                }catch(JSONException e){
                    Toast.makeText(ItemsActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                }
                adapter = new ItemsRecyclerAdapter(ItemsActivity.this,items);
                recyclerItems.setAdapter(adapter);
                if (adapter.getItemCount()==0){
                    Toast.makeText(ItemsActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ItemsActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("cat_name", "all");


                return params;
            }
        };

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    private String baseUrl() {
        return "https://dineshwayaman.com/final/getitems.php";
    }


}