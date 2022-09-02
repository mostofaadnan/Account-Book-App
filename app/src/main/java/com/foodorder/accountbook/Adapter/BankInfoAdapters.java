package com.foodorder.accountbook.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.Fragment.BankSetup;
import com.foodorder.accountbook.Model.BankInfoModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

public class BankInfoAdapters extends RecyclerView.Adapter<BankInfoAdapters.viewholder> {
    ArrayList<BankInfoModels> list;
    Context context;


    public BankInfoAdapters(ArrayList<BankInfoModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bankinfo_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        if (list != null && list.size() > 0) {
         BankInfoModels model=list.get(position);
         holder.bankname.setText(model.getName());
         holder.branchname.setText(model.getBranchname());
         holder.accountnumber.setText(model.getAccountno());
         holder.bankstatus.setText(model.getStatus());
         holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager=((AppCompatActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    BankSetup NAME = new BankSetup();
                    Bundle bundle=new Bundle();
                    bundle.putInt("insertType",1);
                    bundle.putString("bank_name",model.getName());
                    bundle.putString("branch_name",model.getBranchname());
                    bundle.putString("account_number",model.getAccountno());
                    bundle.putString("status",model.getStatus());
                    bundle.putInt("id",model.getId());
                    NAME.setArguments(bundle);
                    fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                    fragmentTransaction.addToBackStack(null).commit();

                }
            });
        }else{
            return;

        }

        holder.Deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Bank Information")
                        .setMessage("Are You Sure Want To Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BankInfoModels model=list.get(position);
                                DbHelper dBhelper=new DbHelper(context);
                                if(dBhelper.DeleteBankInfo(model.getId())>0){
                                    Toast.makeText(context, "Delete Successfuly", Toast.LENGTH_SHORT).show();
                                    list.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, list.size());
                                }else {
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
        TextView bankid, bankname,branchname,accountnumber, bankstatus;
        Button Deletebtn,copybtn;


        public viewholder(@NonNull View itemView) {
            super(itemView);
         //   bankid = itemView.findViewById(R.id.bankinfoid);
            bankname = itemView.findViewById(R.id.bankinfoname_v);
            branchname=itemView.findViewById(R.id.branchname_tv);
            accountnumber=itemView.findViewById(R.id.accountnumber_tv);
            bankstatus = itemView.findViewById(R.id.bankinfostatus_tv);
            Deletebtn=itemView.findViewById(R.id.bankinfoDltBtn);


        }
    }

}
