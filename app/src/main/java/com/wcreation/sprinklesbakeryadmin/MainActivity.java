package com.wcreation.sprinklesbakeryadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    CardView crdProducts,crdCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crdProducts = findViewById(R.id.crdProducts);
        crdCategory = findViewById(R.id.crdCategory);
        crdProducts.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, ItemsActivity.class);
            startActivity(i);
        });


        crdCategory.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(i);
        });
    }
}