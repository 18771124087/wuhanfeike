package com.xueye.pda.ui.receive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;
import com.xueye.pda.adapter.ReceiveRowAdapter;
import com.xueye.pda.finals.InterfaceFinals;
import com.xueye.pda.model.BaseModel;
import com.xueye.pda.model.DoctorRowDataInfoModel;
import com.xueye.pda.model.ReceiveRowModel;
import com.xueye.pda.model.RejectionReceiveMsgModel;
import com.xueye.pda.model.SpecTypeModel;
import com.xueye.pda.obj.DoctorRowDataInfoObj;
import com.xueye.pda.obj.ReceiveRowObj;
import com.xueye.pda.obj.RejectionReceiveMsgObj;
import com.xueye.pda.obj.SpecTypeObj;

/**
 * @author wok
 * 
 * 类说明
 **/
public class ReceiveActivity extends BaseActivity implements OnClickListener {

//	EditText et_receive_codeno;
//	TextView receive_submit ,receive_rejection;
//	ListView receive_listview;
//	Spinner receive_spinner;
//	ImageView receive_img;
//	
//
//	private Handler handler = new Handler();
//	private ValueBroadcastReceiver valueBroadcastReceiver = null;
//	List<SpecTypeObj> list = new ArrayList<SpecTypeObj>();
//	List<ReceiveRowObj> list_2 = new ArrayList<ReceiveRowObj>();
//	List<ReceiveRowObj> list_2_2 = new ArrayList<ReceiveRowObj>();
//	private String arrs[];
//
//	private DoctorRowDataInfoObj infoObj = new DoctorRowDataInfoObj();
//	Boolean text_click = true;
//	String SpecimenName = null;
//	ReceiveRowAdapter adapter;
//	List<RejectionReceiveMsgObj>  list_rrmsgObj = new ArrayList<RejectionReceiveMsgObj>();
//	RejectionReceiveMsgObj rrmsgObj;
//	boolean isDialog = true;
	
