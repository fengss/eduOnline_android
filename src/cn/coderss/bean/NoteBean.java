package cn.coderss.bean;

public class NoteBean {
	public String id;
	public String uid;
	public String title;
	public String vid;
	public String content;
	public String addtime;
	public String username;
	public String picture;
	public String videoname;
	public String picname;
	public String clicknum;
	public String collnum;
	public String commnum;
	public String zanNum;
	public String maxNum;
	public String isZan;
	public String isColl;
	
	

	public String getZanNum() {
		return zanNum;
	}

	public void setZanNum(String zanNum) {
		this.zanNum = zanNum;
	}

	public String getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}

	public String getIsZan() {
		return isZan;
	}

	public void setIsZan(String isZan) {
		this.isZan = isZan;
	}

	public String getIsColl() {
		return isColl;
	}

	public void setIsColl(String isColl) {
		this.isColl = isColl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getVideoname() {
		return videoname;
	}

	public void setVideoname(String videoname) {
		this.videoname = videoname;
	}

	public String getPicname() {
		return picname;
	}

	public void setPicname(String picname) {
		this.picname = picname;
	}

	public String getClicknum() {
		return clicknum;
	}

	public void setClicknum(String clicknum) {
		this.clicknum = clicknum;
	}

	public String getCollnum() {
		return collnum;
	}

	public void setCollnum(String collnum) {
		this.collnum = collnum;
	}

	public String getCommnum() {
		return commnum;
	}

	public void setCommnum(String commnum) {
		this.commnum = commnum;
	}

	@Override
	public String toString() {
		return "NoteBean [id=" + id + ", uid=" + uid + ", title=" + title
				+ ", vid=" + vid + ", content=" + content + ", addtime="
				+ addtime + ", username=" + username + ", picture=" + picture
				+ ", videoname=" + videoname + ", picname=" + picname
				+ ", clicknum=" + clicknum + ", collnum=" + collnum
				+ ", commnum=" + commnum + "]";
	}

}
