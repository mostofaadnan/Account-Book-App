package com.foodorder.accountbook.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foodorder.accountbook.Database.CurrencyDb;
import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrencySetup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrencySetup extends Fragment {
    Spinner currencylist, typeSppiner;
    TextView curencycode, curencysymbole;
    private CurrencyDb currencyDb;
    Button updateBtn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CurrencySetup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrencySetup.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrencySetup newInstance(String param1, String param2) {
        CurrencySetup fragment = new CurrencySetup();
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
        View v = inflater.inflate(R.layout.fragment_currency_setup, container, false);
        currencylist = v.findViewById(R.id.currencynam_sp);
        typeSppiner = v.findViewById(R.id.curencyUse_sp);
        curencycode = v.findViewById(R.id.cureencycode_et);
        curencysymbole = v.findViewById(R.id.currencySymbol_et);
        updateBtn = v.findViewById(R.id.currencyUpdate_btn);
        CurrencyType(v);
        onResume();
        return v;
    }
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Currency Setup");
    }

    private void CurrencyType(View v) {
        CurrencyDb db = new CurrencyDb(v.getContext());
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> labels = db.CurrencyList();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencylist.setAdapter(dataAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(), R.array.currency_code, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSppiner.setAdapter(adapter);

        Cursor cursor = db.getSelectedCurency();
        String compareValue = cursor.getString(cursor.getColumnIndex("currency_name"));
        curencycode.setText(cursor.getString(cursor.getColumnIndex("currency_code")));
        curencysymbole.setText(cursor.getString(cursor.getColumnIndex("currency_symbol")));
        String typevalue = cursor.getString(cursor.getColumnIndex("use_code"));
        if (compareValue != null) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            currencylist.setSelection(spinnerPosition);
        }

        if (typevalue != null) {
            int spinnerPosition1 = adapter.getPosition(typevalue);
            typeSppiner.setSelection(spinnerPosition1);
        }


        currencylist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String curencyname = (String) parent.getItemAtPosition(position).toString();
                Cursor cursor1 = db.getSelectedCurencyItem(curencyname);
                curencycode.setText(cursor1.getString(cursor1.getColumnIndex("currency_code")));
                curencysymbole.setText(cursor1.getString(cursor1.getColumnIndex("currency_symbol")));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is_inserted = db.CurencyUpdate(
                        (String) currencylist.getSelectedItem(),
                        curencycode.getText().toString(),
                        curencysymbole.getText().toString(),
                        (String) typeSppiner.getSelectedItem()
                );
                if (is_inserted) {
                    Toast.makeText(v.getContext(), "Successfully Update Curency", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}