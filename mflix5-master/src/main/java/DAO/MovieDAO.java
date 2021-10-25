package DAO;

import com.mongodb.client.MongoCollection;
import model.Movie;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MovieDAO extends AbsDAO {

    public Movie getMovieByID(String id) {
        MongoCollection<Movie> movies = getDB().getCollection("movies", Movie.class);
        Movie movie = movies.find(eq("_id", new ObjectId(id))).first();
        return movie;
    }

    public List<Movie> searchMovies(Document filter, Document sort, int limit, int skip) {
        MongoCollection<Movie> movies = getDB().getCollection("movies", Movie.class);
        List<Movie> list = new ArrayList<>();
        movies.find(filter).sort(sort).limit(limit).skip(skip).forEach(d -> list.add(d));
        return list;
    }

    public long getMoviesNumber(Document filter) {
        MongoCollection<Document> movies = getDB().getCollection("movies");
        return movies.countDocuments(filter);

    }
}