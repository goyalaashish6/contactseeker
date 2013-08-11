package com.rdc;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;

public class MyActivity extends Activity {
String str,r,n;
TextView txt;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		txt = (TextView)findViewById(R.id.tv1);
		StringBuilder s = new StringBuilder();
		Intent intent = getIntent();
		r = intent.getStringExtra("message");
		n = intent.getStringExtra("sender");
	    	 ContentResolver cr = getContentResolver();
	    	 Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
	    	 "DISPLAY_NAME = '" + r + "'", null, null);
	    	 try{
	    	 if (cursor.moveToFirst()) {
	    	        String contactId =
	    	            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

	    	        Cursor phones = cr.query(Phone.CONTENT_URI, null,
	    	                Phone.CONTACT_ID + " = " + contactId, null, null);
	    	            while (phones.moveToNext()) {
	    	                String number = phones.getString(phones.getColumnIndex(Phone.NUMBER));
	    	                int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
	    	                switch (type) {
	    	                    case Phone.TYPE_HOME: 	
	    	                    	s.append(number);
	    	                    	s.append("\n");
	    	                        break;
	    	                    case Phone.TYPE_MOBILE:
	    	                    	s.append(number);
	    	                    	s.append("\n");
	    	                        // do something with the Mobile number here...
	    	                        break;
	    	                    case Phone.TYPE_WORK:
	    	                    	s.append(number);
	    	                    	s.append("\n");
	    	                        // do something with the Work number here...
	    	                        break;
	    	                    case Phone.TYPE_OTHER:
	    	                    	s.append(number);
	    	                    	s.append("\n");
	    	                    }
	    	            }
	    	            phones.close();
	    	 }
	    	 cursor.close();
	    	 str=s.toString();
	    	 txt.setText(str);
	    	 
	    	 sendSMS(n,str);
	         finish();

	}
	    	  catch (IllegalArgumentException e) {
			        e.printStackTrace();
			        Log.e("IllegalArgumentException :: ", e.toString());
			    } catch (Exception e) {
			        e.printStackTrace();
			        Log.e("Error :: ", e.toString());
			    }
			    	 
			    	
			}
	  private void sendSMS(String phoneNumber, String message)
      {
      SmsManager sms = SmsManager.getDefault();
      sms.sendTextMessage(phoneNumber, null, message, null, null);
      }

}
