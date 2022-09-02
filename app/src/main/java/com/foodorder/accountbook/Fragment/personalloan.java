package com.foodorder.accountbook.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foodorder.accountbook.Adapter.CashActivityAdapters;
import com.foodorder.accountbook.Adapter.personalLoanAdapters;
import com.foodorder.accountbook.Database.CurrencyDb;
import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.Model.CashActivityModels;
import com.foodorder.accountbook.Model.personalLoanModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link personalloan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class personalloan extends Fragment {
    Button newloanBalanceBtns;
    RecyclerView claonActivityCycler;
    TextView totaldept, totalrepayment, remainloan;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public personalloan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment personalloan.
     */
    // TODO: Rename and change types and number of parameters
    public static personalloan newInstance(String param1, String param2) {
        personalloan fragment = new personalloan();
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
        View v = inflater.inflate(R.layout.fragment_personalloan, container, false);

        newloanBalanceBtns = v.findViewById(R.id.newLoanBalanceBtn);
        newloanBalanceBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                personalloanActivity NAME = new personalloanActivity();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();
            }
        });
        claonActivityCycler = v.findViewById(R.id.loanActivityRecycler);
        DbHelper db = new DbHelper(v.getContext());
        ArrayList<personalLoanModels> list = db.getLoanActivity();
        personalLoanAdapters adapter = new personalLoanAdapters(list, v.getContext());
        claonActivityCycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        claonActivityCycler.setLayoutManager(layoutManager);

        totaldept = v.findViewById(R.id.totaldept_tv);
        totalrepayment = v.findViewById(R.id.totalrepayment_tv);
        remainloan = v.findViewById(R.id.remainloan_tv);
        totalAction(v);
        onResume();
        return v;
    }
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Loan Activity List");
    }
    public void totalAction(View v) {
        CurrencyDb dbase = new CurrencyDb(v.getContext());
        try {
            dbase.createDataBase();
            dbase.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = dbase.getSelectedCurency();

        float remainloans = 0;
        DbHelper db = new DbHelper(v.getContext());
        float dept = db.totalDept();
        float repayment = db.totalpayment();
        remainloans = dept - repayment;


        String type = cursor.getString(cursor.getColumnIndex("use_code"));
        switch (type) {
            case "Code":
                totaldept.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." +Float.toString(dept));
                totalrepayment.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." +Float.toString(repayment));
                remainloan.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." +Float.toString(remainloans));
                break;
            case "Symbol":
                totaldept.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." +Float.toString(dept));
                totalrepayment.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." +Float.toString(repayment));
                remainloan.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." +Float.toString(remainloans));
                break;
            default:
                break;

        }

    }
}