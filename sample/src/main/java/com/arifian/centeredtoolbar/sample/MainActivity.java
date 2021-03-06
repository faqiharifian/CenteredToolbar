package com.arifian.centeredtoolbar.sample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arifian.centeredtoolbar.R;
import com.arifian.centeredtoolbar.CenteredToolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CenteredToolbar toolbar = (CenteredToolbar) findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(Color.BLACK);
// SpannableStringBuilder sBuilder = new SpannableStringBuilder();
//        sBuilder.append("Hello!") // Bold this
//                .append("I use Calligraphy"); // Default TextView font.
//        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "fonts/Oswald-Stencbab.ttf"));

//        sBuilder.setSpan(typefaceSpan, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        toolbar.getTitleTextView().setText(sBuilder, TextView.BufferType.SPANNABLE);

        setSupportActionBar(toolbar);
//        CalligraphyConfig.initDefault("fonts/your-font.ttf");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        getSupportActionBar().setTitle(sBuilder);
        getSupportActionBar().setTitle("nfrej i");
        getSupportActionBar().setSubtitle("fejiwf");


//        toolbar.setTitleTextSize(getResources().getDimensionPixelSize(R.dimen.text));
//        toolbar.getTitleTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text));
//        toolbar.setTitleTypeface("fonts/Oswald-Stencbab.ttf");

//        toolbar.setTitleTextAppearance(this, R.style.textAppearance);
//        ((TextView) findViewById(R.id.text)).setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
