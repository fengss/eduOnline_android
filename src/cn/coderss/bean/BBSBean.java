package cn.coderss.bean;

public class BBSBean {
	public String id;
	public String title;
	public String uid;
	public String addtime;
	public String content;
	public String keyword;
	public String isbest;
	public String ishot;
	public String scan;
	public String status;
	public String username;
	public String name;
	public String picture;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getIsbest() {
		return isbest;
	}
	public void setIsbest(String isbest) {
		this.isbest = isbest;
	}
	public String getIshot() {
		return ishot;
	}
	public void setIshot(String ishot) {
		this.ishot = ishot;
	}
	public String getScan() {
		return scan;
	}
	public void setScan(String scan) {
		this.scan = scan;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	@Override
	public String toString() {
		return "BBSBean [id=" + id + ", title=" + title + ", uid=" + uid
				+ ", addtime=" + addtime + ", content=" + content
				+ ", keyword=" + keyword + ", isbest=" + isbest + ", ishot="
				+ ishot + ", scan=" + scan + ", status=" + status
				+ ", username=" + username + ", name=" + name + ", picture="
				+ picture + "]";
	}
	
}
