package ru.restaurant.voting.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.slf4j.LoggerFactory.getLogger;

public class VotingServlet extends HttpServlet {
    private static final Logger log = getLogger(VotingServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to voting");

        //request.getRequestDispatcher("/voting.jsp").forward(request, response);
        response.sendRedirect("voting.jsp");
    }
}
