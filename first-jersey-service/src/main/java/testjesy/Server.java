package testjesy;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import org.apache.catalina.tribes.util.Arrays;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class Server {
	private static Gson json = new Gson();
	private static Map<Integer, User> users = new HashMap<Integer, User>();
	private static Map<Integer, Message> messages = new HashMap<Integer, Message>();
	private static Map<Integer, Topic> topics = new HashMap<Integer, Topic>();
	private static Map<Integer, ArrayList<User>> subscribers = new HashMap<Integer, ArrayList<User>>();

	@POST

	@Path("/add")
	public String storeMessage(String s) {
		Message m = json.fromJson(s, Message.class);
		Responsed response = new Responsed();
		if (messages.get(m.getMID()) != null) {
			response.setStatus(false);
			response.setMessage("Message Already Exists");
			return json.toJson(response);
		}
		messages.put(m.getMID(), m);
		response.setStatus(true);
		response.setMessage("Message created successfully");
		return json.toJson(response);
	}
	
@POST
	@Path("/adduser")
	public String adduser(String s) {
		User m = json.fromJson(s, User.class);
		Responsed response = new Responsed();
		if (users.get(m.getId()) != null) {
			response.setStatus(false);
			response.setMessage("User Already Exists");
			return json.toJson(response);
		}
		users.put(m.getId(), m);
		response.setStatus(true);
		response.setMessage("User created successfully");
		return json.toJson(response);
	}

	@GET
	@Path("/msglist")
	public String ListMessage(String s) {
		Topic t = json.fromJson(s, Topic.class);
		int id = t.getTopicID();
		ArrayList<Message> msgg = new ArrayList<Message>();
		for (java.util.Map.Entry<Integer, Message> entry : messages.entrySet()) {
			if (entry.getValue().getTo_id() == id) {
				msgg.add(entry.getValue());
			}
		}
		Message[] msg = new Message[msgg.size()];

		for (int i = 0; i < msgg.size(); i++) {
			msg[i] = msgg.get(i);
		}
		return json.toJson(msg);

	}

	@GET
	@Path("/{id}/get")
	public String getMessage(@PathParam("id") int id) {

		return json.toJson(messages.get(id));
	}
	@GET
	@Path("/{id}/getperson")
	public String getPerson(@PathParam("id") int id) {

		return json.toJson(users.get(id));
	}

	@POST
	@Path("/addtopic")
	public String storeTopics(String s) {
		Topic t = json.fromJson(s, Topic.class);
		Responsed response = new Responsed();
		if (topics.get(t.getTopicID()) != null) {
			response.setStatus(false);
			response.setMessage("Topic Already Exists");
			return json.toJson(response);
		}
		ArrayList<User> use = new ArrayList<User>();
		topics.put(t.getTopicID(), t);
		subscribers.put(t.getTopicID(), use);
		response.setStatus(true);
		response.setMessage("Topic created successfully");
		return json.toJson(response);
	}

	@GET
	@Path("/listTopics")

	public String getAllTopics() {
		Set<Integer> ids = topics.keySet();
		Topic[] t = new Topic[ids.size()];
		int i = 0;
		for (Integer id : ids) {
			t[i] = topics.get(id);
			i++;
		}
		return json.toJson(t);
	}
	
	
	@POST
	@Path("/subscribe/{topic_id}")
	public String Subscribe(@PathParam("topic_id") int topic_id, String s) {

		// Topic t=json.fromJson(s, Topic.class);
		User u = json.fromJson(s, User.class);
		Responsed response = new Responsed();
		User user=users.get(u.id);
		if (topics.get(topic_id) != null && users.get(u.getId()) != null) {
			
			if (user.topic_id.contains(topic_id)) {
				response.setStatus(false);
				response.setMessage("user is already subscrib");
				return json.toJson(response);
			} else {

				for (java.util.Map.Entry<Integer, ArrayList<User>> entry : subscribers.entrySet()) {
					if (entry.getKey() == topic_id) {
						entry.getValue().add(user);
						user.topic_id.add(topic_id);

					}
					
				}

				response.setStatus(true);
				response.setMessage("user is subscribed successfully");
				
				return json.toJson(response);
			}

		} else
			response.setMessage("user can not subscrib");
		return json.toJson(response);
	}
	
	
	@POST
	@Path("/unsubscribe/{topic_id}")
	public String unSubscribe(@PathParam("topic_id") int topic_id, String s) {

		// Topic t=json.fromJson(s, Topic.class);
		User u = json.fromJson(s, User.class);
		Responsed response = new Responsed();
		User user=users.get(u.id);
		if (topics.get(topic_id) != null && users.get(u.getId()) != null) {
			
			if (!(user.topic_id.contains(topic_id))) {
				response.setStatus(false);
				response.setMessage("user is already unsubscrib");
				return json.toJson(response);
			} else {

				for (java.util.Map.Entry<Integer, ArrayList<User>> entry : subscribers.entrySet()) {
					if (entry.getKey() == topic_id) {
						entry.getValue().remove(user);
						user.delete(topic_id);

					}
					
				}

				response.setStatus(true);
				response.setMessage("user is unsubscribed successfully");
				
				return json.toJson(response);
			}

		} else
			response.setMessage("user can not unsubscrib");
		return json.toJson(response);
	}
	
	

	@GET
	@Path("/{topic_id}/number")
	public String getSubscribers(@PathParam("topic_id") int topic_id) {
		int total = 0;
		
		for (java.util.Map.Entry<Integer, ArrayList<User>> entry : subscribers.entrySet()) {
			if (entry.getKey() == topic_id) {

				total = entry.getValue().size();
				

			}
		}
		return json.toJson(total);
	}
	
	@GET
	@Path("/sublist")
	public String Listsubscribers(String s) {
		Topic t = json.fromJson(s, Topic.class);
		int id = t.getTopicID();
		ArrayList<User> msgg = new ArrayList<User>();
		for (java.util.Map.Entry<Integer, ArrayList<User>> entry : subscribers.entrySet()) {
			if (entry.getKey() == id) {
				msgg.addAll(entry.getValue());
			}
		}
		User[] msg = new User[msgg.size()];

		for (int i = 0; i < msgg.size(); i++) {
			msg[i] = msgg.get(i);
		}
		return json.toJson(msg);

	}

}
