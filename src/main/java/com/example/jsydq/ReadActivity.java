package com.example.jsydq;

import android.os.Bundle;
import android.app.Activity;

import com.example.jsydq.bz.BZContainer;

public class ReadActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BZContainer container = new BZContainer(this);
        container.initDraw();
    }
}
