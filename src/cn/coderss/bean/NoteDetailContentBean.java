package cn.coderss.bean;

public class NoteDetailContentBean {
	public String addtime;
	public String content;
	public String id;
	public String picture;
	public String title;
	public String uid;
	public String username;
	public String vid;
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
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	@Override
	public String toString() {
		return "NoteDetailContentBean [addtime=" + addtime + ", content="
				+ content + ", id=" + id + ", picture=" + picture + ", title="
				+ title + ", uid=" + uid + ", username=" + username + ", vid="
				+ vid + "]";
	}
	
}
