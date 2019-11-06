package com.xueye.pda.finals;

import java.io.File;

import android.os.Environment;

/**
 * @author wok
 * 
 * 接口定义
 **/
public class InterfaceFinals {
	//http://192.168.13.153:8080/hisFront/data/dataService.shtml
//	public static final String BASE_URL = "http://27.17.7.70:8800/hisFront/data/dataService.shtml";  //测试请求url
	//public static final String BASE_URL = "http://192.168.16.226:8080/hisFront/data/dataService.shtml"; //请求url

	public static final String BASE_URL = "http://192.168.2.78:8088/WebService.asmx"; //正式
//	public static final String BASE_URL = "http://112.74.203.57:8088/WebService.asmx"; //测试
	
	//public static final String BASE_URL = "http://192.168.16.49:7001/hisFront/data/dataService.shtml";
	
	
	public static final int Login = 1;  //登录
	public static final int GetSpecType = 2;  //获取采集类型
	public static final int Spec_Yet = 3;  //医嘱列表-已采集
	public static final int Spec_Not = 4;  //医嘱列表-未采集
	public static final int Spec_Info = 5;  //医嘱列表-医嘱详情
	

	public static final int Spec_collect_userInfo = 21;  //医嘱_采集页面_腕带号查询病人信息
	
	public static final int Spec_collect = 6;  //医嘱_采集页面
	public static final int SubmitSpec = 7;//提交采集
	
	public static final int Spec_Check = 8; //送检列表
	public static final int Spec_Check_Receive_Info = 9; //送检接收详情
	public static final int SubmitCheck = 10; //送检提交
	public static final int SubmitReceive = 11; //接收提交
	public static final int RejectionReceive = 12; //拒收提交
	public static final int ChangePsw=13;//修改密码
	public static final int Spec_Receive=14;//接收医嘱
	
	public static final int GetPer=15; //获取人员列表
	public static final int SubReg=16;//授权提交
	
	public static final int RejectionReceive_msg = 17; //拒收理由
	
	public static final String userNamePattern = "[\u4e00-\u9fa5]{2,}";//验证姓名是否合法
	
	public static final String HospitalId = "01";  //医院ID
	
	
	/**
	 * 下载医生头像地址imageLoaderURL + 工号
	 */
//	public static final String imageLoaderURL = "http://112.74.203.57:8011/images/";
	public static final String imageLoaderURL = "http://221.233.70.99:7001/hisFront/images/";
	/** 
	 * 文件下载存放目录
	 */
	public static final String fileDirPath = Environment.getExternalStorageDirectory() + File.separator + "imageloader/Cache";// 图片以及GIF文件存放目录
}