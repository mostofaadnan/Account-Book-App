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
import com.foodorder.accountbook.Fragment.BankActivity;
import com.foodorder.accountbook.Fragment.Expenses;
import com.foodorder.accountbook.Fragment.personalloan;
import com.foodorder.accountbook.Fragment.personalloanActivity;
import com.foodorder.accountbook.Model.CashActivityModels;
import com.foodorder.accountbook.Model.ExpensesActivityModels;
import com.foodorder.accountbook.Model.personalLoanModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

public class personalLoanAdapters extends RecyclerView.Adapter<personalLoanAdapters.viewholder> {
    ArrayList<personalLoanModels> list;
    Context context;

    public personalLoanAdapters(ArrayList<personalLoanModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public personalLoanAdapters.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.loan_layout_simple, parent, false);
        return new personalLoanAdapters.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull personalLoanAdapters.viewholder holder, int position) {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                personalloanActivity NAME = new personalloanActivity();
                Bundle bundle = new Bundle();
                bundle.putInt("insertType", 1);
                bundle.putString("inputdate", models.getInputdate());
                bundle.putString("type", models.getType());
                bundle.putString("person", models.getName());
                bundle.putInt("id", models.getId());
                bundle.putFloat("amount", models.getAmount());
                bundle.putString("description", models.getDescription());
                NAME.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.addToBackStack(null).commit();

            }
        });
        holder.DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Personal Loan Activity")
                        .setMessage("Are You Sure Want To Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                personalLoanModels model = list.get(position);
                                DbHelper dBhelper = new DbHelper(context);
                                if (dBhelper.DeleteLoanActivity(model.getId()) > 0) {

                                    Toast.makeText(context, "Delete Successfuly", Toast.LENGTH_SHORT).show();
                                    list.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, list.size());
                                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                                    personalloan NAME = new personalloan();
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
        TextView type, description, amount, inputdate, perosonname;
        Button DeleteBtn;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.loanactivity_type_tv);
            amount = itemView.findViewById(R.id.loanamount_tv);
            inputdate = itemView.findViewById(R.id.loanactivity_date_tvs);
            perosonname = itemView.findViewById(R.id.laonpersonName_tv);
            DeleteBtn = itemView.findViewById(R.id.personalLoanDeleteBtn);

        }
    }
}
