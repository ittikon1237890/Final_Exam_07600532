package com.example.finalexam07600532;


import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText id, Password, Name ;
    Button Register;
    String NameHolder, idHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    DbHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button)findViewById(R.id.register_button);

        id = (EditText)findViewById(R.id.username_edit_text);
        Password = (EditText)findViewById(R.id.password_edit_text);
        Name = (EditText)findViewById(R.id.full_name_edit_text);

        sqLiteHelper = new DbHelper(this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDataBaseBuild();
                SQLiteTableBuild();
                CheckEditTextStatus();
                CheckingIDlAlreadyExistsOrNot();
                Confirm();
                EmptyEditTextAfterDataInsert();
            }
        });
    }
    public void Confirm(){
    }

    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(DbHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + DbHelper.TABLE_NAME + "(" + DbHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " + DbHelper.Table_Column_1_Name + " VARCHAR, " + DbHelper.Table_Column_2_Email + " VARCHAR, " + DbHelper.Table_Column_3_Password + " VARCHAR);");

    }

    public void InsertDataIntoSQLiteDatabase(){

        if(EditTextEmptyHolder == true)
        {

            SQLiteDataBaseQueryHolder = "INSERT INTO "+DbHelper.TABLE_NAME+" (name,email,password) VALUES('"+NameHolder+"', '"+idHolder+"', '"+PasswordHolder+"');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();
            Toast.makeText(RegisterActivity.this,"ลงทะเบียนสำเร็จ", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(RegisterActivity.this,"กรอกข้อมูลให้ครบ.", Toast.LENGTH_LONG).show();
        }
    }
    public void EmptyEditTextAfterDataInsert(){
        Name.getText().clear();
        id.getText().clear();
        Password.getText().clear();

    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        NameHolder = Name.getText().toString() ;
        idHolder = id.getText().toString();
        PasswordHolder = Password.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(idHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    public void CheckingIDlAlreadyExistsOrNot(){

        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        cursor = sqLiteDatabaseObj.query(DbHelper.TABLE_NAME, null, " " + DbHelper.Table_Column_2_Email + "=?", new String[]{idHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();


                F_Result = "Email Found";


                cursor.close();
            }
        }

        CheckFinalResult();

    }

    public void CheckFinalResult(){

        if(F_Result.equalsIgnoreCase("Email Found"))
        {

            Toast.makeText(RegisterActivity.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else {

            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "NotFound" ;

    }

}
