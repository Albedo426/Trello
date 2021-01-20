package com.example.ysp.adapters;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ysp.R;
import com.example.ysp.classes.*;
import com.example.ysp.dbconnect.ApiUtils;
import com.example.ysp.dbconnect.dbInterface;
import com.example.ysp.fragment.TodoFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class kategoriesAdapter extends RecyclerView.Adapter<kategoriesAdapter.viewHolder>  implements View.OnLongClickListener{

    /**
    *listte gelen veriler contexte  veri aldığım activity var
    */
   
    dbInterface dbDIF= ApiUtils.getdbInterface();
    List<Category> list;
    Context context;
    public kategoriesAdapter(List<Category> list, Context context) {
        this.list = list;
        this.context = context;
    }
     /**
    *tasarımın olduğ yer
    */
    @Override
    public boolean onLongClick(View v) {

        //kaydırma işlemi için
        ClipData.Item item= new ClipData.Item((CharSequence) v.getTag());

        String []mimitype={ClipDescription.MIMETYPE_TEXT_PLAIN};

        ClipData clipData= new ClipData(v.getTag().toString(),mimitype,item);

        View.DragShadowBuilder dragShadowBuilder= new View.DragShadowBuilder(v);
        //kaydırma /hareket ettirme başlasın
        v.startDrag(clipData,dragShadowBuilder,v,0);
        //v.setVisibility(View.INVISIBLE);
        return true;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //cartı seçiyorum
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_card,parent,false);
        return new viewHolder(v);
    }
 /**
    *listedeki her eleman içinn  cartların dolduğu yer
    */
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Category s=list.get(position);
        /**
         *@param s her card  viewımızın içini dolduracak olduğumuz veri listedeki x eleman
         */
        Log.e("text", s.getCategoryText()+"" );
        holder.cv.setTag(s.getCategoryId()+"");
        dbDIF.getUser("getUser",s.getCategoryResUId()).enqueue(new Callback<ReturnUser>() {
            @Override
            public void onResponse(Call<ReturnUser> call, Response<ReturnUser> response) {
                holder.kategorireponsiv.setText(response.body().getUser().get(0).getUserMail()) ;
            }

            @Override
            public void onFailure(Call<ReturnUser> call, Throwable t) {

            }
        });
        holder.cv.setOnLongClickListener(this);
        holder.kategorititle.setText(s.getCategoryTitle());
        holder.kategoridate.setText(s.getFinishDate());
        holder.kategoritext.setText(s.getCategoryText().toString());
        if(s.getCategoryType().equals("1")){
            holder.imageprevious.setVisibility(View.INVISIBLE);
        }
        if(s.getCategoryType().equals("3")){
            holder.imagenext.setVisibility(View.INVISIBLE);
        }
        holder.imageprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typechangeer("-" ,s.getCategoryId(),position);

            }
        });
        holder.imagenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typechangeer("+" ,s.getCategoryId(),position);
            }
        });
    }
      /**
    * taskımı sonraki veya önceki işleme alma
    */
    public void typechangeer(String  operation ,String  Id,int position){
        dbDIF.changeCayegoryType("nextOrProvius",operation,Id).enqueue(new Callback<ReturnControl>() {
            @Override
            public void onResponse(Call<ReturnControl> call, Response<ReturnControl> response) {
                Log.e("TAG", "next " );
                list.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReturnControl> call, Throwable t) {

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
        TextView kategorititle;
        TextView kategoritext;
        TextView kategorireponsiv;
        TextView kategoridate;
        ImageView imagenext;
        ImageView imageprevious;
        CardView cv;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            kategorititle=itemView.findViewById(R.id.kategorisname);
            kategoritext=itemView.findViewById(R.id.kategoritxt);
            kategorireponsiv=itemView.findViewById(R.id.kategoriResponsiv);
            kategoridate=itemView.findViewById(R.id.kategoriesDate);
            imageprevious=itemView.findViewById(R.id.imgprevious);
            imagenext=itemView.findViewById(R.id.imgnext);
            cv=itemView.findViewById(R.id.CV);
        }
    }
}
