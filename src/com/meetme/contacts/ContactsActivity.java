package com.meetme.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ContactsActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("Not yet implemented.");
        setContentView(textview);
    }
}
