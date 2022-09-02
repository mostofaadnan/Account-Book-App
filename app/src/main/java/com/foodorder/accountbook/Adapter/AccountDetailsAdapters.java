package com.foodorder.accountbook.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodorder.accountbook.Model.AccountBalanceModels;
import com.foodorder.accountbook.Model.BankActivityModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

public class AccountDetailsAdapters extends RecyclerView.Adapter<AccountDetailsAdapters.viewholder> {
    ArrayList<BankActivityModels> list;
    Context context;
    private String type;

    public AccountDetailsAdapters(ArrayList<BankActivityModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public AccountDetailsAdapters.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.acountbalance_details_layout, parent, false);
        return new AccountDetailsAdapters.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountDetailsAdapters.viewholder holder, int position) {
        BankActivityModels models = list.get(position);
        holder.inputdate.setText(models.getInput_date());
        String  type= models.getType();
        holder.type.setTextColor(type.equals("Credit")? Color.rgb(0,100,0) :Color.rgb(220,20,60));
        holder.type.setText(models.getType());
        holder.amount.setText(models.getAmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView inputdate, type, amount;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            inputdate = itemView.findViewById(R.id.accountdetailsInputdate_tv);
            type = itemView.findViewById(R.id.accountdetailsType_tv);
            amount = itemView.findViewById(R.id.accountdetailsAmount_tv);
        }
    }
}
