package com.foodorder.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;

import com.foodorder.accountbook.Database.CurrencyDb;

public class SplashScreen extends AppCompatActivity {
    int passAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        CurrencyDb db = new CurrencyDb(this);
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getSelectPassCode();
        passAccess = cursor.getInt(cursor.getColumnIndex("Passcode_access"));

        getSupportActionBar().hide();
        Thread thread = new Thread() {
            public void run() {

                try {
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    switch (passAccess) {
                        case 1:
                            Intent intent = new Intent(SplashScreen.this, PasscodeActivity.class);
                            startActivity(intent);
                            break;
                        case 0:
                            Intent intent1 = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(intent1);
                            break;
                        default:
                            break;
                    }

                }
            }
        };
        thread.start();
    }
}