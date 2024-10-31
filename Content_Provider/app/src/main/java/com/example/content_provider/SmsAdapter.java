package com.example.content_provider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsViewHolder> {
    private ArrayList<Sms> smsList;
    public SmsAdapter(ArrayList<Sms> smsList) {
        this.smsList = smsList;
    }
    @NonNull
    @Override
    public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_item, parent, false);
        return new SmsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SmsViewHolder holder, int position) {
        Sms sms = smsList.get(position);
        holder.smsAddress.setText(sms.getAddress());
        holder.smsBody.setText(sms.getBody());
        holder.smsTimestamp.setText(sms.getDate());
    }
    @Override
    public int getItemCount() {
        return smsList.size();
    }
    public static class SmsViewHolder extends RecyclerView.ViewHolder {
        TextView smsAddress;
        TextView smsBody;
        TextView smsTimestamp;
        public SmsViewHolder(@NonNull View itemView) {
            super(itemView);
            smsAddress = itemView.findViewById(R.id.smsAddress);
            smsBody = itemView.findViewById(R.id.smsBody);
            smsTimestamp = itemView.findViewById(R.id.smsTimestamp);
        }
    }
}
