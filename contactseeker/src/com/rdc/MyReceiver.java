package com.rdc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
String mss,sender1;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		if (bundle != null)
		{
		//---retrieve the SMS message received---
		Object[] pdus = (Object[]) bundle.get("pdus");
		msgs = new SmsMessage[pdus.length];
		for (int i=0; i<msgs.length; i++){
		msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
		str += "SMS from " + msgs[i].getOriginatingAddress();
		sender1 =  msgs[i].getOriginatingAddress();
		str += " :";
		str += msgs[i].getMessageBody().toString();
		mss = msgs[i].getMessageBody().toString();
		str += "\n";
		}
		//---display the new SMS message---
	//	Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
		}		
		 String Str = new String(mss);
		 String[] splits = Str.split(" ",2);
	    
		if(splits[0].equals("giveme")){
		Intent myIntent=new Intent(context,MyActivity.class);	
		myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		myIntent.putExtra("message",splits[1]);
		myIntent.putExtra("sender",sender1);
		context.startActivity(myIntent);
		}
	}

}
