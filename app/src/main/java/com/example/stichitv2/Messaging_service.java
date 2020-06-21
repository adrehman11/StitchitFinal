package com.example.stichitv2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Messaging_service extends Service {
private String orderID;
String servicename;
String abc;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("NotificationTailor");
    DatabaseReference orderARs = FirebaseDatabase.getInstance().getReference("NotificationCustomer");
    Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        servicename = intent.getStringExtra("servicename");

        if(servicename.equals("sendorder"))
        {
            orderID = intent.getStringExtra("oid");

            DatabaseReference orderid = database.child(orderID);
            orderid.setValue("Unseen");
        }
       else if(servicename.equals("setorderstatus"))
        {
            orderID = intent.getStringExtra("orderids");
           String status = intent.getStringExtra("orderStatus");
           if(status.equals("Cut")){
               DatabaseReference orderid = orderARs.child(orderID);
               orderid.setValue("Cut");
           }
           else if(status.equals("Stitch"))
           {
               DatabaseReference orderid = orderARs.child(orderID);
               orderid.setValue("Stitch");
           }
           else if(status.equals("Press"))
           {
               DatabaseReference orderid = orderARs.child(orderID);
               orderid.setValue("Press");
           }
           else if(status.equals("Finish"))
           {
               DatabaseReference orderid = orderARs.child(orderID);
               orderid.setValue("Finish");
           }


        }
        else if(servicename.equals("OrderRejected") || servicename.equals("OrderAccepted"))
        {
            orderID = intent.getStringExtra("OrderID");
            if(servicename.equals("OrderRejected"))
            {
                DatabaseReference orderAR = orderARs.child(orderID);
                orderAR.setValue("OrderRejected");
            }
            else if (servicename.equals("OrderAccepted"))
            {
                DatabaseReference orderAR = orderARs.child(orderID);
                orderAR.setValue("OrderAccepted");
            }

        }
        else if(servicename.equals("TailorReOrder") || servicename.equals("CustomerReOrder"))
        {
            orderID = intent.getStringExtra("OrderID");
            if(servicename.equals("TailorReOrder"))
            {
                DatabaseReference reorder = orderARs.child(orderID);
                reorder.setValue("ReOrderTailor");
            }
            else if (servicename.equals("CustomerReOrder"))
            {
                DatabaseReference orderAR = database.child(orderID);
                orderAR.setValue("CustomerReOrder");
            }

        }


        else if(servicename.equals("notificationCustomer")){
            abc=intent.getStringExtra("anc");
            if(abc.equals("customer"))
            {
                String jsonArray = intent.getStringExtra("orderarray");
                try {
                    JSONArray Orderarray = new JSONArray(jsonArray);
                    for(int i=0;i<Orderarray.length();i++) {
                        JSONObject resdata = Orderarray.getJSONObject(i);
                        orderID=resdata.getString("orderID");
                        Log.d("Rehman",orderID);
                        final DatabaseReference getnotification1 = orderARs.child(orderID);
                        getnotification1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String value = dataSnapshot.getValue(String.class);

                                if(value==null)
                                {

                                }
                                else if(value.equals("OrderAccepted"))
                                {
                                    shownotification("Your Order is Accepted");
                                    getnotification1.setValue("seen");
                                }
                                else if(value.equals("OrderRejected"))
                                {
                                    shownotification("Your Order is Rejected");
                                    getnotification1.setValue("seen");
                                }
                                else if(value.equals("ReOrderTailor"))
                                {
                                    shownotification("You recieved new order (reorder)");
                                    getnotification1.setValue("seen");
                                }
                                else if(value.equals("Cut"))
                                {
                                    shownotification("Your Order is in cutting stage");
                                    getnotification1.setValue("seen");
                                }
                                else if(value.equals("Stitch"))
                                {
                                    shownotification("Your Order is ready for stitch");
                                    getnotification1.setValue("seen");
                                }
                                else if(value.equals("Press"))
                                {
                                    shownotification("Your Order is ready for press");
                                    getnotification1.setValue("seen");
                                }
                                else if(value.equals("Finish"))
                                {
                                    shownotification("Your Order is completed");
                                    getnotification1.setValue("seen");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }
        else if(servicename.equals("getnotificationtailor")){

            String jsonArray = intent.getStringExtra("orderarray");
            try {
                JSONArray Orderarray = new JSONArray(jsonArray);
                for(int i=0;i<Orderarray.length();i++) {
                    JSONObject resdata = Orderarray.getJSONObject(i);
                    orderID=resdata.getString("orderID");
                    final DatabaseReference getnotification1 = database.child(orderID);
                    getnotification1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String value = dataSnapshot.getValue(String.class);
                           if(value==null)
                           {

                           }
                            else if(value.equals("Unseen"))
                            {
                                shownotification("You recieved new order");
                                getnotification1.setValue("seen");
                            }
                           else if(value.equals("CustomerReOrder"))
                           {
                               shownotification("Your Order is ReAssigned");
                               getnotification1.setValue("seen");
                           }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        return  START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void shownotification(String message){
        PendingIntent p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, TailorNewOrder.class)},0);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("Mynotification","Mynotification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        if(message.equals("Your Order is Accepted"))
        {
             p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, Home_Customer.class)},0);
        }
        if(message.equals("Your Order is Rejected"))
        {
            p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, RejectedOrderCustomer.class)},0);
        }
        if(message.equals("You recieved new order"))
        {
            p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, TailorNewOrder.class)},0);
        }
        if(message.equals("Your Order is ReAssigned"))
        {
            p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, CustomerNewOrders.class)},0);
        }
        if(message.equals("You recieved new order (reorder)"))
        {
            p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, TailorNewOrder.class)},0);
        }
        if(message.equals("Your Order is in cutting stage") || message.equals("Your Order is ready for press")
                || message.equals("Your Order is ready for stitch"))
        {
            p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, Home_Customer.class)},0);
        }
        if(message.equals("Your Order is completed"))
        {
            p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, CustomerHistory.class)},0);
        }

        NotificationCompat.Builder n = new NotificationCompat.Builder(this,"Mynotification")
                .setSmallIcon(R.drawable.ic_notify)
                .setContentTitle("Notification")
                .setContentText(message)
                .setContentIntent(p1)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000})
                .setSound(uri)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager =NotificationManagerCompat.from(this);
        notificationManager.notify(999,n.build());
    }


}
