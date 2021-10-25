package service;

import DAO.MovieDAO;
import com.mongodb.client.DistinctIterable;
import model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieService {

    public List<Movie> getMoviesforHomePage() {
        List<Movie> list = new MovieDAO().getMovies(6);
        if (list == null) {
            list = new ArrayList<>();
            //add some sample movies;
        }
        return list;
    }

    public Movie getMovieByID(String id) {
        Movie movie = new MovieDAO().getMovieByID(id);
        return movie;
    }

    public DistinctIterable<String> getGenres() {
        DistinctIterable<String> list = new MovieDAO().getGenres();
        return list;
    }

    public List<String> getGenresforHeader() {
        ArrayList<String> list = new ArrayList<>();
        new MovieDAO().getTopGenres(15).forEach(d -> list.add(d.getString("_id")));
        return list;
    }

}