package com.example.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.aidlclient.databinding.ActivityMainBinding;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    AddMethod addMethod;
    boolean isBinded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String string1 = binding.et1.getText().toString();
                    String string2 = binding.et2.getText().toString();
                    addMethod.toAdd(Integer.valueOf(string1),Integer.valueOf(string2));
                    binding.tv1.setText("结果："+addMethod.getResult());
                }catch (Exception ex){
                    ex.printStackTrace();
                    Log.d("TAG", "Exception: "+ex.getMessage());
                }
            }
        });
    }

   private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            addMethod=AddMethod.Stub.asInterface(iBinder);
            isBinded=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("TAG", "onServiceDisconnected: ");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (isBinded)return;
        Intent intent = new Intent();
        intent.setAction("com.example.aidl");
        intent.setPackage("com.example.aidlservice");
        boolean isSuccess = bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
        Log.d("TAG", " connection is " + isSuccess);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isBinded)return;
        unbindService(mServiceConnection);
        isBinded=false;
    }
}