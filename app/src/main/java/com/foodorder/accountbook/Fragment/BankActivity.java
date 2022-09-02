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
import android.widget.EditText;
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
 * Use the {@link BankActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BankActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    int insertType, id;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView dateDisplay;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    Spinner typeSppiner, banklist, accountlis;
    ArrayAdapter<CharSequence> typeadapter;
    ArrayAdapter<String> bankadapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button insertBtn, cleraBtn;
    TextView inputdate;
    EditText amount, description;

    public BankActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BankActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static BankActivity newInstance(String param1, String param2) {
        BankActivity fragment = new BankActivity();
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
        View v = inflater.inflate(R.layout.fragment_bank_activity, container, false);
        paymentType(v);
        dateDisplay = v.findViewById(R.id.Date_etv);
        banklist = v.findViewById(R.id.banklist_sp);
        accountlis = v.findViewById(R.id.activityaccount_esp);
        typeSppiner = v.findViewById(R.id.loanType_sp);
        insertBtn = v.findViewById(R.id.activity_btn);
        cleraBtn = v.findViewById(R.id.bankactivityClearBtn);
        inputdate = v.findViewById(R.id.Date_etv);
        amount = v.findViewById(R.id.bankAmount_et);
        description = v.findViewById(R.id.bankActivityDescription_et);
        insertType = 0;
        CurrentDate();

        DatePick(v);
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

        cleraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clear();
            }
        });
        //Insert data
        ActivityExecution(v);
        Bundle arguments = getArguments();
        if (arguments != null) {
            handleArguments(arguments);
        }
        onResume();
        return v;
    }

    private void handleExtras(Bundle extras) {
    }

    private void handleSavedInstanceState(Bundle savedInstanceState) {
    }
  private  void CurrentDate(){
      Calendar cal = Calendar.getInstance();
      int year = cal.get(Calendar.YEAR);
      int month = cal.get(Calendar.MONTH);
      int day = cal.get(Calendar.DAY_OF_MONTH);
      month = month + 1;
      Log.d(TAG, "onDateSet:mm//dd/yyyy:" + month + "/" + day + "/" + year);
      String date = month + "/" + day + "/" + year;
      dateDisplay.setText(date);
  }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Bank Activity");
    }
    private void handleArguments(Bundle bundle) {
        insertType = bundle.getInt("insertType");
        String bankename = bundle.getString("bank_name");
        // String accountnumber=bundle.getString("account_number");
        String paymenttype = bundle.getString("paymenttype");
        if (bankename != null) {
            int spinnerPosition = bankadapter.getPosition(bankename);
            banklist.setSelection(spinnerPosition);
        }
        if (paymenttype != null) {
            int spinnerPosition = typeadapter.getPosition(paymenttype);
            typeSppiner.setSelection(spinnerPosition);
        }
        id = bundle.getInt("id");
        dateDisplay.setText(bundle.getString("inputdate"));
        amount.setText(bundle.getString("amount"));
        description.setText(bundle.getString("description"));
        insertBtn.setText("Update Data");
    }


    public void ActivityExecution(View v) {
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is_empty;
                if (amount.getText().toString().matches("")) {
                    is_empty = true;
                    Toast.makeText(v.getContext(), "Amount Should be Require", Toast.LENGTH_SHORT).show();
                } else {
                    is_empty = false;

                }
                if (is_empty == false) {
                    float credit = 0;
                    float debit = 0;
                    String types = (String) typeSppiner.getSelectedItem();
                    switch (types) {
                        case "Credit":
                            credit = Float.parseFloat(String.valueOf(amount.getText()));
                            debit = 0;
                            break;
                        case "Debit":
                            credit = 0;
                            debit = Float.parseFloat(String.valueOf(amount.getText()));
                            break;
                        default:
                            break;

                    }
                    DbHelper db = new DbHelper(v.getContext());
                    switch (insertType) {
                        case 0:
                            boolean is_inserted = db.insertData(
                                    (String) banklist.getSelectedItem(),
                                    (String) accountlis.getSelectedItem(),
                                    inputdate.getText().toString(),
                                    (String) typeSppiner.getSelectedItem(),
                                    credit,
                                    debit,
                                    description.getText().toString(),
                                    0
                            );
                            if (is_inserted) {
                                Toast.makeText(v.getContext(), "Successfully Save", Toast.LENGTH_SHORT).show();
                                Clear();
                                BackActivityFragment();
                            } else {
                                Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1:
                            boolean is_updated = db.UpdateBankActivity(
                                    (String) banklist.getSelectedItem(),
                                    (String) accountlis.getSelectedItem(),
                                    inputdate.getText().toString(),
                                    (String) typeSppiner.getSelectedItem(),
                                    credit,
                                    debit,
                                    description.getText().toString(),
                                    id
                            );
                            if (is_updated) {
                                Toast.makeText(v.getContext(), "Successfully Updated Data", Toast.LENGTH_SHORT).show();
                                Clear();
                                BackActivityFragment();
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
    public void BackActivityFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BankActivityList NAME = new BankActivityList();
        fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
        fragmentTransaction.commit();
    }
    public void Clear() {
        banklist.setSelection(0);
        typeSppiner.setSelection(0);
        inputdate.setText(null);
        amount.setText(null);
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

    private void BankList(View v) {
        DbHelper db = new DbHelper(v.getContext());
        List<String> labels = db.BankList();
        bankadapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, labels);
        bankadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        banklist.setAdapter(bankadapter);
    }

    private void paymentType(View v) {
        typeSppiner = v.findViewById(R.id.loanType_sp);
        typeadapter = ArrayAdapter.createFromResource(v.getContext(), R.array.bank_type, android.R.layout.simple_spinner_item);
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSppiner.setAdapter(typeadapter);
    }
}