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
import com.xueye.pda.obj.ReceiveRowObj;

public class ReceiveRowAdapter extends BaseAdapter {
	Context context;
	List<ReceiveRowObj> list;

	public ReceiveRowAdapter(Context context, List<ReceiveRowObj> list) {
		this.context = context;
		this.list = list;
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
			view = LayoutInflater.from(context).inflate(R.layout.item_receiverow, null);
//			viewHolder.item_receive_name = (TextView) view.findViewById(R.id.item_receive_name);
//			viewHolder.item_receive_sex = (TextView) view.findViewById(R.id.item_receive_sex);
//			viewHolder.item_receive_age = (TextView) view.findViewById(R.id.item_receive_age);
			viewHolder.item_receive_type = (TextView) view
					.findViewById(R.id.item_receive_type);
			viewHolder.item_receive_info = (TextView) view
					.findViewById(R.id.item_receive_info);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
//		viewHolder.item_receive_name.setText(list.get(position)
//				.getPatient_Name());
//		viewHolder.item_receive_sex.setText(list.get(position)
//				.getPatient_Gender());
//		viewHolder.item_receive_age
//				.setText(list.get(position).getPatient_Age());
		viewHolder.item_receive_info.setText(list.get(position)
				.getDoctor_Content());
		viewHolder.item_receive_type.setText(list.get(position)
				.getSample_Site());

		return view;
	}

	class ViewHolder {
//		TextView item_receive_name, item_receive_sex, item_receive_age,
		TextView item_receive_info, item_receive_type;
	}
}
