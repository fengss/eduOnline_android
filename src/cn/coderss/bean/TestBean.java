package cn.coderss.bean;

public class TestBean {
	public String id;
	public String tid;
	public String title;
	public String addtime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	@Override
	public String toString() {
		return "TestBean [id=" + id + ", tid=" + tid + ", title=" + title
				+ ", addtime=" + addtime + "]";
	}
	
}
