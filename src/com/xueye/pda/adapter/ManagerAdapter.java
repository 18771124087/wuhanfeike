package com.xueye.pda.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xueye.pda.BaseActivity;
import com.xueye.pda.R;
import com.xueye.pda.adapter.DoctorRowAdapter.ViewHolder;
import com.xueye.pda.obj.PerObj;

public class ManagerAdapter extends BaseAdapter{
    Context context;
    List<PerObj> list=new ArrayList<PerObj>();
	public ManagerAdapter(Context context,List<PerObj> list){
		super();
		this.context=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		ViewHolder viewHolder = null;
		if (view == null) { 
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.item_manager, null);
			viewHolder.item_user_no= (TextView) view.findViewById(R.id.item_user_no);
			viewHolder.item_user_name = (TextView) view.findViewById(R.id.item_user_name);
			viewHolder.item_user_dept = (TextView) view.findViewById(R.id.item_user_dept);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.item_user_no.setText(list.get(position).getUser_no());
		viewHolder.item_user_name.setText(list.get(position).getUser_name());
		viewHolder.item_user_dept.setText(list.get(position).getUser_department());


		
		return view;
	}
	
	class ViewHolder{
		TextView item_user_no,item_user_name,item_user_dept;
	}

}
