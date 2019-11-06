package com.xueye.pda.ui.collect;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
import android.view.inputmethod.InputMethodManager;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;
import com.xueye.pda.adapter.CollectAdapter;
import com.xueye.pda.adapter.CollectAdapter.ViewHolder;
import com.xueye.pda.finals.InterfaceFinals;
import com.xueye.pda.model.BaseModel;
import com.xueye.pda.model.CollectModel;
import com.xueye.pda.model.SpecTypeModel;
import com.xueye.pda.obj.CollectObj;
import com.xueye.pda.obj.SpecTypeObj;

public class collectActivity extends BaseActivity implements OnClickListener {

	private EditText et_collect_codeno;
	private Spinner collect_spinner;
	private TextView collect_submit;
	ImageView col_img;

	private Handler handler = new Handler();
	private ValueBroadcastReceiver valueBroadcastReceiver = null;

	List<SpecTypeObj> list = new ArrayList<SpecTypeObj>();
	List<CollectObj> col_list = new ArrayList<CollectObj>();
	List<CollectObj> col_list_2 = new ArrayList<CollectObj>();

	List<CollectObj> col_list_selected = new ArrayList<CollectObj>();
	ListView collect_listview;
	private String arrs[];
	Boolean text_click = false;
	String SpecimenName = null;
	CollectAdapter collAdapter;

	public collectActivity() {
		super(R.layout.activity_collect);
	}

	@Override
	public void onSuccess(BaseModel resModel) {
		super.onSuccess(resModel);
		int inf = resModel.getInfCord();
		switch (inf) {
		case InterfaceFinals.GetSpecType:
			list.clear();
			list.addAll(((SpecTypeModel) resModel).getPatientDataRow());

			arrs = new String[list.size() + 1];
			arrs[0] = "--请选择--";
			for (int i = 0; i < list.size(); i++) {
				arrs[i + 1] = list.get(i).getSpecimenName();
				Log.e("---", arrs[i + 1]);
			}
			// adpater对象
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, arrs);
			arrayAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			collect_spinner.setAdapter(arrayAdapter);
			collect_spinner
					.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int i, long arg3) {
							SpecimenName = arrs[i];
							if (i != 0) {
								text_click = true;
							} else {
								text_click = false;
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});
			break;

		case InterfaceFinals.Spec_collect:
			col_list.clear();
			col_list_2.clear();
			
			col_list.addAll(((CollectModel) resModel).getPatientData());
			//过滤空数据
			for (int i = 0; i < col_list.size(); i++) {
				if (col_list.get(i).getPatient_Name()!= null && col_list.get(i).getPatient_Gender()!= null  && !"".equals(col_list.get(i).getPatient_Name()) && !"".equals(col_list.get(i).getPatient_Gender())) {
					col_list_2.add(col_list.get(i));
				}
			}
			
			if (col_list_2 != null) {
				tv_name.setText(col_list_2.get(0).getPatient_Name());
				tv_dept.setText(col_list_2.get(0).getPatient_Gender());
				tv_title_age.setText(col_list_2.get(0).getPatient_Age());
				tv_bedno.setText(col_list_2.get(0).getBed_No());
				tv_bedno.setTextColor(Color.parseColor("#EE0000"));
				tv_name.setTextColor(Color.parseColor("#EE0000"));
				tv_dept.setTextColor(Color.parseColor("#EE0000"));
				tv_title_age.setTextColor(Color.parseColor("#EE0000"));
				tv_bedno.setTextColor(Color.parseColor("#EE0000"));
			}
			
