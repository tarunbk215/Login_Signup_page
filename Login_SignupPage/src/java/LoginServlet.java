import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // data tagolak
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Database ka connect madak
        String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";  // SID = 'xe' (oracle dag)
        String dbUsername = "tarun";  //Oracle database username
        String dbPassword = "abc";  //Oracle database password

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // SQL Querry run madak!
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";//(Querry baribek)
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            rs = stmt.executeQuery();
            
            if (rs.next()) {
                response.sendRedirect("https://tarunbk21.github.io/imdb/");
            } else {
                response.sendRedirect("signup.html");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h1>Error occurred during login!</h1>");
            out.println("</body></html>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
