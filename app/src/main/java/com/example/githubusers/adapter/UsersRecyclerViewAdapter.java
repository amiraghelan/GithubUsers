package com.example.githubusers.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubusers.R;
import com.example.githubusers.UserProfileActivity;
import com.example.githubusers.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder> {

    private List<User> users;
    private Context context;

    public UsersRecyclerViewAdapter(Context context, List<User> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UsersRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.tvUserName.setText(users.get(position).getUserName());
        Picasso.get().load(users.get(position).getAvatarUrl()).placeholder(R.drawable.default_avatar).into(holder.imgAvatar);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgAvatar;
        public TextView tvUserName;
        public ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvUserName = itemView.findViewById(R.id.tv_userName);
            layout = itemView.findViewById(R.id.cl_row);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserProfileActivity.class);
                    intent.putExtra("id",users.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
