package DAO.MongoDB;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import model.Comment;
import org.bson.Document;

import java.util.Date;

public class CommentDAO extends AbsDAO {

    public FindIterable<Comment> getComments(Document filter, Document sort) {
        MongoCollection<Comment> comments = getDB().getCollection("comments", Comment.class);
        FindIterable<Comment> list = comments.find(filter).sort(sort);
        return list;
    }

    public void addComment(Comment comment) {
        MongoCollection<Comment> comments = getDB().getCollection("comments", Comment.class);
        comment.setDate(new Date());
        comments.insertOne(comment);
    }
}