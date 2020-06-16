package contacts.ru;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class Db extends RoomDatabase {
    public abstract ContactDao contactDao();
}
