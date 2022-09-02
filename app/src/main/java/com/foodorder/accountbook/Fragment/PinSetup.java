package com.foodorder.accountbook.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.foodorder.accountbook.Database.CurrencyDb;
import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PinSetup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PinSetup extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Switch mySwitch;
    EditText pincode, pincodeconfirm;
    Button pincodesetupbtns;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PinSetup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PinSetup.
     */
    // TODO: Rename and change types and number of parameters
    public static PinSetup newInstance(String param1, String param2) {
        PinSetup fragment = new PinSetup();
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
        View v = inflater.inflate(R.layout.fragment_pin_setup, container, false);
        mySwitch = v.findViewById(R.id.pinaccsessSwitch);
        pincode = v.findViewById(R.id.pincodepass);
        pincodeconfirm = v.findViewById(R.id.pincodepassconfirm);
        pincodesetupbtns = v.findViewById(R.id.pincodesetupbtn);
        CurrencyDb db = new CurrencyDb(v.getContext());
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getSelectPassCode();
        int passAccess = cursor.getInt(cursor.getColumnIndex("Passcode_access"));
        if (passAccess == 0) {
            mySwitch.setChecked(false);
        } else {
            mySwitch.setChecked(true);
        }
        if (mySwitch.isChecked() == true) {

            pincode.setEnabled(true);
            pincode.setInputType(InputType.TYPE_CLASS_NUMBER);
            pincodeconfirm.setEnabled(true);
            pincodeconfirm.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            pincode.setEnabled(false);
            pincode.setInputType(InputType.TYPE_NULL);
            pincodeconfirm.setEnabled(false);
            pincodeconfirm.setInputType(InputType.TYPE_NULL);

        }
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //  boolean = isChecked;
                if (mySwitch.isChecked() == true) {
                   // db.PinCodeAccessTrue();
                    pincode.setEnabled(true);
                    pincode.setInputType(InputType.TYPE_CLASS_NUMBER);
                    pincodeconfirm.setEnabled(true);
                    pincodeconfirm.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else {
                    pincode.setEnabled(false);
                    pincode.setInputType(InputType.TYPE_NULL);
                    pincodeconfirm.setEnabled(false);
                    pincodeconfirm.setInputType(InputType.TYPE_NULL);
                    db.PinCodeAccessFalse();
                }


            }
        });
        pincodesetupbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT).show();
                boolean isEmpty = true;
                if (pincode.getText().toString().matches("") && pincodeconfirm.getText().toString().matches("") && Integer.parseInt(pincode.getText().toString()) > 6 && Integer.parseInt(pincodeconfirm.getText().toString()) > 6 ) {
                    isEmpty = true;
                    Toast.makeText(v.getContext(), "Please Provide Six Digit Numbers", Toast.LENGTH_SHORT).show();
                } else {
                    isEmpty = false;
                }
                if (isEmpty == false) {
                    boolean isValidate = false;
                    int passcode = Integer.parseInt(pincode.getText().toString());
                    int confirmpasscode = Integer.parseInt(pincodeconfirm.getText().toString());
                    if (passcode != confirmpasscode) {
                        Toast.makeText(v.getContext(), "Pincode Not matching", Toast.LENGTH_SHORT).show();
                        isValidate = false;
                    } else {
                        isValidate = true;
                    }

                    if (isValidate == true) {
                        boolean is_Update = db.PinCodeSeup(pincode.getText().toString());
                        if (is_Update) {
                            Toast.makeText(v.getContext(), "Successfully Pincode Update", Toast.LENGTH_SHORT).show();
                            pincode.setText(null);
                            pincodeconfirm.setText(null);
                        } else {
                            Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
        onResume();
        return v;
    }
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Pin Code Setup");
    }
}