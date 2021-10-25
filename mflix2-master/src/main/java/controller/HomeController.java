package controller;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import model.Movie;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import service.MovieService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeController implements IController {

    public void process(final HttpServletRequest request, final HttpServletResponse response, final ServletContext servletContext, final ITemplateEngine templateEngine) throws Exception {
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        List<Movie> list = new MovieService().getMoviesforHomePage();
        ctx.setVariable("list", list);
        List<String> genres = new MovieService().getGenresforHeader();
        ctx.setVariable("genres", genres);
        templateEngine.process("index", ctx, response.getWriter());
    }

}
