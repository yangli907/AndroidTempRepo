package com.android.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsReceiverActivity extends Activity {
    /** Called when the activity is first created. */
    Button btn;
    EditText edt1;
    EditText edt2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btn=(Button) findViewById(R.id.btn1);
        edt1=(EditText) findViewById(R.id.edt1);
        edt2=(EditText) findViewById(R.id.edt2);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String phone=edt1.getText().toString();
                String message=edt2.getText().toString();

                if (phone.length()>0 && message.length()>0)
                    sendSMS(phone, message);
                else
                    Toast.makeText(getApplicationContext(),
                        "Enter the phone_no & message.", Toast.LENGTH_SHORT).show();
            }
        });
        try
        {
        	Intent i = new Intent(this, SmsListen.class);
            startActivity(i);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    }

    private void sendSMS(String phoneNumber, String message)
    {
        Intent i1 = new Intent(this, SmsReceiverActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                                                     i1 , 0);
        SmsManager SMS1 = SmsManager.getDefault();
        SMS1.sendTextMessage(phoneNumber, null, message, pi, null);
    }
}