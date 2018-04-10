package com.leifeng.lib.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.leifeng.lib.R;
import com.leifeng.lib.constants.BaseConstants;
import com.leifeng.lib.utils.LogUtil;

/**
 * 描述:
 *
 * @author leifeng
 * 2018/4/4 14:18
 */

public class BaseWebActivity extends BaseActivity {
    protected AgentWeb mAgentWeb;
    private LinearLayout mLinearLayout;
    private AlertDialog mAlertDialog;
    private WebViewClient mWebViewClient;
    private WebChromeClient mWebChromeClient;

    /**
     * @param title 标题
     * @param url   请求web地址
     */
    public static Intent newIntent(Context context, String title, String url) {
        Intent intent = new Intent(context, BaseWebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(BaseConstants.S.TITLE, title);
        bundle.putString(BaseConstants.S.URL, url);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_base_web);
    }

    @Override
    protected void initView() {
        mLinearLayout = findViewById(R.id.container);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        // 标题
        String title = intent.getStringExtra(BaseConstants.S.TITLE);
        // 设置标题
        setTitleName(title);
        // 请求的web地址
        String url = intent.getStringExtra(BaseConstants.S.URL);
        long p = System.currentTimeMillis();
        // 初始化web
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(url);

        //mAgentWeb.getUrlLoader().loadUrl(getUrl());
        long n = System.currentTimeMillis();
        Log.i("Info", "init used time:" + (n - p));
        // js交互
        if (mAgentWeb != null) {
            //注入对象
            mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mAgentWeb, mActivity));
            /*调用*/
            mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroid");
        }

    }

    @Override
    protected void setListener() {
        mWebViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //do you  work
                LogUtil.i("Info", "BaseWebActivity onPageStarted");
            }
        };
        mWebChromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //do you work
                LogUtil.i("Info", "onProgress:" + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitleName(title);
            }
        };
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    /**
     * 弹框提示
     */
    private void showDialog() {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setMessage("您确定要关闭该页面吗?")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                        }
                    })//
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                            BaseWebActivity.this.finish();
                        }
                    }).create();
        }
        mAlertDialog.show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*跟随 Activity Or Fragment 生命周期 ， 释放 CPU 更省电 。*/
    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.i("Info", "onResult:" + requestCode + " onResult:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    class AndroidInterface {
        private Handler deliver = new Handler(Looper.getMainLooper());
        private AgentWeb agent;
        private Context context;

        public AndroidInterface(AgentWeb agent, Context context) {
            this.agent = agent;
            this.context = context;
        }


        @JavascriptInterface
        public void callAndroid(final String msg) {

            deliver.post(new Runnable() {
                @Override
                public void run() {

                    Log.i("Info", "main Thread:" + Thread.currentThread());
                    Toast.makeText(context.getApplicationContext(), "" + msg, Toast.LENGTH_LONG).show();
                }
            });


            Log.i("Info", "Thread:" + Thread.currentThread());

        }


    }
}
