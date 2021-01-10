package testjesy;

import java.util.ArrayList;

public class Topic {
	public String topicname;
	public int topicid;
	public static ArrayList<Integer> user_id=new ArrayList<Integer>();
	
	public void setTopicName(String name) {
		this.topicname=name;
	
	}
	public void setTopicID(int id) {
		this.topicid=id;
	}
	public String getTopicName() {
		return topicname;
	}
	public int getTopicID() {
		return topicid;
	}
	public void SetSubscribedUser(int use_id) {
		user_id.add(use_id);
	}

}
