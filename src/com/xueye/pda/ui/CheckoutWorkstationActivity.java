package com.xueye.pda.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;
import com.xueye.pda.adapter.NuresWorkstationAdapter;
import com.xueye.pda.finals.InterfaceFinals;
import com.xueye.pda.model.BaseModel;
import com.xueye.pda.model.NurseWorkstationModel;
import com.xueye.pda.model.NurseWorkstationUserModel;
import com.xueye.pda.obj.NurseWorkstationObj;
import com.xueye.pda.obj.NurseWorkstationUserObj;

//送检
public class CheckoutWorkstationActivity extends BaseActivity implements
		OnClickListener {

	private ListView checkout_listview;
	private EditText checkout_et_barcode;
	private TextView checkout_barcode_submit,checkout_tv_clean,checkout_tv_submit;

	private Handler handler = new Handler();
	private ValueBroadcastReceiver valueBroadcastReceiver = null;

	List<NurseWorkstationObj> list = new ArrayList<NurseWorkstationObj>();
	NuresWorkstationAdapter nwAdapter;

	public String SpecType = ""; // 自采1 非自采2   非采集界面全部传空字符
	public String eType = "2"; //  1代表采集 2代表送检 3代表接收 

	public CheckoutWorkstationActivity() {
		super(R.layout.activity_checkout);
	}

	@Override
	public void onSuccess(BaseModel resModel) {
		super.onSuccess(resModel);
		int inf = resModel.getInfCord();
		switch (inf) {
		case InterfaceFinals.Spec_collect:

			List<NurseWorkstationObj> l = ((NurseWorkstationModel) resModel).getData();
			if (!l.isEmpty() && l.size() > 0) {
				if (list.size() > 0) {
					if (!list.get(0).getBarCode().equals(l.get(0).getBarCode())) {
						list.addAll(l);
						nwAdapter.notifyDataSetChanged();
					}else{
						showToast("请勿重复扫码");
					}
					
				}else {
					list.addAll(l);
					nwAdapter.notifyDataSetChanged();
				}
			}else{
			}

//			list.addAll(((NurseWorkstationModel) resModel).getData());
//			nwAdapter.notifyDataSetChanged();

			break;
		case InterfaceFinals.SubmitCheck:
			showToast("提交成功！");
			list.clear();
			nwAdapter.notifyDataSetChanged();
			checkout_et_barcode.setText("");
			// Builder dialog = new AlertDialog.Builder(
			// NurseWorkstationActivity.this);
			// dialog.setTitle("提示");
			// dialog.setCancelable(false);
			// dialog.setMessage(resModel.getMsg()).setPositiveButton("关闭",
			// new DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// // TODO Auto-generated method stub
			//
			// // col_list.clear();
			// // col_list_2.clear();
			// // collAdapter = new CollectAdapter(
			// // NurseWorkstationActivity.this, col_list_2);
			// // collect_listview.setAdapter(collAdapter);
			//
			// dialog.dismiss();
			// }
			// });
			// dialog.create().show();
			break;
		default:
			break;
		}

	}

	@Override
	public void getData() {

	}

	@Override
	public void initView() {

		 button2.setImageResource(R.drawable.nav_songj_select);
		if (user != null) {
			tv_name.setText("" + user.getOprName());
			tv_dept.setText("" + user.getDepartment());
			tv_title_age.setText("");
			tv_bedno.setText("");
		}

		checkout_et_barcode = (EditText) findViewById(R.id.checkout_et_barcode);
		checkout_barcode_submit = (TextView) findViewById(R.id.checkout_barcode_submit);
		checkout_tv_clean = (TextView) findViewById(R.id.checkout_tv_clean);
		checkout_tv_submit = (TextView) findViewById(R.id.checkout_tv_submit);
		checkout_listview = (ListView) findViewById(R.id.checkout_listview);

		nwAdapter = new NuresWorkstationAdapter(CheckoutWorkstationActivity.this,list);
		checkout_listview.setAdapter(nwAdapter);

		checkout_barcode_submit.setOnClickListener(this);
		checkout_tv_clean.setOnClickListener(this);
		checkout_tv_submit.setOnClickListener(this);

		checkout_et_barcode
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEND) {
							String value = checkout_et_barcode.getText().toString().trim();
							if (value.length() > 7) {
								getDataFromNet(InterfaceFinals.Spec_collect,value,SpecType,eType);

							}else{
								showToast("请先扫描正确条码");
							}
						}
						return false;
					}
				});

		// collect_listview.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		// // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
		// ViewHolder holder = (ViewHolder) view.getTag();
		// // 改变CheckBox的状态
		// holder.cb.toggle();
		// CollectAdapter.getIsSelected().put(position,
		// holder.cb.isChecked());
		//
		// collAdapter.notifyDataSetChanged();
		//
		// }
		// });
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.checkout_tv_submit:

			if (list.size() <= 0) {
				showToast("请先扫码查询数据");
			} else {

				StringBuffer sb = new StringBuffer();

				sb.append("<data>");
				for (int i = 0; i < list.size(); i++) {
					sb.append("<row><oprId>").append(user.getOprId())
							.append("</oprId><oprName>")
							.append(user.getOprName())
							.append("</oprName><barCode>")
							.append(list.get(i).getBarCode())
							.append("</barCode><pNme>")
							.append(list.get(i).getpNme())
							.append("</pNme><adviceId>")
							.append(list.get(i).getAdviceId())
							.append("</adviceId><adviceName>")
							.append(list.get(i).getAdviceName())
							.append("</adviceName></row>");
				}
				sb.append("</data>");
				// 调用提交
				getDataFromNet(InterfaceFinals.SubmitCheck, sb.toString());

			}
			// Toast.makeText(getApplicationContext(), "已勾选" + result,
			// 3000).show();
			// if (col_list_selected.size() >= 1) {
			// // 调用提交
			// getDataFromNet(InterfaceFinals.SubmitSpec,
			// "CJcommitStepData", sb.toString());
			// } else {
			// showToast("请至少勾选一条医嘱后，再进行提交！");
			// }

			// getDataFromNet(InterfaceFinals.Spec_Yet, "doctorRowData",
			// user.getDepartment(),SpecimenName,"1");
			break;

		case R.id.checkout_tv_clean:
			list.clear();
			nwAdapter.notifyDataSetChanged();
			checkout_et_barcode.setText("");
			// showToast("");
			break;
		case R.id.checkout_barcode_submit: {
			String value = checkout_et_barcode.getText().toString().trim();
			if (value.length() > 7) {
				getDataFromNet(InterfaceFinals.Spec_collect, value,SpecType,eType);

			} else {
				showToast("请输入正确的条码号");
			}
		}
			break;
		default:
			break;
		}
	}

	public void onResume() {
		super.onResume();
		startBarcodeBroadcastReceiver();
	}

	public void onPause() {
		super.onPause();
		stopBarcodeBroadcastReceiver();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 停止接收广播
	 */
	private void stopBarcodeBroadcastReceiver() {
		try {
			if (valueBroadcastReceiver != null)
				unregisterReceiver(valueBroadcastReceiver);
		} catch (Exception e) {

		}
	}

	/**
	 * 开始接收广播
	 */
	private void startBarcodeBroadcastReceiver() {
		try {
			if (valueBroadcastReceiver == null)
				valueBroadcastReceiver = new ValueBroadcastReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction("lachesis_barcode_value_notice_broadcast");
			registerReceiver(valueBroadcastReceiver, filter);
		} catch (Exception e) {

		}
	}

	/**
	 * 关机广播接收者
	 * 
	 * @author
	 * 
	 */
	private class ValueBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			Log.i("ValueBroadcastReceiver", "onReceive......");
			final String value = arg1.getStringExtra("lachesis_barcode_value_notice_broadcast_data_string");

			handler.post(new Runnable() {
				public void run() {
					// 腕带号7位 条码号12位
					if (value.length() > 7) {

						checkout_et_barcode.setText(value);
							getDataFromNet(InterfaceFinals.Spec_collect, value, SpecType,eType);

					} else {
						showToast("请输入正确的条码号");
					}

				}
			});

		}
	}

	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;

		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				public void run() {
					isExit = false;// 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
		} else {
			finish();
			System.exit(0);
		}
	}

}
