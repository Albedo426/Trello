package com.example.ysp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ysp.*;
import com.example.ysp.classes.*;
import com.example.ysp.dbconnect.ApiUtils;
import com.example.ysp.dbconnect.dbInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class storiesAdapter extends RecyclerView.Adapter<storiesAdapter.viewHolder>  {

    /**
    *listte gelen veriler contexte  veri aldığım activity var
    */
    List<Story> list;
    Context context;
    dbInterface dbDIF= ApiUtils.getdbInterface();;
    public storiesAdapter(List<Story> list, Context context) {
        this.list = list;
        this.context = context;
    }
    /**
    *tasarımın olduğ yer
    */
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.stories_card,parent,false);
        return new viewHolder(v);
    }
    /**
    *listedeki her eleman içinn  cartların dolduğu yer
    */
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Story story=list.get(position);
        holder.storiesName.setText(story.getStoryText());
        holder.kategoriText.setText(story.getStoryTitle());
        dbDIF.getCategoryCount("getSCategoriescount", story.getStoryId()).enqueue(new Callback<ReturnControl>() {
            @Override
            public void onResponse(Call<ReturnControl> call, Response<ReturnControl> response) {
                holder.estimatedDate.setText(response.body().getControl()+"gün");
            }

            @Override
            public void onFailure(Call<ReturnControl> call, Throwable t) {

            }
        });
        holder.storiesDate.setText(story.getStoryCraDate());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), contentActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("story",  story);
                i.putExtra("user",  story.user);
                context.startActivity(i);
            }
        });
    }
 
    @Override
    public int getItemCount() {
        return list.size();
    }
    /**
    *cardların verilerinin değişkenlşere atıldığı ksıım
    */
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView storiesName;
        TextView kategoriText;
        TextView storiesDate;
        TextView estimatedDate;
        CardView cv;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            storiesDate=itemView.findViewById(R.id.storiesDate);
            storiesName=itemView.findViewById(R.id.kategoriTitle);
            kategoriText=itemView.findViewById(R.id.kategoriText);
            estimatedDate=itemView.findViewById(R.id.estimatedDate);
            cv=itemView.findViewById(R.id.CV);
        }
    }
}
