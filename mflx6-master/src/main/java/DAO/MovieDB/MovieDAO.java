package DAO.MovieDB;

import DAO.IMovieDAO;
import DAO.MovieDB.json.DiscoveryMovie;
import DAO.MovieDB.json.ResultsItem;
import model.Movie;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieDAO extends AbsDAO implements IMovieDAO {
    @Override
    public Movie getMovieByID(String id) {
        WebTarget requestTarget = getApiTarget().path("/movie/" + id);
        ResultsItem resultsItem = requestTarget.request(MediaType.APPLICATION_JSON_TYPE).get(ResultsItem.class);
        return convertResulttoMovie(resultsItem);
    }

    @Override
    public List<Movie> searchMovies(Map filter, Map sort, int limit, int skip) {
        WebTarget requestTarget = getApiTarget().path("/discover/movie");
        DiscoveryMovie discovery_movie = requestTarget.request(MediaType.APPLICATION_JSON_TYPE).get(DiscoveryMovie.class);

        List<Movie> list = new ArrayList<>();
        for (ResultsItem resultsItem : discovery_movie.getResults()) {
            list.add(convertResulttoMovie(resultsItem));
        }
        return list;
    }

    @Override
    public long getMoviesNumber(Map filter) {
        return 0;
    }

    Movie convertResulttoMovie(ResultsItem resultsItem) {
        Movie movie = new Movie();
        movie.setTitle(resultsItem.getTitle());
        if (resultsItem.getReleaseDate() != null) {
            String year = resultsItem.getReleaseDate().split("-")[0];
            movie.setYear(Integer.parseInt(year));
            movie.setPoster("https://image.tmdb.org/t/p/w500" + resultsItem.getPosterPath());
        }
        if (resultsItem.getGenres() != null) {
            movie.setGenres(new ArrayList<>());
            resultsItem.getGenres().forEach(g -> movie.getGenres().add(g.getName()));
        }
        movie.setPlot(resultsItem.getOverview());
        movie.setFullplot(resultsItem.getOverview());
        movie.setMovieID(resultsItem.getId() + "");
        return movie;
    }

}
