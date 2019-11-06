package com.xueye.pda;

import java.util.List;

import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;


/**
 * App上下文
 * 
 * @author wok
 * 
 */
public class MyApplication extends Application {
	protected List<RunningTaskInfo> taskList;
	private static MyApplication mApplication = null;


	public static MyApplication getInstance() {
		return mApplication;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		initApplication();

	}

	private void initApplication() {
		if (null == mApplication) {
			mApplication = this;
		}
	}

}
