package app.rappla;

import java.util.Calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.widget.RemoteViews;

import app.rappla.activities.RapplaActivity;
import app.rappla.calendar.RaplaCalendar;
import app.rappla.calendar.RaplaEvent;

public class RapplaWidgetProvider extends AppWidgetProvider {
    private static final float TEXTWIDTH_RATIO = 0.9f;
    private static Typeface font = null;
    private static String eventID = null;
    private TextPaint textPaint;

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent intent = new Intent(context, RapplaActivity.class);
        intent.setAction("WidgedIntent");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);

        if (font == null)
            font = Typeface.createFromAsset(context.getAssets(), "fonts/Graziano.ttf");

        textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(100);
        textPaint.setTypeface(font);

        RapplaActivity activity = RapplaActivity.getInstance();
        RaplaCalendar calendar;
        if (activity == null)
            calendar = RaplaCalendar.load();
        else
            calendar = activity.getActiveCalendar();
        RaplaEvent nextEvent = calendar.getNextEvent();

        if (nextEvent.getUniqueEventID().equals(eventID)) {
            return;
        }

        Bitmap bitmap = getWidgetBitmap(context, nextEvent);

        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            views.setOnClickPendingIntent(R.id.widgetImage, pendingIntent);
            views.setImageViewBitmap(R.id.widgetImage, bitmap);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        eventID = nextEvent.getUniqueEventID();
    }

    private Bitmap getWidgetBitmap(Context context, RaplaEvent nextEvent) {
        Bitmap bitmap;
        try {
            Resources res = context.getResources();
            Bitmap resBitmap = BitmapFactory.decodeResource(res, R.drawable.stickynote);
            bitmap = resBitmap.copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(bitmap);

            drawString(context, canvas, getText(nextEvent), (int) (canvas.getWidth() * (1 - TEXTWIDTH_RATIO) / 2), 100);
        } catch (NullPointerException e) {
            Log.e("RaplaWidget", "Nullpointerexception");
            e.printStackTrace();
            return null; // Something somewhere went terribly wrong
        }
        return bitmap;
    }

    private String getText(RaplaEvent nextEvent) {
        Calendar startTime = nextEvent.getStartTime();
        Calendar endTime = nextEvent.getEndTime();

        // if this ever makes problems, its because of the "%12" on month

        return nextEvent.getEventNameWithoutProfessor() + '\n' + nextEvent.getProfessor() + '\n' + startTime.get(Calendar.DAY_OF_MONTH) + '/'
                + (startTime.get(Calendar.MONTH) % 12 + 1) + '/' + startTime.get(Calendar.YEAR) + '\n' + startTime.get(Calendar.HOUR_OF_DAY) + ':'
                + startTime.get(Calendar.MINUTE) + '-' + endTime.get(Calendar.HOUR_OF_DAY) + ':' + endTime.get(Calendar.MINUTE);
    }

    private void drawString(Context context, Canvas canvas, String str, int x, int y) {
        StaticLayout mTextLayout = new StaticLayout(str, textPaint, (int) (canvas.getWidth() * 0.9), Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        canvas.save();

        canvas.translate(x, y);
        mTextLayout.draw(canvas);
        canvas.restore();

    }
}
