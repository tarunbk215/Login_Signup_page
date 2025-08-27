import jakarta.servlet.*; 
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Data input from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Database connection details
        String dbURL = "jdbc:oracle:thin:@localhost:1521:xe"; // Your Oracle database URL
        String dbUsername = "tarun";  // Your Oracle database username
        String dbPassword = "abc";  // Your Oracle database password

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Load Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // SQL query to insert user data
            String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password); // Ideally, you should hash the password before saving it.
            stmt.setString(3, email);

            // Execute the query to insert the data
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                // If successful, redirect to the login page
                response.sendRedirect("index.html");
            } else {
                // If not successful, show error message
                out.println("<html><body>");
                out.println("<h1>Error: Unable to signup</h1>");
                out.println("</body></html>");
            }

        } catch (Exception e) {
            // Handle the error and print it to the response
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h1>Error occurred during signup!</h1>");
            out.println("</body></html>");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