	public ReceiveActivity() {
		super(R.layout.act_receive);
	}

//	public void onSuccess(BaseModel resModel) {
//		super.onSuccess(resModel);
//		int inf = resModel.getInfCord();
//		Log.e("info", inf+"");
//		switch (inf) {
//		case InterfaceFinals.GetSpecType:
//			list.clear();
//			list.addAll(((SpecTypeModel) resModel).getPatientDataRow());
//
//			arrs = new String[list.size() + 1];
//			arrs[0] = "--请选择--";
//			for (int i = 0; i < list.size(); i++) {
//				arrs[i + 1] = list.get(i).getSpecimenName();
//				Log.e("---", arrs[i + 1]);
//			}
//			// adpater对象
//			ArrayAdapter<String> arrayAdapter_qu = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrs);
//			arrayAdapter_qu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//			receive_spinner.setAdapter(arrayAdapter_qu);
//			receive_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//						@Override
//						public void onItemSelected(AdapterView<?> arg0,
//								View arg1, int i, long arg3) {
//							SpecimenName = arrs[i];
//							if (i != 0) {
//								text_click = true;
//							}else {
//								text_click = false;
//							}
//						}
//
//						@Override
//						public void onNothingSelected(AdapterView<?> arg0) {
//							// TODO Auto-generated method stub
//
//						}
//
//					});
//			break;
//		case InterfaceFinals.Spec_Receive:
//			list_2.clear();
//			list_2_2.clear();
//			list_2.addAll(((ReceiveRowModel) resModel).getPatientData());
//
//			//过滤空数据
//			for (int i = 0; i < list_2.size(); i++) {
//				if (list_2.get(i).getPatient_Name()!= null && list_2.get(i).getPatient_Gender()!= null && !"".equals(list_2.get(i).getPatient_Name()) && !"".equals(list_2.get(i).getPatient_Gender())) {
//					list_2_2.add(list_2.get(i));
//				}
//			}
//
//
//			if (list_2_2 != null) {
//				tv_name.setText(list_2_2.get(0).getPatient_Name());
//				tv_dept.setText(list_2_2.get(0).getPatient_Gender());
//				tv_title_age.setText(list_2_2.get(0).getPatient_Age());
//				tv_bedno.setText(list_2_2.get(0).getBed_No());
//				tv_bedno.setTextColor(Color.parseColor("#EE0000"));
//				tv_name.setTextColor(Color.parseColor("#EE0000"));
//				tv_dept.setTextColor(Color.parseColor("#EE0000"));
//				tv_title_age.setTextColor(Color.parseColor("#EE0000"));
//			}
//			
//			adapter = new ReceiveRowAdapter(ReceiveActivity.this, list_2_2);
//			receive_listview.setAdapter(adapter);
//
//			break;
//
//		case InterfaceFinals.Spec_Check_Receive_Info:
//			infoObj = ((DoctorRowDataInfoModel) resModel).getPatientData();
//            Builder dialog_info = new AlertDialog.Builder(ReceiveActivity.this);
//            dialog_info.setTitle("医嘱详细");
//            dialog_info.setMessage("姓名：" + infoObj.getPatient_Name() + "\n" + 
//            		"性别：" + infoObj.getPatient_Gender() + "\n" +
//            		"年龄：" + infoObj.getPatient_Age() + "\n" +
//            		"科室：" + infoObj.getPatient_Department() + "\n" +
//            		"医嘱：" + infoObj.getDoctor_Content() + "\n" +
//            		"来源：" + infoObj.getPatient_Source() + "\n" +
//            		"申请人：" + infoObj.getApply_Name() + "\n" +
//            		"申请时间：" + infoObj.getApply_Time());
//            dialog_info.create().show();
//			
//			break;
//			case InterfaceFinals.SubmitReceive:
//				 Builder dialogSubmit = new AlertDialog.Builder(ReceiveActivity.this);
//				 dialogSubmit.setTitle("提示");
//				 dialogSubmit.setCancelable(false);
//				 dialogSubmit.setMessage(resModel.getMsg()).setPositiveButton("关闭", new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//
//							list_2.clear();
//							list_2_2.clear();
//							adapter = new ReceiveRowAdapter(ReceiveActivity.this, list_2_2);
//							receive_listview.setAdapter(adapter);
//						}
//					});
//				 dialogSubmit.create().show();
//		        break;
//			case InterfaceFinals.RejectionReceive:
//				 Builder dialogRejection = new AlertDialog.Builder(ReceiveActivity.this);
//				 dialogRejection.setTitle("提示");
//				 dialogRejection.setCancelable(false);
//				 dialogRejection.setMessage(resModel.getMsg()).setPositiveButton("关闭", new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							list_2.clear();
//							list_2_2.clear();
//							adapter = new ReceiveRowAdapter(ReceiveActivity.this, list_2_2);
//							receive_listview.setAdapter(adapter);
//						}
//					});
//				 dialogRejection.create().show();
//		        break;
//			case InterfaceFinals.Spec_Info:
//				infoObj = ((DoctorRowDataInfoModel) resModel).getPatientData();
//	            Builder dialog_2 = new AlertDialog.Builder(ReceiveActivity.this);
//	            dialog_2.setTitle("医嘱详细");
//	            dialog_2.setMessage("姓名：" + infoObj.getPatient_Name() + "\n" + 
//	            		"性别：" + infoObj.getPatient_Gender() + "\n" +
//	            		"年龄：" + infoObj.getPatient_Age() + "\n" +
//	            		"科室：" + infoObj.getPatient_Department() + "\n" +
//	            		"医嘱：" + infoObj.getDoctor_Content() + "\n" +
//	            		"来源：" + infoObj.getPatient_Source() + "\n" +
//	            		"申请人：" + infoObj.getApply_Name() + "\n" +
//	            		"申请时间：" + infoObj.getApply_Time());
//	            dialog_2.create().show();
//				
//				break;
//			case InterfaceFinals.RejectionReceive_msg:
//				list_rrmsgObj.clear();
//				list_rrmsgObj = ((RejectionReceiveMsgModel) resModel).getPatientDataRow();
//				getTime(list_rrmsgObj);
//				
//				
//				break;
//		default:
//			break;
//		}
//
//	}

	
	@Override
	public void getData() {
//		getDataFromNet(InterfaceFinals.GetSpecType, "specimenType");
		// TODO Auto-generated method stub

	}
	
