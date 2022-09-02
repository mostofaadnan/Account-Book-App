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

import com.foodorder.accountbook.Adapter.AccountDetailsAdapters;
import com.foodorder.accountbook.Adapter.BankActivityAdapters;
import com.foodorder.accountbook.Database.CurrencyDb;
import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.Model.BankActivityModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountDetails extends Fragment {
    RecyclerView bankActivityCycler;
    TextView accountNumber, BankName, Balance;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountDetails newInstance(String param1, String param2) {
        AccountDetails fragment = new AccountDetails();
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
        View v = inflater.inflate(R.layout.fragment_account_details, container, false);

        accountNumber = v.findViewById(R.id.accountDetailaAccountNumber_tv);
        BankName = v.findViewById(R.id.accountDetailsBankName_tv);
        Balance = v.findViewById(R.id.accountDetailsBalance_tv);
        Bundle arguments = getArguments();
        if (arguments != null) {
            handleArguments(arguments, v);
        }
        onResume();

        return v;
    }
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Account Details");
    }
    private void handleArguments(Bundle arguments, View v) {
        CurrencyDb dbs = new CurrencyDb(v.getContext());
        try {
            dbs.createDataBase();
            dbs.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Cursor cursor = dbs.getSelectedCurency();
        String account_number = arguments.getString("account_number");
        accountNumber.setText(account_number);
        BankName.setText(arguments.getString("bankname"));
        String type = cursor.getString(cursor.getColumnIndex("use_code"));
        switch (type) {
            case "Code":
                Balance.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." + Float.toString(arguments.getFloat("balance")));
                break;
            case "Symbol":
                Balance.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." + Float.toString(arguments.getFloat("balance")));
                break;
            default:
                break;

        }
        bankActivityCycler = v.findViewById(R.id.accountDetailsRecycler);
        DbHelper db = new DbHelper(v.getContext());
        ArrayList<BankActivityModels> list = db.getAccountDetails(account_number);
        AccountDetailsAdapters adapter = new AccountDetailsAdapters(list, v.getContext());
        bankActivityCycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        bankActivityCycler.setLayoutManager(layoutManager);

    }
}