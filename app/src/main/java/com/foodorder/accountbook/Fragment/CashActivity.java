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

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CashActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CashActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    int id, insertType;
    private String mParam1;
    private String mParam2;
    private TextView dateDisplay;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    Spinner typeSppiner;
    Button insertBtn, clearBtn;
    EditText amount, description;
    ArrayAdapter<CharSequence> adapter;

    public CashActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CashActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static CashActivity newInstance(String param1, String param2) {
        CashActivity fragment = new CashActivity();
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
        View v = inflater.inflate(R.layout.fragment_cash_activity, container, false);
        dateDisplay = v.findViewById(R.id.cashactivityDate_etv);
        insertBtn = v.findViewById(R.id.cashactivity_btn);
        clearBtn = v.findViewById(R.id.cashactivityCleraBtn);
        amount = v.findViewById(R.id.cashAmount_et);
        description = v.findViewById(R.id.cashdesciption_et);
        typeSppiner = v.findViewById(R.id.cashType_sp);
        insertType = 0;
        CurrentDate();
        DatePick(v);
        paymentType(v);
        ActivityExecution(v);
        Bundle arguments = getArguments();
        if (arguments != null) {
            handleArguments(arguments);
        }
        clearBtn.setOnClickListener(new View.OnClickListener() {
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
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("New Cash Activity");
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

    private void handleArguments(Bundle arguments) {
        dateDisplay.setText(arguments.getString("inputdate"));
        String type = arguments.getString("type");
        if (type != null) {
            int spinnerPosition = adapter.getPosition(type);
            typeSppiner.setSelection(spinnerPosition);
        }
        amount.setText(Float.toString(arguments.getFloat("amount")));
        description.setText(arguments.getString("description"));
        id = arguments.getInt("id");
        insertType = arguments.getInt("insertType");
        insertBtn.setText("Update Data");
    }

    public void ActivityExecution(View v) {
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is_Empty;
                if (amount.getText().toString().matches("")) {
                    is_Empty = true;
                    Toast.makeText(v.getContext(), "Amount Should be Require", Toast.LENGTH_SHORT).show();
                } else {
                    is_Empty = false;
                }
                if (is_Empty == false) {
                    float cashin = 0;
                    float cashout = 0;

                    String types = (String) typeSppiner.getSelectedItem();
                    switch (types) {
                        case "Cash In":
                            cashin = Float.parseFloat(String.valueOf(amount.getText()));
                            cashout = 0;
                            break;
                        case "Cash Out":
                            cashin = 0;
                            cashout = Float.parseFloat(String.valueOf(amount.getText()));
                            break;
                        default:
                            break;

                    }
                    DbHelper db = new DbHelper(v.getContext());

                    switch (insertType) {
                        case 0:
                            boolean is_inserted = db.insertDataCashActivity(
                                    description.getText().toString(),
                                    dateDisplay.getText().toString(),
                                    (String) typeSppiner.getSelectedItem(),
                                    cashin,
                                    cashout,
                                    0

                            );
                            if (is_inserted) {
                                Toast.makeText(v.getContext(), "Successfully Save", Toast.LENGTH_SHORT).show();
                                Clear();
                                CashActivityFragment();
                            } else {
                                Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1:
                            boolean is_update = db.updateDataCashActivity(
                                    description.getText().toString(),
                                    dateDisplay.getText().toString(),
                                    (String) typeSppiner.getSelectedItem(),
                                    cashin,
                                    cashout,
                                    id

                            );
                            if (is_update) {
                                Toast.makeText(v.getContext(), "Successfully Save", Toast.LENGTH_SHORT).show();
                                Clear();
                                CashActivityFragment();
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
    public void CashActivityFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Cash NAME = new Cash();
        fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
        fragmentTransaction.commit();
    }
    public void Clear() {

        description.setText(null);
        typeSppiner.setSelection(0);
        dateDisplay.setText(null);
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

    private void paymentType(View v) {

        adapter = ArrayAdapter.createFromResource(v.getContext(), R.array.cash_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSppiner.setAdapter(adapter);
    }
}