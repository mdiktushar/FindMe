package com.example.findme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<UserProfile>uploadList;

    private OnItemClickListener listener;

    public MyAdapter(Context context, ArrayList<UserProfile> uploadList) {
        this.context = context;
        this.uploadList = uploadList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserProfile userProfile = uploadList.get(position);
        holder.textView.setText(userProfile.userName);
        Picasso.get().load(userProfile.getImageUri()).placeholder(R.mipmap.ic_launcher).fit().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.cardTextViewID);
            imageView = itemView.findViewById(R.id.cardImageViewID);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener!=null){
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){

                    listener.onItemClick(position);
                }
            }
        }
    }

    public void filterList(ArrayList<UserProfile> filteredList) {
        uploadList = filteredList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);


    }

    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;
    }
}
