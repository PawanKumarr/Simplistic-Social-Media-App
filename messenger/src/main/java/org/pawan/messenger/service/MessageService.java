package org.pawan.messenger.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.pawan.messenger.exception.DataNotFoundException;
import org.pawan.messenger.model.ErrorMessage;
import org.pawan.messenger.model.Message;
import org.pawan.messenger.resource.bean.MessageFilterBean;

public class MessageService {
	
	static SessionFactory sessionFactory;
	
	static {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	public Message createMessage(Message message){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		message.setCreated(new Date());
		session.save(message);
		
		session.getTransaction().commit();
		session.close();
		
		return message;
	}

	public List<Message> getMessages() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Message");
		List<Message> results = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return results;
	}
	
	public List<Message> getMessages(MessageFilterBean messageFilterBean) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String author = messageFilterBean.getAuthor();
		int offset = messageFilterBean.getOffset();
		int size = messageFilterBean.getSize();
		
		Query query = null;
		
		if(author == null){
			query = session.createQuery("from Message");
		}else{
			query = session.createQuery("from Message where author = :author");
			query.setString("author", author);
		}
		if(offset!=0){
			query.setFirstResult(offset);
		}
		if(size!=0){
			query.setMaxResults(size);
		}
		
		List<Message> results = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return results;
	}

	public List<Message> getMessages(String author, int offset, int size) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query;
		
		if(author == null){
			query = session.createQuery("from Message");
		}else{
			query = session.createQuery("from Message where author = :author");
			query.setString("author", author);
		}
		if(offset!=0){
			query.setFirstResult(offset);
		}
		if(size!=0){
			query.setMaxResults(size);
		}
		
		List<Message> results = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return results;
	}
	
	public Message getMessage(long messageId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Message result = session.get(Message.class, messageId);
		
		session.getTransaction().commit();
		session.close();
		
		if(result == null){
			throw new DataNotFoundException("There is no message with id " + messageId);
			/*ErrorMessage errorMessage = new ErrorMessage("There is no message with id " + messageId, 123, "http://localhost:8082/documentation");
			Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
			throw new WebApplicationException(response);*/
		}
		return result;
	}

	public Message updateMessage(long messageId, Message message) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		message.setId(messageId);
		session.update(message);
		
		session.getTransaction().commit();
		session.close();
		
		return message;
	}

	public void deleteMessage(long messageId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Message message = session.get(Message.class, messageId);
		session.delete(message);
		
		session.getTransaction().commit();
		session.close();
	}
}
