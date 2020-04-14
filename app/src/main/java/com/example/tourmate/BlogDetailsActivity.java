package com.example.tourmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tourmate.adapter.SliderAdapterExample;
import com.example.tourmate.databinding.CreateOrUpdateBlogBinding;
import com.example.tourmate.model.Sys;
import com.example.tourmate.model.db.Blog;
import com.example.tourmate.utility.DB;
import com.google.gson.Gson;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static java.security.AccessController.getContext;

public class BlogDetailsActivity extends AppCompatActivity {

    ArrayList<String> allImage=new ArrayList<>();
    SliderAdapterExample adapter;
    Blog blog;
    CreateOrUpdateBlogBinding binding;
    boolean isEditMode=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.create_or_update_blog);
        adapter=new SliderAdapterExample(this,allImage);
        String type = getIntent().getStringExtra("type");
        if(type.equalsIgnoreCase("DETAILS")){
            String data=getIntent().getStringExtra("details");
            Gson gson=new Gson();
            blog=gson.fromJson(data,Blog.class);
            viewDetails(blog);
        }else if(type.equalsIgnoreCase("EDIT")){
            isEditMode=true;
            String data=getIntent().getStringExtra("details");
            Gson gson=new Gson();
            blog=gson.fromJson(data,Blog.class);
            editDetails(blog);
        }

        EasyImage
                .configuration(this)
                .setImagesFolderName("AOS");


        binding.imageSlider.setSliderAdapter(adapter);
        binding.imageSlider.setScrollTimeInSec(5);

        initListenner();
        permisionChecker();
    }

    private void permisionChecker() {
        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }

    private void initListenner() {
        binding.addOrUpdateBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.addOrUpdateBlog.getText().toString().equalsIgnoreCase("UPDATE")){

                    blog.imgList.clear();
                    blog.dateTime=getCurrentTimeStamp();
                    blog.imgList.addAll(allImage);
                    blog.text=binding.blogContent.getText().toString();

                    DB.getRealm().beginTransaction();
                    DB.getRealm().insertOrUpdate(blog);
                    DB.getRealm().commitTransaction();
                    Toast.makeText(BlogDetailsActivity.this, "Successfully update...", Toast.LENGTH_SHORT).show();
                    clearEverything();


                }else{
                    Blog blog=new Blog(System.currentTimeMillis(),getCurrentTimeStamp(),binding.blogContent.getText().toString());
                    blog.imgList.addAll(allImage);
                    DB.getRealm().beginTransaction();
                    DB.getRealm().insertOrUpdate(blog);
                    DB.getRealm().commitTransaction();
                    Toast.makeText(BlogDetailsActivity.this, "Successfully Added...", Toast.LENGTH_SHORT).show();
                    clearEverything();

                }
            }
        });

        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openCamera(BlogDetailsActivity.this,1);
            }
        });

        binding.gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openGallery(BlogDetailsActivity.this,1);
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void clearEverything(){
        binding.blogContent.setText("");
        allImage.clear();
        adapter.notifyDataSetChanged();
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
    }


    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {

            }

            @Override
            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                for (File file: imagesFiles
                     ) {
                    allImage.add(file.toString());
                }
                adapter.notifyDataSetChanged();
            }

        });
    }

    private void viewDetails(Blog blog) {
        System.out.println("View Details call "+blog.imgList.size());
//        ViewGroup.LayoutParams params=binding.actionContainer.getLayoutParams();
//        params.height=0;
//        binding.actionContainer.setLayoutParams(params);

        allImage.addAll(blog.imgList);
        binding.blogContent.setText(blog.text);
        adapter.notifyDataSetChanged();

    }

    private void editDetails(Blog blog) {
//        ViewGroup.LayoutParams params=binding.actionContainer.getLayoutParams();
//        params.height=0;
        allImage.addAll(blog.imgList);
        binding.blogContent.setText(blog.text);
        adapter.notifyDataSetChanged();
        binding.addOrUpdateBlog.setText("UPDATE");

    }
}
