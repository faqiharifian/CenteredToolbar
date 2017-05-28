package com.arifian.centeredtoolbar.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by faqih on 27/05/17.
 */

public class CenteredToolbar extends Toolbar {
    private final String TITLE = "title";
    private final String SUBTITLE = "subtitle";

    private final String DO_CENTERIZE = "centerize";
    private final String DO_TYPEFACE = "typeface";
    private final String DO_SIZE = "size";

    private final int INVALID_UNIT = -1;


    public CenteredToolbar(Context context) {
        this(context, null);
    }

    public CenteredToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);

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

    public CenteredToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);

        this.post(new Runnable() {
            @Override
            public void run() {
                centerizeTextView(getTextView(TITLE));
            }
        });
    }

    @Override
    public void setSubtitle(CharSequence title) {
        super.setSubtitle(title);

        this.post(new Runnable() {
            @Override
            public void run() {
                centerizeTextView(getTextView(SUBTITLE));
            }
        });
    }

    public void setTitleTypeface(String fontPath){
        changeTypeface(getTextView(TITLE), fontPath);
    }

    public void setSubtitleTypeface(String fontPath){
        changeTypeface(getTextView(SUBTITLE), fontPath);
    }

    public void setTitleTextSize(float size){
        changeSize(getTextView(TITLE), INVALID_UNIT, size);
    }

    public void setSubtitleTextSize(float size){
        changeSize(getTextView(SUBTITLE), INVALID_UNIT, size);
    }

    public void setTitleTextSize(int unit, float size){
        changeSize(getTextView(TITLE), unit, size);
    }

    public void setSubtitleTextSize(int unit, float size){
        changeSize(getTextView(SUBTITLE), unit, size);
    }

    public TextView getTitleTextView(){
        return getTextView(TITLE);
    }

    public TextView getSubtitleTextView(){
        return getTextView(SUBTITLE);
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

    private float getTitleTextSize(){
        TextView textView = getTextView(TITLE);
        return textView.getTextSize();
    }

    private float getSubtitleTextSize(){
        TextView textView = getTextView(SUBTITLE);
        return textView.getTextSize();
    }

    private void centerizeTextView(final TextView textView){
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
