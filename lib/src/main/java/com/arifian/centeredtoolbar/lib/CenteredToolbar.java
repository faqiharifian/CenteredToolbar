package com.arifian.centeredtoolbar.lib;

import android.content.Context;
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
                customizeToolbar(TITLE);
            }
        });
    }

    @Override
    public void setSubtitle(CharSequence title) {
        super.setSubtitle(title);

        this.post(new Runnable() {
            @Override
            public void run() {
                customizeToolbar(SUBTITLE);
            }
        });
    }

    public void customizeToolbar(String key){
        final CharSequence originalTitle = getTitle();
        final CharSequence originalSubtitle = getSubtitle();

        super.setTitle(TITLE);
        super.setSubtitle(SUBTITLE);

        for(int i = 0; i < getChildCount(); i++){
            View view = getChildAt(i);

            if(view instanceof TextView){
                final TextView textView = (TextView) view;

                if(textView.getText().equals(TITLE) && key.equals(TITLE)){
                    LayoutParams params = (LayoutParams) textView.getLayoutParams();
                    params.width = getWidth();
                    textView.setLayoutParams(params);
                    textView.setGravity(Gravity.CENTER);
                    textView.setX(getX());
                } else if(textView.getText().equals(SUBTITLE) && key.equals(SUBTITLE)){
                    LayoutParams params = (LayoutParams) textView.getLayoutParams();
                    params.width = getWidth();
                    textView.setLayoutParams(params);
                    textView.setGravity(Gravity.CENTER);
                    textView.setX(getX());
                }
            }
        }

        super.setTitle(originalTitle);
        super.setSubtitle(originalSubtitle);
    }
}
