package controller;

import model.Movie;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import service.MovieService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HomeController implements IController {

    public void process(final HttpServletRequest request, final HttpServletResponse response, final ServletContext servletContext, final ITemplateEngine templateEngine) throws Exception {
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        String by = null;
        String value = null;
        String text = null;
        String url = "/?";
        if (request.getParameter("by") != null) {
            by = request.getParameter("by").trim();
            url = url + "&by=" + by;
        }
        if (request.getParameter("value") != null) {
            value = request.getParameter("value").trim();
            url = url + "&value=" + value;
        }
        if (request.getParameter("text") != null) {
            text = request.getParameter("text").trim();
            url = url + "&text=" + text;
        }
        ctx.setVariable("url", url);

        long totalPages = new MovieService().getTotalPages(by, value, text);
        ctx.setVariable("totalPages", totalPages);
        int page = 1;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page").trim());
        ctx.setVariable("page", page);


        List<Movie> list = new MovieService().searchMovies(by, value, page, text);
        ctx.setVariable("list", list);
        templateEngine.process("index", ctx, response.getWriter());
    }


}
