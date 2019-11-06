package com.xueye.pda.ui;

import java.util.Set;

import org.apache.http.Header;

import android.view.View;
import android.widget.TextView;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;


/**
 * @author wok
 *
 * 个人中心
 */
public class PerCenterActivity extends BaseActivity implements android.view.View.OnClickListener{

	TextView per_usernum,per_username,per_userdept;

	public PerCenterActivity() {
		super(R.layout.act_percenter,false,false);
	}
	
	
	public void getData() {

	}
 
	public void initView() {
		per_usernum = (TextView) findViewById(R.id.per_usernum);
		per_username = (TextView) findViewById(R.id.per_username);
		per_userdept = (TextView) findViewById(R.id.per_userdept);
		
		per_usernum.setText(user.getOprId());
		per_username.setText(user.getOprName());
		per_userdept.setText(user.getDepartment());
		
		findViewById(R.id.btn_updatePsd).setOnClickListener(this);
		findViewById(R.id.btn_exitLog).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_updatePsd: //修改密码
			startActivity(ChangePasswordActivity.class);
			break;
		case R.id.btn_exitLog: //退出
			finish();
			break;

		default:
			break;
		}
	}

	

}