	@Override
	public void initView() {

//		button3.setImageResource(R.drawable.nav_jies);
//		tv_name.setText("");
//		tv_dept.setText("");
//		tv_title_age.setText("");
//		tv_bedno.setText("");
//
//		et_receive_codeno = (EditText) findViewById(R.id.et_receive_codeno);
//        receive_submit = (TextView) findViewById(R.id.receive_submit);
//        receive_listview=(ListView) findViewById(R.id.receive_listview);
//        receive_spinner=(Spinner) findViewById(R.id.receive_spinner);
//        receive_img=(ImageView) findViewById(R.id.receive_img);
//        receive_rejection = (TextView) findViewById(R.id.receive_rejection);
//
//        receive_rejection.setOnClickListener(this);
//        receive_submit.setOnClickListener(this);
////        receive_img.setOnClickListener(this);
//        et_receive_codeno.setOnEditorActionListener(new TextView.OnEditorActionListener() {  
//            
//          @Override  
//          public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {  
//              if (actionId == EditorInfo.IME_ACTION_SEND) {  
//                  //处理事件  
//            	  if (text_click) {
//  					getDataFromNet(InterfaceFinals.Spec_Receive, "CX_commitStepData",et_receive_codeno.getText().toString().trim());
//
//					list_2.clear();
//					list_2_2.clear();
//					adapter = new ReceiveRowAdapter(ReceiveActivity.this, list_2_2);
//					receive_listview.setAdapter(adapter);
//            	  
//            	  } else {
//  						showToast("请选择采集方式!");
//  					}
//              }  
//              return false;  
//          }
//
//	
//      });  
//        
//        receive_listview.setOnItemClickListener(new OnItemClickListener() {
//
//			public void onItemClick(AdapterView<?> arg0, View view, int i,long arg3) {
//
//				getDataFromNet(InterfaceFinals.Spec_Check_Receive_Info, "doctorData", list_2_2.get(i).getDoctor_Id());
//			
//			}
//		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

//	public void onResume() {
//		super.onResume();
//		startBarcodeBroadcastReceiver();
//	}
//
//	public void onPause() {
//		super.onPause();
//		stopBarcodeBroadcastReceiver();
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//	}
	
	
//	/**
//	 * 停止接收广播
//	 */
//	private void stopBarcodeBroadcastReceiver() {
//		try {
//			if (valueBroadcastReceiver != null)
//				unregisterReceiver(valueBroadcastReceiver);
//		} catch (Exception e) {
//
//		}
//	}
//
//	/**
//	 * 开始接收广播
//	 */
//	private void startBarcodeBroadcastReceiver() {
//		try {
//			if (valueBroadcastReceiver == null)
//				valueBroadcastReceiver = new ValueBroadcastReceiver();
//			IntentFilter filter = new IntentFilter();
//			filter.addAction("lachesis_barcode_value_notice_broadcast");
//			registerReceiver(valueBroadcastReceiver, filter);
//		} catch (Exception e) {
//
//		}
//	}
//
//	/**
//	 * 关机广播接收者
//	 * 
//	 * @author
//	 * 
//	 */
//	private class ValueBroadcastReceiver extends BroadcastReceiver {
//		@Override
//		public void onReceive(Context arg0, Intent arg1) {
//			Log.i("ValueBroadcastReceiver", "onReceive......");
//			final String value = arg1.getStringExtra("lachesis_barcode_value_notice_broadcast_data_string");
//			
//			handler.post(new Runnable() {
//				public void run() {
//					et_receive_codeno.setText(value);
//					//@param bar_Code 腕带条码
//					//@Param flag 条码类型    0腕带条码 1样本条码
//					//@param department 部门
//                     //3914295  value.toString()
//					if (text_click) {
//					getDataFromNet(InterfaceFinals.Spec_Receive, "CX_commitStepData",value);
//
//					list_2.clear();
//					list_2_2.clear();
//					adapter = new ReceiveRowAdapter(ReceiveActivity.this, list_2_2);
//					receive_listview.setAdapter(adapter);
//					} else {
//						showToast("请选择采集方式!");
//					}
//				}
//			});
//
//		}
//	}
//
//	@Override
//	public void onClick(View view) {
//		switch (view.getId()) {
//	case R.id.receive_submit:
//		// 未选择spinner的采集方式
//		if (text_click) {
//			
//			if (list_2_2.size()!=0) {
//				
//			StringBuffer sb = new StringBuffer();
//
//			sb.append("<data>");
//			for (int i = 0; i < list_2_2.size(); i++) {
//				sb.append("<row><type>J</type><name>")
//						.append(user.getOprName())
//						.append("</name><code>")
//						.append(list_2_2.get(i).getBar_Code())
//						.append("</code><id>")
//						.append(list_2_2.get(i).getDoctor_Id())
//                        .append("</id></row>");
//			}
//			sb.append("</data>");
//			
//				getDataFromNet(InterfaceFinals.SubmitReceive,"JS_commitStepData", sb.toString());
//
//			}else {
//				showToast("接收列表为空!");
//			}
//		} else {
//			showToast("请选择采集方式!");
//		}
//		break;
//		
//
//	case R.id.receive_rejection:
//		// 未选择spinner的采集方式
//		if (text_click) {
//			
//			if (list_2_2.size()!=0) {
//				
//				
//				getDataFromNet(InterfaceFinals.RejectionReceive_msg, "rejectionType");
//				
////				
////			StringBuffer sb = new StringBuffer();
////
////			sb.append("<data>");
////			for (int i = 0; i < list_2_2.size(); i++) {
////				sb.append("<row><type>N</type><name>")
////						.append(user.getOprName())
////						.append("</name><code>")
////						.append(list_2_2.get(i).getBar_Code())
////						.append("</code><id>")
////						.append(list_2_2.get(i).getDoctor_Id())
////                        .append("</id><msg>")
////						.append("血量较少") ///////////////////////////////////拒收理由,目前写死，后期掉接口获取数据填充dialog里的listview
////                        .append("</msg></row>");
////			}
////			sb.append("</data>");
////			
////				getDataFromNet(InterfaceFinals.RejectionReceive,"rejectionData", sb.toString());
//
//			}else {
//				showToast("接收列表为空!");
//			}
//		} else {
//			showToast("请选择采集方式!");
//		}
//		break;
//		
//
//		}
//	}
//	//dielog弹出选择时间方法
//		private void getTime(List<RejectionReceiveMsgObj> list){
//			final List<Map<String, String>> msgList = new ArrayList<Map<String, String>>();//建立一个数组存储listview上显示的数据  
//			LayoutInflater layoutInflater = LayoutInflater.from(this);  
//	        View mylistView = layoutInflater.inflate(R.layout.listview, null);
//	        ListView lv = (ListView) mylistView.findViewById(R.id.listview);
//	        
//	        
//	        
//			SimpleAdapter adapter = new SimpleAdapter(this,msgList, R.layout.item, new String[] { "msg" },new int[] { R.id.textview });  
//	        
//			//initData为一个list类型的数据源  
//			for (int k = 0; k < list.size(); k++) {
//				Map<String, String> msgMap = new HashMap<String, String>();  
//				msgMap.put("msg", list.get(k).getRejectionName());
//				msgList.add(msgMap);  
//	      	}  
//			
//			lv.setAdapter(adapter);  
//	        
//			final AlertDialog dialog = new AlertDialog.Builder(this)  
//			        .setTitle("请选择就诊时间")
//			        .setView(mylistView)//在这里把写好的这个listview的布局加载dialog中  
//			        .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
//			            public void onClick(DialogInterface dialog, int which) {  
//			                dialog.cancel();  
//							isDialog=true; 
//			            } 
//			        }).create();  
//			dialog.setCancelable(false);//不能被返回键取消
//			if(isDialog == true){
//				dialog.show(); 
//				isDialog=false;
//				}
//			//使除了dialog以外的地方不能被点击  (此方法只能在show方法之后调用,show之前调用无效)
//			dialog.setCanceledOnTouchOutside(false);
//			lv.setOnItemClickListener(new OnItemClickListener() {//响应listview中的item的点击事件  
//			  
//			    public void onItemClick(AdapterView<?> arg0, View view, int p, long arg3) {  
//	             	Toast.makeText(ReceiveActivity.this, msgList.get(p).get("msg"), Toast.LENGTH_SHORT).show();  
//	             	StringBuffer sb = new StringBuffer();
//	             				sb.append("<data>");
//	             				for (int i = 0; i < list_2_2.size(); i++) {
//	             					sb.append("<row><type>N</type><name>")
//	             							.append(user.getOprName())
//	             							.append("</name><code>")
//	             							.append(list_2_2.get(i).getBar_Code())
//	             							.append("</code><id>")
//	             							.append(list_2_2.get(i).getDoctor_Id())
//	             	                        .append("</id><msg>")
//	             							.append(msgList.get(p).get("msg")) ///////////////////////////////////拒收理由,目前写死，后期掉接口获取数据填充dialog里的listview
//	             	                        .append("</msg></row>");
//	             				}
//	             				sb.append("</data>");
//	             				
//	             					getDataFromNet(InterfaceFinals.RejectionReceive,"rejectionData", sb.toString());
//	             	dialog.cancel();  
//					isDialog = true;
//			    }  
//			});  
//		}
//
//
//	
//	/**
//	 * 菜单、返回键响应
//	 */
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			exitBy2Click(); // 调用双击退出函数
//		}
//		return false;
//	}
//
//	/**
//	 * 双击退出函数
//	 */
//	private static Boolean isExit = false;
//
//	private void exitBy2Click() {
//		Timer tExit = null;
//
//		if (isExit == false) {
//			isExit = true; // 准备退出
//			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//			tExit = new Timer();
//			tExit.schedule(new TimerTask() {
//				public void run() {
//					isExit = false;// 取消退出
//				}
//			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
//		} else {
//			finish();
//			System.exit(0);
//		}
//}

}
