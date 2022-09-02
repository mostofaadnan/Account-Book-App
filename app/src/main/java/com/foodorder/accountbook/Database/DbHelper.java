package com.foodorder.accountbook.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.foodorder.accountbook.Model.AccountBalanceModels;
import com.foodorder.accountbook.Model.BankActivityModels;
import com.foodorder.accountbook.Model.BankInfoModels;
import com.foodorder.accountbook.Model.CashActivityModels;
import com.foodorder.accountbook.Model.ExpensesActivityModels;
import com.foodorder.accountbook.Model.personalLoanModels;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    final static String MyDBname = "AccountBook.db";
    final static int DBversion = 1;

    public DbHelper(@Nullable Context context) {
        super(context, MyDBname, null, DBversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table bank_information" +
                        "(id integer primary key autoincrement," +
                        "bank_name text," +
                        "branch_name text," +
                        "account_number text," +
                        "status  text)"
        );
        db.execSQL(
                "create table bank_activity" +
                        "(id integer primary key autoincrement," +
                        "bank_name text," +
                        "account_number text," +
                        "inputdate text," +
                        "type text," +
                        "credit float," +
                        "debit float," +
                        "description text," +
                        "payment_id int)"
        );

        db.execSQL(
                "create table cash_activity" +
                        "(id integer primary key autoincrement," +
                        "description text," +
                        "inputdate text," +
                        "type text," +
                        "cashin float," +
                        "cashout float," +
                        "balance float," +
                        "payment_id int)"
        );
        db.execSQL(
                "create table loan_activity" +
                        "(id integer primary key autoincrement," +
                        "description text," +
                        "inputdate text," +
                        "type text," +
                        "dept float," +
                        "repayment float," +
                        "person_name text)"
        );
        db.execSQL(
                "create table expenses_activity" +
                        "(id integer primary key autoincrement," +
                        "expense_type text," +
                        "inputdate text," +
                        "source text," +
                        "amount float," +
                        "bank text," +
                        "account_no text," +
                        "description text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table if exists bank_information");
        db.execSQL("DROP table if exists bank_activity");
        db.execSQL("DROP table if exists cash_activity");
        db.execSQL("DROP table if exists loan_activity");
        db.execSQL("DROP table if exists expenses_activity");
        onCreate(db);
    }

    public boolean insertOrder(String name, String branchname, String accountnumber, String status) {
        SQLiteDatabase database = getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("bank_name", name);
        value.put("branch_name", branchname);
        value.put("account_number", accountnumber);
        value.put("status", status);
        long id = database.insert("bank_information", null, value);
        database.close();
        if (id <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<BankInfoModels> getBankinfo() {
        ArrayList<BankInfoModels> bankinfo = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select id,bank_name,branch_name,account_number,status from bank_information ORDER BY id DESC", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                BankInfoModels model = new BankInfoModels();
                model.setId(cursor.getInt(0));
                model.setName(cursor.getString(1));
                model.setBranchname(cursor.getString(2));
                model.setAccountno(cursor.getString(3));
                model.setStatus(cursor.getString(4));
                bankinfo.add(model);
            }
        }
        cursor.close();
        database.close();
        return bankinfo;
    }


    public Cursor GetDataById(int id) {

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from orders where id=" + id, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;

    }

    public boolean updateBankInfo(String name, String branchname, String accountnumber, String status, int id) {
        SQLiteDatabase database = getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("bank_name", name);
        value.put("branch_name", branchname);
        value.put("account_number", accountnumber);
        value.put("status", status);
        long row = database.update("bank_information", value, "id=" + id, null);
        database.close();
        if (row <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public int DeleteBankInfo(int id) {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("bank_information", "id=" + id, null);
    }

    public List<String> BankList() {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from bank_information", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return list;
    }

    public List AccountList(String bankname) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from bank_information where bank_name=?",
                new String[]{bankname});
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return list;
    }

    //bank activity
    public boolean insertData(String bankname, String accountnumber, String inputdate, String type, float credit, float debit, String description, int pyament_id) {
        SQLiteDatabase database = getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("bank_name", bankname);
        value.put("account_number", accountnumber);
        value.put("inputdate", inputdate);
        value.put("type", type);
        value.put("credit", credit);
        value.put("debit", debit);
        value.put("description", description);
        value.put("payment_id", pyament_id);
        long id = database.insert("bank_activity", null, value);
        database.close();
        if (id <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean UpdateBankActivity(String bankname, String accountnumber, String inputdate, String type, float credit, float debit, String description, int id) {
        SQLiteDatabase database = getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("bank_name", bankname);
        value.put("account_number", accountnumber);
        value.put("inputdate", inputdate);
        value.put("type", type);
        value.put("credit", credit);
        value.put("debit", debit);
        value.put("description", description);
        long row = database.update("bank_activity", value, "id=" + id, null);
        database.close();
        if (row <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<BankActivityModels> getBankActivity() {
        ArrayList<BankActivityModels> bankinfo = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select id,bank_name,account_number,inputdate,type,credit,debit,description,payment_id from bank_activity ORDER BY id DESC", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                BankActivityModels model = new BankActivityModels();
                model.setId(cursor.getInt(0));
                model.setBank_name(cursor.getString(1));
                model.setAccount_number(cursor.getString(2));
                model.setInput_date(cursor.getString(3));
                String Type = cursor.getString(4);
                model.setType(Type);
                switch (Type) {
                    case "Credit":
                        model.setAmount(cursor.getString(5));
                        break;
                    case "Debit":
                        model.setAmount(cursor.getString(6));
                        break;
                    default:
                        break;
                }
                model.setDescription(cursor.getString(7));
                model.setPayment_id(cursor.getInt(8));
                bankinfo.add(model);
            }
        }
        cursor.close();
        database.close();
        return bankinfo;
    }

    public ArrayList<BankActivityModels> getAccountDetails(String accountNumber) {
        ArrayList<BankActivityModels> bankinfo = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select id,bank_name,account_number,inputdate,type,credit,debit,description,payment_id from bank_activity  where account_number=" + accountNumber , null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                BankActivityModels model = new BankActivityModels();
                model.setId(cursor.getInt(0));
                model.setBank_name(cursor.getString(1));
                model.setAccount_number(cursor.getString(2));
                model.setInput_date(cursor.getString(3));
                String Type = cursor.getString(4);
                model.setType(Type);
                switch (Type) {
                    case "Credit":
                        model.setAmount(cursor.getString(5));
                        break;
                    case "Debit":
                        model.setAmount(cursor.getString(6));
                        break;
                    default:
                        break;
                }
                model.setDescription(cursor.getString(7));
                model.setPayment_id(cursor.getInt(8));
                bankinfo.add(model);
            }
        }
        cursor.close();
        database.close();
        return bankinfo;
    }

    public ArrayList<BankActivityModels> getAccountDetailsStatemtn(String fromdate, String todete, String accountNumer) {
        ArrayList<BankActivityModels> bankinfo = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select id,bank_name,account_number,inputdate,type,credit,debit,description,payment_id from bank_activity where inputdate >= '" + fromdate + "' and inputdate <= '" + todete + "' and account_number='" + accountNumer + "' ORDER BY id DESC ", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                BankActivityModels model = new BankActivityModels();
                model.setId(cursor.getInt(0));
                model.setBank_name(cursor.getString(1));
                model.setAccount_number(cursor.getString(2));
                model.setInput_date(cursor.getString(3));
                String Type = cursor.getString(4);
                model.setType(Type);
                switch (Type) {
                    case "Credit":
                        model.setAmount(cursor.getString(5));
                        break;
                    case "Debit":
                        model.setAmount(cursor.getString(6));
                        break;
                    default:
                        break;
                }
                model.setDescription(cursor.getString(7));
                model.setPayment_id(cursor.getInt(8));
                bankinfo.add(model);
            }
        }
        cursor.close();
        database.close();
        return bankinfo;
    }

    public int DeleteBankActivity(int id) {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("bank_activity", "id=" + id, null);
    }

    //account Balance
    public ArrayList<AccountBalanceModels> AccountBalance() {
        ArrayList<AccountBalanceModels> bankinfo = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select bank_name," +
                "account_number," +
                "sum(credit)  as 'totalcredit'," +
                "sum(debit)  as 'totaldebit'" +
                " from bank_activity  GROUP BY account_number", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AccountBalanceModels model = new AccountBalanceModels();
                model.setBank_name(cursor.getString(cursor.getColumnIndex("bank_name")));
                model.setAccount_number(cursor.getString(cursor.getColumnIndex("account_number")));
                float totalcredit = cursor.getFloat(cursor.getColumnIndex("totalcredit"));
                float totaldebit = cursor.getFloat(cursor.getColumnIndex("totaldebit"));
                float balance = (totalcredit - totaldebit);
                model.setCredit(totalcredit);
                model.setDebit(totaldebit);
                model.setBalance(balance);
                bankinfo.add(model);
            }
        }
        cursor.close();
        database.close();
        return bankinfo;
    }

    public float bankcredit() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT sum(credit) as 'totalcredit'" +
                "FROM bank_activity ", null);
        if (c.moveToFirst()) {
            float totalcredits = c.getFloat(c.getColumnIndex("totalcredit"));
            return totalcredits;
        }
        return 0;
    }

    public float bankdebit() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT sum(debit) as 'totaldebit'" +
                "FROM bank_activity ", null);
        if (c.moveToFirst()) {
            float totaldebits = c.getFloat(c.getColumnIndex("totaldebit"));
            return totaldebits;
        }
        return 0;
    }

    //cash Activity
    public boolean insertDataCashActivity(String description, String inputdate, String type, float cashin, float cashout, int payment_id) {
        float newBalance = 0;
        float balance = CashBalance();
        switch (type) {
            case "Cash In":
                newBalance = balance + cashin;
                break;
            case "Cash Out":
                newBalance = balance - cashout;
                break;
            default:
                break;
        }

        SQLiteDatabase database = getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("description", description);
        value.put("inputdate", inputdate);
        value.put("type", type);
        value.put("cashin", cashin);
        value.put("cashout", cashout);
        value.put("balance", newBalance);
        value.put("payment_id", payment_id);
        long id = database.insert("cash_activity", null, value);
        database.close();
        if (id <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public float CashBalance() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT sum(cashin) as 'totalcashin'," +
                "sum(cashout) as 'totalcashout' " +
                "FROM cash_activity ", null);
        if (c.moveToFirst()) {
            float totalcashin = c.getFloat(c.getColumnIndex("totalcashin"));
            float totalcashout = c.getFloat(c.getColumnIndex("totalcashout"));
            float balance = (totalcashin - totalcashout);
            return balance;
        }
        return 0;
    }

    public ArrayList<CashActivityModels> getCashActivity() {
        ArrayList<CashActivityModels> cashActivity = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select id,description,inputdate,type,cashin,cashout,balance,payment_id from cash_activity ORDER BY id DESC", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CashActivityModels model = new CashActivityModels();
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                model.setInpudate(cursor.getString(cursor.getColumnIndex("inputdate")));
                String Type = cursor.getString(cursor.getColumnIndex("type"));
                model.setPaymentId(cursor.getInt(cursor.getColumnIndex("payment_id")));
                model.setType(Type);
                switch (Type) {
                    case "Cash In":
                        model.setAmount(cursor.getFloat(cursor.getColumnIndex("cashin")));
                        break;
                    case "Cash Out":
                        model.setAmount(cursor.getFloat(cursor.getColumnIndex("cashout")));
                    default:
                        break;
                }
                model.setBalance(cursor.getFloat(cursor.getColumnIndex("balance")));
                cashActivity.add(model);
            }
        }
        cursor.close();
        database.close();
        return cashActivity;
    }


    public ArrayList<CashActivityModels> getCashStatement(String fromdate, String todete) {
        ArrayList<CashActivityModels> cashActivity = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select id,description,inputdate,type,cashin,cashout,balance,payment_id from cash_activity where inputdate >= '" + fromdate + "' and inputdate <= '" + todete + "' ORDER BY id DESC", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CashActivityModels model = new CashActivityModels();
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                model.setInpudate(cursor.getString(cursor.getColumnIndex("inputdate")));
                String Type = cursor.getString(cursor.getColumnIndex("type"));
                model.setPaymentId(cursor.getInt(cursor.getColumnIndex("payment_id")));
                model.setType(Type);
                switch (Type) {
                    case "Cash In":
                        model.setAmount(cursor.getFloat(cursor.getColumnIndex("cashin")));
                        break;
                    case "Cash Out":
                        model.setAmount(cursor.getFloat(cursor.getColumnIndex("cashout")));
                    default:
                        break;
                }
                model.setBalance(cursor.getFloat(cursor.getColumnIndex("balance")));
                cashActivity.add(model);
            }
        }
        cursor.close();
        database.close();
        return cashActivity;
    }

    public boolean updateDataCashActivity(String description, String inputdate, String type, float cashin, float cashout, int id) {
        int is_Delete = DeleteCashActivity(id);
        if (is_Delete > 0) {
            insertDataCashActivity(description, inputdate, type, cashin, cashout, 0);
            return true;
        }
        return false;
    }

    public int DeleteCashActivity(int id) {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("cash_activity", "id=" + id, null);
    }
