package com.foodorder.accountbook.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.foodorder.accountbook.Adapter.BankInfoAdapters;
import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.Model.BankInfoModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BankInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BankInfo extends Fragment {
    Button btn,newBankinfosbtn,copybtn;
    RecyclerView bankInfoRecycler;
    BankInfoAdapters adapters;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BankInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BankInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static BankInfo newInstance(String param1, String param2) {
        BankInfo fragment = new BankInfo();
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
        View v = inflater.inflate(R.layout.fragment_bank_info, container, false);
        btn = v.findViewById(R.id.newBankinfobtns);
        newBankinfosbtn=v.findViewById(R.id.newBankinfobtns);
        newBankinfosbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                BankSetup NAME = new BankSetup();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();

            }
        });

        bankInfoRecycler=v.findViewById(R.id.bankInfoRecycler);
        DbHelper dbHelper =new DbHelper(v.getContext());
        ArrayList<BankInfoModels> list= dbHelper.getBankinfo();
        BankInfoAdapters adapter=new BankInfoAdapters(list,v.getContext());
        bankInfoRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(v.getContext());
        bankInfoRecycler.setLayoutManager(layoutManager);
        onResume();


        return v;
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Bank Information");
    }
    private ArrayList<BankInfoModels> getList(View v) {
        DbHelper dbHelper =new DbHelper(v.getContext());
        ArrayList<BankInfoModels> list = dbHelper.getBankinfo();

        return list;
    }

}
