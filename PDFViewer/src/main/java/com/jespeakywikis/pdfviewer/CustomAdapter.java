package com.jespeakywikis.pdfviewer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
//import com.github.chrisbanes.photoview.PhotoView;
import java.io.File;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private String cacheFilePath;
    private PdfRenderer pdfRenderer;
    private RecyclerView recyclerView;
    private int count = 0;
    private ParcelFileDescriptor fileDescriptor;
    private BroadcastReceiver aLBReceiver;


    CustomAdapter(Context context, String cacheFile, RecyclerView recyclerView)
    {
        this.context = context;
        this.cacheFilePath = cacheFile;
        this.recyclerView = recyclerView;
        aLBReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onDetached();
            }
        };
        LocalBroadcastManager.getInstance(context).registerReceiver(aLBReceiver, new IntentFilter("custom-action"));
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)  {
        PdfRenderer.Page currentPage;
        try {

            if (pdfRenderer.getPageCount() <= position) {
                Toast.makeText(context, "Error: Cannot Open Page", Toast.LENGTH_SHORT).show();
                return;
            }
            currentPage = pdfRenderer.openPage(position);

            final Bitmap bitmap = Bitmap.createBitmap(recyclerView.getWidth(), (int) ((float) recyclerView.getWidth() / currentPage.getWidth() * currentPage.getHeight()),
                    Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap, 0f, 0f, null);

            currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            holder.imageView.setImageBitmap(bitmap);

            if(null != currentPage)
            {
                currentPage.close();
                ((SimplePDFActivity)context).getProgressBar().setVisibility(View.GONE);
            }

        } catch ( Exception e) {

            Toast.makeText(context, "Error Loading", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public int getItemCount() {
        if(count == 0) {
            try {
                fileDescriptor = ParcelFileDescriptor.open(new File(cacheFilePath), ParcelFileDescriptor.MODE_READ_ONLY);
                pdfRenderer = new PdfRenderer(fileDescriptor);
                count = pdfRenderer.getPageCount();
            } catch (Exception e) {
                Toast.makeText(context, "Error: Cant Read PDF", Toast.LENGTH_SHORT).show();
            }
        }
        ((SimplePDFActivity)context).totalPage = count;
        return count <= 0 ? 0 : count;
    }


    public void onDetached() {
        new File(cacheFilePath).delete();
        try {
            pdfRenderer.close();
            fileDescriptor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Error Try ReOpen", Toast.LENGTH_SHORT).show();
            return;
        }
        LocalBroadcastManager.getInstance(context).unregisterReceiver(aLBReceiver);
        ((SimplePDFActivity)context).finish();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //PhotoView imageView;
        ImageView imageView;

        public MyViewHolder (View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }

    }
}

