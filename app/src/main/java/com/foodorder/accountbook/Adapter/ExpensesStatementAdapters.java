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
import com.foodorder.accountbook.Model.ExpensesActivityModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

public class ExpensesStatementAdapters extends RecyclerView.Adapter<ExpensesStatementAdapters.viewholder> {
    ArrayList<ExpensesActivityModels> list;
    Context context;

    public ExpensesStatementAdapters(ArrayList<ExpensesActivityModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpensesStatementAdapters.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expensesstatement_layout, parent, false);
        return new ExpensesStatementAdapters.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesStatementAdapters.viewholder holder, int position) {
        CurrencyDb db = new CurrencyDb(context);
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getSelectedCurency();

        ExpensesActivityModels models = list.get(position);
        holder.expensestype.setText(models.getExpensesType());
        holder.inputdate.setText(models.getInputdate());
        holder.source.setText(models.getSource());
        String type = cursor.getString(cursor.getColumnIndex("use_code"));
        switch (type) {
            case "Code":
                holder.amount.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." + Float.toString(models.getAmount()));
                break;
            case "Symbol":
                holder.amount.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." + Float.toString(models.getAmount()));
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
        TextView expensestype, inputdate, amount, source;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            expensestype = itemView.findViewById(R.id.expensesStatementType_tv);
            inputdate = itemView.findViewById(R.id.expensesStatementDate_tv);
            amount = itemView.findViewById(R.id.expensesStatementAmount_tv);
            source = itemView.findViewById(R.id.expensesStatementSource_tv);
        }
    }
}
