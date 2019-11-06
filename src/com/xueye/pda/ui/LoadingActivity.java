package com.xueye.pda.ui;

import org.apache.http.Header;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;

/**
 * 加载页
 * 
 * @author wok
 * 
 */
public class LoadingActivity extends BaseActivity {
	private ImageView loading_imageview;

	public LoadingActivity() {
		super(R.layout.act_loading, false, false);

	}

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				if (user == null) {
//					startActivity(MainActivity.class);
					startActivity(LoginActivity.class);
				} else {
					if ("100".equals(user.getRole_no())) {
//						startActivity(CheckoutWorkstationActivity.class);
						startActivity(NurseWorkstationActivity.class);
					}else if("101".equals(user.getRole_no())){
						startActivity(CheckoutWorkstationActivity.class);
					}else{
						showToast("该账号未启用，请联系网络管理员");
					}
					
				}
				finish();
				break;

			default:
				break;
			}

		};
	};

	@Override
	public void getData() {

		Message message = new Message();
		message.what = 1;
		mHandler.sendMessageDelayed(message, 2000);

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
