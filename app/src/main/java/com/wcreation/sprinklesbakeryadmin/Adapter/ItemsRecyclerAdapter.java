package com.wcreation.sprinklesbakeryadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wcreation.sprinklesbakeryadmin.Model.Items;
import com.wcreation.sprinklesbakeryadmin.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.MyViewHolder>{

    private Context context;
    private List<Items> items = new ArrayList<>();

    public ItemsRecyclerAdapter(Context mContext, List<Items> itemsList){
        this.context = mContext;
        this.items = itemsList;
    }


    @NonNull
    @Override
    public ItemsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_items, parent, false);
        return new ItemsRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsRecyclerAdapter.MyViewHolder holder, int position) {
        final Items itemsNew = items.get(position);
        holder.txtItemName.setText(itemsNew.getI_name());
        holder.txtItemPrice.setText("Rs."+ String.valueOf(itemsNew.getI_price()));
        holder.txtItemQty.setText("Qty : "+itemsNew.getI_qty());
        Glide.with(context).load(itemsNew.getI_img()).into(holder.imgItem);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName, txtItemQty, txtItemPrice;
        CircleImageView imgItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.imgItem);
            txtItemName = itemView.findViewById(R.id.txtItemname);
            txtItemQty = itemView.findViewById(R.id.txtItemQty);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);

        }
    }
}
