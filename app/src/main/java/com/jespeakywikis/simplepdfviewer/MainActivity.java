package com.jespeakywikis.simplepdfviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jespeakywikis.pdfviewer.PDFView;
import com.jespeakywikis.pdfviewer.SimplePDFActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new PDFView(this,"http://www.pdf995.com/samples/pdf.pdf");
    }
}