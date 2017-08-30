package com.jiahaoliuliu.akami.test.transactionslist;

import android.app.Application;

import com.jiahaoliuliu.akami.BaseApplication;
import com.jiahaoliuliu.akami.BuildConfig;
import com.jiahaoliuliu.akami.MainApplication;
import com.jiahaoliuliu.akami.MainTestApplication;
import com.jiahaoliuliu.akami.di.AppTestComponent;
import com.jiahaoliuliu.akami.transactionslist.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by jiahaoliuliu on 7/15/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName = "com.jiahaoliuliu.akami",
        application = MainTestApplication.class)
public class MainActivityTest {


    private MainActivity mMainActivity;

    @Before
    public void setup() {
        (((MainTestApplication) BaseApplication.getInstance())
                .getComponent()).inject(this);

        mMainActivity = Robolectric.buildActivity(MainActivity.class).setup().get();
    }

    @Test
    public void simpleTest() {
        assertTrue(true);
        assertThat(mMainActivity).isNotNull();
    }
}
