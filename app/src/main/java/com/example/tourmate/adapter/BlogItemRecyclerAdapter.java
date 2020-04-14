package com.example.tourmate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate.BlogDetailsActivity;
import com.example.tourmate.R;
import com.example.tourmate.databinding.BlogItemRowBinding;
import com.example.tourmate.model.db.Blog;
import com.example.tourmate.utility.DB;
import com.google.gson.Gson;

import java.util.ArrayList;

public class BlogItemRecyclerAdapter extends RecyclerView.Adapter<BlogItemRecyclerAdapter.VH> {

    ArrayList<Blog> blogs;
    Context context;

    public BlogItemRecyclerAdapter(ArrayList<Blog> blogs, Context context) {
        this.blogs = blogs;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.blog_item_row,parent,false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Blog blog=blogs.get(position);
        holder.binding.mainText.setText(blog.text);
        holder.binding.dateText.setText(blog.dateTime);
        holder.binding.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson=new Gson();
                Intent intent=new Intent(context, BlogDetailsActivity.class);
                intent.putExtra("type","DETAILS");
                intent.putExtra("details",gson.toJson(blog));
                context.startActivity(intent);
            }
        });

        holder.binding.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu=new PopupMenu(context,holder.binding.dateText);
                menu.getMenu().add("EDIT");
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().toString().equalsIgnoreCase("EDIT")){
                            Gson gson=new Gson();
                            Intent intent=new Intent(context, BlogDetailsActivity.class);
                            intent.putExtra("type","EDIT");
                            intent.putExtra("details",gson.toJson(blog));
                            context.startActivity(intent);
                        }else{
                            blogs.remove(position);
                            DB.getRealm().beginTransaction();
                            DB.getRealm().where(Blog.class).equalTo("id",blog.id).findAll().deleteAllFromRealm();
                            DB.getRealm().commitTransaction();
                            Toast.makeText(context, "Successfully remove", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                        return true;
                    }
                });
                menu.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        BlogItemRowBinding binding;
        public VH(@NonNull View itemView) {
            super(itemView);
            binding=BlogItemRowBinding.bind(itemView);
        }
    }
}
