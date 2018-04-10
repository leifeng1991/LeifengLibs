package com.leifeng.module.news.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.leifeng.lib.utils.LogUtil;
import com.leifeng.module.news.R;

@Route(path = "/news/list")
public class NewsListActivity extends AppCompatActivity {
    // ARouter会自动对字段进行赋值，无需主动获取
    @Autowired
    String age = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        // 注入
        ARouter.getInstance().inject(this);
        String uriStr = getIntent().getStringExtra(ARouter.RAW_URI);
        String name = getIntent().getStringExtra("name");
        LogUtil.e("========" + uriStr);
        LogUtil.e("========" + name);
        LogUtil.e("========" + age);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("name", "张三");
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
