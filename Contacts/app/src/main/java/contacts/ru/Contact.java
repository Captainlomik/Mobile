package contacts.ru;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.net.URL;
@Entity (tableName = "contacts")

public class Contact {
    @PrimaryKey
    public  int id;

    @ColumnInfo public  String name;

    @ColumnInfo public String patronymic;

    @ColumnInfo public  String surname;

    @ColumnInfo public  String phone;

    @ColumnInfo public  String email;



    @Ignore
    public Uri photo;

    @Ignore
@Override public  String toString(){

    return (surname.isEmpty() ? "" : surname)
            + (name.isEmpty() ? "" : (surname.isEmpty() ? "" : " ") + name)
            + (patronymic.isEmpty() ? "" : (name.isEmpty() ? "" : " ")+ patronymic);
}
}
