package com.hamza.printingpress.Adapders;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.printingpress.Api.URLs;
import com.hamza.printingpress.Api.VolleyApiRequest;
import com.hamza.printingpress.Models.ShopPrintRequest;
import com.hamza.printingpress.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    Context mContext;
    LayoutInflater inflater;
    AlertDialog.Builder builder;
    int done_request_id;
    HashMap<Integer, ViewHolder> myHolders;
    private List<ShopPrintRequest> requestList = null;

    public RequestAdapter(Context mContext, List<ShopPrintRequest> requestList) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.requestList = requestList;
        builder = new AlertDialog.Builder(mContext);
        done_request_id = 0;
        myHolders = new HashMap<>();

        builder.setMessage("Are you sure to make this print done?").setPositiveButton("Done", this::dialogYesClick).setNegativeButton("Cancel", this::dialogYesClick);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.shop_request_single_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopPrintRequest request = requestList.get(position);

            myHolders.put(request.getId(), holder);
            holder.orderId.setText("Order# " + request.getId());
            holder.orderTotalPrice.setText(request.getCost() + " PKR");
            holder.orderDateAndTime.setText(String.valueOf(request.getQuantity()));
            holder.paymentStatus.setText(request.getProduct_size());
            holder.orderService.setText(request.getPhone_number());
            if (request.isStatus()) {
                holder.orderStatus.setText("Completed");
                holder.status.setBackgroundResource(R.drawable.mystatus_green);
            } else {
                holder.orderStatus.setVisibility(View.GONE);
                holder.doneBtn.setVisibility(View.VISIBLE);
                holder.status.setBackgroundResource(R.drawable.mystatus_bg);

                holder.doneBtn.setOnClickListener(v -> {
                    done_request_id = request.getId();
                    builder.show();
                });

            }
            holder.orderPaymentMethod.setText(request.getPayment_method());

            holder.downloadDesign.setOnClickListener(v -> {
                if(!request.getDesign().isEmpty()) {
                    String url = URLs.imageUrl + request.getDesign();
                    DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(url));
                    downloadRequest.setDescription("Print request design file");
                    downloadRequest.setTitle(request.getDesign());
                    downloadRequest.allowScanningByMediaScanner();
                    downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    downloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, request.getDesign());

                    DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(downloadRequest);

                    Toast.makeText(mContext, "Download started. Check notification bar", Toast.LENGTH_SHORT).show();
                }
            });

    }

    private void dialogYesClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            try {
                if (done_request_id > 0) {
                    JSONObject data = new JSONObject();
                    data.put("request_id", done_request_id);
                    //Yes button clicked
                    VolleyApiRequest.authObjectPost(mContext, URLs.shopRequestDone, data, e -> null, response -> {
                        try {
                            if (response.getBoolean("success")) {
                                removeItem(done_request_id);
                            }
                            Toast.makeText(mContext, response.getString("message"), Toast.LENGTH_SHORT).show();
                        }catch(Exception ignore) {}
                        return null;
                    });
                }
            } catch (Exception ignore) {
            }
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    private void removeItem(int request_id) {
        ShopPrintRequest request;
        int pos = 0;
        for (int i = 0; i < requestList.size(); i++) {
            if(requestList.get(i).getId() == request_id) {
                request = requestList.get(i);
                pos = i;
            }
        }
        requestList.remove(pos);
        notifyItemRemoved(pos);
        //notifyItemRangeChanged(pos, requestList.size());
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId;
        TextView orderStatus;
        View status;
        TextView orderTotalPrice;
        TextView orderPaymentMethod;
        TextView paymentStatus;
        TextView orderService;
        TextView orderDateAndTime;
        Button doneBtn;
        Button downloadDesign;

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
            doneBtn = (Button) itemView.findViewById(R.id.btnDone);
            downloadDesign = (Button) itemView.findViewById(R.id.download_btn);
        }

    }
}
