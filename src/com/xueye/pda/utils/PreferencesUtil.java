package com.xueye.pda.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

/**
 * SharePreference工具类
 * 
 * @author wok
 * 
 */
public class PreferencesUtil {
	private static String PreferenceName = "MolianConstant";

	/**
	 * SharePreference中的值
	 */
	public static String getStringPreferences(Context context, String name) {
		SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);

		// 获取数据
		return sp.getString(name, "");
	}

	/**
	 * SharePreference中的值
	 */
	public static int getIntPreferences(Context context, String name) {
		SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);

		// 获取数据
		return sp.getInt(name, -1);
	}

	public static boolean getBooleanPreferences(Context context, String name) {
		SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);

		// 获取数据
		return sp.getBoolean(name, false);
	}

	public static float getFloatPreferences(Context context, String name) {
		SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);

		// 获取数据
		return sp.getFloat(name, 1l);
	}

	/**
	 * 将String信息存入Preferences
	 * 
	 * @param context
	 * @param userName
	 */
	public static void setPreferences(Context context, String name, String value) {
		SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);

		// 存入数据
		Editor editor = sp.edit();
		editor.putString(name, value);
		editor.commit();
	}

	public static void setPreferences(Context context, String name, Object obj) {
		if (obj == null) {
			return;
		}
		
		SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);

		ByteArrayOutputStream toByte;
		try {
			toByte = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(toByte);
			oos.writeObject(obj);

			// 对byte[]进行Base64编码
			String obj64 = new String(Base64.encode(toByte.toByteArray(), Base64.DEFAULT));
			Editor editor = sp.edit();
			editor.putString(name, obj64);
			editor.commit();

			System.out.println("保存成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object getPreferences(Context context, String name) {
		SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);

		try {
			String obj64 = sp.getString(name, "");
			if (obj64.length() == 0) {
				return null;
			}
			byte[] base64Bytes = Base64.decode(obj64, Base64.DEFAULT);
			ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (OptionalDataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将int信息存入Preferences
	 * 
	 * @param context
	 * @param userName
	 */
	public static void setPreferences(Context context, String name, int value) {
		SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);

		// 存入数据
		Editor editor = sp.edit();
		editor.putInt(name, value);
		editor.commit();
	}

	/**
	 * 将float信息存入Preferences
	 * 
	 * @param context
	 * @param userName
	 */
	public static void setPreferences(Context context, String name, float value) {
		SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);

		// 存入数据
		Editor editor = sp.edit();
		editor.putFloat(name, value);
		editor.commit();
	}

	/**
	 * 将boolean信息存入Preferences
	 * 
	 * @param context
	 * @param userName
	 */
	public static void setPreferences(Context context, String name, boolean value) {
		SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);

		// 存入数据
		Editor editor = sp.edit();
		editor.putBoolean(name, value);
		editor.commit();
	}

	public static void clearPreferences(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);

		Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();

		System.out.println("清除成功");
	}
}
