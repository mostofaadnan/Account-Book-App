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
import com.foodorder.accountbook.Adapter.ExpensesActivityAdapters;
import com.foodorder.accountbook.Database.CurrencyDb;
import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.Model.CashActivityModels;
import com.foodorder.accountbook.Model.ExpensesActivityModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Expenses#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Expenses extends Fragment {
     Button expenseNewBtns;
    RecyclerView expensesActivityCycler;
    TextView totalExpenses;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Expenses() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Expenses.
     */
    // TODO: Rename and change types and number of parameters
    public static Expenses newInstance(String param1, String param2) {
        Expenses fragment = new Expenses();
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

       View v= inflater.inflate(R.layout.fragment_expenses, container, false);
        expenseNewBtns=v.findViewById(R.id.expenseNewBtn);
        expenseNewBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ExpensesActivity NAME = new ExpensesActivity();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();
            }
        });
        expensesActivityCycler=v.findViewById(R.id.expensesActivityCycler_re);
        DbHelper db = new DbHelper(v.getContext());
        ArrayList<ExpensesActivityModels> list=db.getExpensesActivity();
        ExpensesActivityAdapters adapter=new ExpensesActivityAdapters(list,v.getContext());
        expensesActivityCycler.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(v.getContext());
        expensesActivityCycler.setLayoutManager(layoutManager);

        totalExpenses=v.findViewById(R.id.totalexpense_tv);
        TotalExpense(v);
        onResume();
          return v;
    }
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Expenses List");
    }
    public void TotalExpense(View v){
        CurrencyDb dbase = new CurrencyDb(v.getContext());
        try {
            dbase.createDataBase();
            dbase.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = dbase.getSelectedCurency();

        DbHelper db = new DbHelper(v.getContext());
        float totalExpense=db.TotalExpense();
        String type = cursor.getString(cursor.getColumnIndex("use_code"));
        switch (type) {
            case "Code":
                totalExpenses.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." + Float.toString(totalExpense));
                break;
            case "Symbol":
                totalExpenses.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." + Float.toString(totalExpense));
                break;
            default:
                break;

        }
    }

}