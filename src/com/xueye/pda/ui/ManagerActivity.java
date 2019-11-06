package com.xueye.pda.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.ManagerFactoryParameters;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;
import com.xueye.pda.adapter.ManagerAdapter;
import com.xueye.pda.finals.InterfaceFinals;
import com.xueye.pda.model.BaseModel;
import com.xueye.pda.model.PerModel;
import com.xueye.pda.model.SpecTypeModel;
import com.xueye.pda.obj.PerObj;
import com.xueye.pda.ui.collect.collectActivity;
import com.xueye.pda.utils.PreferencesUtil;

public class ManagerActivity extends BaseActivity{
	
	TextView tv_search;
	EditText et_search;
	
    List<PerObj> list=new ArrayList<PerObj>();
    List<PerObj> list_2=new ArrayList<PerObj>();
    ManagerAdapter adapter;
    String role_no="1048";
	String role_name="护士";
	int p=0;
	public ManagerActivity() {
		super(R.layout.activity_manager,false,false);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onSuccess(BaseModel resModel) {
		// TODO Auto-generated method stub
		super.onSuccess(resModel);
		int inf = resModel.getInfCord();
		Log.e("----get---", inf+"123321");
		switch (inf) {
		case InterfaceFinals.GetPer:
			list.clear();
			list_2.clear();
			list.addAll(((PerModel) resModel).getPatientData());
			
			//过滤空数据
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getUser_no()!= null && list.get(i).getUser_name()!= null && !"".equals(list.get(i).getUser_no()) && !"".equals(list.get(i).getUser_name())) {
					list_2.add(list.get(i));
				}
			}
			
			adapter=new ManagerAdapter(ManagerActivity.this, list_2);
			manager_listview.setAdapter(adapter);
		
			break;
			
		case InterfaceFinals.SubReg:
			showToast(resModel.getMsg()+"成功");
			break;
        default:
        	break;
		
	}
	}

	@Override
	public void getData() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initView() {
		manager_listview = (ListView) findViewById(R.id.manager_listview);
		tv_search = (TextView) findViewById(R.id.tv_search);
		et_search = (EditText) findViewById(R.id.et_search);
		
		tv_search.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				String et = et_search.getText().toString().trim();
				if (!"".equals(et)) {
					getDataFromNet(InterfaceFinals.GetPer, "Jurisdiction",et);
				}else {
					showToast("搜索编号不能为空！");
				}
			}
		});
		
		manager_listview.setOnItemClickListener(new OnItemClickListener() {

			private EditText et_setpw;
			private RadioGroup rg;

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				 p=position;
				 Builder dialog = new AlertDialog.Builder(ManagerActivity.this);
				    LayoutInflater mInflater=LayoutInflater.from(ManagerActivity.this);
				    View view2=mInflater.inflate(R.layout.dailog_manager, null, false);
				   
				    et_setpw = (EditText) view2.findViewById(R.id.et_setpw);
				    rg = (RadioGroup) view2.findViewById(R.id.rg);
//				    RadioButton rb_cj=(RadioButton) view2.findViewById(R.id.rb_cj);
//				    RadioButton rb_js=(RadioButton) view2.findViewById(R.id.rb_js);
				    rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(RadioGroup group, int checkedId) {
							// TODO Auto-generated method stub
							switch (checkedId) {
							case R.id.rb_cj:
								role_no="1048";
								role_name="护士";
								break;
								
                            case R.id.rb_js:
                            	role_no="1108";
                            	role_name="检验员";
								break;

							default:
								break;
							}
						}
					});
				    
		            dialog.setTitle("为"+list_2.get(p).getUser_no()+":"+list_2.get(p).getUser_name()+"授权？");
		            dialog.setView(view2).setPositiveButton("确定授权", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//授权接口 User_GetList
							String pw=et_setpw.getText().toString().trim();
							if("".equals(pw)){
								showToast("密码不可为空");
							}else{
							getDataFromNet(InterfaceFinals.SubReg, "User_GetList",list_2.get(p).getUser_no(),role_no,role_name,pw);
							}
						}
					});
		            dialog.setNegativeButton("取消编辑", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
		            dialog.create().show();
			}
		});
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
	private ListView manager_listview;

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
			PreferencesUtil.setPreferences(ManagerActivity.this,"USER", null);
			finish();
			System.exit(0);
		}
	}
}
