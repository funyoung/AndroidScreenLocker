package com.github.funyoung.looker.locker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.funyoung.looker.R;
import com.github.funyoung.looker.util.TimeUtil;
import com.github.funyoung.looker.util.ToastUtil;
import com.github.funyoung.text.ArticleParagraphModel;
import com.github.funyoung.text.ArticleParser;

/**
 * if the screen is locked, this Activity will show.
 *
 */
public class LockerActivity extends Activity {

    /**
     * check whether the screen is locked or not
     */
    public static boolean isLocked = false;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        setContentView(R.layout.activity_locker);

        isLocked = true;

        /*
      click this ImageView to unlock screen
     */
        View iv_key = findViewById(R.id.iv_key);
        /*
      this TextView is used to show current time as an example, also you can show any thing on the {@link LockerActivity} you want to
     */
        TextView tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setText(TimeUtil.getTime());

        iv_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate();
                isLocked = false;
                ToastUtil.show(getApplicationContext(), "");
                finish();
            }
        });

        try {
            startService(new Intent(this, LockerService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        textView = (TextView)findViewById(R.id.prompt_text);
    }

    private void displayLearningText() {
        if (null != textView) {
//            ArticleCharModel model = ArticleParser.getInstance().getArticleCharModel();
//            textView.setText(model.indexInfo());
            ArticleParagraphModel model = ArticleParser.getInstance().getParagraphModel();
            textView.setText(model.randomSentence());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayLearningText();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        // Key code constant: Home key. This key is handled by the framework and is never delivered to applications.
        return (keyCode == KeyEvent.KEYCODE_HOME) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onBackPressed() {
        //return;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * vibrate means that the screen is unlocked success
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
