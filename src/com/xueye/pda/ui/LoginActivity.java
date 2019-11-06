package com.xueye.pda.ui;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;
import com.xueye.pda.finals.InterfaceFinals;
import com.xueye.pda.model.BaseModel;
import com.xueye.pda.model.UserModel;
import com.xueye.pda.obj.UserObj;
import com.xueye.pda.utils.PreferencesUtil;


/**
 * @author wok
 * 
 *         登录
 **/
public class LoginActivity extends BaseActivity implements OnClickListener {

	EditText et_login_name, et_login_pwd;
	TextView iv_login;

	public LoginActivity() {
		super(R.layout.act_login,false,false);
	} 

	public void onSuccess(BaseModel resModel) {
		super.onSuccess(resModel);
		int infCode = resModel.getInfCord();
		switch (infCode) {
		case InterfaceFinals.Login:

			user = ((UserModel)resModel).getData();

			showToast("登陆成功!");
			PreferencesUtil.setPreferences(this, "USER", user); // 将user对象存入sharedPreferences

			if ("100".equals(user.getRole_no())) {
				startActivity(NurseWorkstationActivity.class);
				finish();
//				startActivity(CheckoutWorkstationActivity.class);
//				finish();
			}else if("101".equals(user.getRole_no())){
				startActivity(CheckoutWorkstationActivity.class);
				finish();
			}else{
				showToast("该账号未启用，请联系网络管理员");
			}
			
			break;

		default:
			break;
		}

	}

	public void getData() {

	}

	public void initView() {
		et_login_name = (EditText) findViewById(R.id.et_login_name);
		et_login_pwd = (EditText) findViewById(R.id.et_login_pwd);
		iv_login = (TextView) findViewById(R.id.iv_login);
		
		iv_login.setOnClickListener(this);
	}

	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.iv_login:
			String userName = et_login_name.getText().toString().trim();
			String passWord = et_login_pwd.getText().toString().trim();

			if (userName == null || "".equals(userName)) {
				showToast("用户名不能为空!");
			} else if (passWord == null || "".equals(passWord)) {
				showToast("密码不能为空!");
			} else if (passWord.length() > 30) {
				showToast("密码过长!");
			} else {
//				passWord = MyMD5.getMD5(passWord);
				// 请求参数 - lxdh , passWord
				getDataFromNet(InterfaceFinals.Login, userName, passWord);
//				et_login_name.setText("");
				et_login_pwd.setText("");
			}
			break;



		default:
			break;
		}
	}



}
