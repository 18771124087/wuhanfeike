package com.xueye.pda.net;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xueye.pda.BaseActivity;
import com.xueye.pda.finals.InterfaceFinals;
import com.xueye.pda.model.BaseModel;
import com.xueye.pda.model.CheckRowModel;
import com.xueye.pda.model.DoctorRowDataInfoModel;
import com.xueye.pda.model.DoctorRowDataModel;
import com.xueye.pda.model.NurseWorkstationModel;
import com.xueye.pda.model.NurseWorkstationUserModel;
import com.xueye.pda.model.PerModel;
import com.xueye.pda.model.ReceiveRowModel;
import com.xueye.pda.model.RejectionReceiveMsgModel;
import com.xueye.pda.model.SpecTypeModel;
import com.xueye.pda.model.UserModel;

/**
 * @author wok
 * 
 *         接口获取数据，解析数据，返回BaseModel
 **/
public class AsyncHttpUtils extends AsyncTask<String, Integer, BaseModel> {

	Context mContext = null;
	int infCord = -1;
	boolean responseError = false;
	boolean parseError = false;
	boolean responseNull = false;
	boolean showProgressDialog = true;
	private ProgressDialog progressDialog = null;
	private String jsonData;

	public AsyncHttpUtils(Context context, int infCord) {
		this.mContext = context;
		this.infCord = infCord;
	}

	public AsyncHttpUtils(Context context, int infCord,
			boolean showProgressDialog) {
		this.showProgressDialog = showProgressDialog;
		this.mContext = context;
		this.infCord = infCord;
	}

