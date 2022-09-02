package com.foodorder.accountbook.Adapter;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.foodorder.accountbook.Database.CurrencyDb;
import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.Fragment.BankActivity;
import com.foodorder.accountbook.Fragment.BankActivityList;
import com.foodorder.accountbook.Model.BankActivityModels;
import com.foodorder.accountbook.Model.BankInfoModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

public class BankActivityAdapters extends RecyclerView.Adapter<BankActivityAdapters.viewholder> {
    ArrayList<BankActivityModels> list;
    Context context;

    public BankActivityAdapters(ArrayList<BankActivityModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bank_activity_simple, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        CurrencyDb db = new CurrencyDb(context);
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getSelectedCurency();
        BankActivityModels models = list.get(position);
        holder.bank_name.setText(models.getBank_name());
        holder.acc_no.setText(models.getAccount_number());
        holder.inputdate.setText(models.getInput_date());
        holder.paymenttype.setTextColor(models.getType().equals("Credit")? Color.rgb(0,100,0) :Color.rgb(220,20,60));
        holder.paymenttype.setText(models.getType());
        holder.amount.setText(models.getAmount());
        String type = cursor.getString(cursor.getColumnIndex("use_code"));
        switch (type) {
            case "Code":
                holder.curency.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + ".");
                break;
            case "Symbol":
                holder.curency.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + ".");
                break;
            default:
                break;

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ((LinearLayout) v.findViewById(R.id.)).removeAllViews();

                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                BankActivity NAME = new BankActivity();
                Bundle bundle = new Bundle();
                bundle.putInt("insertType", 1);
                bundle.putString("bank_name", models.getBank_name());
                bundle.putString("account_number", models.getAccount_number());
                bundle.putString("inputdate", models.getInput_date());
                bundle.putString("paymenttype", models.getType());
                bundle.putInt("id", models.getId());
                bundle.putString("amount", models.getAmount());
                bundle.putString("description", models.getDescription());
                NAME.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.addToBackStack(null).commit();

            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Bank Activity")
                        .setMessage("Are You Sure Want To Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BankActivityModels model = list.get(position);
                                DbHelper dBhelper = new DbHelper(context);
                                if (dBhelper.DeleteBankActivity(model.getId()) > 0) {
                                    if (model.getPayment_id() > 0) {
                                        dBhelper.DeleteExpensesActivity(model.getPayment_id());
                                    }

                                    Toast.makeText(context, "Delete Successfuly", Toast.LENGTH_SHORT).show();
                                    list.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, list.size());

                                } else {
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        TextView bank_name, acc_no, inputdate, paymenttype, amount, curency;
        Button deleteBtn;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            bank_name = itemView.findViewById(R.id.activityBank_tvs);
            acc_no = itemView.findViewById(R.id.activityAccount_tv);
            inputdate = itemView.findViewById(R.id.loanactivity_date_tvs);
            paymenttype = itemView.findViewById(R.id.loanactivity_type_tv);
            amount = itemView.findViewById(R.id.loanamount_tv);
            curency = itemView.findViewById(R.id.currencyCode_tv);
            deleteBtn = itemView.findViewById(R.id.bankActivityDeleteBtn);
        }
    }
}
