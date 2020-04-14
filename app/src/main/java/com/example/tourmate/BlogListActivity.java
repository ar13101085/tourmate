package com.example.tourmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tourmate.adapter.BlogItemRecyclerAdapter;
import com.example.tourmate.databinding.ActivityBlogListBinding;
import com.example.tourmate.model.db.Blog;
import com.example.tourmate.utility.DB;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmResults;

public class BlogListActivity extends AppCompatActivity {

    BlogItemRecyclerAdapter adapter;
    ArrayList<Blog> blogs=new ArrayList<>();
    ActivityBlogListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_blog_list);
        adapter=new BlogItemRecyclerAdapter(blogs,this);
        LinearLayoutManager manager=new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
        binding.addBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BlogListActivity.this, BlogDetailsActivity.class);
                intent.putExtra("type","ADD");
                startActivity(intent);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void loadData() {
        blogs.clear();
        RealmResults<Blog> blogResult= DB.getRealm().where(Blog.class).findAll();
        //RealmList<Blog> blogList=new RealmList<>();
        DB.getRealm().beginTransaction();
        blogs.addAll( DB.getRealm().copyFromRealm(blogResult));
        DB.getRealm().commitTransaction();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