	public void onPreExecute() {
		super.onPreExecute();
		// 检查网络是否打开
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取代表联网状态的NetWorkInfo对象
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			// 获取当前的网络连接是否可用
			if (!networkInfo.isAvailable() || !networkInfo.isConnected()) {
				((BaseActivity) mContext).showToast("网络连接异常，请检查网络状态");
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
				alertDialog.setTitle("网络连接异常，请检查网络状态")
						.setCancelable(false)
						.setPositiveButton("确定",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0,
											int arg1) {
										((Activity) mContext).finish();
									}
								}).create().show();
			}
		} else {
			((BaseActivity) mContext).showToast("网络连接异常，请检查网络状态");
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
			alertDialog
					.setTitle("网络连接异常，请检查网络状态")
					.setCancelable(false)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0,
										int arg1) {
									((Activity) mContext).finish();
								}
							}).create().show();
		}

		// 请求开始弹窗
		if (showProgressDialog && mContext != null && !((Activity) mContext).isFinishing()) {
			progressDialog = new ProgressDialog(mContext);
			progressDialog.setMessage("正在加载中，请稍后..");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(true);
			progressDialog.show();
		}
	}

	@Override
	public BaseModel doInBackground(String... params) {
		BaseModel baseModel = null;
		WebServiceSoap soap =null;
		String res =null;
		String inputXml =null;
		ArrayList<String> paramsKey = new ArrayList<String>();
		try {
		switch (infCord) {
		case InterfaceFinals.Login: // 登录
			// 参数顺序 - 
			paramsKey.add("iCode");
			paramsKey.add("iPassWord");
			inputXml = paramsXml(paramsKey, params);
			soap = new WebServiceSoap("checkLogin",inputXml);

			res = soap.MsgInterface();
			baseModel = soapData(res,UserModel.class);
			break;
		case InterfaceFinals.ChangePsw: // 修改密码
			// 参数顺序 - 
			paramsKey.add("user_id");
			paramsKey.add("pwd");
			paramsKey.add("modifyPsw");
			inputXml = paramsXml(paramsKey, params);
			soap = new WebServiceSoap("modifyPassword",inputXml);
			res = soap.MsgInterface();
			baseModel = soapData(res,BaseModel.class);
			break;
		case InterfaceFinals.Spec_collect_userInfo: // 采集-腕带号查询病人信息
			// 参数顺序 - 
			//@param pId 腕带条码
			paramsKey.add("pId");
			inputXml = paramsXml(paramsKey, params);
			soap = new WebServiceSoap("getPatientInfo", inputXml);
			
			res = soap.MsgInterface();
			baseModel = soapData(res,NurseWorkstationUserModel.class);
			break;
//		case InterfaceFinals.GetSpecType: // 获取采集方式
//			// 参数顺序 - 
//	 		soap = new WebServiceSoap(paramsKey, params[0]);
//			
//			res = soap.MsgInterface();
//			
//			baseModel = soapData(res,SpecTypeModel.class);
//			break;
//		case InterfaceFinals.Spec_Yet: // 医嘱列表-已采集
//			// 参数顺序 - 
//			paramsKey.add("department");
//			paramsKey.add("site");
//			paramsKey.add("flag");
//			soap = new WebServiceSoap(paramsKey, params[0],params[1],params[2],params[3]);
//			res = soap.MsgInterface();
//			
//			baseModel = soapData(res,DoctorRowDataModel.class);
//			break;
//		case InterfaceFinals.Spec_Not: // 医嘱列表-未采集
//			// 参数顺序 - 
//			paramsKey.add("department");
//			paramsKey.add("site");
//			paramsKey.add("flag");
//			soap = new WebServiceSoap(paramsKey, params[0],params[1],params[2],params[3]);
//			res = soap.MsgInterface();
//			
//			baseModel = soapData(res,DoctorRowDataModel.class);
//			break;
//		case InterfaceFinals.Spec_Info: // 医嘱列表-医嘱详情
//			// 参数顺序 - 
//			paramsKey.add("doctor_Id");
//			soap = new WebServiceSoap(paramsKey, params[0],params[1]);
//			res = soap.MsgInterface();
//			
//			baseModel = soapData(res,DoctorRowDataInfoModel.class);
//			break;

		case InterfaceFinals.Spec_collect: // 医嘱_采集
			// 参数顺序 - eType
			paramsKey.add("barCode");
			paramsKey.add("cType");
			paramsKey.add("eType");
			inputXml = paramsXml(paramsKey, params);
			soap = new WebServiceSoap("getMedicalAdvice", inputXml);
			res = soap.MsgInterface();
			baseModel = soapData(res,NurseWorkstationModel.class);
			
//			// 参数顺序 - 
//			//@param bar_Code 腕带条码
//			//@Param flag 条码类型    0腕带条码 1样本条码
//			//@param department 部门
//			paramsKey.add("bar_Code");
//			paramsKey.add("site");
//			paramsKey.add("department");
//			soap = new WebServiceSoap(paramsKey, params[0],params[1],params[2],params[3]);
//			res = soap.MsgInterface();
//			baseModel = soapData(res,CollectModel.class);
			break;
//		case InterfaceFinals.Spec_Check: // 医嘱列表 送检
//			// 参数顺序 - 
////			<code>string</code>
//
//			paramsKey.add("code");
//			soap = new WebServiceSoap(paramsKey, params[0],params[1]);
//			res = soap.MsgInterface();
//			baseModel = soapData(res,CheckRowModel.class);
//			break;
		case InterfaceFinals.SubmitSpec: // 采集提交
			// 参数顺序 - 
//			paramsKey.add("data");
			inputXml = params[0]; //已经在页面内拼接好参数
			soap = new WebServiceSoap( "collectSubmit",inputXml);
			res = soap.MsgInterface();
			baseModel = soapData(res,BaseModel.class);
			break;	
		case InterfaceFinals.SubmitCheck: // 送检提交
			// 参数顺序 - 
//			paramsKey.add("data");
			inputXml = params[0]; //已经在页面内拼接好参数
			soap = new WebServiceSoap("checkSubmit", inputXml);
			res = soap.MsgInterface();
			baseModel = soapData(res,BaseModel.class);
			break;
		case InterfaceFinals.SubmitReceive: // 接收提交
			// 参数顺序 - 
//			paramsKey.add("data");
			inputXml = params[0]; //已经在页面内拼接好参数
			soap = new WebServiceSoap("receiveSubmit",inputXml);
			res = soap.MsgInterface();
			baseModel = soapData(res,BaseModel.class);
			break;
		case InterfaceFinals.RejectionReceive_msg: // 拒收理由
			// 参数顺序 - 
			inputXml = paramsXml(paramsKey);
			soap = new WebServiceSoap("getRejectionReason", inputXml);
			res = soap.MsgInterface();
			baseModel = soapData(res,RejectionReceiveMsgModel.class);
			break;
		case InterfaceFinals.RejectionReceive: // 拒收提交
			// 参数顺序 - 
//			paramsKey.add("data");
			inputXml = params[0]; //已经在页面内拼接好参数
			soap = new WebServiceSoap("rejectionSubmit",inputXml);
			res = soap.MsgInterface();
			baseModel = soapData(res,BaseModel.class);
			break;
//		case InterfaceFinals.Spec_Receive: // 医嘱列表 送检
//			// 参数顺序 - 
////			<code>string</code>
//
//			paramsKey.add("code");
//			soap = new WebServiceSoap(paramsKey, params[0],params[1]);
//			res = soap.MsgInterface();
//			baseModel = soapData(res,ReceiveRowModel.class);
//			break;
//		case InterfaceFinals.Spec_Check_Receive_Info: // 送检接收详细
//			// 参数顺序 - 
//			paramsKey.add("doctor_Id");
//			soap = new WebServiceSoap(paramsKey, params[0],params[1]);
//			res = soap.MsgInterface();
//			baseModel = soapData(res,DoctorRowDataInfoModel.class);
//			break;
//			
//		case InterfaceFinals.GetPer: // 送检接收详细
//			// 参数顺序 - 
//			paramsKey.add("userid");
//			soap = new WebServiceSoap(paramsKey, params[0],params[1]);
//			res = soap.MsgInterface();
//			baseModel = soapData(res,PerModel.class);
//			break;
//		case InterfaceFinals.SubReg: // 提交授权
//			// 参数顺序 - 
////			<iUser>string</iUser>
////		      <role_no>string</role_no>
////		      <role_name>string</role_name>
////		      <iPassWord>string</iPassWord>
//			paramsKey.add("iUser");
//			paramsKey.add("role_no");
//			paramsKey.add("role_name");
//			paramsKey.add("iPassWord");
//			soap = new WebServiceSoap(paramsKey, params[0],params[1],params[2],params[3],params[4]);
//			res = soap.MsgInterface();
//			baseModel = soapData(res,BaseModel.class);
//			break;

		default:
			break;
		}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return baseModel;
	}

	/**
	 * soap请求数据
	 * 
	 * @param url
	 * @param jsonData
	 * @param modle
	 * @return
	 */
	public BaseModel soapData(String res,Class<? extends BaseModel> modle) {

//		String result = res.substring(res.indexOf("{", res.indexOf("result")), res.lastIndexOf("}"));
//		Log.e("截取返回字符串", result);
//		result = result.replace("null", "\"\"");
		return parseJson(res, modle);
	}

	/**
	 * 解析json
	 * 
	 * @param response
	 * @param cls
	 * @return
	 */
	public BaseModel parseJson(String response, Class<? extends BaseModel> cls) {
		Gson gson = new Gson();
		BaseModel baseModel = null;
		try {
			if ("".equals(response)) { // 返回为空 网络异常
				baseModel = new BaseModel();
				responseNull = true;
			} else if (response.contains("{")) { // 通信成功
				String s = response;
				baseModel = (BaseModel) gson.fromJson(s, cls);

			} else { // 通信是异常返回
				baseModel = new BaseModel();
				responseError = true;
				// ((BaseActivity) mContext).showToast("返回异常");
	 		}
			baseModel.setInfCord(infCord);
		} catch (JsonSyntaxException e) {
			parseError = true;
			// ((BaseActivity) mContext).showToast("数据解析错误");
		}

		return baseModel;
	}

	@Override
	protected void onPostExecute(BaseModel result) {
		closeProDialog();

		try {
			if (responseNull) {
		 		((BaseActivity) mContext).showToast("返回空数据");
			}else if (parseError) {
				((BaseActivity) mContext).showToast("数据解析错误");
			} else if (responseError) {
				((BaseActivity) mContext).showToast("返回异常");
			} else if ("1".equals(result.getState())) {// success
				((BaseActivity) mContext).onSuccess(result);
			} else if ("2".equals(result.getState())) {// success空记录
				((BaseActivity) mContext).onFail(result);
			} else {// fail
				((BaseActivity) mContext).onFail(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			((BaseActivity) mContext).showToast("暂无更新数据");
			Log.e("暂无更新数据的异常信息 = ", e.toString());
			Log.e("暂无更新数据的异常信息 = ", result.toString());
		}

		super.onPostExecute(result);

	}

	@Override
	protected void onCancelled() {
		closeProDialog();

		super.onCancelled();
	}

	/**
	 * 将数据格式化成json字符串
	 */
	public String formatJson(String actionName, ArrayList<String> paramsKey,
			String... params) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"actionName\":\"").append(actionName)
				.append("\",\"actionInfo\":{");
		for (int i = 0; i < paramsKey.size(); i++) {
			sb.append("\"").append(paramsKey.get(i)).append("\":\"")
					.append(params[i]).append("\"");
			if (i != paramsKey.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("}}");
//		Log.e("=====gz=====", "转化的请求json数据" + sb.toString());
		return sb.toString();
	}

	public String paramsXml(ArrayList<String> key, String... params){

		StringBuffer sb = new StringBuffer();
		sb.append("<data><row>");
		for (int i = 0; i < params.length; i++) {
			sb.append("<" + key.get(i) + ">")
			.append(params[i])
			.append("</" + key.get(i) + ">");
		};
         sb.append("</row></data>");
         
         return sb.toString();
	}
	
	
	/**
	 * 关闭Dialog
	 */
	public void closeProDialog() {
		if (showProgressDialog && progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
}
