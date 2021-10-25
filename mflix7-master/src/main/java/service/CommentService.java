package service;

import DAO.MongoDB.CommentDAO;
import com.mongodb.client.FindIterable;
import model.Comment;
import org.bson.Document;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/comment")
public class CommentService {

    public FindIterable<Comment> getComments(String by, Object value) {
        Document filter = new Document();
        Document sort = new Document("date", -1);
        filter.append(by, value);
        FindIterable<Comment> list = new CommentDAO().getComments(filter,sort);
        return list;
    }

    @POST
    @Path("/add")
    public String addComment(Comment comment) {
        new CommentDAO().addComment(comment);
        return "Done";
    }
}