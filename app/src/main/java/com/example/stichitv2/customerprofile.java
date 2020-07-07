package com.example.stichitv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class customerprofile extends AppCompatActivity {

    private ImageView back_btn_profile, profile_to_setting_btn,addprofileimg,profileimg;
    private TextView name;

    String urli = Config.url;
    String temp  = urli+"test/picture";
    String temp1  = urli+"test";
    ProgressDialog progressDialog;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    private final int IMG_REQUEST =1;
    private Bitmap bitmap;
    Boolean tag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerprofile);
        progressDialog = new ProgressDialog((customerprofile.this));
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_profile        =  findViewById(R.id.Customer_back_btn_profile);
        profile_to_setting_btn  =  findViewById(R.id.Customer_profile_to_setting_btn);
        addprofileimg           = findViewById(R.id.Customer_addprofileimg);
        profileimg              = findViewById(R.id.Customer_profileimg);

        final TextView Name = findViewById(R.id.Customer_Name);
        final TextView Contact = findViewById(R.id.Customer_phoneNumber);
        queue= Volley.newRequestQueue(customerprofile.this);

        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customerprofile.this,Home_Customer.class);
                startActivity(intent);
            }
        });

        //     NAVIGATE BACK TO SETTINGS SCREEN
        profile_to_setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customerprofile.this,Settings.class);
                intent.putExtra("screen_name","Customer");
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
            post_data.put("id",Home_Customer.user_id);
            post_data.put("utype",Home_Customer.utype);
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d("Rehman", "onresp" + response.toString());
                    String name =null;
                    String contact =null;
                    String image =null;
                 ;

                    try {
                        name = response.getString("name");
                        contact = response.getString("contact");

                        image = response.getString("image");

                        Name.setText(name);
                        Contact.setText("0"+contact);

                        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        profileimg.setImageBitmap(decodedByte);
                        profileimg.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(customerprofile.this, "Check your Connection", Toast.LENGTH_LONG).show();
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
                profileimg.setImageBitmap(bitmap);
                profileimg.setVisibility(View.VISIBLE);
                try {
                    post_data.put("id", Home_Customer.user_id);
                    post_data.put("utype", Home_Customer.utype);

                    post_data.put("image", imagetostring(bitmap));

                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp1, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            try {
                                message = response.getString("message");
                                if (message.equals("uploaded")) {
                                    Toast.makeText(customerprofile.this, "picture uploaded", Toast.LENGTH_LONG).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(customerprofile.this, "Check your Connection", Toast.LENGTH_LONG).show();
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