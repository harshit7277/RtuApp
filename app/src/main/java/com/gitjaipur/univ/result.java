package com.gitjaipur.univ;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class result extends Fragment implements IOnBackPressed {
    private Activity mActivity;
    boolean doubleBackToExitPressedOnce = false;
    public SwipeRefreshLayout swipe;
    public WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View myview = inflater.inflate( R.layout.result, container, false );

        mActivity = result.this.getActivity();

        final WebView webView = (WebView) myview.findViewById( R.id.webView );
        swipe = (SwipeRefreshLayout) myview.findViewById( R.id.swipe );
        swipe.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                webView.setInitialScale( 1 );
                webView.getSettings().setJavaScriptEnabled( true );
                webView.getSettings().setAppCacheEnabled( true );
                webView.getSettings().setLoadWithOverviewMode( true );
                webView.getSettings().setUseWideViewPort( true );
                webView.setScrollBarStyle( WebView.SCROLLBARS_OUTSIDE_OVERLAY );
                webView.setScrollbarFadingEnabled( false );
                webView.getSettings().setSupportZoom( false );
                webView.setWebChromeClient( new WebChromeClient() {
                    public void onProgressChanged(WebView webview , int progress) {
                        mActivity.setTitle( "Loading......" );
                        mActivity.setProgress( progress*100 );
                        if (progress==100)
                            mActivity.setTitle( R.string.app_name );

                    }
                });
                webView.loadUrl( "http://www.esuvidha.info/" );
                swipe.setRefreshing( true );
                webView.setWebViewClient( new WebViewClient() {

                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                        webView.loadUrl( "file:///android_assets/error.html" );

                    }

                    public void onPageFinished(WebView view, String url) {

                        swipe.setRefreshing( false );
                    }

                } );
            }
        } );


        webView.setInitialScale( 1 );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setAppCacheEnabled( true );
        webView.getSettings().setLoadWithOverviewMode( true );
        webView.getSettings().setUseWideViewPort( true );
        webView.setScrollBarStyle( WebView.SCROLLBARS_OUTSIDE_OVERLAY );
        webView.setScrollbarFadingEnabled( false );

        webView.loadUrl( "http://www.esuvidha.info/" );
        swipe.setRefreshing( true );
        webView.setWebChromeClient( new WebChromeClient(){
                   public void onProgressChanged(WebView webview,int progress){
                       mActivity.setTitle( "Loading..." );
                       mActivity.setProgress( progress*100 );
                       if (progress==100)
                           mActivity.setTitle( R.string.app_name );
                   }

        } );
        webView.setWebViewClient( new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                webView.loadUrl( "file:///android_assets/error.html" );

            }

            public void onPageFinished(WebView view, String url) {

                swipe.setRefreshing( false );
            }

        } );
        webView.setDownloadListener( new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse( url );
                Intent intent = new Intent( Intent.ACTION_VIEW, uri );
                startActivity( intent );

            }
            } );
        return myview;
    }


    @Override
    public void onBackPressed() {
        int fragments = getFragmentManager().getBackStackEntryCount();
        if (fragments > 0) {
            getFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText( getActivity(), "Please click BACK again to exit.", Toast.LENGTH_SHORT ).show();
            }
            new Handler().postDelayed( new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000 );
        } else {
            mActivity.onBackPressed();
            return;
        }


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate( R.menu.main_menu,menu );
        super.onCreateOptionsMenu( menu, inflater );
    }
}