package cn.coderss.bean;

public class NoteReplyBean {
	public String addtime;
	public String content;
	public String id;
	public String nid;
	public String picture;
	public String status;
	public String uid;
	public String username;
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "NoteReplyBean [addtime=" + addtime + ", content=" + content
				+ ", id=" + id + ", nid=" + nid + ", picture=" + picture
				+ ", status=" + status + ", uid=" + uid + ", username="
				+ username + "]";
	}
	
}
