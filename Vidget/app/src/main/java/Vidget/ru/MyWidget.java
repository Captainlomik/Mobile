package Vidget.ru;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class MyWidget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        SharedPreferences preferences = context.getSharedPreferences(Config.WIDGET_PREF, Context.MODE_PRIVATE);
        for (int id: appWidgetIds) {
            updateWidget(context, appWidgetManager, preferences, id);
        }
    }
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        SharedPreferences.Editor editor = context.getSharedPreferences(Config.WIDGET_PREF, Context.MODE_PRIVATE).edit();
        for (int widgetID: appWidgetIds) {
            editor.remove(Config.WIDGET_TEXT + widgetID);
            editor.remove(Config.WIDGET_COLOR + widgetID);
        }
        editor.apply();
    }
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
    static void updateWidget(Context context, AppWidgetManager appWidgetManager, SharedPreferences preferences, int widgetID) {
        String widgetText = preferences.getString(Config.WIDGET_TEXT + widgetID, null);
        if (widgetText == null) return;
        int widgetColor = preferences.getInt(Config.WIDGET_COLOR + widgetID, 0);
        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.activity_main);
        widgetView.setTextViewText(R.id.widgetText, widgetText);
        widgetView.setInt(R.id.widgetText, "setBackgroundColor", widgetColor);
        appWidgetManager.updateAppWidget(widgetID, widgetView);
    }
}