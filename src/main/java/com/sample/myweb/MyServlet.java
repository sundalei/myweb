/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.myweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author sundalei
 */
public class MyServlet extends HttpServlet {
    
    @Resource(lookup="jdbc/courseManagementDataSource")
    private DataSource dataSource;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        try {
//            Context initCtx = new InitialContext();
//            DataSource ds = (DataSource) initCtx.lookup("jdbc/courseManagementDataSource");

            PrintWriter writer = response.getWriter();
            response.setContentType("text/html");
            writer.println(getInfo(dataSource));
//        } catch (NamingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    private String getInfo(DataSource ds) {
        Connection con = null;
        Statement stat = null;
        StringBuilder sb = new StringBuilder();

        try {
            con = ds.getConnection();
            stat = con.createStatement();
            String sql = "select * from Course";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                sb.append("id = ").append(rs.getInt("id")).append(", ")
                        .append("name = ").append(rs.getString("name")).append(", ")
                        .append("credits = ").append(rs.getString("credits")).append(", ")
                        .append("<br>");
            }
            return sb.toString();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
