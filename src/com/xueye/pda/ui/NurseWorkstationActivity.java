package com.xueye.pda.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;
import com.xueye.pda.adapter.CollectAdapter;
import com.xueye.pda.adapter.CollectAdapter.ViewHolder;
import com.xueye.pda.adapter.NuresWorkstationAdapter;
import com.xueye.pda.finals.InterfaceFinals;
import com.xueye.pda.model.BaseModel;
import com.xueye.pda.model.NurseWorkstationModel;
import com.xueye.pda.model.NurseWorkstationUserModel;
import com.xueye.pda.obj.NurseWorkstationObj;
import com.xueye.pda.obj.NurseWorkstationUserObj;

public class NurseWorkstationActivity extends BaseActivity implements
		OnClickListener {

	private ListView main_listview;
	private EditText main_et_collect_codeno, main_et_collect_barcode;
	private TextView main_tv_yet, main_tv_not, main_collect_submit,
			main_collect_barcode_submit, main_tv_collect_name,
			main_tv_collect_bednum, main_tv_clean, main_tv_submit;

	private Handler handler = new Handler();
	private ValueBroadcastReceiver valueBroadcastReceiver = null;

	List<NurseWorkstationObj> list = new ArrayList<NurseWorkstationObj>();
//	NurseWorkstationUserObj obj = new NurseWorkstationUserObj();

	// List<CollectObj> col_list = new ArrayList<CollectObj>();
	// List<CollectObj> col_list_2 = new ArrayList<CollectObj>();
	// List<CollectObj> col_list_selected = new ArrayList<CollectObj>();
	// ListView collect_listview;
	private String arrs[];
	Boolean text_click = false;
	String SpecimenName = null;
	NuresWorkstationAdapter nwAdapter;
	public String SpecType = "2"; // 自采1 非自采2
	public String eType = "1"; //  1代表采集 2代表送检 3代表接收 


	public NurseWorkstationActivity() {
		super(R.layout.activity_main,true,false);
	}

	@Override
	public void onSuccess(BaseModel resModel) {
		super.onSuccess(resModel);
		int inf = resModel.getInfCord();
		switch (inf) {

		case InterfaceFinals.Spec_collect_userInfo:
			NurseWorkstationUserObj obj = ((NurseWorkstationUserModel) resModel).getData();
			main_tv_collect_name.setText("姓名：" + obj.getpNme());
			main_tv_collect_bednum.setText("床位号：" + obj.getBedNo());

			break;
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
				// 过滤空数据
				// for (int i = 0; i < list.size(); i++) {
				// if (list.get(i).getPatient_Name()!= null &&
				// list.get(i).getPatient_Gender()!= null &&
				// !"".equals(col_list.get(i).getPatient_Name()) &&
				// !"".equals(col_list.get(i).getPatient_Gender())) {
				// col_list_2.add(col_list.get(i));
				// }
				// }
			}


			break;
		case InterfaceFinals.SubmitSpec:
			showToast("提交成功！");
			list.clear();
			nwAdapter.notifyDataSetChanged();
			main_et_collect_codeno.setText("");
			main_et_collect_barcode.setText("");
			main_tv_collect_name.setText("姓名：");
			main_tv_collect_bednum.setText("床位号：");
//			Builder dialog = new AlertDialog.Builder(
//					NurseWorkstationActivity.this);
//			dialog.setTitle("提示");
//			dialog.setCancelable(false);
//			dialog.setMessage(resModel.getMsg()).setPositiveButton("关闭",
//					new DialogInterface.OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//
////							col_list.clear();
////							col_list_2.clear();
////							collAdapter = new CollectAdapter(
////									NurseWorkstationActivity.this, col_list_2);
////							collect_listview.setAdapter(collAdapter);
//
//							dialog.dismiss();
//						}
//					});
//			dialog.create().show();
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

//		button1.setImageResource(R.drawable.nav_caij_select);
		if (user != null) {
			tv_name.setText("" + user.getOprName());
			tv_dept.setText("" + user.getDepartment());
			tv_title_age.setText("");
			tv_bedno.setText("");
		}

		main_et_collect_codeno = (EditText) findViewById(R.id.main_et_collect_codeno);
		main_et_collect_barcode = (EditText) findViewById(R.id.main_et_collect_barcode);
		main_tv_yet = (TextView) findViewById(R.id.main_tv_yet);
		main_tv_not = (TextView) findViewById(R.id.main_tv_not);
		main_collect_submit = (TextView) findViewById(R.id.main_collect_submit);
		main_collect_barcode_submit = (TextView) findViewById(R.id.main_collect_barcode_submit);
		main_tv_collect_name = (TextView) findViewById(R.id.main_tv_collect_name);
		main_tv_collect_bednum = (TextView) findViewById(R.id.main_tv_collect_bednum);
		main_tv_clean = (TextView) findViewById(R.id.main_tv_clean);
		main_tv_submit = (TextView) findViewById(R.id.main_tv_submit);
		main_listview = (ListView) findViewById(R.id.main_listview);

		nwAdapter = new NuresWorkstationAdapter(NurseWorkstationActivity.this,
				list);
		main_listview.setAdapter(nwAdapter);

		main_collect_submit.setOnClickListener(this);
		main_collect_barcode_submit.setOnClickListener(this);
		main_tv_clean.setOnClickListener(this);
		main_tv_submit.setOnClickListener(this);
		main_tv_yet.setOnClickListener(this);
		main_tv_not.setOnClickListener(this);

		main_et_collect_codeno
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEND) {
							String value = main_et_collect_codeno.getText()
									.toString().trim();
							if (value.length() == 11) {
								main_et_collect_codeno.setText(value);
								getDataFromNet(
										InterfaceFinals.Spec_collect_userInfo, value);

							}
						}
						return false;
					}
				});

		main_et_collect_barcode
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEND) {
							// 处理事件
							String value = main_et_collect_barcode.getText()
									.toString().trim();
							if (value.length() > 11) {
								if ("2".equals(SpecType)) {
									String code = main_et_collect_codeno.getText().toString().trim();
									if (!"".equals(code)) {
										getDataFromNet(
												InterfaceFinals.Spec_collect, value, SpecType,eType);

									} else {
										showToast("请先扫描腕带号");

										main_et_collect_barcode.setText("");
									}

								} else {
									getDataFromNet(
											InterfaceFinals.Spec_collect, value, SpecType,eType);
								}
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

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.main_tv_submit:
			
			if (list.size() <= 0) {
				showToast("请先扫码查询数据");
			}else{
				
				StringBuffer sb = new StringBuffer();
				
//				for (int i = 0; i < col_list_2.size(); i++) {
//					if (collAdapter.getIsSelected().get(i)) {
//						col_list_selected.add(col_list_2.get(i));
//					}
//				}

				sb.append("<data>");
				for (int i = 0; i < list.size(); i++) {
					// result = result+
					// col_list_selected.get(i).getDoctor_Content();
					sb.append("<row><oprId>")
							.append(user.getOprId())
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
				getDataFromNet(InterfaceFinals.SubmitSpec, sb.toString());
				

			}
				// Toast.makeText(getApplicationContext(), "已勾选" + result,
				// 3000).show();
//				if (col_list_selected.size() >= 1) {
//					// 调用提交
//					getDataFromNet(InterfaceFinals.SubmitSpec,
//							"CJcommitStepData", sb.toString());
//				} else {
//					showToast("请至少勾选一条医嘱后，再进行提交！");
//				}

				// getDataFromNet(InterfaceFinals.Spec_Yet, "doctorRowData",
				// user.getDepartment(),SpecimenName,"1");
			break;

		case R.id.main_tv_clean:
			list.clear();
			nwAdapter.notifyDataSetChanged();
			main_et_collect_codeno.setText("");
			main_et_collect_barcode.setText("");
			main_tv_collect_name.setText("姓名：");
			main_tv_collect_bednum.setText("床位号：");
			
			// showToast("");
			break;
		case R.id.main_collect_submit: {
			String value = main_et_collect_codeno.getText().toString().trim();
			if (value.length() == 11) {
				main_et_collect_codeno.setText(value);
				getDataFromNet(InterfaceFinals.Spec_collect_userInfo, value);

			}else{
				showToast("请输入正确的腕带号");
			}
		}
			break;
		case R.id.main_collect_barcode_submit: {
			String value = main_et_collect_barcode.getText().toString().trim();
			if (value.length() > 11) {
				if ("2".equals(SpecType)) {
					String code = main_et_collect_codeno.getText().toString().trim();
					if (!"".equals(code)) {
						getDataFromNet(InterfaceFinals.Spec_collect, value, SpecType,eType);

					} else {
						showToast("请先扫描腕带号");
						main_et_collect_barcode.setText("");
					}

				} else {
					getDataFromNet(InterfaceFinals.Spec_collect, value, SpecType,eType);
				}
			}else{
				showToast("请输入正确的条码号");
			}
		}
			break;
		case R.id.main_tv_yet:
			if (!list.isEmpty() && list.size() > 0) {
				showToast("请先提交或清空");
			}else{
				SpecType = "1";
				main_tv_yet.setBackgroundColor(Color.parseColor("#57a2c1"));
				main_tv_yet.setTextColor(Color.parseColor("#ffffff"));
				main_tv_not.setBackgroundColor(Color.parseColor("#e6e8de"));
				main_tv_not.setTextColor(Color.parseColor("#363636"));
				main_collect_submit.setEnabled(false);
				main_collect_submit.setBackgroundColor(Color.parseColor("#cdcdcd"));
				main_et_collect_codeno.setText("");
				main_et_collect_barcode.setText("");
			}
			break;
		case R.id.main_tv_not:
			if (!list.isEmpty() && list.size() > 0) {
				showToast("请先提交或清空");
			}else{
			SpecType = "2";
			main_tv_not.setBackgroundColor(Color.parseColor("#57a2c1"));
			main_tv_not.setTextColor(Color.parseColor("#ffffff"));
			main_tv_yet.setBackgroundColor(Color.parseColor("#e6e8de"));
			main_tv_yet.setTextColor(Color.parseColor("#363636"));
			main_collect_submit.setEnabled(true);
			main_collect_submit.setBackgroundColor(Color.parseColor("#57a2c1"));
			main_et_collect_codeno.setText("");
			main_et_collect_barcode.setText("");
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
			final String value = arg1
					.getStringExtra("lachesis_barcode_value_notice_broadcast_data_string");

			handler.post(new Runnable() {
				public void run() {
					// 腕带号11位 条码号12位
					if (value.length() == 11) {
						if ("1".equals(SpecType)) {
//							showToast("");
						}else{
							main_et_collect_codeno.setText(value);
							getDataFromNet(InterfaceFinals.Spec_collect_userInfo, value);
						}

					} else if (value.length() > 11) {

						main_et_collect_barcode.setText(value);
						
						if ("2".equals(SpecType)) {
							String code = main_et_collect_codeno.getText().toString().trim();
							if (!"".equals(code)) {
								getDataFromNet(InterfaceFinals.Spec_collect, value, SpecType,eType);

							} else {
								showToast("请先扫描腕带号");
								main_et_collect_barcode.setText("");
							}

						} else {
							getDataFromNet(InterfaceFinals.Spec_collect, value, SpecType,eType);
						}

					} else {
						showToast("条码位数不符合规则");
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
