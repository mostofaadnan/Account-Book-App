package com.foodorder.accountbook.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.foodorder.accountbook.Database.CurrencyDb;
import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.Fragment.BankSetup;
import com.foodorder.accountbook.Fragment.Cash;
import com.foodorder.accountbook.Fragment.CashActivity;
import com.foodorder.accountbook.Model.BankActivityModels;
import com.foodorder.accountbook.Model.CashActivityModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CashActivityAdapters extends RecyclerView.Adapter<CashActivityAdapters.viewholder> {

    ArrayList<CashActivityModels> list;
    Context context;

    public CashActivityAdapters(ArrayList<CashActivityModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public CashActivityAdapters.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cashlayout_simple, parent, false);
        return new CashActivityAdapters.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CashActivityAdapters.viewholder holder, int position) {
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                CashActivity NAME = new CashActivity();
                Bundle bundle = new Bundle();
                bundle.putInt("insertType", 1);
                bundle.putString("inputdate", models.getInpudate());
                bundle.putString("type", models.getType());
                bundle.putFloat("amount", models.getAmount());
                bundle.putString("description", models.getDescription());
                bundle.putInt("id", models.getId());
                NAME.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });
        holder.DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Cash Activity")
                        .setMessage("Are You Sure Want To Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CashActivityModels model = list.get(position);
                                DbHelper dBhelper = new DbHelper(context);
                                if (dBhelper.DeleteCashActivity(model.getId()) > 0) {
                                    if (model.getPaymentId() > 0) {
                                        dBhelper.DeleteExpensesActivity(model.getPaymentId());
                                    }

                                    Toast.makeText(context, "Delete Successfuly", Toast.LENGTH_SHORT).show();
                                    list.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, list.size());
                                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                                    Cash NAME = new Cash();
                                    fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                                    fragmentTransaction.addToBackStack(null).commit();

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

    public class viewholder extends RecyclerView.ViewHolder {
        TextView type, description, amount, balance, inputdate;
        Button DeleteBtn;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.cashactivity_type_tv);
            amount = itemView.findViewById(R.id.cashamount_tv);
            inputdate = itemView.findViewById(R.id.cashactivity_date_tvs);
            balance = itemView.findViewById(R.id.cashpersonName_tv);
            DeleteBtn = itemView.findViewById(R.id.cashActivityDeleteBtn);
        }
    }
}
