package cn.coderss.bean;

public class TeacherCateBean {
	public String id;
	public String name;

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

	@Override
	public String toString() {
		return "TeacherCateBean [id=" + id + ", name=" + name + "]";
	}

}
