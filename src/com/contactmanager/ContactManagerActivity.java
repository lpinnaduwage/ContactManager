package com.contactmanager;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.Contacts.People;
import android.provider.Contacts.People.Phones;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.PhoneLookup;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class ContactManagerActivity extends Activity {

	TabHost tabHost;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_contact_manager);


		tabHost=(TabHost)findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec1=tabHost.newTabSpec("TAB 1");
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("TAB 1");


		TabSpec spec2=tabHost.newTabSpec("TAB 2");
		spec2.setIndicator("TAB 2");
		spec2.setContent(R.id.tab2);


		TabSpec spec3=tabHost.newTabSpec("TAB 3");
		spec3.setContent(R.id.tab3);
		spec3.setIndicator("TAB 3");
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);

	
		ListView listView = (ListView)findViewById(R.id.listView1);

		


		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);


		ArrayList<String> contacts = new ArrayList<String>();

		while(cur.moveToNext())
		{
		    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
			String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

			Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
					new String[]{id}, null);
			while (pCur.moveToNext()) {
				String phone = pCur.getString(
						pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				
				
				contacts.add(name + " " + phone);
			}
			pCur.close();

			

		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,contacts);

		listView.setAdapter(adapter);

	}


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_manager, menu);
		return true;
	}

}
