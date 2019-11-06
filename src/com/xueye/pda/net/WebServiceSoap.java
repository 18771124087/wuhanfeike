package com.xueye.pda.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.xueye.pda.finals.InterfaceFinals;

/**
 * @author wok
 * 
 *   OrgCode：医院编码（如果部署的是云平台或多院区需要指定编码） InputXml：函数入参 RspType：返回类型（XML,JSON 1）
 */
public class WebServiceSoap {
	
//	ArrayList<String> key = new ArrayList<String>();
	 String[] params;
	public WebServiceSoap(String... params) {
//		this.key = key;
		this.params = params;
	}
	
	public  String MsgInterface() throws IOException{

		String result = null;
		String nameSpace = "http://tempuri.org/";
		// 调用的方法名称
		String methodName = params[0];
		// EndPoint
		String endPoint = InterfaceFinals.BASE_URL;
		// SOAP Action
		String soapAction = "";

		// 创建HttpTransportSE对象，该对象用于调用WebService操作
		HttpTransportSE ht = new HttpTransportSE(endPoint, 20000);

		// 指定WebService的命名空间和调用的方法名
		SoapObject soapObject = new SoapObject(nameSpace, methodName);
		soapObject.addProperty("inputXml", params[1]);

		Log.e("===调用方法名===", params[0]);
		Log.e("===请求参数====", params[1]);

		// 设置需调用WebService接口需要传入的参数
//		for (int i = 0; i < key.size(); i++) {
//
//			soapObject.addProperty(key.get(i), params[i+1]);
//			Log.e("===请求参数==" + i +"=", "key = "+ key.get(i) + " ; param = " + params[i+1]);
//		}

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);

		envelope.bodyOut = soapObject;

		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;

		// 等价于envelope.bodyOut = soapObject;
		envelope.setOutputSoapObject(soapObject);
		SoapObject object;

		try {
			// 调用webservice
			ht.call(soapAction, envelope);

			// 获取返回的数据
			if (envelope.getResponse() != null) {
				object = (SoapObject) envelope.bodyIn;
				result = object.getProperty(0).toString();
			}

		}  catch (XmlPullParserException e) {
//			e.getStackTrace();
			Log.e("soap请求错误的异常信息", e.toString());

		} catch (Exception e) {
//			e.getStackTrace();
			Log.e("soap请求错误的异常信息", e.toString());

		}
		Log.e("result", result+"");
		if (result == null) {
			return "";
		}else{
			return result;
		}
	}

	public  String getSMSFromWebservice(String PhoneNumber, String IdentifyingCode) {

		String result = "";
		String nameSpace = "http://tempuri.org/";
		// 调用的方法名称
		String methodName = "SendSMS";
		// EndPoint
		String endPoint = "http://112.74.203.57:8520/SMS_Service.asmx";
		// SOAP Action
		String soapAction = nameSpace + methodName;

		// 创建HttpTransportSE对象，该对象用于调用WebService操作
		HttpTransportSE ht = new HttpTransportSE(endPoint, 20000);
		ht.debug = true;
		// System.out.println(ht.requestDump);
		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);

		// 设置需调用WebService接口需要传入的参数
		rpc.addProperty("PhoneNumber", PhoneNumber);
		rpc.addProperty("IdentifyingCode", IdentifyingCode);

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);
		SoapObject object;

		try {
			ht.call(soapAction, envelope);
			System.out.println(ht.requestDump);
			if (envelope.getResponse() != null) {
				object = (SoapObject) envelope.bodyIn;
				result = object.getProperty(0).toString();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 获取返回的结果
		return result;
	}

}
