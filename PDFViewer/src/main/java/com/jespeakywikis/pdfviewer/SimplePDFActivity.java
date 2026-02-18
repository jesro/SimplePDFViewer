package com.jespeakywikis.pdfviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimplePDFActivity extends AppCompatActivity {

    private Handler uiThread;
    private final int KB = 1024;
    private final int BUFFERLEN = 1 * KB;
    private final int NOTIFY = 150 * KB;
    private RecyclerView recyclerView;
    private MenuItem itemMenu;
    private ProgressBar progressBar;
    protected int totalPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_p_d_f);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String userFileUrl = getIntent().getStringExtra("userInputUrl");
        if (userFileUrl.equals("")){
            Toast.makeText(this, "Url Field is empty", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        uiThread = new Handler();
        pdfFromUrl(userFileUrl, new File(this.getCacheDir(), userFileUrl.substring(userFileUrl.lastIndexOf('/') + 1)).getAbsolutePath());
    }

    private void pdfFromUrl(final String url, final String destinationPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(destinationPath);
                    FileOutputStream fileOutput = new FileOutputStream(file);
                    HttpURLConnection urlConnection;
                    URL urlObj = new URL(url);
                    urlConnection = (HttpURLConnection) urlObj.openConnection();
                    int counter = 0;
                    byte[] buffer = new byte[BUFFERLEN];
                    int bufferLength = 0;
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    while ((bufferLength = in.read(buffer)) > 0) {
                        fileOutput.write(buffer, 0, bufferLength);
                        counter += bufferLength;
                        if (counter > NOTIFY) {
                            counter = 0;
                        }
                    }

                    urlConnection.disconnect();
                    fileOutput.close();

                    onSuccessListener(destinationPath);
                } catch (Exception e) {
                    onFailedListener();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }).start();
    }

    private void onSuccessListener(final String destinationPath) {
        if (uiThread == null) {
            return;
        }

        uiThread.post(new Runnable() {
            @Override
            public void run() {
                try {
                    pdfViewer(destinationPath);
                } catch (Exception e) {
                    Toast.makeText(SimplePDFActivity.this, "Error Try ReOpen", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void onFailedListener() {
        if (uiThread == null) {
            return;
        }

        uiThread.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SimplePDFActivity.this, "Error: Loading Failed", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void pdfViewer(String cacheFilePath) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int position = 0;
                for(int i=lm.findFirstVisibleItemPosition();i<=lm.findLastVisibleItemPosition();i++)
                {
                    View view = lm.findViewByPosition(i);
                    position = lm.getPosition(view);
                }
                itemMenu.setTitle((position+1)+"/"+totalPage);
            }
        });
        CustomAdapter customAdapter = new CustomAdapter(SimplePDFActivity.this, cacheFilePath, recyclerView);
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("custom-action"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("custom-action"));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        itemMenu = menu.findItem(R.id.page);
        return super.onPrepareOptionsMenu(menu);
    }


    protected ProgressBar getProgressBar()
    {
        return progressBar;
    }

}