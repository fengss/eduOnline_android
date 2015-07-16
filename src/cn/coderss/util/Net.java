package cn.coderss.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Net {

	/**
	 * Post传递
	 * @param url
	 * @param params
	 * @return
	 */
	public static String Post(String url,String params){
		String result="";
		try {
			URL Url=new URL(url);
			HttpURLConnection con=(HttpURLConnection) Url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.connect();
			PrintWriter pw=new PrintWriter(con.getOutputStream());
			pw.print(params);
			pw.flush();
			pw.close();
			int code=con.getResponseCode();
			if(code==200){
				InputStream in=con.getInputStream();
				result=getStringFromInPutStream(in);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * Get 传递
	 * @param URl
	 * @return
	 */
	public static String Get(String URl){
		String result="";
		try {
			URL url=new URL(URl);
			HttpURLConnection con=(HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod("GET");
			con.connect();
			InputStream is=con.getInputStream();
			result=getStringFromInPutStream(is);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 输入流转文字
	 * @param in
	 * @return
	 */
	public static String getStringFromInPutStream(InputStream in){
		byte[] b=new byte[999999];
		int len=0;
		String result="";
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		try {
			while((len=in.read(b))!=-1){
				out.write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result=new String(out.toString());
		return result;
	}
}
