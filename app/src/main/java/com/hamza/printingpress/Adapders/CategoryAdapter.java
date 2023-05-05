package com.hamza.printingpress.Adapders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hamza.printingpress.Models.Category;
import com.hamza.printingpress.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context mContext;
    LayoutInflater inflater;
    private List<Category> categoryList = null;
    private ArrayList<Category> arraylist;
    NavController navController;
    FragmentActivity activity;

    public CategoryAdapter(Context mContext, List<Category> categoryList, FragmentActivity fragmentActivity) {
        this.mContext = mContext;
        this.categoryList = categoryList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Category>();
        this.arraylist.addAll(categoryList);
        this.activity=fragmentActivity;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        categoryList.clear();
        if (charText.length() == 0) {
            categoryList.addAll(arraylist);
        } else {
            for (Category wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    categoryList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.single_category_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.name;
        textView.setText(category.getName());

        ImageView image=holder.image;
        Glide.with(mContext).load(category.getCategory_image()).into(image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController= Navigation.findNavController(activity, R.id.nav_host_fragment_content_main);
                Bundle bundle = new Bundle();
                bundle.putString("service",category.getName());
                bundle.putInt("type",0);
                navController.navigate(R.id.serviceDetailFragment,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.cat_name);
            image=(ImageView) itemView.findViewById(R.id.cat_image);
        }

    }
}
