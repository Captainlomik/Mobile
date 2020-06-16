package contacts.ru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Edit extends AppCompatActivity {
    public  static  final int ADD = 0;
    public  static  final int EDIT = 1;
    public  static  final int IMAGE = 2;

    public  static  final String SURNAME = "SURNAME",
            NAME = "NAME",
            PATRONYMIC = "PATRONYMIC ",
            PHONE = "PHONE",
            EMAIL = "EMAIL",
            PHOTO = "PHOTO",
            POSITION = "POSITION";


    EditText editName, editPatronymic, editSurname, editPhone, editEmail;
    ImageView imageEdit;
    Intent result;
    int position;
    Uri photoUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editName=findViewById(R.id.editName);
        editSurname=findViewById(R.id.editSurename);
        editPatronymic=findViewById(R.id.editPatronomic);
        editEmail=findViewById(R.id.editEmail);
        editPhone=findViewById(R.id.editPhone);
        imageEdit=findViewById(R.id.imageEdit);

        Intent intent = getIntent();
        if(intent.getExtras()!= null) {
            position = intent.getExtras().getInt(POSITION);
            editSurname.setText(intent.getExtras().getString(SURNAME));
            editName.setText(intent.getExtras().getString(NAME));
            editPatronymic.setText(intent.getExtras().getString(PATRONYMIC));
            editEmail.setText(intent.getExtras().getString(EMAIL));
            editPhone.setText(intent.getExtras().getString(PHONE));
            photoUri = Uri.parse(intent.getExtras().getString(PHOTO));
        }
        else
        {
            photoUri = MainActivity.getUriFromDrawable(this, android.R.drawable.ic_menu_myplaces);
        }
        imageEdit.setImageURI(photoUri);
        result = new Intent();
        setResult(RESULT_CANCELED, result);
    }


@Override
protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && data != null)
    {
        if (requestCode == IMAGE)
        {
            photoUri = data.getData();
            imageEdit.setImageURI(photoUri);
        }
    }
}


    public  void  onSaveClick (View view) //сохраняем, передаем в main
    {
        result.putExtra(POSITION, position);
        result.putExtra(NAME, editName.getText().toString());
        result.putExtra(SURNAME, editSurname.getText().toString());
        result.putExtra(PATRONYMIC, editPatronymic.getText().toString());
        result.putExtra(PHONE, editPhone.getText().toString());
        result.putExtra(EMAIL, editEmail.getText().toString());
        result.putExtra(PHOTO, photoUri.toString());
        setResult(RESULT_OK, result);
        finish();
    }




    public void onSelectPhoto(View view)
    {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE);
    }

}
