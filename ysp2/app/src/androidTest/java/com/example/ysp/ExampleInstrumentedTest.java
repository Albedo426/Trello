package com.example.ysp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.ysp.classes.ReturnControl;
import com.example.ysp.classes.ReturnUser;
import com.example.ysp.classes.User;
import com.example.ysp.dbconnect.ApiUtils;
import com.example.ysp.dbconnect.dbInterface;

import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    dbInterface dbDIF= ApiUtils.getdbInterface();;
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.ysp", appContext.getPackageName());
    }
    @Test
     public void test1(){
         dbDIF.getCategoryCount("getSCategoriescount", "1").enqueue(new Callback<ReturnControl>() {
             @Override
             public void onResponse(Call<ReturnControl> call, Response<ReturnControl> response) {
                 assertEquals("6", response.body().getControl());
             }

             @Override
             public void onFailure(Call<ReturnControl> call, Throwable t) {

             }
         });
     }
    @Test
     //tarih format kontrolü
     public void test2(){
        String[] arrOfStr = "20.20.2020".split("\\.");
         assertEquals(3, arrOfStr.length);

     }
    @Test
    public void test3(){
        dbDIF.getCategoryCount("getSCategoriescount", "2").enqueue(new Callback<ReturnControl>() {
            @Override
            public void onResponse(Call<ReturnControl> call, Response<ReturnControl> response) {
                assertEquals("2", response.body().getControl());
            }

            @Override
            public void onFailure(Call<ReturnControl> call, Throwable t) {

            }
        });
    }
    @Test
    public void test4(){
        dbDIF.getCategoryCount("getSCategoriescount", "3").enqueue(new Callback<ReturnControl>() {
            @Override
            public void onResponse(Call<ReturnControl> call, Response<ReturnControl> response) {
                assertEquals("0", response.body().getControl());
            }

            @Override
            public void onFailure(Call<ReturnControl> call, Throwable t) {

            }
        });
    }
    @Test
    public void test5(){
        dbDIF.getUser("getUser","1").enqueue(new Callback<ReturnUser>() {
            @Override
            public void onResponse(Call<ReturnUser> call, Response<ReturnUser> response) {
                User u=response.body().getUser().get(0);
                assertEquals("1", u.getUserId());

            }

            @Override
            public void onFailure(Call<ReturnUser> call, Throwable t) {

            }
        });

    }
}