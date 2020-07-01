package com.example.stichitv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TailorNewOrderDetails extends AppCompatActivity {

    private ImageView back_btn_orderDetail;
    private TextView dresstype,ordersid,ordertype,oderdeadline,phone_no,orderDate,dresstype_2,tailorname
            ,shir_details,trouser_details,sticthtype,lace,pipeing,buttons,comment,shirt_length,Shirt_neck,
            Shirt_chest,Shirt_waist,Shirt_backwidthck,
            Shirt_Hips,Shirt_sleeevelenght,Shirt_Shoulder
            ,Shirt_QuaterSleeveLength,Shirt_wrist,price,
            trouser_length,trouser_calf,trouser_ankle;
    private Button accept,reject;
    private ImageView imageView;
    String orderID;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    String urli = Config.url;
    String temp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_new_order_details);
        progressDialog = new ProgressDialog((TailorNewOrderDetails.this));
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        Intent intent           = getIntent();
        orderID      = intent.getStringExtra("OID");

        queue= Volley.newRequestQueue(TailorNewOrderDetails.this);

        back_btn_orderDetail =  findViewById(R.id.back_btn_CustomerNewOrderDetail);

        back_btn_orderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),TailorNewOrder.class);
                startActivity(intent);
            }
        });
        accept =  findViewById(R.id.acceptOrder);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    post_data.put("id",Home_Tailor.user_id);
                    post_data.put("utype",Home_Tailor.utype);
                    post_data.put("oid",orderID);
                    String temp =urli+"order/accept";
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            try {
                                message = response.getString("message");
                                if (message.equals("orderaccepted")){
                                    Intent a  = new Intent(TailorNewOrderDetails.this,Messaging_service.class);
                                    a.putExtra("servicename","OrderAccepted");
                                    a.putExtra("OrderID",orderID);
                                    startService(a);
                                    Intent intent = new Intent(v.getContext(),TailorNewOrder.class);
                                    startActivity(intent);
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

            }
        });
        reject =  findViewById(R.id.rejectOrder);

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                try {
                    post_data.put("id",Home_Tailor.user_id);
                    post_data.put("utype",Home_Tailor.utype);
                    post_data.put("oid",orderID);
                    String temp = urli+"order/orderreject";
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            try {
                                message = response.getString("message");
                                if (message.equals("orderrejected")){
                                    Intent a  = new Intent(TailorNewOrderDetails.this,Messaging_service.class);
                                    a.putExtra("servicename","OrderRejected");
                                    a.putExtra("OrderID",orderID);
                                    startService(a);
                                    Intent intent = new Intent(v.getContext(),TailorNewOrder.class);
                                    startActivity(intent);
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

            }
        });
        imageView =  findViewById(R.id.csutomerneworder_image);

        shirt_length =  findViewById(R.id.cstomerneworder_Shirt_length);
        Shirt_neck = findViewById(R.id.cstomerneworder_Shirt_neck);
        Shirt_chest = findViewById(R.id.cstomerneworder_Shirt_chest);
        Shirt_waist = findViewById(R.id.cstomerneworder_Shirt_waist);
        Shirt_backwidthck = findViewById(R.id.cstomerneworder_Shirt_backwidth);
        Shirt_Hips = findViewById(R.id.cstomerneworder_Shirt_Hips);
        Shirt_sleeevelenght = findViewById(R.id.cstomerneworder_Shirt_sleeevelenght);
        Shirt_Shoulder = findViewById(R.id.cstomerneworder_Shirt_Shoulder);
        Shirt_QuaterSleeveLength = findViewById(R.id.cstomerneworder_Shirt_QuaterSleeveLength);
        Shirt_wrist = findViewById(R.id.cstomerneworder_Shirt_wrist);
        trouser_length = findViewById(R.id.cstomerneworder_trouser_length);
        trouser_calf = findViewById(R.id.cstomerneworder_trouser_calf);
        trouser_ankle = findViewById(R.id.cstomerneworder_trouser_ankle);

        dresstype =  findViewById(R.id.cstomerneworder_DressNameOnTop);
        ordersid = findViewById(R.id.cstomerneworder__DressID);
        ordertype = findViewById(R.id.cstomerneworder_deliverytype);
        oderdeadline = findViewById(R.id.cstomerneworder__expexteddate);
        tailorname = findViewById(R.id.cstomerneworder_tailorName);
        phone_no = findViewById(R.id.cstomerneworder_phonno);
        orderDate = findViewById(R.id.cstomerneworder__orderStarted);
        dresstype_2 = findViewById(R.id.cstomerneworder__DressName);
        shir_details = findViewById(R.id.cstomerneworder__shirtdetails);
        trouser_details = findViewById(R.id.cstomerneworder__trouserdetails);
        sticthtype = findViewById(R.id.cstomerneworder__stitchType);
        lace = findViewById(R.id.cstomerneworder___lace);
        pipeing = findViewById(R.id.cstomerneworder___piping);
        buttons = findViewById(R.id.cstomerneworder__buttons);
        comment= findViewById(R.id.cstomerneworder_comments);
        price= findViewById(R.id.cstomerneworder__expextedprice);

        try {
            post_data.put("id",Home_Tailor.user_id);
            post_data.put("utype",Home_Tailor.utype);
            post_data.put("oid",orderID);
            temp = urli+"order/myorder/requests/details";
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    String message = null;

                    try {
                        message = response.getString("message");
                        if (message.equals("details")){


                            byte[] decodedString = Base64.decode(response.getString("image"), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageView.setImageBitmap(decodedByte);


                            shirt_length.setText(response.getString("Shirt_length"));
                            Shirt_neck.setText(response.getString("Shirt_neck"));
                            Shirt_chest .setText(response.getString("Shirt_chest"));
                            Shirt_waist.setText(response.getString("Shirt_waist"));
                            Shirt_backwidthck .setText(response.getString("Shirt_backwidth"));
                            Shirt_Hips .setText(response.getString("Shirt_Hips"));
                            Shirt_sleeevelenght .setText(response.getString("Shirt_sleeevelenght"));
                            Shirt_Shoulder .setText(response.getString("Shirt_Shoulder"));
                            Shirt_QuaterSleeveLength .setText(response.getString("Shirt_QuaterSleeveLength"));
                            Shirt_wrist.setText(response.getString("Shirt_wrist"));
                            trouser_length .setText(response.getString("trouser_length"));
                            trouser_calf .setText(response.getString("trouser_calf"));
                            trouser_ankle.setText(response.getString("trouser_ankle"));

                            dresstype .setText(response.getString("dresstype"));
                           // Log.d("Rehman",response.getString("ordersID"));
                            ordersid .setText(response.getString("ordersID"));
                            ordertype .setText(response.getString("odertype"));
                            oderdeadline .setText(response.getString("OderDeadline"));
                            tailorname .setText(response.getString("tailorname"));
                            phone_no.setText(response.getString("phoneno"));
                            orderDate .setText(response.getString("orderDate"));
                            dresstype_2.setText(response.getString("dresstype"));
                            shir_details .setText(response.getString("shirtDetails"));
                            trouser_details .setText(response.getString("trouserDetails"));
                            sticthtype .setText(response.getString("stichtype"));
                            lace .setText(response.getString("lace"));
                            pipeing .setText(response.getString("pipe"));
                            buttons.setText(response.getString("button"));
                            comment.setText(response.getString("coments"));
                            price.setText(response.getString("Dressprice"));
                            progressDialog.dismiss();

                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                }
            });
            queue.add(getRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }
}
