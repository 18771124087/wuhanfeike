package com.xueye.pda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xueye.pda.model.BaseModel;
import com.xueye.pda.net.AsyncHttpUtils;
import com.xueye.pda.obj.UserObj;
import com.xueye.pda.ui.CheckoutWorkstationActivity;
import com.xueye.pda.ui.LoginActivity;
import com.xueye.pda.ui.MainActivity;
import com.xueye.pda.ui.NurseWorkstationActivity;
import com.xueye.pda.ui.PerCenterActivity;
import com.xueye.pda.ui.ReceiveWorkstationActivity;
import com.xueye.pda.ui.check.CheckActivity;
import com.xueye.pda.ui.collect.collectActivity;
import com.xueye.pda.ui.receive.ReceiveActivity;
import com.xueye.pda.utils.PreferencesUtil;
import com.xueye.pda.utils.SystemBarTintManager;

/**
 * @author wok
 * 
 *         BaseActivity
 **/
public abstract class BaseActivity extends Activity {

	private int mLayoutId = 0;
	public boolean title = true;
	public boolean bottom = true;
	public boolean mIsShow = true;
	public Toast mToast = null;

	protected ImageView button0, button1, button2, button3,button4;

	private List<AsyncHttpUtils> taskList = new ArrayList<AsyncHttpUtils>();

	public ImageView iv_left, iv_right;
	public TextView tv_name, tv_dept, tv_title_age, tv_bedno;
	public Button[] bts;

	public UserObj user = null;

	// ------------------------------------------------------------

	public BaseActivity(int resid) {
		mLayoutId = resid;
	}
 
	/**
	 * 是否显示title、bottom
	 */
	public BaseActivity(int resid, boolean title, boolean bottom) {
		mLayoutId = resid;
		this.title = title;
		this.bottom = bottom;
	}

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(mLayoutId);

		user = (UserObj) PreferencesUtil.getPreferences(this, "USER");

		
		getData();
		initTitle();
		initBottom();
		initView();
	}

	/**
	 * 初始化标题栏
	 */
	public void initTitle() {
		if (title) {
			// 沉浸式改变状态栏颜色，并且底部栏不沉浸
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				setTranslucentStatus(true);
				SystemBarTintManager tintManager = new SystemBarTintManager(
						this);
				tintManager.setStatusBarTintEnabled(true);
				tintManager.setStatusBarTintResource(R.color.green);// 通知栏所需颜色
			}
			iv_left = (ImageView) findViewById(R.id.iv_left);
			iv_right = (ImageView) findViewById(R.id.iv_right);
			tv_name = (TextView) findViewById(R.id.tv_name);
			tv_dept = (TextView) findViewById(R.id.tv_dept);
			tv_title_age = (TextView) findViewById(R.id.tv_title_age);
			tv_bedno = (TextView) findViewById(R.id.tv_badno);

//			tv_name.setText(user.getOprName());
//			tv_dept.setText(user.getDepartment());
//			
			iv_left.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
//					showToast("个人中心 - 修改密码");
					startActivity(PerCenterActivity.class);
		 		}
			});
			iv_right.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// Intent intent = new Intent(BaseActivity.this,
					// MainActivity.class);
					// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//
					// 回到主页并且销毁其他activity
					// startActivity(intent);

					 Builder dialog_info = new AlertDialog.Builder(BaseActivity.this);
			            dialog_info.setMessage("注销登录并退出？");
			            dialog_info.setNegativeButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								PreferencesUtil.setPreferences(BaseActivity.this,"USER", null);
								finish();
							}
						});
			            dialog_info.setPositiveButton("取消", null);
			            dialog_info.create().show();
				}
			});

		}
	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	/**
	 * 初始化底部栏
	 */
	protected void initBottom() {
		if (!bottom) {
			return;
		}
		bts = new Button[4];
		button0 = (ImageView) findViewById(R.id.bt_button0);
		button1 = (ImageView) findViewById(R.id.bt_button1);
		button2 = (ImageView) findViewById(R.id.bt_button2);
		button3 = (ImageView) findViewById(R.id.bt_button3);
		button4 = (ImageView) findViewById(R.id.bt_button4);
//		Log.e("==Role_no==", user.getRole_no());
		if("100".equals(user.getRole_no())){
			button1.setVisibility(View.VISIBLE);
		}else if("101".equals(user.getRole_no())){
			button2.setVisibility(View.VISIBLE);
			button3.setVisibility(View.VISIBLE);
			button4.setVisibility(View.VISIBLE);
		}else{
			
		}
//		bottomState();
		button0.setOnClickListener(clickListener);
		button1.setOnClickListener(clickListener);
		button2.setOnClickListener(clickListener);
		button3.setOnClickListener(clickListener);
		button4.setOnClickListener(clickListener);
	}
	
	private void bottomState(){

		button0.setImageResource(R.drawable.nav_chax);
		button1.setImageResource(R.drawable.nav_caij);
		button2.setImageResource(R.drawable.nav_songj);
		button3.setImageResource(R.drawable.nav_jies);
//		switch (i) {
//		case 0:
//			button0.setImageResource(R.drawable.home_select);
//			button1.setImageResource(R.drawable.outpatient);
//			button2.setImageResource(R.drawable.u);
//			button3.setImageResource(R.drawable.msg);
//			break;
//		case 1:
//			button0.setImageResource(R.drawable.home);
//			button1.setImageResource(R.drawable.outpatient_select);
//			button2.setImageResource(R.drawable.u);
//			button3.setImageResource(R.drawable.msg);
//			break;
//
//		case 2:
//			button0.setImageResource(R.drawable.home);
//			button1.setImageResource(R.drawable.outpatient);
//			button2.setImageResource(R.drawable.u_select);
//			button3.setImageResource(R.drawable.msg);
//			break;
//
//		case 3:
//			button0.setImageResource(R.drawable.home);
//			button1.setImageResource(R.drawable.outpatient);
//			button2.setImageResource(R.drawable.u);
//			button3.setImageResource(R.drawable.msg_select);
//			break;
//
//
//		default:
//			break;
//		}
	}

	/**
	 * 1048   采集+送检  
       1068  采集+送检
       1108  接受
	 */
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			// 首页查询
			case R.id.bt_button0:
