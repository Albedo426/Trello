package com.example.ysp.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysp.R;
import com.example.ysp.adapters.*;
import com.example.ysp.classes.Category;
import com.example.ysp.classes.ReturnCategory;
import com.example.ysp.classes.ReturnControl;
import com.example.ysp.classes.ReturnUser;
import com.example.ysp.classes.Story;
import com.example.ysp.classes.User;
import com.example.ysp.dbconnect.ApiUtils;
import com.example.ysp.dbconnect.dbInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.dinuscxj.refresh.RecyclerRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TodoFragment extends Fragment  implements View.OnClickListener {

    FloatingActionButton fab;
    RecyclerView rc;
    kategoriesAdapter adap;
    Story story;
    String typestory;
    RecyclerRefreshLayout rcrl;
     /**
    *internetten veri alışverişi yaptığım kısım
    */
    dbInterface dbDIF= ApiUtils.getdbInterface();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ViewHolder=inflater.inflate(R.layout.fragment_todo, container, false);
        fab=ViewHolder.findViewById(R.id.fabadd);
        rc=ViewHolder.findViewById(R.id.Recy);
        rcrl=ViewHolder.findViewById(R.id.refresh_layout);
        typestory = String.valueOf(getArguments().getSerializable("typestory"));

        story = (Story)getArguments().getSerializable("story");
        getListKategories(typestory,story.getStoryId());
        rc.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc.setHasFixedSize(true);
        fab.setOnClickListener(this);
        rcrl.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListKategories(typestory,story.getStoryId());
                rcrl.setRefreshing(false);
            }
        });

        return ViewHolder;
    }
      /** 
        *gelen kullanıcın oluşturduğu veya içinde bulunduğu projeşer geliyor
        */
    public void getListKategories(String type ,String storyID) {

        dbDIF.getCategory("getCategoryParser",storyID,type).enqueue(new Callback<ReturnCategory>() {
            @Override
            public void onResponse(Call<ReturnCategory> call, Response<ReturnCategory> response) {
                adap= new kategoriesAdapter(response.body().getCategories(),getActivity());
                rc.setAdapter(adap);
            }

            @Override
            public void onFailure(Call<ReturnCategory> call, Throwable t) {

            }
        });

    }
      /** 
        *format kontrolü yapar 
        */
    public boolean dateformatControl(String s){

        String[] arrOfStr = s.split("\\.");
        if(arrOfStr.length==3){
            return  true;
        }
        return  false;
    }

      /** 
        *rask ekleme
        */
    @Override
    public void onClick(View v) {

        LayoutInflater inflater = getLayoutInflater();
        View desing= inflater.inflate(R.layout.crativ_categorie_view,null);
        EditText editTitle=desing.findViewById(R.id.editTitle);
        EditText editText=desing.findViewById(R.id.editText);
        EditText editTextDate=desing.findViewById(R.id.editTextDate);

        Spinner spinner=desing.findViewById(R.id.spinner);
            dbDIF.getUsers("getUser").enqueue(new Callback<ReturnUser>() {
                @Override
                public void onResponse(Call<ReturnUser> call, Response<ReturnUser> response) {
                    ArrayAdapter<User> adapter = new ArrayAdapter<User>(getActivity(), android.R.layout.simple_spinner_dropdown_item, response.body().getUser());
                    spinner.setAdapter(adapter);
                    AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
                    build.setTitle("Kategori Ekle");
                    build.setView(desing);
                    build.setPositiveButton("kaydet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(dateformatControl(editTextDate.getText().toString())){

                                Category s =new Category("0",story,typestory,editTextDate.getText().toString(),editText.getText().toString(),editTitle.getText().toString(),(User)spinner.getSelectedItem());
                                dbDIF.addCategory("addCategory",s.getStoriesId(),s.getCategoryType(),s.getFinishDate(),s.getCategoryText(),s.getCategoryTitle(),s.getCategoryResUId()).enqueue(new Callback<ReturnControl>() {
                                    @Override
                                    public void onResponse(Call<ReturnControl> call, Response<ReturnControl> response) {
                                        getListKategories(typestory,story.getStoryId());
                                    }

                                    @Override
                                    public void onFailure(Call<ReturnControl> call, Throwable t) {

                                    }
                                });
                            }else{
                                Toast.makeText(getActivity(), "düzgün tarih formatı girin", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    build.setNegativeButton("iptal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    build.show();
                }

                @Override
                public void onFailure(Call<ReturnUser> call, Throwable t) {

                }
            });

    }



}