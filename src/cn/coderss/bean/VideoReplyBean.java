package cn.coderss.bean;

public class VideoReplyBean {
	public String id;
	public String uid;
	public String vid;
	public String content;
	public String addtime;
	public String status;
	public String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public VideoReplyBean(String id, String uid, String vid, String content,
			String addtime, String status) {
		super();
		this.id = id;
		this.uid = uid;
		this.vid = vid;
		this.content = content;
		this.addtime = addtime;
		this.status = status;
	}
	
}
