package com.xueye.pda.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xueye.pda.R;
import com.xueye.pda.obj.NurseWorkstationObj;

public class ReceiveWorkstationAdapter extends BaseAdapter {
	Context context;
	public List<NurseWorkstationObj> list;
	public HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public ReceiveWorkstationAdapter(Context context,List<NurseWorkstationObj> list) {
		this.context = context;
		this.list = list;
		isSelected.clear();
		for (int i = 0; i < list.size(); i++) {
			isSelected.put(i, true);
		}
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
			view = LayoutInflater.from(context).inflate(R.layout.item_receive,
					null);
			viewHolder.item_receive_name = (TextView) view
					.findViewById(R.id.item_receive_name);
			viewHolder.item_receive_id = (TextView) view
					.findViewById(R.id.item_receive_id);
			viewHolder.item_receive_info = (TextView) view
					.findViewById(R.id.item_receive_info);
			viewHolder.item_receive_cb = (CheckBox) view
					.findViewById(R.id.item_receive_cb);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.item_receive_name.setText(list.get(position).getpNme());
		viewHolder.item_receive_id.setText(list.get(position).getBarCode());
		viewHolder.item_receive_info.setText(list.get(position).getAdviceName());

		if(list.size()!=0){
		viewHolder.item_receive_cb.setChecked(getIsSelected().get(position));
		}
		
		return view;
	}

	public HashMap<Integer,Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(HashMap<Integer,Boolean> isSelected) {
    	this.isSelected = isSelected;
    }
//    public static void setData(List<NurseWorkstationObj> list){
//    	ReceiveWorkstationAdapter.list = list;
//    }


	public class ViewHolder {
		public TextView item_receive_name, item_receive_id, item_receive_info;
		public CheckBox item_receive_cb;
	}
}
