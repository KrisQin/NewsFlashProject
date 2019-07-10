package com.blockadm.common.bean;

/**
 *
* @ClassName: MobileInfo
* @Description: TODO(手机信息实体类)
* @author 唐宏宇
* @date 2015年4月2日 下午7:16:45
*
 */
public class MobileInfo {


	private String mobileImei;
	private String mobileImsi;
	private String mobileModel;
	private String mobileBrand;
	private String mobileNum1;

	public String getMobileModel() {
		return mobileModel;
	}
	public void setMobileModel(String mobileModel) {
		this.mobileModel = mobileModel;
	}
	public String getMobileBrand() {
		return mobileBrand;
	}
	public void setMobileBrand(String mobileBrand) {
		this.mobileBrand = mobileBrand;
	}
	public String getMobileNum1() {
		return mobileNum1;
	}
	public void setMobileNum1(String mobileNum1) {
		this.mobileNum1 = mobileNum1;
	}

	public String getMobileImei() {
		return mobileImei;
	}
	public void setMobileImei(String mobileImei) {
		this.mobileImei = mobileImei;
	}
	public String getMobileImsi() {
		return mobileImsi;
	}
	public void setMobileImsi(String mobileImsi) {
		this.mobileImsi = mobileImsi;
	}


}
