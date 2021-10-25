package service;

import DAO.IMovieDAO;
import DAO.MongoDB.MovieDAO;
import model.Movie;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/movie")
public class MovieService {

    IMovieDAO movieDAO;
    public MovieService(){
        this.movieDAO = new MovieDAO();
    }
    public MovieService(IMovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Movie getMovieByID(@QueryParam("id") String id) {
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