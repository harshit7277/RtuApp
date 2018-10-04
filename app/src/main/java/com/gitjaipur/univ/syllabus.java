package com.gitjaipur.univ;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class syllabus extends Fragment {


    public WebView webView;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.syllabus,
                container, false);
        final String[] items = new String[]{"First year", "CS", "EE", "ME",
                "ECE", "CIVIL"};
        webView = rootView.findViewById(R.id.webView);
        ListView lv = rootView.findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.listview, items);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView) view).getText().toString();


                if (item.equalsIgnoreCase("First year")) {

                    String pdf = "http://testing727732.000webhostapp.com/pdf/Syllabus.pdf";
                    openPdf(pdf);

                } else if (item.equalsIgnoreCase("CS")) {

                    String pdf = "http://testing727732.000webhostapp.com/pdf/cs.pdf";
                    openPdf(pdf);
                } else if (item.equalsIgnoreCase("EE")) {

                    String pdf = "http://testing727732.000webhostapp.com/pdf/ee.pdf";
                    openPdf(pdf);
                } else if (item.equalsIgnoreCase("Me")) {

                    String pdf = "http://testing727732.000webhostapp.com/pdf/me.pdf";
                    openPdf(pdf);
                } else if (item.equalsIgnoreCase("ECE")) {

                    String pdf = "http://testing727732.000webhostapp.com/pdf/ece.pdf";
                    openPdf(pdf);
                } else {

                    String pdf = "http://testing727732.000webhostapp.com/pdf/civil.pdf";
                    openPdf(pdf);
                }


            }
        });


        lv.setAdapter(adapter);
        return rootView;


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    public void openPdf(String pdf) {
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.loadUrl(pdf);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webview, int progress) {
                getActivity().setTitle("Loading...");
                getActivity().setProgress(progress * 100);
                if (progress == 100)
                    getActivity().setTitle(R.string.app_name);
            }

        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

    }
}