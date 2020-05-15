package com.example.cubegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random random=new Random();
    TextView User ;
    TextView Robot;
    int user= 0;
    int robot= 0;
   String text1="0";
    String text2="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User =findViewById(R.id.User);
        Robot=findViewById(R.id.Robot);
        User.setText(getString(R.string.User, user, text1));
        Robot.setText(getString(R.string.Robot, robot, text2));
    }

public  void onClick(View View)
{  text1=" ";
    text2=" ";
    user();
    robot();
    User.setText(getString(R.string.User, user, text1));
    Robot.setText(getString(R.string.Robot, robot, text2));
    if(user>100 && robot >100)
    {
        Intent intent = new Intent(this, DawActivity.class);
        startActivity(intent);
        clear();
    }
    else if (user>100){
        Intent intent = new Intent(this, WinActivity.class);
        startActivity(intent);
        clear();
    }
    else if (robot>100)
    {
        Intent intent = new Intent(this, LoseActivity.class);
        startActivity(intent);
        clear();
    }

}

public  void  user () {
    int user1 = random.nextInt(6) + 1;
    int user2 = random.nextInt(6) + 1;
    user += user1 + user2;
    text1 += String.valueOf(user1) + "|" + String.valueOf(user2) + " ";
    if (user1 == user2)
    {
        user();
    }
}

public  void  robot ()
{
    int robot1=random.nextInt(6)+1;
    int robot2=random.nextInt(6)+1;
    text2 += String.valueOf(robot1)+"|"+ String.valueOf(robot2)+" ";
    robot+=robot1+robot2;
    if (robot1 == robot2){
        robot();
    }
}

public  void clear ()
{
    final Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            user=0;
            robot=0;
             text1="";
             text2="";
            User.setText(getString(R.string.User, user, text1));
            Robot.setText(getString(R.string.Robot, robot, text2));
        }
    }, 1000);

}

public  void onClick2(View View)
{
    clear();
}


}
