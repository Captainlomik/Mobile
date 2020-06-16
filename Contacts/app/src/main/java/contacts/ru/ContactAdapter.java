package contacts.ru;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ContactAdapter
        extends RecyclerView.Adapter<ContactAdapter.ViewHolder>
        implements  ItemTouchInterface
{
    public Context context;
    public List<Contact> list;

    public  ContactAdapter(Context context, List<Contact> list)
    {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //создание view
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) { //связка представления данных и данных
    final Contact contact = list.get(position);
    holder.textName.setText(contact.toString());
    holder.textPhone.setText(contact.phone);
    holder.imageView.setImageURI(contact.photo);
    holder.contact.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) { //редактирование
            Intent intent = new Intent(context, Edit.class);//открываем
            intent.putExtra(Edit.POSITION, position);
            intent.putExtra(Edit.NAME, contact.name);
            intent.putExtra(Edit.SURNAME, contact.surname);
            intent.putExtra(Edit.PATRONYMIC, contact.patronymic);
            intent.putExtra(Edit.PHONE, contact.phone);
            intent.putExtra(Edit.EMAIL, contact.email);
            if (contact.photo != null)
            {
                intent.putExtra(Edit.PHOTO, contact.photo.toString());
            }
            else {
               Uri photoUri = MainActivity.getUriFromDrawable(context, android.R.drawable.ic_menu_myplaces);
                intent.putExtra(Edit.PHOTO, photoUri.toString());
            }

            ((MainActivity)context).startActivityForResult(intent, Edit.EDIT);
        }
    });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemDismiss(int position) { //Удаление
      final Contact contact = list.get(position);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                App.app.db.contactDao().delete(contact);
                MainActivity.handler.sendEmptyMessage(0);
            }
        });
        thread.start();
        list.remove(position);
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textName, textPhone;
        LinearLayout contact;


        public ViewHolder(@NonNull View itemView) { // Краткий список
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textName = itemView.findViewById(R.id.textName);
            textPhone=itemView.findViewById(R.id.textPhone);
            contact = itemView.findViewById(R.id.contact);
        }
    }
}
