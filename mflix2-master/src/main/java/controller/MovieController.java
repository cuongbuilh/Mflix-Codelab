package controller;

import model.Movie;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import service.MovieService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MovieController implements IController {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, ITemplateEngine templateEngine) throws Exception {
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String id = request.getParameter("id");
        Movie movie = new MovieService().getMovieByID(id);
        ctx.setVariable("movie", movie);
        List<String> genres = new MovieService().getGenresforHeader();
        ctx.setVariable("genres", genres);
        templateEngine.process("movie", ctx, response.getWriter());
    }
}
