package cn.coderss.bean;

import java.util.ArrayList;

public class NoteDetailBean {
	public String collect;
	public ArrayList<NoteReplyBean> comment;
	public NoteDetailContentBean note;
	public String zannum;
	public String getCollect() {
		return collect;
	}
	public void setCollect(String collect) {
		this.collect = collect;
	}
	public ArrayList<NoteReplyBean> getComment() {
		return comment;
	}
	public void setComment(ArrayList<NoteReplyBean> comment) {
		this.comment = comment;
	}
	public NoteDetailContentBean getNote() {
		return note;
	}
	public void setNote(NoteDetailContentBean note) {
		this.note = note;
	}
	public String getZannum() {
		return zannum;
	}
	public void setZannum(String zannum) {
		this.zannum = zannum;
	}
	
	
}
