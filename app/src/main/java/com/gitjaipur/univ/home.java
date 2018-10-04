package com.gitjaipur.univ;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class home extends Fragment {
    public SwipeRefreshLayout swipe;
    public WebView webView;
    public Activity mActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View myview = inflater.inflate(R.layout.home, container,false);
        mActivity = home.this.getActivity();
        final WebView webView = (WebView)myview.findViewById(R.id.webView);
        swipe = (SwipeRefreshLayout) myview.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                webView.setInitialScale(1);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setAppCacheEnabled(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                webView.setScrollbarFadingEnabled(false);
                webView.setWebChromeClient(new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress)
                    {
                        //Make the bar disappear after URL is loaded, and changes string to Loading...
                        mActivity.setTitle("Loading...");
                        mActivity.setProgress(progress * 100); //Make the bar disappear after URL is loaded

                        // Return the app name after finish loading
                        if(progress == 100)
                            mActivity.setTitle(R.string.app_name);
                    }
                });
                webView.setOnKeyListener(new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event)
                    {
                        if(event.getAction() == KeyEvent.ACTION_DOWN)
                        {
                            WebView webView = (WebView) v;

                            switch(keyCode)
                            {
                                case KeyEvent.KEYCODE_BACK:
                                    if(webView.canGoBack())
                                    {
                                        webView.goBack();
                                        return true;
                                    }
                                    break;
                            }
                        }

                        return false;
                    }
                });

                webView.loadUrl("http://www.rtu.ac.in/RTU/");
                swipe.setRefreshing(true);
                webView.setWebViewClient(new WebViewClient(){

                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                        webView.loadUrl("file:///android_assets/error.html");

                    }

                    public void onPageFinished(WebView view, String url) {

                        swipe.setRefreshing(false);
                    }

                });
            }
        });


        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                mActivity.setTitle("Loading...");
                mActivity.setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if(progress == 100)
                    mActivity.setTitle(R.string.app_name);
            }
        });
        webView.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    WebView webView = (WebView) v;

                    switch(keyCode)
                    {
                        case KeyEvent.KEYCODE_BACK:
                            if(webView.canGoBack())
                            {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });

        webView.loadUrl("http://www.rtu.ac.in/RTU/");
        swipe.setRefreshing(true);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                mActivity.setTitle("Loading...");
                mActivity.setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if(progress == 100)
                    mActivity.setTitle(R.string.app_name);
            }
        });
        webView.setWebViewClient(new WebViewClient(){

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                webView.loadUrl("http://www.rtu.ac.in/RTU/");

            }

            public void onPageFinished(WebView view, String url) {

                swipe.setRefreshing(false);
            }

        });




        webView.setDownloadListener( new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDescription,
                                        String mimetype, long contentLength) {

                Uri uri = Uri.parse( url );
                Intent intent = new Intent( Intent.ACTION_VIEW, uri );
                startActivity( intent );
            }
        } );
        return myview;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate( R.menu.main_menu,menu );
        super.onCreateOptionsMenu( menu, inflater );
    }
}

