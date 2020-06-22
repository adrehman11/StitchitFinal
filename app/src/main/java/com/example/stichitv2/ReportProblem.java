package com.example.stichitv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ReportProblem extends AppCompatActivity  {

    private ImageView back_btn_reportproblme;
    private Button sendreq;
    private ImageView addimages,abc;
    private EditText edit1;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    private final int IMG_REQUEST =1;
    private Bitmap bitmap;
    private RadioGroup typeofsubmit;
    String urli = Config.url;
    String temp  = urli+"editprofile/reportproblem";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);

        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_reportproblme = findViewById(R.id.back_btn_reportproblem);
        sendreq                = findViewById(R.id.abc);
        addimages              = findViewById(R.id.addimgetoreport);
        edit1        = findViewById(R.id.edittexts);
        abc=findViewById(R.id.addimgetoreport);

        Intent intent           = getIntent();
       String  screen =intent.getStringExtra("screen_name");
        queue= Volley.newRequestQueue(ReportProblem.this);
        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_reportproblme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),HelpandSupport.class);
                startActivity(intent);
            }
        });
        typeofsubmit = findViewById(R.id.usertype_radioGroup);
        sendreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton type = findViewById(typeofsubmit.getCheckedRadioButtonId());
                final String typeofsubmit = type.getText().toString();
                String discription =edit1.getText().toString();

                try {
                    if(screen.equals("Tailor"))
                    {
                        post_data.put("id", Home_Tailor.user_id);
                        post_data.put("utype", Home_Tailor.utype);
                    }
                    else
                    {
                        post_data.put("id", Home_Customer.user_id);
                        post_data.put("utype", Home_Customer.utype);
                    }

                    post_data.put("type", typeofsubmit);
                    post_data.put("image", imagetostring(bitmap));
                    post_data.put("description", discription);
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            try {
                                message = response.getString("message");
                                if(message.equals("Done") &&  screen.equals("Tailor"))
                                {

                                    Intent intent = new Intent(ReportProblem.this,Home_Tailor.class);
                                    startActivity(intent);
                                }
                                else if(message.equals("Done") &&  screen.equals("Customer"))
                                {
                                    Intent intent = new Intent(ReportProblem.this,Home_Customer.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(ReportProblem.this,"Try Again",Toast.LENGTH_LONG).show();
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

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });


        addimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    selectimage();

            }
        });


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
                abc.setImageBitmap(bitmap);

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
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }

}
