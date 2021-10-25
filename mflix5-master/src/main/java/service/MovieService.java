package service;

import DAO.MovieDAO;
import model.Movie;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MovieService {


    public Movie getMovieByID(String id) {
        Movie movie = new MovieDAO().getMovieByID(id);
        return movie;
    }

    final static int NUM_OF_MOVIE_ON_PAGE = 12;

    public List<Movie> searchMovies(String by, String value, int page, String text) {
        Document filter = new Document();
        if (by == null & text == null) {
            filter.append("poster", new Document("$ne", null));  //No poster -> Dont't Appear on Home Page
            filter.append("plot", new Document("$ne", null)); //No Plot -> Dont't Appear on Home Page
        }
        Document sort = new Document();
        if (by != null && value != null)
            filter.append(by, value);
        if (text != null)
            filter.append("$text", new Document("$search", text));
        else
            sort.append("year", -1);

        List<Movie> list = new MovieDAO().searchMovies(filter, sort, NUM_OF_MOVIE_ON_PAGE, (page - 1) * NUM_OF_MOVIE_ON_PAGE);
        if (list == null) {
            list = new ArrayList<>();
            //add some sample movies;
        }
        return list;
    }

    public long getTotalPages(String by, String value, String text) {
        Document filter = new Document();
        if (by == null & text == null) {
            filter.append("poster", new Document("$ne", null));  //No poster -> Dont't Appear on Home Page
            filter.append("plot", new Document("$ne", null)); //No Plot -> Dont't Appear on Home Page
        }
        if (by != null && value != null)
            filter.append(by, value);
        if (text != null)
            filter.append("$text", new Document("$search", text));
        long totalMovies = new MovieDAO().getMoviesNumber(filter);
        return (long) Math.ceil((float) totalMovies / NUM_OF_MOVIE_ON_PAGE);
    }

}