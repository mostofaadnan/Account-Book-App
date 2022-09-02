package com.foodorder.accountbook.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodorder.accountbook.Adapter.ExpensesActivityAdapters;
import com.foodorder.accountbook.Adapter.ExpensesStatementAdapters;
import com.foodorder.accountbook.Adapter.LoanStatementAdapters;
import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.Model.ExpensesActivityModels;
import com.foodorder.accountbook.Model.personalLoanModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpensesStatementDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpensesStatementDetails extends Fragment {
    TextView fromdate, todate;
    RecyclerView expensesStatementreCycler;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExpensesStatementDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpensesStatementDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpensesStatementDetails newInstance(String param1, String param2) {
        ExpensesStatementDetails fragment = new ExpensesStatementDetails();
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
        View v= inflater.inflate(R.layout.fragment_expenses_statement_details, container, false);
        fromdate = v.findViewById(R.id.expensesStatementFromdate_tv);
        todate = v.findViewById(R.id.expensesStatementTodate_tv);
        expensesStatementreCycler = v.findViewById(R.id.expensesStatementRecycler);
        Bundle arguments = getArguments();
        if (arguments != null) {
            handleArguments(arguments, v);
        }
        onResume();
        return  v;
    }
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Expenses Statement");
    }
    private void handleArguments(Bundle arguments, View v) {
        String fromdates = arguments.getString("fromdate");
        String todates = arguments.getString("todate");
        fromdate.setText(fromdates);
        todate.setText(todates);
        DbHelper db = new DbHelper(v.getContext());
        ArrayList<ExpensesActivityModels> list=db.ExpensesStatement(fromdates,fromdates);
        ExpensesStatementAdapters adapter=new ExpensesStatementAdapters(list,v.getContext());
        expensesStatementreCycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        expensesStatementreCycler.setLayoutManager(layoutManager);
    }
}