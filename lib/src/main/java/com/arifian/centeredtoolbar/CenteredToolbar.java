package com.arifian.centeredtoolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.arifian.centeredtoolbar.lib.R;

/**
 * Created by faqih on 27/05/17.
 */

public class CenteredToolbar extends Toolbar {
    private final String TITLE = "title";
    private final String SUBTITLE = "subtitle";

    private final int INVALID_UNIT = -1;
    int titleColor, subtitleColor;

    TextView titleTextView, subtitleTextView;

    public CenteredToolbar(Context context) {
        this(context, null);

        initiate(context, null);
    }

    public CenteredToolbar(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);

        initiate(context, attrs);
    }

    public CenteredToolbar(final Context context, @Nullable final AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initiate(context, attrs);
    }

    private void initiate(Context context, AttributeSet attrs){
        getTextView();

        if(attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CenteredToolbar);
            float titleSize = a.getDimension(R.styleable.CenteredToolbar_titleTextSize, getTitleTextSize());
            float subtitleSize = a.getDimension(R.styleable.CenteredToolbar_subtitleTextSize, getSubtitleTextSize());
            String titleTypeface = a.getString(R.styleable.CenteredToolbar_titleTypeface);
            String subtitleTypeface = a.getString(R.styleable.CenteredToolbar_subtitleTypeface);
            a.recycle();

            setTitleTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
            setSubtitleTextSize(TypedValue.COMPLEX_UNIT_PX, subtitleSize);
            setTitleTypeface(titleTypeface);
            setSubtitleTypeface(subtitleTypeface);
        }
    }

    @Override
    public void setTitle(final CharSequence title) {
        titleTextView.post(new Runnable() {
            @Override
            public void run() {
                CenteredToolbar.super.setTitle(title);
                centerizeTextView(titleTextView);
            }
        });
    }

    @Override
    public void setSubtitle(final CharSequence title) {
        subtitleTextView.post(new Runnable() {
            @Override
            public void run() {
                CenteredToolbar.super.setSubtitle(title);
                centerizeTextView(subtitleTextView);
            }
        });
    }

    public void setTitleTypeface(final String fontPath){
        titleTextView.post(new Runnable() {
            @Override
            public void run() {
                changeTypeface(titleTextView, fontPath);
            }
        });
    }

    public void setSubtitleTypeface(final String fontPath){
        subtitleTextView.post(new Runnable() {
            @Override
            public void run() {
                changeTypeface(subtitleTextView, fontPath);
            }
        });
    }

    public void setTitleTextSize(final float size){
        titleTextView.post(new Runnable() {
            @Override
            public void run() {
                changeSize(titleTextView, INVALID_UNIT, size);
            }
        });
    }

    public void setSubtitleTextSize(final float size){
        subtitleTextView.post(new Runnable() {
            @Override
            public void run() {
                changeSize(subtitleTextView, INVALID_UNIT, size);
            }
        });
    }

    public void setTitleTextSize(final int unit, final float size){
        titleTextView.post(new Runnable() {
            @Override
            public void run() {
                changeSize(titleTextView, unit, size);
            }
        });
    }

    public void setSubtitleTextSize(final int unit, final float size){
        subtitleTextView.post(new Runnable() {
            @Override
            public void run() {
                changeSize(subtitleTextView, unit, size);
            }
        });
    }

    @Override
    public void setTitleTextColor(@ColorInt final int color) {
        titleColor = color;
        super.setTitleTextColor(color);
    }

    @Override
    public void setSubtitleTextColor(@ColorInt final int color) {
        subtitleColor = color;
        super.setSubtitleTextColor(color);
    }

    public TextView getTitleTextView(){
        return titleTextView;
    }

    public TextView getSubtitleTextView(){
        return subtitleTextView;
    }

    private TextView getTextView(String what){
        TextView textView = null;
        final CharSequence originalTitle = getTitle();
        final CharSequence originalSubtitle = getSubtitle();

        super.setTitle(TITLE);
        super.setSubtitle(SUBTITLE);

        for(int i = 0; i < getChildCount(); i++){
            View view = getChildAt(i);

            if(view instanceof TextView){
                final TextView tempTextView = (TextView) view;

                if(tempTextView.getText().equals(TITLE) && what.equals(TITLE)){
                    textView = tempTextView;
                } else if(tempTextView.getText().equals(SUBTITLE) && what.equals(SUBTITLE)){
                    textView = tempTextView;
                }
            }
        }

        super.setTitle(originalTitle);
        super.setSubtitle(originalSubtitle);

        return textView;
    }

    private void getTextView(){
        final CharSequence originalTitle = getTitle();
        final CharSequence originalSubtitle = getSubtitle();

        super.setTitle(TITLE);
        super.setSubtitle(SUBTITLE);

        for(int i = 0; i < getChildCount(); i++){
            View view = getChildAt(i);

            if(view instanceof TextView){
                final TextView tempTextView = (TextView) view;

                if(tempTextView.getText().equals(TITLE)){
                    titleTextView = tempTextView;
                } else if(tempTextView.getText().equals(SUBTITLE)){
                    subtitleTextView = tempTextView;
                }
            }
        }

        super.setTitle(originalTitle);
        super.setSubtitle(originalSubtitle);
    }

    private float getTitleTextSize(){
        TextView textView = titleTextView;
        return textView.getTextSize();
    }

    private float getSubtitleTextSize(){
        TextView textView = subtitleTextView;
        return textView.getTextSize();
    }

    private void centerizeTextView(final TextView textView){
        textView.setVisibility(INVISIBLE);
        textView.post(new Runnable() {
            @Override
            public void run() {
                LayoutParams params = (LayoutParams) textView.getLayoutParams();
                params.width = LayoutParams.MATCH_PARENT;
                textView.setLayoutParams(params);

                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        LayoutParams params = (LayoutParams) textView.getLayoutParams();
                        float toolbarStart = getX();
                        float textViewStart = textView.getX();
                        float toolbarEnd = getX() + getWidth();
                        float textViewEnd = textView.getX() + textView.getWidth();
                        int diffStart = (int) Math.abs((toolbarStart-textViewStart));
                        int diffEnd = (int) Math.abs((toolbarEnd-textViewEnd));
                        int padding = Math.max(diffStart, diffEnd);

                        params.width = getWidth();
                        textView.setLayoutParams(params);

                        textView.setPadding(padding, textView.getPaddingTop(), padding, textView.getPaddingBottom());
                        textView.setGravity(Gravity.CENTER);
                        textView.setX(getX());

                        textView.setVisibility(VISIBLE);
                    }
                });

            }
        });
    }

    private void changeTypeface(TextView textView, String path){
        if(path != null) {
            if (!path.isEmpty()) {
                Typeface font = Typeface.createFromAsset(getContext().getAssets(), path);
                textView.setTypeface(font);
            }
        }
    }

    private void changeSize(TextView textView, int unit, float size){
        if(unit == INVALID_UNIT)
            textView.setTextSize(size);
        else
            textView.setTextSize(unit, size);
    }
}
