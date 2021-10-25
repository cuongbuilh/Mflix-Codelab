package DAO;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import model.Movie;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MovieDAO extends AbsDAO {
    public List<Movie> getMovies(int limit) {
        MongoCollection<Document> movies = getDB().getCollection("movies");
        List<Movie> list = new ArrayList<>();
        movies.find().limit(limit).forEach(d -> list.add(docToMovie(d)));
        return list;
    }


    public Movie docToMovie(Bson bson) {
        Movie movie = new Movie();
        Document document = (Document) bson;
        movie.set_id(document.getObjectId("_id").toHexString());
        movie.setTitle(MessageFormat.format("{0}", document.get("title")));
        movie.setCast((List<String>) document.get("cast"));
        movie.setPlog(document.getString("plot"));
        movie.setFullPlot(document.getString("fullplot"));
        movie.setType(document.getString("type"));
        movie.setDirectors((List<String>) document.get("directors"));
        movie.setWriters((List<String>) document.get("writers"));
        movie.setCountries((List<String>) document.get("countries"));
        movie.setGenres((List<String>) document.get("genres"));
        movie.setPoster(document.getString("poster"));
        movie.setYear(document.getInteger("year"));
        movie.setRuntime(document.getInteger("runtime"));

        return movie;
    }

    public Movie getMovieByID(String id) {
        MongoCollection<Document> movies = getDB().getCollection("movies");
        Document movie = movies.find(eq("_id", new ObjectId(id))).first();
        return docToMovie(movie);
    }

    public DistinctIterable<String> getGenres() {
        MongoCollection<Document> movies = getDB().getCollection("movies");
        DistinctIterable<String> genres = movies.distinct("genres", String.class);
        return genres;
    }

    public AggregateIterable<Document> getTopGenres(int limit) {
        MongoCollection<Document> movies = getDB().getCollection("movies");
        AggregateIterable<Document> result = movies.aggregate(Arrays.asList(new Document("$unwind", "$genres"),
                new Document("$group", new Document("_id", "$genres").append("numOfMovies",new Document("$sum", 1L))),
                new Document("$sort", new Document("numOfMovies", -1L)),
                new Document("$limit",limit)));
        return result;
    }
}