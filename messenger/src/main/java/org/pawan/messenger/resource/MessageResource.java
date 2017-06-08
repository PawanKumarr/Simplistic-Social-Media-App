package org.pawan.messenger.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.pawan.messenger.model.Message;
import org.pawan.messenger.resource.bean.MessageFilterBean;
import org.pawan.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();

	@GET
	public List<Message> getMessagesJson(@BeanParam MessageFilterBean messageFilterBean){
			return messageService.getMessages(messageFilterBean);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Message> getMessagesXml(@BeanParam MessageFilterBean messageFilterBean){
			return messageService.getMessages(messageFilterBean);
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId){
		return messageService.getMessage(messageId);
	}
	
	@POST
	public Response createMessageJson(Message message, @Context UriInfo uriInfo) throws URISyntaxException{
		Message createdMessage = messageService.createMessage(message);
		//return Response.status(Status.CREATED).entity(createdMessage).header("Location-header", new URI("/messenger/webapi/messages/" + createdMessage.getId())).build();
		String newId = String.valueOf(createdMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(createdMessage).build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long messageId, Message message){
		return messageService.updateMessage(messageId, message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long messageId){
		messageService.deleteMessage(messageId);
	}
	
	@GET
	@Path("headerDemo")
	@Produces(MediaType.TEXT_PLAIN)
	public String headerDemo(@HeaderParam("authCode") String header){
		return "Header Param: " + header;
	}
	
	@GET
	@Path("contextDemo/{abc}")
	@Produces(MediaType.TEXT_PLAIN)
	public String contextDemo(@Context UriInfo uriInfo, @Context HttpHeaders headers){
		String absolutePath = uriInfo.getAbsolutePath().toString();
		String baseUri = uriInfo.getBaseUri().toString();
		String queryParameters = uriInfo.getQueryParameters().toString();
		String pathParameters = uriInfo.getPathParameters().toString();
		String requestHeaders = headers.getRequestHeaders().toString();
		/*return "Absolute Path: " + absolutePath
				+ "        Base URI: " + baseUri
				+ "        Query Parameters: " + queryParameters
				+ "        Path Parameters: " + pathParameters
				+ "        Request Headers: " + requestHeaders;*/
		return "Request Headers: " + requestHeaders;
	}
}
