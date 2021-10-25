package DAO.MongoDB;

import DAO.IMovieDAO;
import com.mongodb.client.MongoCollection;
import model.Movie;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class MovieDAO extends AbsDAO implements IMovieDAO {

    public Movie getMovieByID(String id) {
        MongoCollection<Movie> movies = getDB().getCollection("movies", Movie.class);
        Movie movie = movies.find(eq("_id", new ObjectId(id))).first();
        return movie;
    }

    public List<Movie> searchMovies(Map filter, Map sort, int limit, int skip) {
        MongoCollection<Movie> movies = getDB().getCollection("movies", Movie.class);
        List<Movie> list = new ArrayList<>();
        movies.find(new Document(filter)).sort(new Document(sort)).limit(limit).skip(skip).forEach(d ->
                {
                    d.setMovieID(d.getId().toString());
                    list.add(d);
                }
        );
        return list;
    }

    public long getMoviesNumber(Map filter) {
        MongoCollection<Document> movies = getDB().getCollection("movies");
        return movies.countDocuments(new Document(filter));
    }
}