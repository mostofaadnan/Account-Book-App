package com.foodorder.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.foodorder.accountbook.Database.CurrencyDb;
import com.hanks.passcodeview.PasscodeView;

public class PasscodeActivity extends AppCompatActivity {
    PasscodeView passcodeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        passcodeView=findViewById(R.id.passcodeView);
        CurrencyDb db = new CurrencyDb(this);
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getSelectPassCode();
        String passCode=cursor.getString(cursor.getColumnIndex("passcode"));
        passcodeView.setPasscodeLength(6).setLocalPasscode(passCode).setListener(new PasscodeView.PasscodeViewListener() {
            @Override
            public void onFail() {
                Toast.makeText(PasscodeActivity.this, "Your Pin Code Is Wrong", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(String number) {
                Intent intent=new Intent(PasscodeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}