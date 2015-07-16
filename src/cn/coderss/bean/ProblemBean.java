package cn.coderss.bean;

public class ProblemBean {
	public String addtime;
	public String content;
	public String id;
	public String isbest;
	public String keyword;
	public String remind;
	public String replay;
	public String rtime;
	public String status;
	public String tid;
	public String uid;
	public String username;
	public String picture;
	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getIsbest() {
		return isbest;
	}

	public void setIsbest(String isbest) {
		this.isbest = isbest;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}

	public String getReplay() {
		return replay;
	}

	public void setReplay(String replay) {
		this.replay = replay;
	}

	public String getRtime() {
		return rtime;
	}

	public void setRtime(String rtime) {
		this.rtime = rtime;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "ProblemBean [addtime=" + addtime + ", content=" + content
				+ ", id=" + id + ", isbest=" + isbest + ", keyword=" + keyword
				+ ", remind=" + remind + ", replay=" + replay + ", rtime="
				+ rtime + ", status=" + status + ", tid=" + tid + ", uid="
				+ uid + "]";
	}

}
