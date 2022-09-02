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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.R;

import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountStatement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountStatement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView fromDate, ToDate;
    Button StatementBtn;
    private DatePickerDialog.OnDateSetListener onDateSetListener, onDateSetListener1;
    Spinner banklist, accountlis;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountStatement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountStatement.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountStatement newInstance(String param1, String param2) {
        AccountStatement fragment = new AccountStatement();
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
        View v = inflater.inflate(R.layout.fragment_account_statement, container, false);
        fromDate = v.findViewById(R.id.accdeFromdate_et);
        ToDate = v.findViewById(R.id.accdeTodate_et);
        banklist = v.findViewById(R.id.accdebankname_sp);
        accountlis = v.findViewById(R.id.accdeStatementAccNo_sp);
        StatementBtn = v.findViewById(R.id.accdestBtns);
        FromDate(v);
        ToDate(v);
        BankList(v);
        banklist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                accountlis.setAdapter(null);
                final String bankname = (String) parent.getItemAtPosition(position).toString();
                DbHelper db = new DbHelper(v.getContext());
                List<String> labels = db.AccountList(bankname);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, labels);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                accountlis.setAdapter(dataAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                accountlis.setAdapter(null);
            }
        });

        StatementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is_empty;
                if(fromDate.getText().toString().matches("") || ToDate.getText().toString().matches("")){
                    is_empty=true;
                    Toast.makeText(v.getContext(), "From Date and To Date Require", Toast.LENGTH_SHORT).show();
                }else {
                    is_empty=false;
                }
                if(is_empty==false){
                    AccountDetailsStatement NAME = new AccountDetailsStatement();
                    Bundle bundle = new Bundle();
                    bundle.putString("fromdate", fromDate.getText().toString());
                    bundle.putString("todate", ToDate.getText().toString());
                    bundle.putString("bankname", (String) banklist.getSelectedItem());
                    bundle.putString("accountnumber", (String) accountlis.getSelectedItem());
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
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Bank Activity Statement");
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

    private void BankList(View v) {
        DbHelper db = new DbHelper(v.getContext());
        List<String> labels = db.BankList();
        ArrayAdapter<String> bankadapter;
        bankadapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, labels);
        bankadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        banklist.setAdapter(bankadapter);
    }

}