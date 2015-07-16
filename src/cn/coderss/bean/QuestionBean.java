package cn.coderss.bean;

public class QuestionBean {
	public String id;
	public String tid;
	public String content;
	public String aA;
	public String aB;
	public String aC;
	public String aD;
	public String answer;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getaA() {
		return aA;
	}
	public void setaA(String aA) {
		this.aA = aA;
	}
	public String getaB() {
		return aB;
	}
	public void setaB(String aB) {
		this.aB = aB;
	}
	public String getaC() {
		return aC;
	}
	public void setaC(String aC) {
		this.aC = aC;
	}
	public String getaD() {
		return aD;
	}
	public void setaD(String aD) {
		this.aD = aD;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	@Override
	public String toString() {
		return "QuestionBean [id=" + id + ", tid=" + tid + ", content="
				+ content + ", aA=" + aA + ", aB=" + aB + ", aC=" + aC
				+ ", aD=" + aD + ", answer=" + answer + ", addtime=" + addtime
				+ "]";
	}
	
}