//loan activity

    public boolean insertDataloanActivity(String description, String inputdate, String type, float dept, float repayment, String person_name) {

        SQLiteDatabase database = getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("description", description);
        value.put("inputdate", inputdate);
        value.put("type", type);
        value.put("dept", dept);
        value.put("repayment", repayment);
        value.put("person_name", person_name);
        long id = database.insert("loan_activity", null, value);
        database.close();
        if (id <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<personalLoanModels> getLoanActivity() {
        ArrayList<personalLoanModels> loanActivity = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select id,description,inputdate,type,dept,repayment,person_name from loan_activity ORDER BY id DESC", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                personalLoanModels model = new personalLoanModels();
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                model.setInputdate(cursor.getString(cursor.getColumnIndex("inputdate")));
                String Type = cursor.getString(cursor.getColumnIndex("type"));
                model.setType(Type);
                switch (Type) {
                    case "Debt":
                        model.setAmount(cursor.getFloat(cursor.getColumnIndex("dept")));
                        break;
                    case "Loan repayment":
                        model.setAmount(cursor.getFloat(cursor.getColumnIndex("repayment")));
                    default:
                        break;
                }
                model.setName(cursor.getString(cursor.getColumnIndex("person_name")));
                loanActivity.add(model);
            }
        }
        cursor.close();
        database.close();
        return loanActivity;
    }

    public ArrayList<personalLoanModels> loanStatement(String fromdate, String todete) {
        ArrayList<personalLoanModels> loanActivity = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select id,description,inputdate,type,dept,repayment,person_name from loan_activity where inputdate >= '" + fromdate + "' and inputdate <= '" + todete + "' ORDER BY id DESC ", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                personalLoanModels model = new personalLoanModels();
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                model.setInputdate(cursor.getString(cursor.getColumnIndex("inputdate")));
                String Type = cursor.getString(cursor.getColumnIndex("type"));
                model.setType(Type);
                switch (Type) {
                    case "Debt":
                        model.setAmount(cursor.getFloat(cursor.getColumnIndex("dept")));
                        break;
                    case "Loan repayment":
                        model.setAmount(cursor.getFloat(cursor.getColumnIndex("repayment")));
                    default:
                        break;
                }
                model.setName(cursor.getString(cursor.getColumnIndex("person_name")));
                loanActivity.add(model);
            }
        }
        cursor.close();
        database.close();
        return loanActivity;
    }

    public float totalDept() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT sum(dept) as 'totaldept'" +
                "FROM loan_activity ", null);
        if (c.moveToFirst()) {
            float totaldepts = c.getFloat(c.getColumnIndex("totaldept"));
            return totaldepts;
        }
        return 0;
    }

    public float totalpayment() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT sum(repayment) as 'totalrepayment'" +
                "FROM loan_activity ", null);
        if (c.moveToFirst()) {
            float totalrepayments = c.getFloat(c.getColumnIndex("totalrepayment"));
            return totalrepayments;
        }
        return 0;
    }

    public boolean updateDataloanActivity(String description, String inputdate, String type, float dept, float repayment, String person_name, int id) {

        SQLiteDatabase database = getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("description", description);
        value.put("inputdate", inputdate);
        value.put("type", type);
        value.put("dept", dept);
        value.put("repayment", repayment);
        value.put("person_name", person_name);
        long row = database.update("loan_activity", value, "id=" + id, null);
        database.close();
        if (row <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public int DeleteLoanActivity(int id) {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("loan_activity", "id=" + id, null);
    }
    //Expensen Activity

    public boolean ExpenseActivityInsert(String expensestype, String inputdate, String source, float amount, String bank, String account_no, String description) {
        SQLiteDatabase database = getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("expense_type", expensestype);
        value.put("inputdate", inputdate);
        value.put("source", source);
        value.put("amount", amount);
        value.put("bank", bank);
        value.put("account_no", account_no);
        value.put("description", description);
        long id = database.insert("expenses_activity", null, value);
        switch (source) {
            case "Cash":
                insertDataCashActivity(expensestype, inputdate, "Cash Out", 0, amount, (int) id);
                break;
            case "Bank":
                insertData(bank, account_no, inputdate, "Debit", 0, amount, expensestype, (int) id);
                break;
            default:
                break;
        }
        database.close();
        if (id <= 0) {
            return false;
        } else {
            return true;
        }
    }


    public ArrayList<ExpensesActivityModels> getExpensesActivity() {
        ArrayList<ExpensesActivityModels> ExpensesActivity = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select id," +
                "expense_type," +
                "inputdate," +
                "source," +
                "amount," +
                "bank," +
                "account_no," +
                "description" +
                " from expenses_activity", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ExpensesActivityModels model = new ExpensesActivityModels();
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setExpensesType(cursor.getString(cursor.getColumnIndex("expense_type")));
                model.setInputdate(cursor.getString(cursor.getColumnIndex("inputdate")));
                model.setSource(cursor.getString(cursor.getColumnIndex("source")));
                model.setAmount(cursor.getFloat(cursor.getColumnIndex("amount")));
                model.setBank(cursor.getString(cursor.getColumnIndex("bank")));
                model.setAccountNo(cursor.getString(cursor.getColumnIndex("account_no")));
                model.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                ExpensesActivity.add(model);
            }
        }
        cursor.close();
        database.close();
        return ExpensesActivity;
    }

    public ArrayList<ExpensesActivityModels> ExpensesStatement(String fromdate, String todete) {
        ArrayList<ExpensesActivityModels> ExpensesActivity = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select id," +
                "expense_type," +
                "inputdate," +
                "source," +
                "amount," +
                "bank," +
                "account_no," +
                "description" +
                " from expenses_activity where inputdate >= '" + fromdate + "' and inputdate <= '" + todete + "' ORDER BY id DESC ", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ExpensesActivityModels model = new ExpensesActivityModels();
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setExpensesType(cursor.getString(cursor.getColumnIndex("expense_type")));
                model.setInputdate(cursor.getString(cursor.getColumnIndex("inputdate")));
                model.setSource(cursor.getString(cursor.getColumnIndex("source")));
                model.setAmount(cursor.getFloat(cursor.getColumnIndex("amount")));
                model.setBank(cursor.getString(cursor.getColumnIndex("bank")));
                model.setAccountNo(cursor.getString(cursor.getColumnIndex("account_no")));
                model.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                ExpensesActivity.add(model);
            }
        }
        cursor.close();
        database.close();
        return ExpensesActivity;
    }

    public boolean ExpenseActivityUpdate(String expensestype, String inputdate, String source, float amount, String bank, String account_no, String description, int id, String PreviousSource) {
        SQLiteDatabase database = getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("expense_type", expensestype);
        value.put("inputdate", inputdate);
        value.put("source", source);
        value.put("amount", amount);
        value.put("bank", bank);
        value.put("account_no", account_no);
        value.put("description", description);
        long row = database.update("expenses_activity", value, "id=" + id, null);
        switch (PreviousSource) {
            case "Cash":
                DeleteCashActivityByPayment(id);
                break;
            case "Bank":
                DeleteBankActivityByPayment(id);
                break;
            default:
                break;
        }
        switch (source) {
            case "Cash":
                insertDataCashActivity(expensestype, inputdate, "Cash Out", 0, amount, (int) id);
                break;
            case "Bank":
                insertData(bank, account_no, inputdate, "Debit", 0, amount, expensestype, (int) id);
                break;
            default:
                break;
        }
        database.close();
        if (row <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public int DeleteCashActivityByPayment(int id) {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("cash_activity", "payment_id=" + id, null);
    }

    public int DeleteBankActivityByPayment(int id) {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("bank_activity", "payment_id=" + id, null);
    }

    public float TotalExpense() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT sum(amount) as 'totalamount'" +
                "FROM expenses_activity ", null);
        if (c.moveToFirst()) {
            float totalamounts = c.getFloat(c.getColumnIndex("totalamount"));
            return totalamounts;
        }
        return 0;
    }

    public int DeleteExpensesActivity(int id) {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("expenses_activity", "id=" + id, null);
    }
}

