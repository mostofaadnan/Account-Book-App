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

import com.foodorder.accountbook.Adapter.BankActivityAdapters;
import com.foodorder.accountbook.Database.DbHelper;
import com.foodorder.accountbook.MainActivity;
import com.foodorder.accountbook.Model.BankActivityModels;
import com.foodorder.accountbook.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BankActivityList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BankActivityList extends Fragment {
    RecyclerView bankActivityCycler;
    BankActivityAdapters adapters;
    Button btn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BankActivityList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BankActivityList.
     */
    // TODO: Rename and change types and number of parameters
    public static BankActivityList newInstance(String param1, String param2) {
        BankActivityList fragment = new BankActivityList();
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

        View v= inflater.inflate(R.layout.fragment_bank_activity_list, container, false);
        btn=v.findViewById(R.id.clilckbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                BankActivity NAME = new BankActivity();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();
            }
        });

        bankActivityCycler=v.findViewById(R.id.bankActivity_Recycler);
        DbHelper db = new DbHelper(v.getContext());
        ArrayList<BankActivityModels> list=db.getBankActivity();
        BankActivityAdapters adapter=new BankActivityAdapters(list,v.getContext());
        bankActivityCycler.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(v.getContext());
        bankActivityCycler.setLayoutManager(layoutManager);
        onResume();
        return  v;
    }
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Bank Activity List");
    }
}