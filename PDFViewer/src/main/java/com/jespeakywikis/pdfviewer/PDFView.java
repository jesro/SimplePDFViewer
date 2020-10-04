package com.jespeakywikis.pdfviewer;

import android.content.Context;
import android.content.Intent;

public class PDFView {
    private Context context;
    private String filePath;

    public PDFView(Context context, String filePath){
        this.context = context;
        this.filePath = filePath;
        Intent intent = new Intent(this.context, SimplePDFActivity.class);
        intent.putExtra("userInputUrl",this.filePath);
        this.context.startActivity(intent);
    }
}
