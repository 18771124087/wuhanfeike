package com.xueye.pda.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;
import com.xueye.pda.adapter.ReceiveWorkstationAdapter;
import com.xueye.pda.adapter.ReceiveWorkstationAdapter.ViewHolder;
import com.xueye.pda.finals.InterfaceFinals;
import com.xueye.pda.model.BaseModel;
import com.xueye.pda.model.NurseWorkstationModel;
import com.xueye.pda.model.RejectionReceiveMsgModel;
import com.xueye.pda.obj.NurseWorkstationObj;
import com.xueye.pda.obj.RejectionReceiveMsgObj;

//送检
public class ReceiveWorkstationActivity extends BaseActivity implements
		OnClickListener {

	private ListView receive_listview;
	private EditText receive_et_barcode;
	private TextView receive_barcode_submit,receive_tv_clean,receive_tv_submit,receive_tv_reject;

	private Handler handler = new Handler();
	private ValueBroadcastReceiver valueBroadcastReceiver = null;

	List<NurseWorkstationObj> list = new ArrayList<NurseWorkstationObj>();
	List<NurseWorkstationObj> list_submit = new ArrayList<NurseWorkstationObj>();

	List<RejectionReceiveMsgObj> list_rejection = new ArrayList<RejectionReceiveMsgObj>();
	ReceiveWorkstationAdapter nwAdapter;

	public String SpecType = ""; // 自采1 非自采2   非采集界面全部传空字符
	public String eType = "3"; //  1代表采集 2代表送检 3代表接收 

	boolean isDialog = true;

	public ReceiveWorkstationActivity() {
		super(R.layout.activity_receive);
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
						nwAdapter = new ReceiveWorkstationAdapter(ReceiveWorkstationActivity.this,list);
						receive_listview.setAdapter(nwAdapter);
					}else{
						showToast("请勿重复扫码");
					}
					
				}else {
					list.addAll(l);
					nwAdapter = new ReceiveWorkstationAdapter(ReceiveWorkstationActivity.this,list);
					receive_listview.setAdapter(nwAdapter);
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
			
//			list.addAll(((NurseWorkstationModel) resModel).getData());
//			nwAdapter = new ReceiveWorkstationAdapter(ReceiveWorkstationActivity.this,list);
//			receive_listview.setAdapter(nwAdapter);
			break;
		case InterfaceFinals.SubmitReceive:
			showToast("提交成功！");
			list_submit.clear();
			nwAdapter = new ReceiveWorkstationAdapter(ReceiveWorkstationActivity.this,list);
			receive_listview.setAdapter(nwAdapter);
			receive_et_barcode.setText("");
//			nwAdapter.notifyDataSetChanged();
			break;
		case InterfaceFinals.RejectionReceive:
			showToast("拒收成功！");
			list_submit.clear();
			nwAdapter = new ReceiveWorkstationAdapter(ReceiveWorkstationActivity.this,list);
			receive_listview.setAdapter(nwAdapter);
			receive_et_barcode.setText("");
//			nwAdapter.notifyDataSetChanged();
			break;
		case InterfaceFinals.RejectionReceive_msg:
			list_rejection.clear();
			list_rejection.addAll(((RejectionReceiveMsgModel)resModel).getData());
			getRejectionReason(list_rejection);
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

		 button3.setImageResource(R.drawable.nav_jies_select);
		if (user != null) {
			tv_name.setText("" + user.getOprName());
			tv_dept.setText("" + user.getDepartment());
			tv_title_age.setText("");
			tv_bedno.setText("");
		}

		receive_et_barcode = (EditText) findViewById(R.id.receive_et_barcode);
		receive_barcode_submit = (TextView) findViewById(R.id.receive_barcode_submit);
		receive_tv_clean = (TextView) findViewById(R.id.receive_tv_clean);
		receive_tv_submit = (TextView) findViewById(R.id.receive_tv_submit);
		receive_tv_reject = (TextView) findViewById(R.id.receive_tv_reject);
		receive_listview = (ListView) findViewById(R.id.receive_listview);

		nwAdapter = new ReceiveWorkstationAdapter(ReceiveWorkstationActivity.this,list);
		receive_listview.setAdapter(nwAdapter);

		receive_barcode_submit.setOnClickListener(this);
		receive_tv_clean.setOnClickListener(this);
		receive_tv_submit.setOnClickListener(this);
		receive_tv_reject.setOnClickListener(this);

		receive_et_barcode
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEND) {
							String value = receive_et_barcode.getText().toString().trim();
							if (value.length() > 7) {
								getDataFromNet(InterfaceFinals.Spec_collect,value,SpecType,eType);

							}else{
								showToast("请先扫描正确条码");
							}
						}
						return false;
					}
				});

		receive_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
				ViewHolder holder = (ViewHolder) view.getTag();
				// 改变CheckBox的状态
				holder.item_receive_cb.toggle();
				nwAdapter.getIsSelected().put(position, holder.item_receive_cb.isChecked());

				nwAdapter.notifyDataSetChanged();

			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.receive_tv_submit:

			if (list.size() <= 0 && list_submit.size() <= 0) {
				showToast("请先扫码查询数据");
			} else {
				List<NurseWorkstationObj> list_box = new ArrayList<NurseWorkstationObj>();
				list_box.clear();
				list_box.addAll(list); // list剩余数据的中转

//				list_submit.clear();
				// 当提交失败时，list_submit将不为空，提交成功后才会被清空
				Log.e("====list_submit====", ""+list_submit.size());
				if (list_submit.size() <= 0) {
					for (int i = list_box.size()-1; i >= 0; i--) {
						if (nwAdapter.getIsSelected().get(i)) {
							list_submit.add(list_box.get(i));
							list.remove(i);
						}
					}
				}

				if (list_submit.size() >= 1) {
					// 调用提交

					StringBuffer sb = new StringBuffer();
					sb.append("<data>");
					for (int i = 0; i < list_submit.size(); i++) {
						sb.append("<row><oprId>").append(user.getOprId())
								.append("</oprId><oprName>")
								.append(user.getOprName())
								.append("</oprName><barCode>")
								.append(list_submit.get(i).getBarCode())
								.append("</barCode><pNme>")
								.append(list_submit.get(i).getpNme())
								.append("</pNme><adviceId>")
								.append(list_submit.get(i).getAdviceId())
								.append("</adviceId><adviceName>")
								.append(list_submit.get(i).getAdviceName())
								.append("</adviceName></row>");
					}
					sb.append("</data>");
					// 调用提交
					getDataFromNet(InterfaceFinals.SubmitReceive, sb.toString());

				} else {
					showToast("请至少勾选一条医嘱后，再进行提交！");
				}

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
		case R.id.receive_tv_reject:
			//如果已经加载过将复用改数据
			if (list_rejection.size() <= 0) {
				getDataFromNet(InterfaceFinals.RejectionReceive_msg);
			}else{
				getRejectionReason(list_rejection);
			}
			
			break;
		case R.id.receive_tv_clean:
			list.clear();
			list_submit.clear();
			nwAdapter.notifyDataSetChanged();
			receive_et_barcode.setText("");
			// showToast("");
			break;
		case R.id.receive_barcode_submit: {
			String value = receive_et_barcode.getText().toString().trim();
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
	
	public void ReJectSubmit(String ReasonId, String ReasonName){
		if (list.size() <= 0 && list_submit.size() <= 0) {
			showToast("请先扫码查询数据");
		} else {
			List<NurseWorkstationObj> list_box = new ArrayList<NurseWorkstationObj>();
			list_box.clear();
			list_box.addAll(list); // list剩余数据的中转

//			list_submit.clear();
			//当提交失败时，list_submit将不为空，提交成功后才会被清空
			if (list_submit.size() <= 0) {

				for (int i = list_box.size()-1; i >= 0; i--) {
					if (nwAdapter.getIsSelected().get(i)) {
						list_submit.add(list_box.get(i));
						list.remove(i);
						Log.e("size+i==============", i+"");
					}
				}

			}

			if (list_submit.size() >= 1) {
				// 调用提交

				StringBuffer sb = new StringBuffer();
				sb.append("<data>");
				for (int i = 0; i < list_submit.size(); i++) {
					sb.append("<row><oprId>").append(user.getOprId())
							.append("</oprId><oprName>")
							.append(user.getOprName())
							.append("</oprName><barCode>")
							.append(list_submit.get(i).getBarCode())
							.append("</barCode><pNme>")
							.append(list_submit.get(i).getpNme())
							.append("</pNme><adviceId>")
							.append(list_submit.get(i).getAdviceId())
							.append("</adviceId><adviceName>")
							.append(list_submit.get(i).getAdviceName())
							.append("</adviceName><reasonId>")
							.append(ReasonId)
							.append("</reasonId><reasonName>")
							.append(ReasonName)
							.append("</reasonName></row>");
				}
				sb.append("</data>");
				// 调用提交
				getDataFromNet(InterfaceFinals.RejectionReceive, sb.toString());

			} else {
				showToast("请至少勾选一条医嘱后，再进行提交！");
			}

		}
	}
	

	    //dielog弹出选择拒收理由方法
		private void getRejectionReason(List<RejectionReceiveMsgObj> list){
			final List<Map<String, String>> msgList = new ArrayList<Map<String, String>>();//建立一个数组存储listview上显示的数据  
			LayoutInflater layoutInflater = LayoutInflater.from(this);  
	        View mylistView = layoutInflater.inflate(R.layout.listview, null);
	        ListView lv = (ListView) mylistView.findViewById(R.id.listview);
	        
	        
	        
			SimpleAdapter adapter = new SimpleAdapter(this,msgList, R.layout.item, new String[] { "msg" },new int[] { R.id.textview });  
	        
			//initData为一个list类型的数据源  
			for (int k = 0; k < list.size(); k++) {
				Map<String, String> msgMap = new HashMap<String, String>();  
				msgMap.put("id", list.get(k).getReasonId());
				msgMap.put("msg", list.get(k).getReasonName());
				msgList.add(msgMap);  
	      	}  
			
			lv.setAdapter(adapter);  
	        
			final AlertDialog dialog = new AlertDialog.Builder(this)  
			        .setTitle("请选择拒收理由")
			        .setView(mylistView)//在这里把写好的这个listview的布局加载dialog中  
			        .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
			            public void onClick(DialogInterface dialog, int which) {  
			                dialog.cancel();  
							isDialog=true; 
			            } 
			        }).create();  
			dialog.setCancelable(false);//不能被返回键取消
			if(isDialog == true){
				dialog.show(); 
				isDialog=false;
				}
			//使除了dialog以外的地方不能被点击  (此方法只能在show方法之后调用,show之前调用无效)
			dialog.setCanceledOnTouchOutside(false);
			lv.setOnItemClickListener(new OnItemClickListener() {//响应listview中的item的点击事件  
			  
			    public void onItemClick(AdapterView<?> arg0, View view, int p, long arg3) {  
	             	Toast.makeText(ReceiveWorkstationActivity.this, msgList.get(p).get("msg"), Toast.LENGTH_SHORT).show();  
	             	
	             	ReJectSubmit(msgList.get(p).get("id"), msgList.get(p).get("msg"));
	             	
	             	dialog.cancel();  
					isDialog = true;
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
					// 腕带号7位 条码号12位
					if (value.length() > 7) {

						receive_et_barcode.setText(value);
							getDataFromNet(InterfaceFinals.Spec_collect, value,SpecType,eType);

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
