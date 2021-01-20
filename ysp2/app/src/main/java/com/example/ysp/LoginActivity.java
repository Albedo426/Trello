package com.example.ysp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ysp.classes.ReturnUser;
import com.example.ysp.classes.User;
import com.example.ysp.dbconnect.ApiUtils;
import com.example.ysp.dbconnect.dbInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText email,pass;
    Button btn;

    /**
    *internetten veri alışverişi yaptığım kısım
    */
    dbInterface dbDIF=ApiUtils.getdbInterface();
    /**
    *giriş yapan kullanıcı 
    */
    User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.emailText);
        pass=findViewById(R.id.passText);
        btn=findViewById(R.id.loginBtn);
        //login işlemi için
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dbDIF.getUser("getUserLogin",email.getText().toString(),pass.getText().toString()).enqueue(new Callback<ReturnUser>() {
            @Override
            public void onResponse(Call<ReturnUser> call, Response<ReturnUser> response) {
                //giriş başarılıysa maine gider login yapan kullanıcıyla birlikte
                List<User> k= response.body().getUser();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.putExtra("user", k.get(0));
                startActivity(i);
            }

            @Override
            public void onFailure(Call<ReturnUser> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Kullanıcı adi veya şifre hatalı", Toast.LENGTH_SHORT).show();
            }
        });
    }
}