			collAdapter = new CollectAdapter(collectActivity.this, col_list_2);
			collect_listview.setAdapter(collAdapter);
			break;
		case InterfaceFinals.SubmitSpec:
			 Builder dialog = new AlertDialog.Builder(collectActivity.this);
	            dialog.setTitle("提示");
	            dialog.setCancelable(false);
	            dialog.setMessage(resModel.getMsg()).setPositiveButton("关闭", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						

						col_list.clear();
						col_list_2.clear();
						collAdapter = new CollectAdapter(collectActivity.this, col_list_2);
						collect_listview.setAdapter(collAdapter);
						
						dialog.dismiss();
					}
				});
	            dialog.create().show();
	        break;
		default:
			break;
		}

	}

	@Override
	public void getData() {
		getDataFromNet(InterfaceFinals.GetSpecType, "specimenType");

	}

	@Override
	public void initView() {

		button1.setImageResource(R.drawable.nav_caij_select);
		tv_name.setText("");
		tv_dept.setText("");
		tv_title_age.setText("");
		tv_bedno.setText("");
		col_img = (ImageView) findViewById(R.id.col_img);
		et_collect_codeno = (EditText) findViewById(R.id.et_collect_codeno);
		collect_spinner = (Spinner) findViewById(R.id.collect_spinner);
		collect_submit = (TextView) findViewById(R.id.collect_submit);
		collect_listview = (ListView) findViewById(R.id.collect_listview);
		collAdapter = new CollectAdapter(collectActivity.this, col_list_2);
		collect_listview.setAdapter(collAdapter);
		collect_submit.setOnClickListener(this);
		
		
		
		
		
		et_collect_codeno.setOnEditorActionListener(new TextView.OnEditorActionListener() {  
            
          @Override  
          public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {  
              if (actionId == EditorInfo.IME_ACTION_SEND) {  
                  //处理事件  
            	  if (text_click) {
  					getDataFromNet(InterfaceFinals.Spec_collect,
  							"sweepWristband", et_collect_codeno.getText().toString().trim(),
  							SpecimenName.toString(), user.getDepartment());

					col_list.clear();
					col_list_2.clear();
					collAdapter = new CollectAdapter(collectActivity.this, col_list_2);
					collect_listview.setAdapter(collAdapter);
  				} else {
  					showToast("请选择采集方式!");
  				}
              }  
              return false;  
          }  
      });  


		collect_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
				ViewHolder holder = (ViewHolder) view.getTag();
				// 改变CheckBox的状态
				holder.cb.toggle();
				CollectAdapter.getIsSelected().put(position,
						holder.cb.isChecked());

				collAdapter.notifyDataSetChanged();

			}
		});
	}
	
	
	

	
	

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.collect_submit:
			// 未选择spinner的采集方式
			if (text_click) {
//				String result = "";
				StringBuffer sb = new StringBuffer();
				col_list_selected.clear();
				for (int i = 0; i < col_list_2.size(); i++) {
					if (collAdapter.getIsSelected().get(i)) {
						col_list_selected.add(col_list_2.get(i));
					 }
				}

				sb.append("<data>");
				for (int i = 0; i < col_list_selected.size(); i++) {
//					result = result+ col_list_selected.get(i).getDoctor_Content();
					sb.append("<row><type>C</type><name>")
							.append(user.getOprName())
							.append("</name><code>")
							.append(col_list_selected.get(i).getBar_Code())
							.append("</code><id>")
						    .append(col_list_selected.get(i).getDoctor_Id())
			                .append("</id></row>");
				}
				sb.append("</data>");
				Log.e("-----select-----", sb.toString()+"----");
//				Toast.makeText(getApplicationContext(), "已勾选" + result, 3000).show();
				if (col_list_selected.size() >= 1) {
					// 调用提交
					getDataFromNet(InterfaceFinals.SubmitSpec,
							"CJcommitStepData", sb.toString());
				} else {
					showToast("请至少勾选一条医嘱后，再进行提交！");
				}

				// getDataFromNet(InterfaceFinals.Spec_Yet, "doctorRowData",
				// user.getDepartment(),SpecimenName,"1");
			} else {
				showToast("请选择采集方式!");
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
					et_collect_codeno.setText(value);
					// @param bar_Code 腕带条码
					// @Param flag 条码类型 0腕带条码 1样本条码
					// @param department 部门
					// 3914295 value.toString()
					if (text_click) {
						getDataFromNet(InterfaceFinals.Spec_collect,"sweepWristband", value,SpecimenName.toString(), user.getDepartment());

						col_list.clear();
						col_list_2.clear();
						collAdapter = new CollectAdapter(collectActivity.this, col_list_2);
						collect_listview.setAdapter(collAdapter);
					} else {
						showToast("请选择采集方式!");
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
