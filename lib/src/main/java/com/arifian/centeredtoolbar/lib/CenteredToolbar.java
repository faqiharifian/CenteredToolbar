package com.arifian.centeredtoolbar.lib;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
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

    private final int INVALID_UNIT = -7423894;

    public CenteredToolbar(Context context) {
        this(context, null);
    }

    public CenteredToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
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

    private TextView getTextView(String what){
        final CharSequence originalTitle = getTitle();
        final CharSequence originalSubtitle = getSubtitle();

        super.setTitle(TITLE);
        super.setSubtitle(SUBTITLE);

        for(int i = 0; i < getChildCount(); i++){
            View view = getChildAt(i);

            if(view instanceof TextView){
                final TextView textView = (TextView) view;

                if(textView.getText().equals(TITLE) && what.equals(TITLE)){
                    return textView;
                } else if(textView.getText().equals(SUBTITLE) && what.equals(SUBTITLE)){
                    return textView;
                }
            }
        }

        super.setTitle(originalTitle);
        super.setSubtitle(originalSubtitle);

        return null;
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
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), path);
        textView.setTypeface(font);
    }

    private void changeSize(TextView textView, int unit, float size){
        if(unit == INVALID_UNIT)
            textView.setTextSize(size);
        else
            textView.setTextSize(unit, size);
    }
}
