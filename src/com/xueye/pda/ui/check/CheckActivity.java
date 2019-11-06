package com.xueye.pda.ui.check;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;
import com.xueye.pda.adapter.CheckRowAdapter;
import com.xueye.pda.adapter.CollectAdapter;
import com.xueye.pda.adapter.DoctorRowAdapter;
import com.xueye.pda.finals.InterfaceFinals;
import com.xueye.pda.model.BaseModel;
import com.xueye.pda.model.CheckRowModel;
import com.xueye.pda.model.CollectModel;
import com.xueye.pda.model.DoctorRowDataInfoModel;
import com.xueye.pda.model.DoctorRowDataModel;
import com.xueye.pda.model.SpecTypeModel;
import com.xueye.pda.obj.CheckRowObj;
import com.xueye.pda.obj.DoctorRowDataInfoObj;
import com.xueye.pda.obj.DoctorRowDataObj;
import com.xueye.pda.obj.SpecTypeObj;
import com.xueye.pda.ui.MainActivity;
import com.xueye.pda.ui.collect.collectActivity;

public class CheckActivity extends BaseActivity implements OnClickListener {

	EditText et_check_codeno;
	TextView check_submit;
	ListView check_listview;
	Spinner check_spinner;
	ImageView check_img;
	

	private Handler handler = new Handler();
	private ValueBroadcastReceiver valueBroadcastReceiver = null;
	List<SpecTypeObj> list = new ArrayList<SpecTypeObj>();
	List<CheckRowObj> list_2 = new ArrayList<CheckRowObj>();
	List<CheckRowObj> list_2_2 = new ArrayList<CheckRowObj>();

	private DoctorRowDataInfoObj infoObj = new DoctorRowDataInfoObj();
	private String arrs[];
	Boolean text_click = true;
	String SpecimenName = null;
	CheckRowAdapter adapter;
	
	
	public CheckActivity() {
		super(R.layout.act_check);
	}

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
			ArrayAdapter<String> arrayAdapter_qu = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrs);
			arrayAdapter_qu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			check_spinner.setAdapter(arrayAdapter_qu);
			check_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int i, long arg3) {
							SpecimenName = arrs[i];
							if (i != 0) {
								text_click = true;
							}else {
								text_click = false;
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});
			break;
		case InterfaceFinals.Spec_Check:
			list_2.clear();
			list_2_2.clear();
			list_2.addAll(((CheckRowModel) resModel).getPatientData());
			

			//过滤空数据
			for (int i = 0; i < list_2.size(); i++) {
				if (list_2.get(i).getPatient_Name()!= null && list_2.get(i).getPatient_Gender()!= null && !"".equals(list_2.get(i).getPatient_Name()) && !"".equals(list_2.get(i).getPatient_Gender())) {
					list_2_2.add(list_2.get(i));
				}
			}
			
			if (list_2_2 != null) {
				tv_name.setText(list_2_2.get(0).getPatient_Name());
				tv_dept.setText(list_2_2.get(0).getPatient_Gender());
				tv_title_age.setText(list_2_2.get(0).getPatient_Age());
				tv_bedno.setText(list_2_2.get(0).getBed_No());
				tv_bedno.setTextColor(Color.parseColor("#EE0000"));
				tv_name.setTextColor(Color.parseColor("#EE0000"));
				tv_dept.setTextColor(Color.parseColor("#EE0000"));
				tv_title_age.setTextColor(Color.parseColor("#EE0000"));
			}
			
			
			
			adapter = new CheckRowAdapter(CheckActivity.this, list_2_2);
			check_listview.setAdapter(adapter);

			break;

		case InterfaceFinals.Spec_Check_Receive_Info:
			infoObj = ((DoctorRowDataInfoModel) resModel).getPatientData();
            Builder dialog_info = new AlertDialog.Builder(CheckActivity.this);
            dialog_info.setTitle("医嘱详细");
            dialog_info.setMessage("姓名：" + infoObj.getPatient_Name() + "\n" + 
            		"性别：" + infoObj.getPatient_Gender() + "\n" +
            		"年龄：" + infoObj.getPatient_Age() + "\n" +
            		"科室：" + infoObj.getPatient_Department() + "\n" +
            		"医嘱：" + infoObj.getDoctor_Content() + "\n" +
            		"来源：" + infoObj.getPatient_Source() + "\n" +
            		"申请人：" + infoObj.getApply_Name() + "\n" +
            		"申请时间：" + infoObj.getApply_Time());
            dialog_info.create().show();
			
			break;
			case InterfaceFinals.SubmitCheck:
				 Builder dialog = new AlertDialog.Builder(CheckActivity.this);
		            dialog.setTitle("提示");
		            dialog.setCancelable(false);
		            dialog.setMessage(resModel.getMsg()).setPositiveButton("关闭", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							list_2.clear();
							list_2_2.clear();
							adapter = new CheckRowAdapter(CheckActivity.this, list_2_2);
							check_listview.setAdapter(adapter);
							
							dialog.dismiss();
						}
					});
		            dialog.create().show();
		        break;
			case InterfaceFinals.Spec_Info:
				infoObj = ((DoctorRowDataInfoModel) resModel).getPatientData();
	            Builder dialog_2 = new AlertDialog.Builder(CheckActivity.this);
	            dialog_2.setTitle("医嘱详细");
	            dialog_2.setMessage("姓名：" + infoObj.getPatient_Name() + "\n" + 
	            		"性别：" + infoObj.getPatient_Gender() + "\n" +
	            		"年龄：" + infoObj.getPatient_Age() + "\n" +
	            		"科室：" + infoObj.getPatient_Department() + "\n" +
	            		"医嘱：" + infoObj.getDoctor_Content() + "\n" +
	            		"来源：" + infoObj.getPatient_Source() + "\n" +
	            		"申请人：" + infoObj.getApply_Name() + "\n" +
	            		"申请时间：" + infoObj.getApply_Time());
	            dialog_2.create().show();
				
				break;
		default:
			break;
		}

	}

	
	@Override
	public void getData() {
//		getDataFromNet(InterfaceFinals.GetSpecType, "specimenType");
		// TODO Auto-generated method stub

	}
