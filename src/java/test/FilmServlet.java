/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author gabrielwong
 */
@WebServlet(urlPatterns = {"/films"})
public class FilmServlet extends HttpServlet {
    
    @Resource(lookup="jdbc/sakila")
    private DataSource sakilaDS;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        
        try (Connection conn = sakilaDS.getConnection()) {
            
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from film");
            
            while (rs.next()) {
                JsonObjectBuilder f = Json.createObjectBuilder();
                f.add("filmId", rs.getInt("film_id"))
                        .add("title", rs.getString("title"));
                arrBuilder.add(f);
            }
            
            JsonArray films = arrBuilder.build();
            
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().write(films.toString());
            
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log("Performing query", e);
            return;
        }
        
    }

}
