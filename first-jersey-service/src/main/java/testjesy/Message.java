package testjesy;

public class Message {
	public int id;
	public String timestamp;
	public int sender_id;
	public int topic_id;
	public String content;
	
	public void getMID(int id) {
		this.id=id;
	}
	public void setTime(String time) {
		this.timestamp=time;
	}
	public void setS_id(int sid) {
		this.sender_id=sid;
	}
	public void setTo_id(int sid) {
		this.topic_id=sid;
	}
	public void setMsg(String msg) {
		this.content=msg;
	}
	
	public int getMID() {
		return id;
	}
	public String getTime() {
		return timestamp;
	}
	public int getS_id() {
		return sender_id;
	}
	public int getTo_id() {
		return topic_id;
	}
	public String setMsg() {
		return content;
	}
	

}
