package com.example.ysp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.example.ysp.adapters.*;
import com.example.ysp.classes.ReturnControl;
import com.example.ysp.classes.ReturnStory;
import com.example.ysp.classes.Story;
import com.example.ysp.classes.User;
import com.example.ysp.dbconnect.ApiUtils;
import com.example.ysp.dbconnect.dbInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab;
    RecyclerView rc;
    storiesAdapter sAdapter;
    /**
    *internetten veri alışverişi yaptığım kısım
    */
    dbInterface dbDIF= ApiUtils.getdbInterface();
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "onCreate: " );
        setContentView(R.layout.activity_main);
        rc=findViewById(R.id.rcv);
        fab=findViewById(R.id.floatingActionButton);

        /**
        *gelen user
        */
        user = (User) getIntent().getSerializableExtra("user");

        /**
        *recyclerviewımı ayarlıyorum
        */
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setHasFixedSize(true);
        fab.setOnClickListener(this);
        getListStories(user);

    }
    public void getListStories(User u) {
        /** 
        *gelen kullanıcın oluşturduğu veya içinde bulunduğu projeşer geliyor
        */
        dbDIF.getStories("getStory",u.getUserMail()).enqueue(new Callback<ReturnStory>() {
            @Override
            public void onResponse(Call<ReturnStory> call, Response<ReturnStory> response) {
                List<Story> s= response.body().getStories();
                Log.e("TAG", "hata"+s.get(0).getStoryId() );

                sAdapter= new storiesAdapter(s,getApplicationContext());
                rc.setAdapter(sAdapter);
            }
            @Override
            public void onFailure(Call<ReturnStory> call, Throwable t) {
                Log.e("TAG", "hata" );
            }
        });

    }
    /**
    *yeni proje oluşturma
    */
    @Override
    public void onClick(View v) {

        View desing= getLayoutInflater().inflate(R.layout.creative_stories_alert,null);
        EditText saveTitle=desing.findViewById(R.id.storiesaveTitle);
        EditText savetext=desing.findViewById(R.id.storiesaveText);

        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Stories ekle");
        build.setView(desing);
        build.setPositiveButton("kaydet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /**
                *format ayar
                */
                DateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
                Date tarih = new Date();
                Story s=new Story("0",user,savetext.getText().toString(),saveTitle.getText().toString(),sdf.format(tarih));

                /**
                *kullanıcı ekleme yerine gidior
                */
                dbDIF.addStory("addStory",s.getStoryCraUId(),s.getStoryText(),s.getStoryTitle(),s.getStoryCraDate()).enqueue(new Callback<ReturnControl>() {
                    @Override
                    public void onResponse(Call<ReturnControl> call, Response<ReturnControl> response) {
                        getListStories(user);

                    }
                    @Override
                    public void onFailure(Call<ReturnControl> call, Throwable t) {

                    }
                });
            }
        });
        build.setNegativeButton("iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        build.show();
    }
}