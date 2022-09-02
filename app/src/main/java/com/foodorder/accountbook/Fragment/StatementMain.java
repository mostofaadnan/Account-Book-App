package com.foodorder.accountbook.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatementMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatementMain extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button accountactivity, cashactivity, loanstatement,expensesstatement;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatementMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatementMain.
     */
    // TODO: Rename and change types and number of parameters
    public static StatementMain newInstance(String param1, String param2) {
        StatementMain fragment = new StatementMain();
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
        View v = inflater.inflate(R.layout.fragment_statement_main, container, false);
        accountactivity = v.findViewById(R.id.accactivityst_btn);
        cashactivity = v.findViewById(R.id.cashstbtn);
        loanstatement = v.findViewById(R.id.loanBtn);
        expensesstatement=v.findViewById(R.id.expenseBtn);
        accountactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AccountStatement NAME = new AccountStatement();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();

            }
        });

        cashactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CashStatement NAME = new CashStatement();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();
            }
        });

        loanstatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LoanActivityStatement NAME = new LoanActivityStatement();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();
            }
        });

        expensesstatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ExpensesStatement NAME = new ExpensesStatement();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();
            }
        });
        onResume();
        return v;
    }
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Statement");
    }
}