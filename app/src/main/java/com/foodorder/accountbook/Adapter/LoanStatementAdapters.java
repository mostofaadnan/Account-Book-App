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
import com.foodorder.accountbook.Model.personalLoanModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

public class LoanStatementAdapters extends RecyclerView.Adapter<LoanStatementAdapters.viewholder> {
    ArrayList<personalLoanModels> list;
    Context context;

    public LoanStatementAdapters(ArrayList<personalLoanModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LoanStatementAdapters.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.loanstatement_layout, parent, false);
        return new LoanStatementAdapters.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanStatementAdapters.viewholder holder, int position) {
        CurrencyDb db = new CurrencyDb(context);
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getSelectedCurency();

        personalLoanModels models = list.get(position);
        holder.inputdate.setText(models.getInputdate());
        holder.type.setText(models.getType());
        holder.perosonname.setText(models.getName());

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
        TextView type, amount, inputdate, perosonname;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.loanStatementType_tv);
            amount = itemView.findViewById(R.id.loanStatementAmount_tv);
            inputdate = itemView.findViewById(R.id.loanStatementDate_tv);
            perosonname = itemView.findViewById(R.id.loanStatementPerson_tv);
        }
    }
}
