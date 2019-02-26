package ipt.ipg.contactos;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class NewContactActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;

    private ContactHandler handler;

    private String picturePath = "";

    private String name;
    private String phone;
    private String email;
    private String address;
    private String photograph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_contact);

        handler = new ContactHandler(getApplicationContext());

        ImageView iv_user_photo = (ImageView) findViewById(R.id.iv_user_photo);
        iv_user_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, RESULT_LOAD_IMAGE);

            }
        });

        Button btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                EditText et_name = (EditText) findViewById(R.id.et_name);
                name = et_name.getText().toString();

                EditText et_phone = (EditText) findViewById(R.id.et_phone);
                phone = et_phone.getText().toString();

                EditText et_email = (EditText) findViewById(R.id.et_email);
                email = et_email.getText().toString();

                EditText et_address = (EditText) findViewById(R.id.et_address);
                address = et_address.getText().toString();

                ImageView iv_photograph = (ImageView) findViewById(R.id.iv_user_photo);
                photograph = picturePath;

                Contact contact = new Contact();
                contact.setName(name);
                contact.setPhoneNumber(phone);
                contact.setEmail(email);
                contact.setPostalAddress(address);
                contact.setPhotograph(photograph);

                Boolean added = handler.addContactDetails(contact);
                if(added){
                    Intent intent = new Intent(NewContactActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Contacto não adicionado.Por favor, tente novamente.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri imageUri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(imageUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.iv_user_photo);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }


}
