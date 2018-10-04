package com.gitjaipur.univ;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class questions extends Fragment implements IOnBackPressed {
    private Context mContext;
    private Activity mActivity;

    private LinearLayout mRootLayout;
    private WebView mWebView;


    private static final int MY_PERMISSION_REQUEST_CODE = 123;
    private boolean doubleBackToExitPressedOnce=false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myview = inflater.inflate( R.layout.questions, container, false );


        mContext = myview.getContext();
        mActivity = questions.this.getActivity();



        // Get the widget reference from xml layout

        mWebView = (WebView)myview. findViewById(R.id.webView );


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();





        // Check permission for write external storage
        checkPermission();
        // The target url to surf using web view
        final String url = "http://rtupaper.com";
        // Load the url in web view


        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) myview.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mWebView.setInitialScale(1);
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.getSettings().setAppCacheEnabled(true);
                mWebView.getSettings().setLoadWithOverviewMode(true);
                mWebView.getSettings().setUseWideViewPort(true);
                mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                mWebView.setScrollbarFadingEnabled(false);
                mWebView.setWebChromeClient(new WebChromeClient() {
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
                mWebView.setOnKeyListener(new View.OnKeyListener()
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

                mWebView.loadUrl(url);
                swipe.setRefreshing(true);
                mWebView.setWebViewClient(new WebViewClient(){

                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                        mWebView.loadUrl("file:///android_assets/error.html");

                    }

                    public void onPageFinished(WebView view, String url) {

                        swipe.setRefreshing(false);
                    }

                });
            }
        });
        // Enable java script on web view
        mWebView.setInitialScale(1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(false);
        mWebView.setWebChromeClient(new WebChromeClient() {
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
        mWebView.setOnKeyListener(new View.OnKeyListener()
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

        mWebView.loadUrl(url);
        swipe.setRefreshing(true);
        mWebView.setWebViewClient(new WebViewClient(){

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                mWebView.loadUrl("file:///android_assets/error.html");

            }

            public void onPageFinished(WebView view, String url) {

                swipe.setRefreshing(false);
            }

        });



        mWebView.setDownloadListener( new DownloadListener() {
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




    public void checkPermission(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if(shouldShowRequestPermissionRationale( WRITE_EXTERNAL_STORAGE)){
                    // show an alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setMessage("Write external storage permission is required.");
                    builder.setTitle("Please grant permission");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(
                                    mActivity,
                                    new String[]{WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSION_REQUEST_CODE
                            );
                        }
                    });
                    builder.setNeutralButton("Cancel",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    // Request permission
                    ActivityCompat.requestPermissions(
                            mActivity,
                            new String[]{WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSION_REQUEST_CODE
                    );
                }
            }else {
                // Permission already granted
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch(requestCode){
            case MY_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Permission granted
                }else {
                    // Permission denied
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        int fragments = getFragmentManager().getBackStackEntryCount();
        if (fragments > 0) {
            getFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(getActivity(),"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            mActivity.onBackPressed();
            return;
        }
    }


    private class WebViewClientDemo extends WebViewClient {
        @Override

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate( R.menu.main_menu,menu );
        super.onCreateOptionsMenu( menu, inflater );
    }

}
