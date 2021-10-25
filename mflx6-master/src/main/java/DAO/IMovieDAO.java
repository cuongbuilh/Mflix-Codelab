package DAO;

import model.Movie;

import java.util.List;
import java.util.Map;

public interface IMovieDAO<T extends Map> {
    Movie getMovieByID(String id);

    List<Movie> searchMovies(T filter, T sort, int limit, int skip);

    long getMoviesNumber(T filter);

}
