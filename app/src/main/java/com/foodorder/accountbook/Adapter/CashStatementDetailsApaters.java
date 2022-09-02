package com.foodorder.accountbook.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodorder.accountbook.Database.CurrencyDb;
import com.foodorder.accountbook.Model.CashActivityModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

public class CashStatementDetailsApaters extends RecyclerView.Adapter<CashStatementDetailsApaters.viewholder>  {

    ArrayList<CashActivityModels> list;
    Context context;

    public CashStatementDetailsApaters(ArrayList<CashActivityModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CashStatementDetailsApaters.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cashstatement_layout, parent, false);
        return new CashStatementDetailsApaters.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CashStatementDetailsApaters.viewholder holder, int position) {
        CurrencyDb db = new CurrencyDb(context);
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getSelectedCurency();
        CashActivityModels models = list.get(position);
        holder.inputdate.setText(models.getInpudate());
        holder.type.setText(models.getType());
        String type = cursor.getString(cursor.getColumnIndex("use_code"));
        switch (type) {
            case "Code":
                holder.amount.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." + Float.toString(models.getAmount()));
                holder.balance.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." + Float.toString(models.getBalance()));
                break;
            case "Symbol":
                holder.amount.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." + Float.toString(models.getAmount()));
                holder.balance.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." + Float.toString(models.getBalance()));
                break;
            default:
                break;

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView type, description, amount, balance, inputdate;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.cashStatementType_tv);
            amount = itemView.findViewById(R.id.cashStatementAmount_tv);
            inputdate = itemView.findViewById(R.id.cashStatementDate_tv);
            balance = itemView.findViewById(R.id.cashStatementBalance_tv);
        }
    }
}
