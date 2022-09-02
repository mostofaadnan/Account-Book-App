package com.foodorder.accountbook.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.LinearLayout;
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
 * Use the {@link ExpensesActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpensesActivity extends Fragment {
    private TextView dateDisplay;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    Spinner typeSppiner, banklist, accountlis;
    Button insertBtn, clerBtn;
    EditText amount, description, expensestype;
    LinearLayout bankPanel_layouts;
    ArrayAdapter<CharSequence> sourcsadapater;
    ArrayAdapter<String> banklistadapater, accountlistadapater;
    int insertType, id;
    String PreviousSource;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExpensesActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpensesActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpensesActivity newInstance(String param1, String param2) {
        ExpensesActivity fragment = new ExpensesActivity();
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
        View v = inflater.inflate(R.layout.fragment_expenses_activity, container, false);
        typeSppiner = v.findViewById(R.id.sourceType_sp);
        dateDisplay = v.findViewById(R.id.expenses_date_et);
        accountlis = v.findViewById(R.id.expensesactivityaccount_esp);
        bankPanel_layouts = v.findViewById(R.id.bankPanel_layout);
        insertBtn = v.findViewById(R.id.expenseActivity_btn);
        clerBtn = v.findViewById(R.id.expensesactivityClearBtn);
        expensestype = v.findViewById(R.id.expenseActivity_ets);
        description = v.findViewById(R.id.expensesActivityDescription_et);
        amount = v.findViewById(R.id.expensesActivityamount_et);
        insertType = 0;
        CurrentDate();
        DatePick(v);
        paymentType(v);
        ActivityExecution(v);
        Bundle arguments = getArguments();
        if (arguments != null) {
            handleArguments(arguments, v);
        }
        clerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clear();
            }
        });
        onResume();
        return v;
    }
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("New Expenses Activity");
    }
    private void CurrentDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        Log.d(TAG, "onDateSet:mm//dd/yyyy:" + month + "/" + day + "/" + year);
        String date = month + "/" + day + "/" + year;
        dateDisplay.setText(date);
    }

    private void handleArguments(Bundle arguments, View v) {
        insertType=arguments.getInt("insertType");
        dateDisplay.setText(arguments.getString("inputdate"));
        expensestype.setText(arguments.getString("expensetype"));
        description.setText(arguments.getString("description"));
        amount.setText(Float.toString(arguments.getFloat("amount")));
        PreviousSource = arguments.getString("source");
        String type = arguments.getString("source");

        if (type != null) {
            int spinnerPosition = sourcsadapater.getPosition(type);
            typeSppiner.setSelection(spinnerPosition);
        }

        switch (type) {
            case "Cash":
                bankPanel_layouts.setVisibility(v.INVISIBLE);
                break;
            case "Bank":
                bankPanel_layouts.setVisibility(v.VISIBLE);
                String bankname = arguments.getString("bank");
                String accountName = arguments.getString("accountnum");
               

               if (bankname == "" || accountName == "" )
                {
                    bankPanel_layouts.setVisibility(v.VISIBLE);
                    BankList(v);
                    int spinnerPosition1 = banklistadapater.getPosition(bankname);
                    banklist.setSelection(spinnerPosition1);
                int spinnerPosition2 = accountlistadapater.getPosition(accountName);
                accountlis.setSelection(spinnerPosition2);
                }
                break;
            default:
                break;
        }
        insertType = arguments.getInt("insertType");
        id = arguments.getInt("id");
        insertBtn.setText("Update Data");
        insertBtn.setBackgroundColor(Color.GREEN);
    }

    public void ActivityExecution(View v) {
        insertBtn.setOnClickListener(new View.OnClickListener() {
            String bankname, accountnu;
            public void onClick(View v) {
                String source = (String) typeSppiner.getSelectedItem();
                boolean is_empty;
                if (expensestype.getText().toString().matches("") || amount.getText().toString().matches("")) {
                    is_empty = true;
                    Toast.makeText(v.getContext(), "Expenses Type And Amount Should be Require", Toast.LENGTH_SHORT).show();
                } else {
                    is_empty = false;
                }
                if (is_empty==false) {
                    switch (source) {
                        case "Cash":
                            bankname = "";
                            accountnu = "";
                            break;
                        case "Bank":
                            bankname = (String) banklist.getSelectedItem();
                            accountnu = (String) accountlis.getSelectedItem();
                    }
                    DbHelper db = new DbHelper(v.getContext());
                    switch (insertType) {
                        case 0:
                            boolean is_inserted = db.ExpenseActivityInsert(
                                    expensestype.getText().toString(),
                                    dateDisplay.getText().toString(),
                                    source,
                                    Float.parseFloat(String.valueOf(amount.getText())),
                                    bankname,
                                    accountnu,
                                    description.getText().toString()
                            );
                            if (is_inserted) {
                                Toast.makeText(v.getContext(), "Successfully Save", Toast.LENGTH_SHORT).show();
                                Clear();
                                ExpensesActivityFragment();
                            } else {
                                Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1:
                            boolean is_update = db.ExpenseActivityUpdate(
                                    expensestype.getText().toString(),
                                    dateDisplay.getText().toString(),
                                    source,
                                    Float.parseFloat(String.valueOf(amount.getText())),
                                    bankname,
                                    accountnu,
                                    description.getText().toString(),
                                    id,
                                    PreviousSource
                            );
                            if (is_update) {
                                Toast.makeText(v.getContext(), "Successfully Update Data", Toast.LENGTH_SHORT).show();
                                Clear();
                                ExpensesActivityFragment();
                            } else {
                                Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }

                            break;
                        default:
                            break;
                    }

                }
            }
        });
    }
    public void ExpensesActivityFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Expenses NAME = new Expenses();
        fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
        fragmentTransaction.commit();
    }
    public void Clear() {
        expensestype.setText(null);
        dateDisplay.setText(null);
        typeSppiner.setSelection(0);
        amount.setText(null);
        // banklist.setAdapter(null);
        //accountlis.setAdapter(null);
        insertType = 0;
        CurrentDate();
    }

    public void DatePick(View fragment) {
        dateDisplay.setOnClickListener(new View.OnClickListener() {

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
                dateDisplay.setText(date);

            }
        };

    }

    private void paymentType(View v) {

        sourcsadapater = ArrayAdapter.createFromResource(v.getContext(), R.array.source_array, android.R.layout.simple_spinner_item);
        sourcsadapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSppiner.setAdapter(sourcsadapater);
        typeSppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final String type = (String) parent.getItemAtPosition(position).toString();
                switch (type) {
                    case "Cash":
                        bankPanel_layouts.setVisibility(v.INVISIBLE);
                        //banklist.setAdapter(null);
                        //accountlis.setAdapter(null);
                        break;
                    case "Bank":
                        bankPanel_layouts.setVisibility(v.VISIBLE);
                        BankList(v);
                        banklist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                accountlis.setAdapter(null);
                                final String bankname = (String) parent.getItemAtPosition(position).toString();
                                DbHelper db = new DbHelper(v.getContext());
                                List<String> labels = db.AccountList(bankname);
                                accountlistadapater = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, labels);
                                accountlistadapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                accountlis.setAdapter(accountlistadapater);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                accountlis.setAdapter(null);
                            }
                        });
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void BankList(View v) {
        banklist = v.findViewById(R.id.expensesbanklist_sp);
        DbHelper db = new DbHelper(v.getContext());
        List<String> labels = db.BankList();
        banklistadapater = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, labels);
        banklistadapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        banklist.setAdapter(banklistadapater);
    }
}