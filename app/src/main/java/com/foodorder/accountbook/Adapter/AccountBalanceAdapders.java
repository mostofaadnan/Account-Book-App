package com.foodorder.accountbook.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.foodorder.accountbook.Database.CurrencyDb;
import com.foodorder.accountbook.Fragment.AccountDetails;
import com.foodorder.accountbook.Fragment.BankActivity;
import com.foodorder.accountbook.Model.AccountBalanceModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

public class AccountBalanceAdapders extends RecyclerView.Adapter<AccountBalanceAdapders.viewholder> {
    ArrayList<AccountBalanceModels> list;
    Context context;

    public AccountBalanceAdapders(ArrayList<AccountBalanceModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AccountBalanceAdapders.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.account_balance_simple, parent, false);
        return new AccountBalanceAdapders.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountBalanceAdapders.viewholder holder, int position) {
        CurrencyDb db = new CurrencyDb(context);
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getSelectedCurency();

        AccountBalanceModels models = list.get(position);
        holder.bankname.setText(models.getBank_name());
        holder.accountnumber.setText(models.getAccount_number());

        String type = cursor.getString(cursor.getColumnIndex("use_code"));
        switch (type) {
            case "Code":
                holder.credit.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." + Float.toString(models.getCredit()));
                holder.debit.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." + Float.toString(models.getDebit()));
                holder.balance.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." + Float.toString(models.getBalance()));
                break;
            case "Symbol":
                holder.credit.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." + Float.toString(models.getCredit()));
                holder.debit.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." + Float.toString(models.getDebit()));
                holder.balance.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." + Float.toString(models.getBalance()));
                break;
            default:
                break;

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                AccountDetails NAME = new AccountDetails();
                Bundle bundle = new Bundle();
                bundle.putString("account_number", models.getAccount_number());
                bundle.putString("bankname", models.getBank_name());
                bundle.putFloat("balance",models.getBalance());
                NAME.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.addToBackStack(null).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        TextView bankname, accountnumber, credit, debit, balance;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            bankname = itemView.findViewById(R.id.accountBalance_bankname_tv);
            accountnumber = itemView.findViewById(R.id.accountBalanace_accountNo_tv);
            credit = itemView.findViewById(R.id.accountBalance_credit_tv);
            debit = itemView.findViewById(R.id.accountBalance_debit_tv);
            balance = itemView.findViewById(R.id.accountbalance_balance_tv);

        }
    }
}
