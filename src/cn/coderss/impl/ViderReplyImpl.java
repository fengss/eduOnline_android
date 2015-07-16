package cn.coderss.impl;

public interface ViderReplyImpl {
	// 用户评论成功之后通知评论刷新
	public void updateReply(boolean refresh);
}
