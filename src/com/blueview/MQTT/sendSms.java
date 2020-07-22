package com.blueview.MQTT;//接口类型：互亿无线触发短信接口，支持发送验证码短信、订单通知短信等。
// 账户注册：请通过该地址开通账户http://sms.ihuyi.com/register.html
// 注意事项：
//（1）调试期间，请用默认的模板进行测试，默认模板详见接口文档；
//（2）请使用APIID（查看APIID请登录用户中心->验证码短信->产品总览->APIID）及 APIkey来调用接口；
//（3）该代码仅供接入互亿无线短信接口参考使用，客户可根据实际需要自行编写；

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class sendSms {

	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	private static String str;

	/**
	 *
	 * @param User 用户名`
	 * @param adder	地址
	 * @param Phone	手机号
	 * @param tilt	倾斜角度
	 */
	public static void	tiltAlarm(String User,String adder,String Phone,String tilt) throws IOException {
		String ttltMsg = "您好,尊敬的"+User+",你管理的倾斜传感器:"+adder+"发生倾斜,倾斜角度为:"+tilt+"请检查处理!";
		messageSMS(ttltMsg,Phone);
	}




	/**
	 *
	 * @param User		用户名
	 * @param adder		安装地址
	 * @param Phone		手机号
	 * @param t		温度
	 */
	public static void tempAlarm(String User,String adder,String Phone,String t) throws IOException {
		String tipGateway = new String("您好,尊敬的" + User + ",你管理的温度传感器:" + adder + "温度过高,当前温度:"+t+"°,请检查处理!");
		messageSMS(tipGateway,Phone);
	}

	/**
	 * 烟雾传感器报警
	 * @param User 用户名
	 * @param Phone	手机号
	 * @param adder	安装地址
	 */
	public static void  smockAlarm(String User,String Phone,String adder) throws IOException {
		String tipGateway = new String("您好,尊敬的" + User + ",你管理的烟雾传感器:" + adder + "烟雾报警,请检查处理!");
		messageSMS(tipGateway,Phone);
	}


	public static void dianLiu(String user, String Phone, String adder,int stat) throws IOException {
		if (stat == 1){
			str = "电流降低故障";
			String tipGateway = new String("您好,尊敬的" + user + ",你管理的节点:" + adder + "发生"+str+",请检查处理!");
			messageSMS(tipGateway, Phone);
		}else if (stat == 2){
			str = "电流增加故障";
			String tipGateway = new String("您好,尊敬的" + user + ",你管理的节点:" + adder + "发生"+str+",请检查处理!");
			messageSMS(tipGateway, Phone);
		}
	}

	public static void gatewaySMS(String user, String Phone, String adder,Double stat) throws IOException {
		String tipGateway = new String("您好,尊敬的" + user + ",你管理的节点:" + adder + "发生短路故障,当前电压为:"+stat+"V,已经自动切断输出,请检查处理!");
		messageSMS(tipGateway, Phone);
	}

	/**
	 * 短信提示/报警
	 *
	 * @param contents 文本信息
	 */
	private static void messageSMS(String contents, String Phone) throws IOException {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);

		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");

//		String content = new String("您好,尊敬的【变量】,你管理的【变量】已经离线,请技术检查处理");
		String content = contents;

		NameValuePair[] data = {//提交短信
				new NameValuePair("account", "C05164186"),
				new NameValuePair("password", "573c19135bd2e24bb975c2188edcf1c5"),
				new NameValuePair("mobile", Phone),
//				new NameValuePair("mobile", "18543129521"),
				new NameValuePair("content", content),
		};
		method.setRequestBody(data);

		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();

			//System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			String code = root.elementText("code");
//			String msg = root.elementText("msg");
//			String smsid = root.elementText("smsid");
//
//			System.out.println(code);
//			System.out.println(msg);
//			System.out.println(smsid);

			if ("2".equals(code)) {
				System.out.println("短信提交成功");
			}

		} catch (HttpException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Release connection
			method.releaseConnection();
			//client.getConnectionManager().shutdown();
		}
	}
}