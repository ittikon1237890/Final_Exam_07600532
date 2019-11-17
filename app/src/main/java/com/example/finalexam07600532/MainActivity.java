package com.example.finalexam07600532;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button LogInButton, RegisterButton ;
    EditText Username, Password ,Fullname;
    String UsernameHolder, PasswordHolder,FullnameHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    DbHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    String UserName;
    URL url;
    public static final String UserEmail = "";
    public static final String userName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogInButton = (Button)findViewById(R.id.login_button);

        RegisterButton = (Button)findViewById(R.id.register_button);

        Username = (EditText)findViewById(R.id.username_edit_text);
        Password = (EditText)findViewById(R.id.password_edit_text);
        Fullname = (EditText)findViewById(R.id.full_name_edit_text);

        sqLiteHelper = new DbHelper(this);

        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling EditText is empty or no method.
                CheckEditTextStatus();

                // Calling login method.
                LoginFunction();


            }
        });

        // Adding click listener to register button.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

    }

    // Login function starts from here.
    public void LoginFunction(){

        if(EditTextEmptyHolder) {
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
            cursor = sqLiteDatabaseObj.query(DbHelper.TABLE_NAME, null, " " + DbHelper.Table_Column_2_Email + "=?", new String[]{UsernameHolder}, null, null, null);
            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();
                    TempPassword = cursor.getString(cursor.getColumnIndex(DbHelper.Table_Column_3_Password));
                    UserName=cursor.getString(cursor.getColumnIndex(DbHelper.Table_Column_1_Name));
                    cursor.close();
                }
            }
            CheckFinalResult();
        }
        else {
            Toast.makeText(MainActivity.this,".ใส่ username password",Toast.LENGTH_LONG).show();

        }

    }

    public void CheckEditTextStatus(){

        UsernameHolder = Username.getText().toString();
        PasswordHolder = Password.getText().toString();

        if( TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    public void CheckFinalResult(){

        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {
            String fullname = "ล็อคอินสำเร็จ สวัสดีคุณ ";
            Toast.makeText(MainActivity.this,fullname,Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this,"รหัสผิดพลาด",Toast.LENGTH_LONG).show();
        }
        TempPassword = "NOT_FOUND" ;

    }

}
