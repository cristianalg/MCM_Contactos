package ipt.ipg.contactos;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactDetailsActivity extends AppCompatActivity {

    Bundle extras;

    private ContactHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);


        // Get intent data
        extras = getIntent().getExtras();

        handler = new ContactHandler(getApplicationContext());

        ImageView iv_photo = (ImageView) findViewById(R.id.iv_contact_photo);
        iv_photo.setImageBitmap(BitmapFactory.decodeFile(extras.getString("photograph")));

        TextView tv_name = (TextView) findViewById(R.id.tv_contact_name);
        tv_name.setText(extras.getString("name"));

        TextView tv_phone = (TextView) findViewById(R.id.tv_contact_phone);
        tv_phone.setText(extras.getString("phone"));

        TextView tv_email = (TextView) findViewById(R.id.tv_contact_email);
        tv_email.setText(extras.getString("email"));

        TextView tv_address = (TextView) findViewById(R.id.tv_contact_address);
        tv_address.setText(extras.getString("address"));

        Button btn_back = (Button) findViewById(R.id.btn_back_to_contact);
        btn_back.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View arg0) {
                 finish();
                 Intent intent = new Intent(ContactDetailsActivity.this, MainActivity.class);
                 startActivity(intent);
             }
        });
    }
}
