package com.foodorder.accountbook.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BankSetup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BankSetup extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Spinner statusSpiner;
    Button btn,clearbtn;
    EditText bankname, branchname, accountnumber;
    int id, insertType;
    ArrayAdapter<CharSequence> adapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BankSetup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BankSetup.
     */
    // TODO: Rename and change types and number of parameters
    public static BankSetup newInstance(String param1, String param2) {
        BankSetup fragment = new BankSetup();
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
        View v = inflater.inflate(R.layout.fragment_bank_setup, container, false);
        setStatus(v);
        btn = v.findViewById(R.id.bankSetupBtn);
        clearbtn=v.findViewById(R.id.banksetupClearBtn);
        bankname = v.findViewById(R.id.bankname_et);
        branchname = v.findViewById(R.id.branchname_et);
        accountnumber = v.findViewById(R.id.accountno_et);
        statusSpiner = v.findViewById(R.id.statusSpiner);
        insertType = 0;
        BankSetupExecution(v);
        Bundle arguments = getArguments();
        if (arguments != null) {
            handleArguments(arguments);
        }

        clearbtn.setOnClickListener(new View.OnClickListener() {
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
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("New Bank Setup");
    }
    private void handleArguments(Bundle arguments) {
        bankname.setText(arguments.getString("bank_name"));
        branchname.setText(arguments.getString("branch_name"));
        accountnumber.setText(arguments.getString("account_number"));
        id = arguments.getInt("id");
        insertType = arguments.getInt("insertType");
        String status = arguments.getString("status");
        if (status != null) {
            int spinnerPosition = adapter.getPosition(status);
            statusSpiner.setSelection(spinnerPosition);
        }
        btn.setText("Update Data");
    }

    private void BankSetupExecution(View v) {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is_empty;
                if(bankname.getText().toString().matches("")|| accountnumber.getText().toString().matches("")){
                    is_empty=true;
                    Toast.makeText(v.getContext(), "Bank Name Or Account Number is Empty ", Toast.LENGTH_SHORT).show();
                }else {
                    is_empty=false;
                }
                if(is_empty==false){
                    DbHelper dbHelper = new DbHelper(v.getContext());
                    switch (insertType) {
                        case 0:
                            boolean is_inserted = dbHelper.insertOrder(
                                    bankname.getText().toString(),
                                    branchname.getText().toString(),
                                    accountnumber.getText().toString(),
                                    (String) statusSpiner.getSelectedItem()
                            );
                            if (is_inserted) {
                                Toast.makeText(v.getContext(), "Successfully Save", Toast.LENGTH_SHORT).show();
                                BackFragment();
                            } else {
                                Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1:
                            boolean is_update = dbHelper.updateBankInfo(
                                    bankname.getText().toString(),
                                    branchname.getText().toString(),
                                    accountnumber.getText().toString(),
                                    (String) statusSpiner.getSelectedItem(),
                                    id
                            );
                            if (is_update) {
                                Toast.makeText(v.getContext(), "Successfully Update Data", Toast.LENGTH_SHORT).show();
                               // Clear();
                                BackFragment();
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
public void BackFragment(){
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    BankInfo NAME = new BankInfo();
    fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
    fragmentTransaction.commit();
}
    public void Clear() {
        bankname.setText(null);
        statusSpiner.setSelection(0);
        branchname.setText(null);
        accountnumber.setText(null);
        insertType = 0;
    }

    private void setStatus(View v) {
        statusSpiner = v.findViewById(R.id.statusSpiner);
        adapter = ArrayAdapter.createFromResource(v.getContext(), R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpiner.setAdapter(adapter);
    }

}