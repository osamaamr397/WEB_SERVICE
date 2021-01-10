package testjesy;

import java.util.ArrayList;

public class User {
public String name;
public int id;
public ArrayList<Integer> topic_id=new ArrayList<Integer>();

public String getName	() {
	return name;
}

public void setName(String name) {
	this.name = name;
}
public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}
public void Subscribe(int top_id) {
	topic_id.add(top_id);
	
}
public void delete(int index) {
	int deleted=0;
	
	if(topic_id.contains(index)) {
		deleted = topic_id.indexOf(index);
	}
	topic_id.remove(deleted);
}


}
