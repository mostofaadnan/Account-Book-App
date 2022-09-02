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
import com.foodorder.accountbook.Fragment.Cash;
import com.foodorder.accountbook.Fragment.CashActivity;
import com.foodorder.accountbook.Fragment.Expenses;
import com.foodorder.accountbook.Fragment.ExpensesActivity;
import com.foodorder.accountbook.Model.BankActivityModels;
import com.foodorder.accountbook.Model.ExpensesActivityModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

public class ExpensesActivityAdapters extends RecyclerView.Adapter<ExpensesActivityAdapters.viewholder> {

    ArrayList<ExpensesActivityModels> list;
    Context context;

    public ExpensesActivityAdapters(ArrayList<ExpensesActivityModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public ExpensesActivityAdapters.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expense_layout_simple, parent, false);
        return new ExpensesActivityAdapters.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesActivityAdapters.viewholder holder, int position) {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                ExpensesActivity NAME = new ExpensesActivity();
                Bundle bundle = new Bundle();
                bundle.putInt("insertType", 1);
                bundle.putString("inputdate", models.getInputdate());
                bundle.putString("expensetype", models.getExpensesType());
                bundle.putString("description", models.getDescription());
                bundle.putFloat("amount", models.getAmount());
                bundle.putString("source", models.getSource());
                bundle.putString("bank", models.getBank());
                bundle.putString("accountnum", models.getAccountNo());
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
                        .setTitle("Delete Expenses Activity")
                        .setMessage("Are You Sure Want To Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ExpensesActivityModels model = list.get(position);
                                DbHelper dBhelper = new DbHelper(context);
                                if (dBhelper.DeleteExpensesActivity(model.getId()) > 0) {
                                    String Source = model.getSource();
                                    switch (Source) {
                                        case "Cash":
                                            dBhelper.DeleteCashActivityByPayment(model.getId());
                                            break;
                                        case "Bank":
                                            dBhelper.DeleteBankActivityByPayment(model.getId());
                                            break;
                                        default:
                                            break;
                                    }
                                    Toast.makeText(context, "Delete Successfuly", Toast.LENGTH_SHORT).show();
                                    list.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, list.size());
                                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                                    Expenses NAME = new Expenses();
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
        TextView expensestype, inputdate, amount, source;
        Button DeleteBtn;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            expensestype = itemView.findViewById(R.id.expensesAtivityType_tv);
            inputdate = itemView.findViewById(R.id.expensesAtivityDate_tv);
            amount = itemView.findViewById(R.id.expensesAtivityAmount_tv);
            source = itemView.findViewById(R.id.expensesAtivitySource_tv);
            DeleteBtn = itemView.findViewById(R.id.expensesActivityDeleteBtn);

        }
    }
}
