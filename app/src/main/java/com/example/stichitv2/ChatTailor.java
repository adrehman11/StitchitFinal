package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.cometchat.pro.constants.CometChatConstants;

import constant.StringContract;
import screen.messagelist.CometChatMessageScreen;

public class ChatTailor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_tailor);

        Intent intent           = getIntent();
        String tailorID      = intent.getStringExtra("UID");
        Bundle bundle = new Bundle();
        Fragment chatFragment=new CometChatMessageScreen();

        bundle.putString(StringContract.IntentStrings.AVATAR,"AvatarUrl");
        bundle.putString(StringContract.IntentStrings.NAME, "Name");
        bundle.putString(StringContract.IntentStrings.STATUS,"STATUS");
        bundle.putString(StringContract.IntentStrings.UID,tailorID);

        bundle.putString(StringContract.IntentStrings.TYPE, CometChatConstants.RECEIVER_TYPE_USER);
        chatFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.chatFrame,chatFragment).commit();
    }
}