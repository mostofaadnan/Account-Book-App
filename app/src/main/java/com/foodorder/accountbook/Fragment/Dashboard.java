package com.foodorder.accountbook.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foodorder.accountbook.Database.CurrencyDb;
import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private AdView mAdView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button bankinfonavbtn, bankactivitynavbtn, accountinnavbtn, cashactivitynavbtn, personalloannavbtn, expensesactivitynavbtn;
    TextView totalaccountBalance, totalcashbalance, totalexpenses, totalloan;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
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
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        MobileAds.initialize(v.getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        bankinfonavbtn = v.findViewById(R.id.bankInfonavbtn);
        bankactivitynavbtn = v.findViewById(R.id.bankactivitynavbtn);
        accountinnavbtn = v.findViewById(R.id.accountbalancenavbtn);
        cashactivitynavbtn = v.findViewById(R.id.cashactivitynavbtn);
        personalloannavbtn = v.findViewById(R.id.loanactivitynavbtn);
        expensesactivitynavbtn = v.findViewById(R.id.expensesactivitynavbtn);

        totalaccountBalance = v.findViewById(R.id.deshboardAccountBalance_tv);
        totalcashbalance = v.findViewById(R.id.deshboardCashBalance_tv);
        totalexpenses = v.findViewById(R.id.deshboardExpensesBalance_tv);
        totalloan = v.findViewById(R.id.deshboardLoanBalance_tv);

        bankinfonavbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                BankInfo bankInfo = new BankInfo();
                fragmentTransaction.replace(R.id.nav_host_fragment, bankInfo);
                fragmentTransaction.commit();
            }
        });

        bankactivitynavbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                BankActivityList bankInfo = new BankActivityList();
                fragmentTransaction.replace(R.id.nav_host_fragment, bankInfo);
                fragmentTransaction.commit();
            }
        });

        accountinnavbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AccountInfo accountInfo = new AccountInfo();
                fragmentTransaction.replace(R.id.nav_host_fragment, accountInfo);
                fragmentTransaction.commit();
            }
        });

        cashactivitynavbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Cash cashActivity = new Cash();
                fragmentTransaction.replace(R.id.nav_host_fragment, cashActivity);
                fragmentTransaction.commit();
            }
        });

        personalloannavbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                personalloan personalloan = new personalloan();
                fragmentTransaction.replace(R.id.nav_host_fragment, personalloan);
                fragmentTransaction.commit();
            }
        });

        expensesactivitynavbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Expenses expenses = new Expenses();
                fragmentTransaction.replace(R.id.nav_host_fragment, expenses);
                fragmentTransaction.commit();
            }
        });
        TotalAction(v);
        return v;
    }
    public void TotalAction(View v) {
        CurrencyDb dbase = new CurrencyDb(v.getContext());
        try {
            dbase.createDataBase();
            dbase.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = dbase.getSelectedCurency();
        //Account Balance
        float balanceamt = 0;
        DbHelper db = new DbHelper(v.getContext());
       //Account Balance
        float credit = db.bankcredit();
        float debit = db.bankdebit();
        balanceamt = credit - debit;
       //Cash Balance
        float Cashbalance = db.CashBalance();
        //Total Expense
        float totalExpense = db.TotalExpense();
        //Total Loan
        float remainloans = 0;
        float dept = db.totalDept();
        float repayment = db.totalpayment();
        remainloans = dept - repayment;

        String type = cursor.getString(cursor.getColumnIndex("use_code"));
        switch (type) {
            case "Code":
                totalaccountBalance.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." + Float.toString(balanceamt));
                totalcashbalance.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." + Float.toString(Cashbalance));
                totalexpenses.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." + Float.toString(totalExpense));
                totalloan.setText(cursor.getString(cursor.getColumnIndex("currency_code")) + "." +Float.toString(remainloans));
                break;
            case "Symbol":
                totalaccountBalance.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." + Float.toString(balanceamt));
                totalcashbalance.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." + Float.toString(Cashbalance));
                totalexpenses.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." + Float.toString(totalExpense));
                totalloan.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")) + "." +Float.toString(remainloans));
                break;
            default:
                break;

        }

    }

}