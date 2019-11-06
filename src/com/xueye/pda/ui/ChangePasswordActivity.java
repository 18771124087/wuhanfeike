package com.xueye.pda.ui;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;
import com.xueye.pda.finals.InterfaceFinals;
import com.xueye.pda.model.BaseModel;
import com.xueye.pda.ui.collect.collectActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 个人信息-修改密码
 * 
 * @author wok
 * 
 */
public class ChangePasswordActivity extends BaseActivity implements
		OnClickListener {

	private EditText et_old_password, et_new_password, et_confirm_password;
	private Button bt_change;
	private String newPassword;

	public ChangePasswordActivity() {
		super(R.layout.act_change_password,false,false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_change:
			changePassword();
			
			break;

		default:
			break;
		}

	}

	@Override
	public void onSuccess(BaseModel resModel) {

//		switch (infCode) {
//		case InterfaceFinals.INF_UPDATE_PASSWORD:
//			user.setPassword(newPassword);
//			PreferencesUtil.setPreferences(this, PreferenceFinals.KEY_USER,user);
//			showToast("修改成功");
//			finish();
//			break;
//
//		default:
//			break;
//		}
		int infCode=resModel.getInfCord();
		switch (infCode) {
		
	    case InterfaceFinals.ChangePsw:
		 Builder dialog = new AlertDialog.Builder(ChangePasswordActivity.this);
		 dialog.setCancelable(false);
           dialog.setTitle("提示");
           dialog.setMessage(resModel.getMsg()).setPositiveButton("关闭", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					finish();
				}
			});
           dialog.create().show();
           break;
           
		}
       

	}

	@Override
	public void initView() {

		et_old_password = (EditText) findViewById(R.id.et_old_password);
		et_new_password = (EditText) findViewById(R.id.et_new_password);
		et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
		bt_change = (Button) findViewById(R.id.bt_change);

		bt_change.setOnClickListener(this);
	}

	@Override
	public void getData() {

	}

	/**
	 * 修改密码
	 */
	private void changePassword() {
		String oldPassword = et_old_password.getText().toString().trim();
		newPassword = et_new_password.getText().toString().trim();
		String confirmPassword = et_confirm_password.getText().toString().trim();

		if (oldPassword.length() == 0) {
			showToast("旧密码不能为空");
			return;
		}
		if (newPassword.length() == 0) {
			showToast("新密码不能为空");
			return;
		}
		if (confirmPassword.length() == 0) {
			showToast("确认密码不能为空");
			return;
		}
		if (!newPassword.equals(confirmPassword)) {
			showToast("2次密码不一致");
			return;
		}
		getDataFromNet(InterfaceFinals.ChangePsw,user.getOprId(),oldPassword,newPassword);
	}

}
