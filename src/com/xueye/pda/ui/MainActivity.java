package com.xueye.pda.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;
import com.xueye.pda.adapter.DoctorRowAdapter;
import com.xueye.pda.finals.InterfaceFinals;
import com.xueye.pda.model.BaseModel;
import com.xueye.pda.model.DoctorRowDataInfoModel;
import com.xueye.pda.model.DoctorRowDataModel;
import com.xueye.pda.model.SpecTypeModel;
import com.xueye.pda.obj.DoctorRowDataInfoObj;
import com.xueye.pda.obj.DoctorRowDataObj;
import com.xueye.pda.obj.SpecTypeObj;

@SuppressLint("ResourceAsColor")
public class MainActivity extends BaseActivity implements OnClickListener {

	TextView main_tv_yet, main_tv_not;
	ListView main_listview;
	Spinner main_spinner;

	List<SpecTypeObj> list = new ArrayList<SpecTypeObj>();
	List<DoctorRowDataObj> list_2 = new ArrayList<DoctorRowDataObj>();
	List<DoctorRowDataObj> list_2_2 = new ArrayList<DoctorRowDataObj>();

	Boolean text_click = false;
	String SpecimenName = null;
	private String arrs[];
	private DoctorRowAdapter adapter = new DoctorRowAdapter(MainActivity.this, list_2_2);;
	private DoctorRowDataInfoObj infoObj = new DoctorRowDataInfoObj();

	public MainActivity() {
		super(R.layout.activity_main);
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
//				Log.e("---", arrs[i + 1]);
			}
			// adpater对象
			ArrayAdapter<String> arrayAdapter_qu = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrs);
			arrayAdapter_qu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			main_spinner.setAdapter(arrayAdapter_qu);
			main_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
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
		case InterfaceFinals.Spec_Yet:

			list_2.clear();
			list_2_2.clear();
			list_2.addAll(((DoctorRowDataModel) resModel).getPatientData());

			//过滤空数据
			for (int i = 0; i < list_2.size(); i++) {
				if (list_2.get(i).getPatient_Name()!= null && list_2.get(i).getPatient_Gender()!= null && !"".equals(list_2.get(i).getPatient_Name()) && !"".equals(list_2.get(i).getPatient_Gender())) {
					list_2_2.add(list_2.get(i));
				}
			}
			
			
			
			adapter = new DoctorRowAdapter(MainActivity.this, list_2_2);
			main_listview.setAdapter(adapter);

			
			break;
		case InterfaceFinals.Spec_Not:

			list_2.clear();
			list_2_2.clear();
			list_2.addAll(((DoctorRowDataModel) resModel).getPatientData());
			

			//过滤空数据
			for (int i = 0; i < list_2.size(); i++) {
				if (list_2.get(i).getPatient_Name()!= null && list_2.get(i).getPatient_Gender()!= null && !"".equals(list_2.get(i).getPatient_Name()) && !"".equals(list_2.get(i).getPatient_Gender())) {
					list_2_2.add(list_2.get(i));
				}
			}
			
			
			adapter = new DoctorRowAdapter(MainActivity.this, list_2_2);
			main_listview.setAdapter(adapter);
			

			break;
		case InterfaceFinals.Spec_Info:
			infoObj = ((DoctorRowDataInfoModel) resModel).getPatientData();
            Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("医嘱详细");
            dialog.setMessage("姓名：" + infoObj.getPatient_Name() + "\n" + 
            		"性别：" + infoObj.getPatient_Gender() + "\n" +
            		"年龄：" + infoObj.getPatient_Age() + "\n" +
            		"科室：" + infoObj.getPatient_Department() + "\n" +
            		"医嘱：" + infoObj.getDoctor_Content() + "\n" +
            		"来源：" + infoObj.getPatient_Source() + "\n" +
            		"申请人：" + infoObj.getApply_Name() + "\n" +
            		"申请时间：" + infoObj.getApply_Time());
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

		button0.setImageResource(R.drawable.nav_chax_select);
		tv_name.setVisibility(View.GONE);
		tv_dept.setVisibility(View.GONE);
		tv_bedno.setText(user.getOprName());
		tv_title_age.setText(user.getDepartment());
		main_tv_yet = (TextView) findViewById(R.id.main_tv_yet);
		main_tv_not = (TextView) findViewById(R.id.main_tv_not);
		main_listview = (ListView) findViewById(R.id.main_listview);
//		main_spinner = (Spinner) findViewById(R.id.main_spinner);

		
		main_tv_yet.setOnClickListener(this);
		main_tv_not.setOnClickListener(this);
		
		main_listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int i,long arg3) {

				getDataFromNet(InterfaceFinals.Spec_Info, "doctorData", list_2_2.get(i).getDoctor_Id());
			
			}
		});
		
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.main_tv_yet:
			if (text_click) {
				list_2.clear();
				list_2_2.clear();
				adapter.notifyDataSetChanged();
				main_tv_yet.setTextColor(Color.parseColor("#01a3ae"));
				main_tv_not.setTextColor(Color.parseColor("#a1a1a1"));
				getDataFromNet(InterfaceFinals.Spec_Yet, "doctorRowData", user.getDepartment(),SpecimenName,"1");
			}else{
				showToast("请选择采集方式!");
			}
			break;
		case R.id.main_tv_not:
			if (text_click) {
				list_2.clear();
				list_2_2.clear();
				adapter.notifyDataSetChanged();
				main_tv_yet.setTextColor(Color.parseColor("#a1a1a1"));
				main_tv_not.setTextColor(Color.parseColor("#01a3ae"));
				getDataFromNet(InterfaceFinals.Spec_Not, "doctorRowData", user.getDepartment(),SpecimenName,"0");
			}else{
				showToast("请选择采集方式!");
			}
			break;

		default:
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
