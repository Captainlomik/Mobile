package contacts.ru;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {

    public static   App app;
    public  Db db;

    @Override
    public  void onCreate()
    { super.onCreate();
    app=this;
    db= Room.databaseBuilder(this, Db.class,"bd" ).build();
    }

}
