package com.jiahaoliuliu.akami.transactionslist;

import com.jiahaoliuliu.akami.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by jiahaoliuliu on 7/15/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity mMainActivity;

    @Before
    public void setup() {
//        mMainActivity = Robolectric.buildActivity(MainActivity.class).setup().get();
    }

    @Test
    public void simpleTest() {
        assertTrue(true);
//        assertThat(mMainActivity).isNotNull();
    }
}