//				bottomState(0);
				if (user == null) {
					startActivity(LoginActivity.class);
				} else {

					starthomeActivity(MainActivity.class);
				}

				break;
			// 采集
			case R.id.bt_button1:

//				bottomState(1);
				
//				if ("58".equals(user.getRole_no()) || "1068".equals(user.getRole_no()) || "1136".equals(user.getRole_no())) {
					starthomeActivity(NurseWorkstationActivity.class);
//				}else {
//					showToast("您没有采集权限！");
//				}
				break;
			// 送检
			case R.id.bt_button2:

//				bottomState(2);
//				if ("1108".equals(user.getRole_no())) {
					starthomeActivity(CheckoutWorkstationActivity.class);
//				}else {
//					showToast("您没有送检权限！");
//				}
				break;
			// 接收
			case R.id.bt_button3:

//				bottomState(3);
//				if ("1108".equals(user.getRole_no())) {
				
				
				

				showToast("正在建设中..");
//					starthomeActivity(ReceiveWorkstationActivity.class);
					
					
					
					
//				}else {
//					showToast("您没有接收权限！");
//				}

				break;
			// 报告
			case R.id.bt_button4:

					showToast("正在建设中..");

				break;
			}
		}
	};


		/**
	 * 获取activity页面所需初始数据
	 */
	public abstract void getData();

	/**
	 * 网络请求成功返回方法
	 */
	public void onSuccess(BaseModel resModel) {

	};

	/**
	 * 失败返回 Msg
	 */
	public void onFail(BaseModel resModel) {
		if (resModel != null) {
			showToast(resModel.getMsg());
		}
	};

	/**
	 * 初始化控件，设置监听事件
	 */
	public abstract void initView();

	/**
	 * 显示提示框
	 * 
	 * @param msg
	 */
	public void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(BaseActivity.this, "", Toast.LENGTH_LONG);
		}
		mToast.setText(msg);
		mToast.show();
	}

	/**
	 * 网络请求方法
	 * 
	 * @param infCord
	 * @param params
	 */
	public void getDataFromNet(int infCord, String... params) {
		AsyncHttpUtils http = new AsyncHttpUtils(this, infCord);
		http.execute(params);
		taskList.add(http);
	}

	/**
	 * 网络请求方法
	 * 
	 * @param infCord
	 * @param showProgressDialog
	 * @param params
	 */
	public void getDataFromNet(int infCord, boolean showProgressDialog,
			String... params) {
		AsyncHttpUtils http = new AsyncHttpUtils(this, infCord,
				showProgressDialog);
		http.execute(params);
		taskList.add(http);
	}

	/**
	 * 跳转activity
	 * 
	 * @param cls
	 */
	public void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	/**
	 * 跳转activity并携带数据
	 * 
	 * @param cls
	 * @param obj
	 */
	public void startActivity(Class<?> cls, Object obj) {
		Intent intent = new Intent(this, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		if (obj != null) {
			intent.putExtra("data", (Serializable) obj);
		}
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
	
//	Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK


	/**
	 * 跳转activity并清空栈内所有的activity
	 * 
	 * @param cls
	 * @param obj
	 */
	public void starthomeActivity(Class<?> cls) {
		Intent intent = new Intent(this, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
	
	protected void onDestroy() {
		if (taskList != null && !taskList.isEmpty()) {
			for (AsyncHttpUtils task : taskList) {
				task.cancel(true);
			}
		}

		super.onDestroy();
	}
}
