package com.hamza.printingpress.Adapders;

import static com.hamza.printingpress.Models.Product.LayoutOne;
import static com.hamza.printingpress.Models.Product.LayoutTwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.printingpress.Models.Product;
import com.hamza.printingpress.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter{
    Context context;
    private List<Product> itemList;

    public ProductAdapter(List<Product> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (itemList.get(position).getViewType()) {
            case 0:
                return LayoutOne;
            case 1:
                return LayoutTwo;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        switch (viewType) {
            case LayoutOne:
                View layoutOne
                        = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_product_service_item, parent,
                                false);
                return new layoutOneViewHolder(layoutOne);
            case LayoutTwo:
                View layoutTwo
                        = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_product_service_item_alternative, parent,
                                false);
                return new layoutTwoViewHolder(layoutTwo);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (itemList.get(position).getViewType()) {
            case LayoutOne:
                int image=itemList.get(position).getImage();
                String name=itemList.get(position).getName();
                String desc=itemList.get(position).getDesc();
                String price= String.valueOf(itemList.get(position).getPrice());
                ((layoutOneViewHolder)holder).setView(image,name,desc,price);
                break;
            case LayoutTwo:
                int image1=itemList.get(position).getImage();
                String name1=itemList.get(position).getName();
                String desc1=itemList.get(position).getDesc();
                String price1= String.valueOf(itemList.get(position).getPrice());
                ((layoutTwoViewHolder)holder).setView(image1,name1,desc1,price1);
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class layoutOneViewHolder
            extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productDesc, productPrice;


        public layoutOneViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.pro_image);
            productName=itemView.findViewById(R.id.pro_name);
            productDesc=itemView.findViewById(R.id.pro_desc);
            productPrice=itemView.findViewById(R.id.pro_price);
        }

        private void setView(int image,String name, String desc, String price)
        {
            productImage.setImageResource(image);
            productName.setText(name);
            productDesc.setText(desc);
            productPrice.setText(price);
        }
    }

    class layoutTwoViewHolder
            extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productDesc, productPrice;


        public layoutTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.pro_image);
            productName=itemView.findViewById(R.id.pro_name);
            productDesc=itemView.findViewById(R.id.pro_desc);
            productPrice=itemView.findViewById(R.id.pro_price);
        }

        private void setView(int image,String name, String desc, String price)
        {
            productImage.setImageResource(image);
            productName.setText(name);
            productDesc.setText(desc);
            productPrice.setText(price);
        }
    }
}
