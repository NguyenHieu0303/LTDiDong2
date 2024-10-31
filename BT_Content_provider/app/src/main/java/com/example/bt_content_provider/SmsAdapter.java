package com.example.bt_content_provider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsViewHolder> {
    private final List<SmsAccount> smsAccounts;
    private final OnSmsAccountClickListener listener;

    public interface OnSmsAccountClickListener {
        void onSmsAccountClick(SmsAccount smsAccount);
    }

    public SmsAdapter(List<SmsAccount> smsAccounts, OnSmsAccountClickListener listener) {
        this.smsAccounts = smsAccounts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_detail_item, parent, false);
        return new SmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsViewHolder holder, int position) {
        SmsAccount smsAccount = smsAccounts.get(position);
        holder.bind(smsAccount, listener);
    }

    @Override
    public int getItemCount() {
        return smsAccounts.size();
    }

    public static class SmsViewHolder extends RecyclerView.ViewHolder {
        TextView addressTextView;
        TextView bodyTextView;

        public SmsViewHolder(@NonNull View itemView) {
            super(itemView);
            addressTextView = itemView.findViewById(R.id.smsAddress);
            bodyTextView = itemView.findViewById(R.id.smsBody);
        }

        public void bind(SmsAccount smsAccount, OnSmsAccountClickListener listener) {
            Sms latestSms = smsAccount.getLatestMessage();
            addressTextView.setText(smsAccount.getAddress());
            bodyTextView.setText(latestSms != null ? latestSms.getBody() : "");

            itemView.setOnClickListener(v -> listener.onSmsAccountClick(smsAccount));
        }
    }
}