//	3914059
	@Override
	public void initView() {

		button2.setImageResource(R.drawable.nav_songj_select);
		tv_name.setText("");
		tv_dept.setText("");
		tv_title_age.setText("");

		et_check_codeno = (EditText) findViewById(R.id.et_check_codeno);
        check_submit = (TextView) findViewById(R.id.check_submit);
        check_listview=(ListView) findViewById(R.id.check_listview);
        check_spinner=(Spinner) findViewById(R.id.check_spinner);
        check_img=(ImageView) findViewById(R.id.check_img);

        check_submit.setOnClickListener(this);

        et_check_codeno.setOnEditorActionListener(new TextView.OnEditorActionListener() {  
            
          @Override  
          public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {  
              if (actionId == EditorInfo.IME_ACTION_SEND) {  
                  //处理事件  
            	  if (text_click) {
  					getDataFromNet(InterfaceFinals.Spec_Check, "CX_commitStepData",et_check_codeno.getText().toString().trim(),SpecimenName);

					list_2.clear();
					list_2_2.clear();
					adapter = new CheckRowAdapter(CheckActivity.this, list_2_2);
					check_listview.setAdapter(adapter);
            	  } else {
  					showToast("请选择采集方式!");
  				} 
              }  
              return false;  
          }

      });  
        check_listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int i,long arg3) {

				getDataFromNet(InterfaceFinals.Spec_Check_Receive_Info, "doctorData", list_2_2.get(i).getDoctor_Id());
			
			}
		});
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
					et_check_codeno.setText(value);
					//@param bar_Code 腕带条码
					//@Param flag 条码类型    0腕带条码 1样本条码
					//@param department 部门
                     //3914295  value.toString()
					if (text_click) {
					getDataFromNet(InterfaceFinals.Spec_Check, "CX_commitStepData",value,SpecimenName);

					list_2.clear();
					list_2_2.clear();
					adapter = new CheckRowAdapter(CheckActivity.this, list_2_2);
					check_listview.setAdapter(adapter);
				} else {
					showToast("请选择采集方式!");
				}
				}
			});

		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
	case R.id.check_submit:
		// 未选择spinner的采集方式
		if (text_click) {
			
			if (list_2_2.size()!=0) {
				
			StringBuffer sb = new StringBuffer();

			
			sb.append("<data>");
			for (int i = 0; i < list_2_2.size(); i++) {
				sb.append("<row><type>S</type><name>")
						.append(user.getOprName())
						.append("</name><code>")
						.append(list_2_2.get(i).getBar_Code())
						.append("</code><id>")
						.append(list_2_2.get(i).getDoctor_Id())
			            .append("</id></row>");
			}
			sb.append("</data>");
				getDataFromNet(InterfaceFinals.SubmitCheck,"commitStepData", sb.toString());

			}else {
				showToast("送检列表为空!");
			}
		} else {
			showToast("请选择采集方式!");
		}
		break;
	
		
		
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



