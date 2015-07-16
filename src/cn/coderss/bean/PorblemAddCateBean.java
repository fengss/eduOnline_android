package cn.coderss.bean;

import java.util.ArrayList;

public class PorblemAddCateBean {
	public ArrayList<String> cust_ids;
	public ArrayList<String> cust_names;

	public ArrayList<String> getCust_ids() {
		return cust_ids;
	}

	public void setCust_ids(ArrayList<String> cust_ids) {
		this.cust_ids = cust_ids;
	}

	public ArrayList<String> getCust_names() {
		return cust_names;
	}

	public void setCust_names(ArrayList<String> cust_names) {
		this.cust_names = cust_names;
	}

	@Override
	public String toString() {
		return "PorblemAddCateBean [cust_ids=" + cust_ids + ", cust_names="
				+ cust_names + "]";
	}

}
