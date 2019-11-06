package com.xueye.pda.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xueye.pda.R;
import com.xueye.pda.obj.NurseWorkstationObj;
public class NuresWorkstationAdapter extends BaseAdapter{
	Context context;
	List<NurseWorkstationObj> list;
//	public  static HashMap<Integer,Boolean> isSelected;;

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public NuresWorkstationAdapter(Context context, List<NurseWorkstationObj> list) {
	super();
	this.context = context;
	this.list = list;
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
			view = LayoutInflater.from(context).inflate(R.layout.item_nurse, null);
			viewHolder.item_nurse_name = (TextView) view.findViewById(R.id.item_nurse_name);
			viewHolder.item_nurse_id = (TextView) view.findViewById(R.id.item_nurse_id);
			viewHolder.item_nurse_info= (TextView) view.findViewById(R.id.item_nurse_info);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.item_nurse_name.setText(list.get(position).getpNme());
		viewHolder.item_nurse_id.setText(list.get(position).getBarCode());
		viewHolder.item_nurse_info.setText(list.get(position).getAdviceName());
		
		return view;
	}
	

	public class ViewHolder{
		TextView item_nurse_name,item_nurse_id,item_nurse_info;
	}
}
