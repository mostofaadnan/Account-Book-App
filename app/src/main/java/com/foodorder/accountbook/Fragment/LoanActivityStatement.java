package com.foodorder.accountbook.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.R;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoanActivityStatement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoanActivityStatement extends Fragment {
    private TextView fromDate, ToDate;
    Button StatementBtn;
    private DatePickerDialog.OnDateSetListener onDateSetListener, onDateSetListener1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoanActivityStatement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoanActivityStatement.
     */
    // TODO: Rename and change types and number of parameters
    public static LoanActivityStatement newInstance(String param1, String param2) {
        LoanActivityStatement fragment = new LoanActivityStatement();
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
        View v = inflater.inflate(R.layout.fragment_loan_activity_statement, container, false);
        fromDate = v.findViewById(R.id.loanStatementFromDate_et);
        ToDate = v.findViewById(R.id.loanStatementToDate_et);
        StatementBtn = v.findViewById(R.id.loanStatementBtn);
        FromDate(v);
        ToDate(v);
        StatementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is_empty;
                if (fromDate.getText().toString().matches("") || ToDate.getText().toString().matches("")) {
                    is_empty = true;
                    Toast.makeText(v.getContext(), "From Date and To Date Require", Toast.LENGTH_SHORT).show();
                } else {
                    is_empty = false;
                }
                if (is_empty == false) {
                    LoanStatementDetails NAME = new LoanStatementDetails();
                    Bundle bundle = new Bundle();
                    bundle.putString("fromdate", fromDate.getText().toString());
                    bundle.putString("todate", ToDate.getText().toString());
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    NAME.setArguments(bundle);
                    fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                    fragmentTransaction.commit();
                }

            }
        });
        onResume();
        return v;
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Loan Activity Statement");
    }

    public void FromDate(View fragment) {
        fromDate.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(fragment.getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        onDateSetListener,
                        year,
                        month,
                        day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet:mm//dd/yyyy:" + month + "/" + dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" + year;
                fromDate.setText(date);

            }
        };

    }

    public void ToDate(View fragment) {
        ToDate.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(fragment.getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        onDateSetListener1,
                        year,
                        month,
                        day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                datePickerDialog.show();
            }
        });

        onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet:mm//dd/yyyy:" + month + "/" + dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" + year;
                ToDate.setText(date);
            }
        };

    }
}