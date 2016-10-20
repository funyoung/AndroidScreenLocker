package com.github.funyoung.looker.locker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.funyoung.looker.R;
import com.github.funyoung.text.ArticleParser;

/**
 * This Activity is only used to start {@link LockerService}, it's unnecessary for lock screen
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * start lock screen service
                 */
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LockerService.class);
                startService(intent);
                Toast.makeText(MainActivity.this, "锁屏服务已启动，请先关闭屏幕然后再打开屏幕进行测试", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        displayLearningText();
    }

    private void displayLearningText() {
        TextView textView = (TextView)findViewById(R.id.prompt_text);
        if (null != textView) {
            String result = ArticleParser.getInstance().parse(getResources().openRawResource(R.raw.greatlearning));
            textView.setText(result);
        }
    }

}
