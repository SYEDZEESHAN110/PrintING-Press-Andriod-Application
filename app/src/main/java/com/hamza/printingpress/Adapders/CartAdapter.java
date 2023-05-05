package com.hamza.printingpress.Adapders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.printingpress.Models.CustomerPrintRequest;
import com.hamza.printingpress.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context mContext;
    LayoutInflater inflater;
    private List<CustomerPrintRequest> requestList = null;

    public CartAdapter(Context mContext, List<CustomerPrintRequest> requestList) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.cart_single_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomerPrintRequest request = requestList.get(position);

        holder.orderId.setText("Order# " + request.getId());
        holder.orderTotalPrice.setText(request.getCost());
        holder.orderService.setText(request.getShop_name() + "; " + request.getShop_contact());
        holder.orderDateAndTime.setText( String.valueOf(request.getQuantity()));
        holder.paymentStatus.setText(request.getProduct_size());
        if(request.getStatus() == "1") {
            holder.orderStatus.setText("Completed");
            holder.status.setBackgroundResource(R.drawable.mystatus_bg_green);
        } else {
            holder.orderStatus.setText("Pending");
            holder.status.setBackgroundResource(R.drawable.mystatus_bg);

        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId;
        TextView orderStatus;
        View status;
        TextView orderTotalPrice;
        TextView orderPaymentMethod;
        TextView paymentStatus;
        TextView orderService;
        TextView orderDateAndTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId=(TextView) itemView.findViewById(R.id.orderId);
            orderStatus=(TextView) itemView.findViewById(R.id.orderStatus);
            status = (View) itemView.findViewById(R.id.status);
            orderTotalPrice=(TextView) itemView.findViewById(R.id.orderTotalPrice);
            orderPaymentMethod=(TextView) itemView.findViewById(R.id.orderPaymentMethod);
            paymentStatus=(TextView) itemView.findViewById(R.id.paymentStatus);
            orderService = (TextView) itemView.findViewById(R.id.orderService);
            orderDateAndTime = (TextView) itemView.findViewById(R.id.orderDateAndTime);
        }

    }
}
