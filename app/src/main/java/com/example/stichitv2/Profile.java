package com.example.stichitv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Profile extends AppCompatActivity  {

    private ImageView back_btn_profile, profile_to_setting_btn, tailorwork,addprofileimg;
    private LinearLayout shalwarkamez,Peshwas12,Saree1,Frock1,lahenga1,Kurti1,Men_Shalwar_Kameez1,Sherwani1,Kurta1;
    private LinearLayout addwork;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    private final int IMG_REQUEST =1;
    private Bitmap bitmap;
    ImageView imageView,tailorwork1,tailorwork2,tailorwork3,tailorwork4,tailorwork5,tailorwork6;
    Boolean tag = false;
TextView rating;
    ArrayList<TailorWork> tailorswork;

    String urli = Config.url;
    String temp  = urli+"test/picture";
    String temp1  = urli+"test";
     ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog = new ProgressDialog((Profile.this));
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        tailorswork = new ArrayList<TailorWork>();

        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_profile        =  findViewById(R.id.back_btn_profile);
        profile_to_setting_btn  =  findViewById(R.id.profile_to_setting_btn);
        addwork                 = findViewById(R.id.addwork);
        addprofileimg           = findViewById(R.id.addprofileimg);

        imageView = findViewById(R.id.imageview);
        tailorwork1 = findViewById(R.id.tailorwork1);
        tailorwork2 = findViewById(R.id.tailorwork2);
        tailorwork3 = findViewById(R.id.tailorwork3);
        tailorwork4 = findViewById(R.id.tailorwork4);
        tailorwork5 = findViewById(R.id.tailorwork5);
        tailorwork6 = findViewById(R.id.tailorwork6);
        tailorwork1.setVisibility(View.GONE);
        tailorwork2.setVisibility(View.GONE);
        tailorwork3.setVisibility(View.GONE);
        tailorwork4.setVisibility(View.GONE);
        tailorwork5.setVisibility(View.GONE);
        tailorwork6.setVisibility(View.GONE);
        rating= findViewById(R.id.value);
        rating.setText(Home_Tailor.ratingvalue);
        final TextView Name = findViewById(R.id.textView4);
        final TextView Contact = findViewById(R.id.textView3);
        final TextView Address = findViewById(R.id.locationInProfile);
        final TextView shalwarkamez1 = findViewById(R.id.expertise);
        final TextView Peshwas121 = findViewById(R.id.Peshwas1);
        final TextView Saree123 = findViewById(R.id.expertise3);
        final TextView Frock11 = findViewById(R.id.expertise4);
        final TextView lahenga11 = findViewById(R.id.expertise11);
        final TextView Kurti11 = findViewById(R.id.expertise12);
        final TextView Men_Shalwar_Kameez11 = findViewById(R.id.expertise13);
        final TextView Sherwani11 = findViewById(R.id.expertise14);
        final TextView Kurta11 = findViewById(R.id.expertise18);

        final TextView shalwarkamez1price = findViewById(R.id.Shalwar_Kameez);
        final TextView Peshwas121price = findViewById(R.id.Peshwas);
        final TextView Saree123price = findViewById(R.id.Saree43);
        final TextView Frock11price = findViewById(R.id.Frock);
        final TextView lahenga11price = findViewById(R.id.lahenga);
        final TextView Kurti11price = findViewById(R.id.Kurti);
        final TextView Men_Shalwar_Kameez11price = findViewById(R.id.Men_Shalwar_Kameez);
        final TextView Sherwani11price = findViewById(R.id.Sherwani);
        final TextView Kurta11price = findViewById(R.id.Kurta);

        shalwarkamez= findViewById(R.id.Shalwar_Kameez1);
        Peshwas12= findViewById(R.id.Peshwas12);
        Saree1= findViewById(R.id.Saree1);
        Frock1= findViewById(R.id.Frock1);
        lahenga1= findViewById(R.id.lahenga1);
        Kurti1= findViewById(R.id.Kurti1);
        Men_Shalwar_Kameez1= findViewById(R.id.Men_Shalwar_Kameez1);
        Sherwani1= findViewById(R.id.Sherwani1);
        Kurta1= findViewById(R.id.Kurta1);

        shalwarkamez.setVisibility(View.GONE);
        Peshwas12.setVisibility(View.GONE);
        Saree1.setVisibility(View.GONE);
        Frock1.setVisibility(View.GONE);
        lahenga1.setVisibility(View.GONE);
        Kurti1.setVisibility(View.GONE);
        Men_Shalwar_Kameez1.setVisibility(View.GONE);
        Sherwani1.setVisibility(View.GONE);
        Kurta1.setVisibility(View.GONE);


        queue= Volley.newRequestQueue(Profile.this);
        back_btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,Home_Tailor.class);
                startActivity(intent);
            }
        });

        //     NAVIGATE BACK TO SETTINGS SCREEN
        profile_to_setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,Settings.class);
                intent.putExtra("screen_name","Tailor");
                startActivity(intent);
            }
        });

        addwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,TailorAddWork.class);
                startActivity(intent);
            }
        });
        final SharedPreferences preferences = getSharedPreferences("profile",MODE_PRIVATE);

        tailorwork1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String imagesend = tailorswork.get(0).image;
                String discriptionsend = tailorswork.get(0).description;

                Intent intent = new Intent(Profile.this,ViewTailorWork.class);
                SharedPreferences.Editor editor =preferences.edit();
                editor.putString("image",imagesend);
                editor.apply();
                intent.putExtra("screen","profile");
                intent.putExtra("discription",discriptionsend);
                startActivity(intent);
            }
        });
        tailorwork2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imagesend = tailorswork.get(1).image;
                String discriptionsend = tailorswork.get(1).description;
                Intent intent = new Intent(Profile.this,ViewTailorWork.class);
                SharedPreferences.Editor editor =preferences.edit();
                editor.putString("image",imagesend);
                editor.apply();
                intent.putExtra("screen","profile");
                intent.putExtra("discription",discriptionsend);
                startActivity(intent);
            }
        });
        tailorwork3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imagesend = tailorswork.get(2).image;
                String discriptionsend = tailorswork.get(2).description;
                Intent intent = new Intent(Profile.this,ViewTailorWork.class);
                SharedPreferences.Editor editor =preferences.edit();
                editor.putString("image",imagesend);
                editor.apply();;
                intent.putExtra("screen","profile");
                intent.putExtra("discription",discriptionsend);
                startActivity(intent);
            }
        });
        tailorwork4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imagesend = tailorswork.get(3).image;
                String discriptionsend = tailorswork.get(3).description;
                Intent intent = new Intent(Profile.this,ViewTailorWork.class);
                SharedPreferences.Editor editor =preferences.edit();
                editor.putString("image",imagesend);
                editor.apply();
                intent.putExtra("screen","profile");
                intent.putExtra("discription",discriptionsend);
                startActivity(intent);
            }
        });
        tailorwork5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imagesend = tailorswork.get(4).image;
                String discriptionsend = tailorswork.get(4).description;
                Intent intent = new Intent(Profile.this,ViewTailorWork.class);
                SharedPreferences.Editor editor =preferences.edit();
                editor.putString("image",imagesend);
                editor.apply();
                intent.putExtra("screen","profile");
                intent.putExtra("discription",discriptionsend);
                startActivity(intent);
            }
        });
        tailorwork6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imagesend = tailorswork.get(5).image;
                String discriptionsend = tailorswork.get(5).description;
                Intent intent = new Intent(Profile.this,ViewTailorWork.class);
                SharedPreferences.Editor editor =preferences.edit();
                editor.putString("image",imagesend);
                editor.apply();
                intent.putExtra("screen","profile");
                intent.putExtra("discription",discriptionsend);
                startActivity(intent);
            }
        });


        // UPLOAD IMAGE
        addprofileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectimage();


            }
        });


        try {
            post_data.put("id",Home_Tailor.user_id);
            post_data.put("utype",Home_Tailor.utype);
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d("Rehman", "onresp" + response.toString());
                    String name =null;
                    String contact =null;
                    String image =null;
                    String address=null;

                    try {
                        name = response.getString("name");
                        contact = response.getString("contact");

                        image = response.getString("image");
                        address = response.getString("address");
                        JSONArray Dressarray = response.getJSONArray("DressName");
                        JSONArray Dressprice = response.getJSONArray("DressPrice");

                        for(int i=0;i<Dressarray.length();i++)
                        {

                            if (i==0)
                            {
                                shalwarkamez.setVisibility(View.VISIBLE);
                                shalwarkamez1.setText(Dressarray.getString(i));
                                shalwarkamez1price.setText(Dressprice.getString(i));
                            }
                            else if(i==1)
                            {
                                Peshwas12.setVisibility(View.VISIBLE);
                                Peshwas121.setText(Dressarray.getString(i));
                                Peshwas121price.setText(Dressprice.getString(i));
                            }
                            else if(i==2)
                            {
                                Kurta1.setVisibility(View.VISIBLE);
                                Kurta11.setText(Dressarray.getString(i));
                                Kurta11price.setText(Dressprice.getString(i));
                            }
                            else if(i==3)
                            {
                                Saree1.setVisibility(View.VISIBLE);
                                Saree123.setText(Dressarray.getString(i));
                                Saree123price.setText(Dressprice.getString(i));
                            }
                            else if(i==4)
                            {
                                Frock1.setVisibility(View.VISIBLE);
                                Frock11.setText(Dressarray.getString(i));
                                Frock11price.setText(Dressprice.getString(i));
                            }
                            else if(i==5)
                            {
                                lahenga1.setVisibility(View.VISIBLE);
                                lahenga11.setText(Dressarray.getString(i));
                                lahenga11price.setText(Dressprice.getString(i));
                            }
                            else if(i==6)
                            {
                                Men_Shalwar_Kameez1.setVisibility(View.VISIBLE);
                                Men_Shalwar_Kameez11.setText(Dressarray.getString(i));
                                Men_Shalwar_Kameez11price.setText(Dressprice.getString(i));
                            }
                            else if(i==7)
                            {
                                Kurti1.setVisibility(View.VISIBLE);
                                Kurti11.setText(Dressarray.getString(i));
                                Kurti11price.setText(Dressprice.getString(i));
                            }
                            else if(i==8)
                            {
                                Sherwani1.setVisibility(View.VISIBLE);
                                Sherwani11.setText(Dressarray.getString(i));
                                Sherwani11price.setText(Dressprice.getString(i));
                            }



                        }


                        //worktailor
                        if (response.getString("message").equals("ok1")) {
                            JSONArray array = response.getJSONArray("resData");
                            {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject resdata = array.getJSONObject(i);
                                    String id = resdata.getString("_id");
                                    String description = resdata.getString("description");
                                    String image1 = resdata.getString("image");
                                    TailorWork temp_t = new TailorWork(id, description, image1);
                                    tailorswork.add(temp_t);

                                }
                            }
                            for(int i=0;i<tailorswork.size();i++) {
                                String imagebit =tailorswork.get(i).image;

                                byte[] decodedString = Base64.decode(imagebit, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                if(i==0)
                                {
                                    tailorwork1.setImageBitmap(decodedByte);
                                    tailorwork1.setVisibility(View.VISIBLE);
                                }
                                else if(i==1)
                                {
                                    tailorwork2.setImageBitmap(decodedByte);
                                    tailorwork2.setVisibility(View.VISIBLE);
                                }
                                else if(i==2)
                                {
                                    tailorwork3.setImageBitmap(decodedByte);
                                    tailorwork3.setVisibility(View.VISIBLE);
                                }
                                else if(i==3)
                                {
                                    tailorwork4.setImageBitmap(decodedByte);
                                    tailorwork4.setVisibility(View.VISIBLE);
                                }
                                else if (i==4)
                                {
                                    tailorwork5.setImageBitmap(decodedByte);
                                    tailorwork5.setVisibility(View.VISIBLE);
                                }
                                else if (i==5) {
                                    tailorwork6.setImageBitmap(decodedByte);
                                    tailorwork6.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                        Name.setText(name);
                        Contact.setText(contact);
                        Address.setText(address);

                        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView.setImageBitmap(decodedByte);
                        imageView.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();


                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Profile.this, "Check your Connection", Toast.LENGTH_LONG).show();
                }
            });
            queue.add(getRequest);

        } catch (JSONException e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }




    }


    private void selectimage()
    {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                try {
                    post_data.put("id", Home_Tailor.user_id);
                    post_data.put("utype", Home_Tailor.utype);

                    post_data.put("image", imagetostring(bitmap));

                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp1, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            try {
                                message = response.getString("message");
                                if (message.equals("uploaded")) {
                                    Toast.makeText(Profile.this, "picture uploaded", Toast.LENGTH_LONG).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    queue.add(getRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tag=true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private String imagetostring(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        Log.d("asd", Base64.encodeToString(imgBytes,Base64.DEFAULT));
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
}
