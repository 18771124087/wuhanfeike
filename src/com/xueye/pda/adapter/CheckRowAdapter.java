package com.xueye.pda.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xueye.pda.R;
import com.xueye.pda.obj.CheckRowObj;
import com.xueye.pda.obj.DoctorRowDataObj;

public class CheckRowAdapter extends BaseAdapter{
	Context context;
	List<CheckRowObj> list;
public CheckRowAdapter(Context context,List<CheckRowObj> list){
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
			view = LayoutInflater.from(context).inflate(R.layout.item_checkrow, null);
//			viewHolder.item_checkrow_name = (TextView) view.findViewById(R.id.item_checkrow_name);
//			viewHolder.item_checkrow_sex = (TextView) view.findViewById(R.id.item_checkrow_sex);
//			viewHolder.item_checkrow_age = (TextView) view.findViewById(R.id.item_checkrow_age);
			viewHolder.item_checkrow_type = (TextView) view.findViewById(R.id.item_checkrow_type);
			viewHolder.item_checkrow_info= (TextView) view.findViewById(R.id.item_checkrow_info);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}
//		viewHolder.item_checkrow_name.setText(list.get(position).getPatient_Name());
//		viewHolder.item_checkrow_sex.setText(list.get(position).getPatient_Gender());
//		viewHolder.item_checkrow_age.setText(list.get(position).getPatient_Age());
		viewHolder.item_checkrow_info.setText(list.get(position).getDoctor_Content());
		viewHolder.item_checkrow_type.setText(list.get(position).getSample_Site());

		
		return view;
	}

	class ViewHolder{
//		TextView item_checkrow_name,item_checkrow_sex,item_checkrow_age,
		TextView item_checkrow_info,item_checkrow_type;
	}
}
