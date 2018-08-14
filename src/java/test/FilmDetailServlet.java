/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author gabrielwong
 */
@WebServlet(urlPatterns = {"/film/*"})
public class FilmDetailServlet extends HttpServlet {
    
    public static final String SQL = "select * from film where film_id = ?";
    
    @Resource(lookup="jdbc/sakila")
    private DataSource sakilaDS;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String fid = req.getPathInfo(); // /film/1 -> 1
        // no error checks
        Integer filmId = Integer.parseInt(fid.substring(1));
        
        JsonObjectBuilder objBuilder = null;
        
        try (Connection conn = sakilaDS.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, filmId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                objBuilder = Json.createObjectBuilder()
                    .add("filmId", rs.getInt("film_id"))
                    .add("title", rs.getString("title"))
                    .add("description", rs.getString("description"))
                    .add("releaseYear", rs.getString("release_year"))
                    .add("rating", rs.getString("rating"))
                    .add("specialFeatures", rs.getString("special_features"));
                        

            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.setContentType(MediaType.APPLICATION_JSON);
                objBuilder = Json.createObjectBuilder()
                        .add("message", filmId + "not found");
            }
            
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log("Performing query", e);
            return;
        }
        
        try (PrintWriter pw = resp.getWriter()) {
            pw.println(objBuilder.build().toString());
        }
        
    }
    
    
}
