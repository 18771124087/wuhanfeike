package com.xueye.pda.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xueye.pda.R;
import com.xueye.pda.obj.CollectObj;
public class CollectAdapter extends BaseAdapter{
	Context context;
	List<CollectObj> list=new ArrayList<CollectObj>();
	public  static HashMap<Integer,Boolean> isSelected;;

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public CollectAdapter(Context context, List<CollectObj> list) {
	super();
	this.context = context;
	this.list = list;isSelected=new HashMap<Integer,Boolean>();
	for (int i=0;i<list.size();i++) {

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
			view = LayoutInflater.from(context).inflate(R.layout.item_collect, null);
//			viewHolder.item_collect_name = (TextView) view.findViewById(R.id.item_collect_name);
//			viewHolder.item_collect_sex = (TextView) view.findViewById(R.id.item_collect_sex);
//			viewHolder.item_collect_age = (TextView) view.findViewById(R.id.item_collect_age);
			viewHolder.item_collect_type = (TextView) view.findViewById(R.id.item_collect_type);
			viewHolder.item_collect_info= (TextView) view.findViewById(R.id.item_collect_info);
			viewHolder.cb= (CheckBox) view.findViewById(R.id.item_cb);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}
//		viewHolder.item_collect_name.setText(list.get(position).getPatient_Name());
//		viewHolder.item_collect_sex.setText(list.get(position).getPatient_Gender());
//		viewHolder.item_collect_age.setText(list.get(position).getPatient_Age());
		viewHolder.item_collect_info.setText(list.get(position).getDoctor_Content());
		viewHolder.item_collect_type.setText(list.get(position).getSample_Site());
		
		if(list.size()!=0){
		viewHolder.cb.setChecked(getIsSelected().get(position));
		}
		return view;
	}
	
	public static HashMap<Integer,Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer,Boolean> isSelected) {
        CollectAdapter.isSelected = isSelected;
    }


	public class ViewHolder{
//		TextView item_collect_name,item_collect_sex,item_collect_age,
		TextView item_collect_info,item_collect_type;
		public CheckBox cb;
	}
}
