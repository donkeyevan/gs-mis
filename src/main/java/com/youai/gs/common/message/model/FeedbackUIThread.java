package com.youai.gs.common.message.model;

import java.util.List;

public class FeedbackUIThread {
	private FeedbackThread thread;

	private FeedbackMessage origMsg;
	private List<FeedbackMessage> messages;
	public FeedbackMessage getOrigMsg() {
		return origMsg;
	}
	public void setOrigMsg(FeedbackMessage origMsg) {
		this.origMsg = origMsg;
	}
	public List<FeedbackMessage> getMessages() {
		return messages;
	}
	public void setMessages(List<FeedbackMessage> messages) {
		this.messages = messages;
	}
	public FeedbackThread getThread() {
		return thread;
	}
	public void setThread(FeedbackThread thread) {
		this.thread = thread;
	}

}
