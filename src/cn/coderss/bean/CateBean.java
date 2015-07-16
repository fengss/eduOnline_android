package cn.coderss.bean;

public class CateBean {
	public String id;
	public String name;
	public String pid;
	public String path;
	public String status;
	public String picture;

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CateBean [id=" + id + ", name=" + name + ", pid=" + pid
				+ ", path=" + path + ", status=" + status + "]";
	}

	public CateBean(String id, String name, String pid, String path,
			String status) {
		super();
		this.id = id;
		this.name = name;
		this.pid = pid;
		this.path = path;
		this.status = status;
	}

}
