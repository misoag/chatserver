package tv.baikan.net.message;


/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class ResultMessage {
	private Object body;
	private int messageId;
	private transient boolean html;

	public ResultMessage(int messageId,Object body) {
		this.messageId = messageId;
		this.body = body;
	}
	
	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public boolean isHtml() {
		return html;
	}
	
	public ResultMessage html() {
		this.html = true;
		return this;
	}
}
