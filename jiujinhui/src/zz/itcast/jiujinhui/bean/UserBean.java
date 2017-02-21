package zz.itcast.jiujinhui.bean;

import java.io.Serializable;

public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String nickName;// 微信昵称
	private  String openid;
	private  String unionid;
	private  String headimgurl;// 微信头像
   
	public UserBean(String nickName, String openid, String unionid,
			String headimgurl) {
		super();
		this.nickName = nickName;
		this.openid = openid;
		this.unionid = unionid;
		this.headimgurl = headimgurl;
	}

	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	@Override
	public String toString() {
		return "PersonInfoBean [nickName=" + nickName + ", openid=" + openid
				+ ", unionid=" + unionid + ", headimgurl=" + headimgurl + "]";
	}

}
