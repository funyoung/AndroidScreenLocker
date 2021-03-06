package com.github.funyoung.looker.locker;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.github.funyoung.looker.R;
import com.github.funyoung.looker.util.Validator;
import com.github.funyoung.text.ArticleParser;

import java.util.List;

/**
 * custom launcher home
 * Note: This Activity is not the lock-screen page,but it is important to help implementing lock screen.
 *
 */
public class MainHomeActivity extends Activity {
    private String mPackageName;
    private String mClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, LockerService.class);
        startService(intent);

        getLauncherPackageName(this);

        if (!LockerActivity.isLocked) {
            if (Validator.isEffective(mPackageName) && Validator.isEffective(mClassName)) {
                Intent systemIntent = new Intent();
                systemIntent.setComponent(new ComponentName(mPackageName, mClassName));
                startActivity(systemIntent);
            }
        }

        moveTaskToBack(false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /**
     * get the system launcher package name and class name,we use system launcher as default<br/>
     * also we can let user to choose a launcher from system launcher and our custom launchers,
     * "BaiDu" lock screen app and "Go" lock screen app just do this.<br/>
     *
     * @param context the context to get package name for
     */
    private void getLauncherPackageName(@NonNull Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);
        if (resInfoList != null) {
            ResolveInfo resInfo;
            for (int i = 0; i < resInfoList.size(); i++) {
                resInfo = resInfoList.get(i);
                if ((resInfo.activityInfo.applicationInfo.flags &
                        ApplicationInfo.FLAG_SYSTEM) > 0) {
                    mPackageName = resInfo.activityInfo.packageName;
                    mClassName = resInfo.activityInfo.name;

                    break;
                }
            }
        }
    }
}
