package com.wcreation.sprinklesbakeryadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wcreation.sprinklesbakeryadmin.Model.Category;
import com.wcreation.sprinklesbakeryadmin.R;

import java.util.ArrayList;
import java.util.List;

public class CatRecyclerAdapter extends RecyclerView.Adapter<CatRecyclerAdapter.MyViewHolder>{

    private Context context;
    private List<Category> categories = new ArrayList<>();

    public CatRecyclerAdapter(Context mContext, List<Category> categoriesList){
        this.context = mContext;
        this.categories = categoriesList;
    }


    @NonNull
    @Override
    public CatRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cat_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatRecyclerAdapter.MyViewHolder holder, int position) {
        final Category categorie = categories.get(position);
        holder.txtCatName.setText(categorie.getCat_name());
        holder.txtCatDesc.setText(categorie.getCat_desc());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtCatName, txtCatDesc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCatName = itemView.findViewById(R.id.txtCatName);
            txtCatDesc = itemView.findViewById(R.id.txtCatDesc);

        }
    }
}
