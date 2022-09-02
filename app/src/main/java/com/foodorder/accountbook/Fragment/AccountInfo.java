package com.foodorder.accountbook.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodorder.accountbook.Adapter.AccountBalanceAdapders;
import com.foodorder.accountbook.Database.CurrencyDb;
import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.Model.AccountBalanceModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountInfo extends Fragment {
    RecyclerView accountRecycler;
    AccountBalanceAdapders adapters;
    TextView totalcredit,totaldebit,balance;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountInfo newInstance(String param1, String param2) {
        AccountInfo fragment = new AccountInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v= inflater.inflate(R.layout.fragment_account_info, container, false);

        accountRecycler=v.findViewById(R.id.accountInfo_recycler);
        DbHelper db = new DbHelper(v.getContext());
        ArrayList<AccountBalanceModels> list=db.AccountBalance();
        AccountBalanceAdapders adapter=new AccountBalanceAdapders(list,v.getContext());
        accountRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(v.getContext());
        accountRecycler.setLayoutManager(layoutManager);
        totalcredit=v.findViewById(R.id.totalcredito_tv);
        totaldebit=v.findViewById(R.id.totaldebit_tv);
        balance=v.findViewById(R.id.totalbalance_tv);
        presentBalance(v);
        onResume();
        return  v;
    }
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Account Information");
    }
    public  void  presentBalance(View v){
        CurrencyDb dbase = new CurrencyDb(v.getContext());
        try {
            dbase.createDataBase();
            dbase.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = dbase.getSelectedCurency();

        float balanceamt=0;
        DbHelper db = new DbHelper(v.getContext());
        float credit=db.bankcredit();
        float debit=db.bankdebit();
        balanceamt=credit-debit;

        String type = cursor.getString(cursor.getColumnIndex("use_code"));
        switch (type) {
            case "Code":
                totalcredit.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." +Float.toString(credit));
                totaldebit.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." +Float.toString(debit));
                balance.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." +Float.toString(balanceamt));
                break;
            case "Symbol":
                totalcredit.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." +Float.toString(credit));
                totaldebit.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." +Float.toString(debit));
                balance.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." +Float.toString(balanceamt));
                break;
            default:
                break;

        }



    }
}