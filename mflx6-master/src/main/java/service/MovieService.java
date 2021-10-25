package service;

import DAO.IMovieDAO;
import model.Movie;

import java.util.*;

public class MovieService {

    IMovieDAO movieDAO;

    public MovieService(IMovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public Movie getMovieByID(String id) {
        Movie movie = movieDAO.getMovieByID(id);
        return movie;
    }

    final static int NUM_OF_MOVIE_ON_PAGE = 12;

    public List<Movie> searchMovies(String by, String value, int page, String text) {
        Map filter = new HashMap();
        if (by == null & text == null) {
            filter.put("poster", Collections.singletonMap("$ne", null));  //No poster -> Dont't Appear on Home Page
            filter.put("plot", Collections.singletonMap("$ne", null)); //No Plot -> Dont't Appear on Home Page
        }
        Map sort = new HashMap();
        if (by != null && value != null)
            filter.put(by, value);
        if (text != null)
            filter.put("$text", Collections.singletonMap("$search", text));
        else
            sort.put("year", -1);

        List<Movie> list = movieDAO.searchMovies(filter, sort, NUM_OF_MOVIE_ON_PAGE, (page - 1) * NUM_OF_MOVIE_ON_PAGE);
        if (list == null) {
            list = new ArrayList<>();
            //add some sample movies;
        }
        return list;
    }

    public long getTotalPages(String by, String value, String text) {
        Map filter = new HashMap();
        if (by == null & text == null) {
            filter.put("poster", Collections.singletonMap("$ne", null));  //No poster -> Dont't Appear on Home Page
            filter.put("plot", Collections.singletonMap("$ne", null)); //No Plot -> Dont't Appear on Home Page
        }
        if (text != null)
            filter.put("$text", Collections.singletonMap("$search", text));
        long totalMovies = movieDAO.getMoviesNumber(filter);
        return (long) Math.ceil((float) totalMovies / NUM_OF_MOVIE_ON_PAGE);
    }
}