package zz.itcast.jiujinhui.bean;

import java.io.Serializable;

public class Income implements Serializable {
    public String danhao;
    public String jiubi;
    public String date;
    public String msg;
	public Income(String danhao, String jiubi, String date, String msg) {
		super();
		this.danhao = danhao;
		this.jiubi = jiubi;
		this.date = date;
		this.msg = msg;
	}
	public Income() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
