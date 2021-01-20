package com.example.ysp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ysp.classes.*;
import com.example.ysp.classes.User;
import com.example.ysp.dbconnect.ApiUtils;
import com.example.ysp.dbconnect.dbInterface;
import com.google.android.material.tabs.TabLayout;
import com.example.ysp.fragment.*;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class contentActivity extends AppCompatActivity implements View.OnDragListener{

    private ArrayList<TodoFragment> flist= new ArrayList<>();
    private ArrayList<String> tlist= new ArrayList<>();
    Story story;
    User user;
    LinearLayout leftLin,rightLin;
     /**
    *internetten veri alışverişi yaptığım kısım
    */
    dbInterface dbDIF= ApiUtils.getdbInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        /**
        *değişkenlere verileri atıyorum
        */
        story = (Story) getIntent().getSerializableExtra("story");
        user = (User) getIntent().getSerializableExtra("user");
        leftLin = findViewById(R.id.leftLin);
        rightLin = findViewById(R.id.rightLin);
        ViewPager viewPager = findViewById(R.id.vp2);
        TabLayout tabLayout = findViewById(R.id.tb);

        /**
        *sürüklenince bırakılıcak yerin tanımı ve işlevi
        */
        leftLin.setTag("-");
        leftLin.setOnDragListener(this);

        rightLin.setTag("+");
        rightLin.setOnDragListener(this);


        /**
        *todo in progress ve done  ksımları için tek fragment  farklı işlem olmasu için  her seferinde farklı type atıyorum 
        */
        flist= new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putSerializable("story",story );
        bundle.putSerializable("typestory","1" );

        TodoFragment todoFragment=new TodoFragment();
        todoFragment.setArguments(bundle);
        flist.add(todoFragment);

        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("story",story );
        bundle2.putSerializable("typestory","2" );

        TodoFragment todoFragment2=new TodoFragment();
        todoFragment2.setArguments(bundle2);
        flist.add(todoFragment2);


        Bundle bundle3 = new Bundle();
        bundle3.putSerializable("story",story );
        bundle3.putSerializable("typestory","3" );
        TodoFragment todoFragment3=new TodoFragment();
        todoFragment3.setArguments(bundle3);
        /**
        *tab layoutların isimlendirilmesi
        */
        flist.add(todoFragment3);
        tlist.add("To Do");
        tlist.add("İn progress");
        tlist.add("Done");
        /**
        *pageadapter oluşturulnöasuı
        */
        PagerAdapter adapter = new PagerAdapter(tlist, flist,getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        /**
        *fragmentler değişince gelen fragmenttte verilerin güncellenemsi için
        */
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

        /**
        *geri gittiğimde verilerin güncellenemsi için yeni mir MainActivity çağırıyorum 
        */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,MainActivity.class);
        dbDIF.getUser("getUser",story.getStoryCraUId()).enqueue(new Callback<ReturnUser>() {
            @Override
            public void onResponse(Call<ReturnUser> call, Response<ReturnUser> response) {
                User u=response.body().getUser().get(0);
                i.putExtra("user", u);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<ReturnUser> call, Throwable t) {

            }
        });

    }


        /**
        *sürüklenince çalışacak fonksiyon
        */
    boolean location=true;
    public boolean onDrag(View v, DragEvent Dragevent) {
        switch (Dragevent.getAction()){
            case DragEvent.ACTION_DRAG_ENDED:
            case DragEvent.ACTION_DRAG_EXITED:
                //v.getBackground().clearColorFilter();
               // v.invalidate();
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                //v.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
               // v.invalidate();
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DROP:
                //v.getBackground().clearColorFilter();
                //v.invalidate();

                /**
                *görsel nesnenin verisini alıyorum 
                */
                View gorselnesne = (View)Dragevent.getLocalState();
                Log.e("gösnetne", gorselnesne.getTag().toString() );
                Log.e("TAG", v.getTag()+"" );
                if(v.getTag().equals("+")){
                    location=true;
                }else{
                    location=false;

                }

                /**
                *nereye bırakıldıysa bırakıldığüı kısma göre işlem ilerleme veya gerileme 
                */
                dbDIF.getCategory("getSCategories",gorselnesne.getTag().toString()).enqueue(new Callback<ReturnCategory>() {
                    @Override
                    public void onResponse(Call<ReturnCategory> call, Response<ReturnCategory> response) {
                        Category k= response.body().getCategories().get(0);
                        if(k.getCategoryType().equals("1") &&  location==false ||  k.getCategoryType().equals("3") && location==true){


                        }else{
                            ViewGroup eskitasarimalani= (ViewGroup)gorselnesne.getParent();
                            String s="+";
                            if(location){
                                s="+";
                            }else{
                                s="-";
                            }
                            dbDIF.changeCayegoryType("nextOrProvius",s,gorselnesne.getTag().toString()).enqueue(new Callback<ReturnControl>() {
                                @Override
                                public void onResponse(Call<ReturnControl> call, Response<ReturnControl> response) {
                                    Log.e("TAG", "next " );
                                }

                                @Override
                                public void onFailure(Call<ReturnControl> call, Throwable t) {

                                }
                            });
                            eskitasarimalani.removeView(gorselnesne);
                        }
                    }

                    @Override
                    public void onFailure(Call<ReturnCategory> call, Throwable t) {

                    }
                });

               /* LinearLayout hedeftasarimalani=(LinearLayout)v;
                hedeftasarimalani.addView(gorselnesne);
                gorselnesne.setVisibility(View.VISIBLE);*/
                break;
            default:
                return false;
        }

        return true;
    }



        /**
        * page adapter oluşuturoyuym FragmentPagerAdapter sınıfını kaılıtm alıyorum eski teknoloji pageviewr  sınıfıbu kullanım için !
        */
    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(ArrayList<String> titleList, ArrayList<TodoFragment> fragmentsList, @NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            tlist = titleList;
            flist = fragmentsList;
        }

        /**
        *geçiş yapılacak fragment pozisyonları
        */
        @NonNull
        @Override
        public Fragment getItem(int position) {
            Log.e("test12", "test2" );

            return flist.get(position);
        }
        @Override
        public int getItemPosition(Object object) {
         /**
         * POSITION_NONE makes it possible to reload the PagerAdapter
         */
            return POSITION_NONE;
        }
        @Override
        public int getCount() {
            return flist.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            Log.e("test13", "test3" );

            return tlist.get(position);
        }
    }
}