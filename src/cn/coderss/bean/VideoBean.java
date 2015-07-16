package cn.coderss.bean;

public class VideoBean {
	public String addtime;
	public String clicknum;
	public String descr;
	public String dwloadnum;
	public String favonum;
	public String name;
	public String id;
	public String picname;
	public String size;
	public String status;
	public String tid;
	public String type;
	public String uid;
	public String videoname;
/*	{"id":"58",
 * "tid":"17",
 * "uid":"65",
 * "videoname":"\u9ebb\u8fa3\u9694\u58c1",
 * "name":"530e89cda8051.flv",
 * "picname":"530e89cda8051.jpg",
 * "descr":"\u9ebb\u8fa3\u9694\u58c1",
 * "size":"36293230",
 * "type":"flv",
 * "addtime":"1393461713",
 * "favonum":"0",
 * "clicknum":"5",
 * "dwloadnum":"0",
 * "status":"1"}
*/
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getClicknum() {
		return clicknum;
	}
	public void setClicknum(String clicknum) {
		this.clicknum = clicknum;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getDwloadnum() {
		return dwloadnum;
	}
	public void setDwloadnum(String dwloadnum) {
		this.dwloadnum = dwloadnum;
	}
	public String getFavonum() {
		return favonum;
	}
	public void setFavonum(String favonum) {
		this.favonum = favonum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPicname() {
		return picname;
	}
	public void setPicname(String picname) {
		this.picname = picname;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getVideoname() {
		return videoname;
	}
	public void setVideoname(String videoname) {
		this.videoname = videoname;
	}
	@Override
	public String toString() {
		return "VideoBean [addtime=" + addtime + ", clicknum=" + clicknum
				+ ", descr=" + descr + ", dwloadnum=" + dwloadnum
				+ ", favonum=" + favonum + ", name=" + name + ", id=" + id
				+ ", picname=" + picname + ", size=" + size + ", status="
				+ status + ", tid=" + tid + ", type=" + type + ", uid=" + uid
				+ ", videoname=" + videoname + "]";
	}
	
}
