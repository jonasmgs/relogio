package com.exemplo.relogio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class ClockView extends View {

    private Paint circlePaint;
    private Paint bezelPaint;
    private Paint markPaint;
    private Paint hourPaint;
    private Paint minutePaint;
    private Paint secondPaint;
    private Paint centerPaint;
    private Paint numberPaint;
    private Paint datePaint;
    private Paint subdialPaint;
    private Paint subdialHandPaint;

    private int hours = 10;
    private int minutes = 10;
    private int seconds = 30;

    private float centerX;
    private float centerY;
    private float radius;

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.parseColor("#1a1a1a"));
        circlePaint.setStyle(Paint.Style.FILL);

        bezelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bezelPaint.setColor(Color.parseColor("#C0C0C0"));
        bezelPaint.setStyle(Paint.Style.STROKE);
        bezelPaint.setStrokeWidth(12);

        markPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        markPaint.setColor(Color.parseColor("#D4AF37"));
        markPaint.setStrokeWidth(3);
        markPaint.setStrokeCap(Paint.Cap.ROUND);

        hourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hourPaint.setColor(Color.parseColor("#D4AF37"));
        hourPaint.setStrokeWidth(14);
        hourPaint.setStrokeCap(Paint.Cap.ROUND);
        hourPaint.setShadowLayer(4, 0, 0, Color.parseColor("#80000000"));

        minutePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        minutePaint.setColor(Color.parseColor("#D4AF37"));
        minutePaint.setStrokeWidth(10);
        minutePaint.setStrokeCap(Paint.Cap.ROUND);
        minutePaint.setShadowLayer(4, 0, 0, Color.parseColor("#80000000"));

        secondPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondPaint.setColor(Color.parseColor("#FF0000"));
        secondPaint.setStrokeWidth(4);
        secondPaint.setStrokeCap(Paint.Cap.ROUND);

        centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint.setColor(Color.parseColor("#D4AF37"));
        centerPaint.setStyle(Paint.Style.FILL);

        numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numberPaint.setColor(Color.parseColor("#E8E8E8"));
        numberPaint.setTextSize(48);
        numberPaint.setTextAlign(Paint.Align.CENTER);
        numberPaint.setFakeBoldText(true);

        datePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        datePaint.setColor(Color.parseColor("#E8E8E8"));
        datePaint.setTextSize(32);
        datePaint.setTextAlign(Paint.Align.CENTER);
        datePaint.setStyle(Paint.Style.FILL);

        subdialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subdialPaint.setColor(Color.parseColor("#2a2a2a"));
        subdialPaint.setStyle(Paint.Style.FILL);

        subdialHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subdialHandPaint.setColor(Color.parseColor("#A0A0A0"));
        subdialHandPaint.setStrokeWidth(3);
        subdialHandPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2f;
        centerY = h / 2f;
        radius = Math.min(centerX, centerY) - 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Fundo do relógio
        canvas.drawCircle(centerX, centerY, radius, circlePaint);

        // Moldura (bezel)
        canvas.drawCircle(centerX, centerY, radius, bezelPaint);
        canvas.drawCircle(centerX, centerY, radius - 6, bezelPaint);

        // Marcações das horas
        for (int i = 0; i < 12; i++) {
            float angle = (float) Math.toRadians(i * 30 - 90);
            float startX = centerX + (float) Math.cos(angle) * (radius - 50);
            float startY = centerY + (float) Math.sin(angle) * (radius - 50);
            float endX = centerX + (float) Math.cos(angle) * (radius - 30);
            float endY = centerY + (float) Math.sin(angle) * (radius - 30);
            
            Paint paint = new Paint(markPaint);
            paint.setStrokeWidth(8);
            canvas.drawLine(startX, startY, endX, endY, paint);
        }

        // Marcações dos minutos
        for (int i = 0; i < 60; i++) {
            if (i % 5 != 0) {
                float angle = (float) Math.toRadians(i * 6 - 90);
                float startX = centerX + (float) Math.cos(angle) * (radius - 40);
                float startY = centerY + (float) Math.sin(angle) * (radius - 40);
                float endX = centerX + (float) Math.cos(angle) * (radius - 30);
                float endY = centerY + (float) Math.sin(angle) * (radius - 30);
                canvas.drawLine(startX, startY, endX, endY, markPaint);
            }
        }

        // Números das horas (estilo Rolex - só alguns números)
        String[] numbers = {"12", "3", "6", "9"};
        int[] angles = {0, 90, 180, 270};
        for (int i = 0; i < numbers.length; i++) {
            float angle = (float) Math.toRadians(angles[i] - 90);
            float x = centerX + (float) Math.cos(angle) * (radius - 80);
            float y = centerY + (float) Math.sin(angle) * (radius - 80) + 16;
            canvas.drawText(numbers[i], x, y, numberPaint);
        }

        // Logo/marca (ROLEX style)
        Paint logoPaint = new Paint(numberPaint);
        logoPaint.setTextSize(36);
        canvas.drawText("OYSTER", centerX, centerY - radius + 120, logoPaint);

        // Sub-mostradores (cronógrafo style)
        drawSubdial(canvas, centerX - radius * 0.5f, centerY - radius * 0.2f, radius * 0.15f, seconds / 2);
        drawSubdial(canvas, centerX + radius * 0.5f, centerY - radius * 0.2f, radius * 0.15f, minutes / 2);
        drawSubdial(canvas, centerX, centerY + radius * 0.45f, radius * 0.15f, hours * 5);

        // Janela de data
        RectF dateRect = new RectF(centerX + radius * 0.25f, centerY - 25, centerX + radius * 0.55f, centerY + 25);
        Paint dateBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dateBoxPaint.setColor(Color.parseColor("#0a0a0a"));
        canvas.drawRoundRect(dateRect, 8, 8, dateBoxPaint);
        canvas.drawText(String.format("%02d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)), 
                        dateRect.centerX(), dateRect.centerY() + 12, datePaint);

        // Ponteiros
        drawHourHand(canvas);
        drawMinuteHand(canvas);
        drawSecondHand(canvas);

        // Centro do relógio
        canvas.drawCircle(centerX, centerY, 20, centerPaint);
        Paint centerRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerRingPaint.setColor(Color.parseColor("#1a1a1a"));
        centerRingPaint.setStyle(Paint.Style.STROKE);
        centerRingPaint.setStrokeWidth(4);
        canvas.drawCircle(centerX, centerY, 20, centerRingPaint);
    }

    private void drawSubdial(Canvas canvas, float cx, float cy, float r, int value) {
        canvas.drawCircle(cx, cy, r, subdialPaint);
        
        Paint ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ringPaint.setColor(Color.parseColor("#404040"));
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setStrokeWidth(2);
        canvas.drawCircle(cx, cy, r, ringPaint);

        for (int i = 0; i < 12; i++) {
            float angle = (float) Math.toRadians(i * 30 - 90);
            float x = cx + (float) Math.cos(angle) * (r - 8);
            float y = cy + (float) Math.sin(angle) * (r - 8);
            canvas.drawCircle(x, y, 2, subdialHandPaint);
        }

        float angle = (float) Math.toRadians(value * 6 - 90);
        float endX = cx + (float) Math.cos(angle) * (r - 15);
        float endY = cy + (float) Math.sin(angle) * (r - 15);
        canvas.drawLine(cx, cy, endX, endY, subdialHandPaint);
    }

    private void drawHourHand(Canvas canvas) {
        float angle = (float) Math.toRadians((hours % 12) * 30 + minutes * 0.5 - 90);
        float handLength = radius * 0.4f;
        float endX = centerX + (float) Math.cos(angle) * handLength;
        float endY = centerY + (float) Math.sin(angle) * handLength;
        canvas.drawLine(centerX, centerY, endX, endY, hourPaint);
    }

    private void drawMinuteHand(Canvas canvas) {
        float angle = (float) Math.toRadians(minutes * 6 + seconds * 0.1 - 90);
        float handLength = radius * 0.6f;
        float endX = centerX + (float) Math.cos(angle) * handLength;
        float endY = centerY + (float) Math.sin(angle) * handLength;
        canvas.drawLine(centerX, centerY, endX, endY, minutePaint);
    }

    private void drawSecondHand(Canvas canvas) {
        float angle = (float) Math.toRadians(seconds * 6 - 90);
        float handLength = radius * 0.7f;
        float endX = centerX + (float) Math.cos(angle) * handLength;
        float endY = centerY + (float) Math.sin(angle) * handLength;
        
        float backLength = radius * 0.15f;
        float backX = centerX - (float) Math.cos(angle) * backLength;
        float backY = centerY - (float) Math.sin(angle) * backLength;
        
        canvas.drawLine(backX, backY, endX, endY, secondPaint);
    }

    public void setTime(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        invalidate();
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }
}