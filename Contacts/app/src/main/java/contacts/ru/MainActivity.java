package contacts.ru;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ContactAdapter adapter;
    List<Contact> list;

    static Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list =new ArrayList<>();
        adapter = new ContactAdapter(this, list);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new ItemTouchCallback(adapter); //Подклюяение touchHelper
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        handler = new Handler()
        {
            public  void handleMessage(Message message)
            {
                adapter.list = list;
                adapter.notifyDataSetChanged();
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                list = App.app.db.contactDao().readAll();
                handler.sendEmptyMessage(0);
            }
        });
        thread.start();
    }

        @Override
        public  void onActivityResult( int requestCode, int resultCode, Intent data) { //вывод результата
            super.onActivityResult(resultCode, requestCode, data);
            if (resultCode==RESULT_OK){
                if (requestCode == Edit.ADD)
                {
                    final Contact contact = new Contact();
                    contact.id=list.size()+1;
                    contact.name = data.getStringExtra(Edit.NAME);
                    contact.surname = data.getStringExtra(Edit.SURNAME);
                    contact.patronymic = data.getStringExtra(Edit.PATRONYMIC);
                    contact.phone = data.getStringExtra(Edit.PHONE);
                    contact.email= data.getStringExtra(Edit.EMAIL);
                    contact.photo=Uri.parse(data.getStringExtra(Edit.PHOTO));
                    list.add(contact);

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            App.app.db.contactDao().create(contact);
                            handler.sendEmptyMessage(0);
                        }
                    });
                    thread.start();
                }
                if (requestCode == Edit.EDIT)
                {
                    int position =data.getIntExtra(Edit.POSITION, 0);
                    final Contact contact = list.get(position);
                    contact.name = data.getStringExtra(Edit.NAME);
                    contact.surname = data.getStringExtra(Edit.SURNAME);
                    contact.patronymic = data.getStringExtra(Edit.PATRONYMIC);
                    contact.phone = data.getStringExtra(Edit.PHONE);
                    contact.email= data.getStringExtra(Edit.EMAIL);
                    list.set(position, contact);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            App.app.db.contactDao().update(contact);
                            handler.sendEmptyMessage(0);
                        }
                    });
                    thread.start();
                }
            }
        }


    public  void  onAddClick(View view) //
    {
        Intent intentAdd = new Intent(this, Edit.class);//открываем
        startActivityForResult(intentAdd, Edit.ADD);
    }


    public static Uri getUriFromDrawable(@NonNull Context context, @AnyRes int drawableId) //преобразование в uri
    {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
    + '/' + context.getResources().getResourceTypeName(drawableId)
    + '/' + context.getResources().getResourceEntryName(drawableId));
        return imageUri;
    }


}
