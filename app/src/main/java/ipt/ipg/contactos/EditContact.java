package ipt.ipg.contactos;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditContact  extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;

    private ContactHandler handler;

    private String picturePath = "";

    EditText et_name;
    EditText et_phone;
    EditText et_email;
    EditText et_address;
    ImageView iv_photograph;

    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_contact);

        extras = getIntent().getExtras();

        handler = new ContactHandler(getApplicationContext());

        TextView tv_title = (TextView) findViewById(R.id.tv_new_contact_title);
        tv_title.setVisibility(View.GONE);

        et_name = (EditText) findViewById(R.id.et_name);
        et_name.setText(extras.getString("name"));

        et_phone = (EditText) findViewById(R.id.et_phone);
        et_phone.setText(extras.getString("phone"));

        et_email = (EditText) findViewById(R.id.et_email);
        et_email.setText(extras.getString("email"));

        et_address = (EditText) findViewById(R.id.et_address);
        et_address.setText(extras.getString("address"));

        iv_photograph = (ImageView) findViewById(R.id.iv_user_photo);
        iv_photograph.setImageBitmap(BitmapFactory.decodeFile(extras.getString("photograph")));

        Button btn_update = (Button) findViewById(R.id.btn_add);
        btn_update.setText(getString(R.string.button_Editar));
        btn_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Contact contact = new Contact();

                contact.setID(extras.getInt("id")); // Update the data where id = extras.getInt("id").
                contact.setName(et_name.getText().toString());
                contact.setPhoneNumber(et_phone.getText().toString());
                contact.setEmail(et_email.getText().toString());
                contact.setPostalAddress(et_address.getText().toString());
                contact.setPhotograph(picturePath);

                Boolean updated = handler.editContact(contact); // calling ContactHandler method
                if(updated){
                    Intent intent = new Intent(EditContact.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), getString(R.string.editar_Contacto), Toast.LENGTH_LONG).show();
                }


            }
        });

        ImageView iv_user_photo = (ImageView) findViewById(R.id.iv_user_photo);
        iv_user_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, RESULT_LOAD_IMAGE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
