package ipt.ipg.contactos;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContactHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new ContactHandler(getApplicationContext());

        Button btn_add_new = (Button) findViewById(R.id.btn_add_contact);
        btn_add_new.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, NewContactActivity.class);
                startActivity(intent);
            }
        });


        loadContactData();
    }


    private void loadContactData(){
        // Code for loading contact list in ListView
        // Reading all contacts
        List<Contact> contacts = handler.readAllContacts();

        for(Contact c : contacts){
            String record = "ID=" + c.getID() + " | Name=" + c.getName() + " | " + c.getPhoneNumber();

            Log.d("Record",record);
        }
    }


}
