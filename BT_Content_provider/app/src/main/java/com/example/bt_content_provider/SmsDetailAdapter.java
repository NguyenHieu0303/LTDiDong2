package com.example.bt_content_provider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SmsDetailAdapter extends RecyclerView.Adapter<SmsDetailAdapter.SmsDetailViewHolder> {
    private final List<Sms> messages;

    public SmsDetailAdapter(List<Sms> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public SmsDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_detail_item, parent, false);
        return new SmsDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsDetailViewHolder holder, int position) {
        Sms sms = messages.get(position);
        holder.bind(sms);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class SmsDetailViewHolder extends RecyclerView.ViewHolder {
        TextView bodyTextView;

        public SmsDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            bodyTextView = itemView.findViewById(R.id.smsBody);
        }

        public void bind(Sms sms) {
            bodyTextView.setText(sms.getBody());
        }
    }
}
