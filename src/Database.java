import java.sql.*;

public class Database {
    private static Connection conn;

    //This method executes a query and returns the number of albums for the artist with name artistName
    public int getTitles(String artistName) {
        PreparedStatement getTitlesSqlQuery;
        int titleNum = 0;
        try {
            getTitlesSqlQuery = conn.prepareStatement("SELECT * FROM artist INNER JOIN album ON artist.artistid = album.artistid WHERE (((artist.name)=?));");
            getTitlesSqlQuery.setString(1, artistName);
            ResultSet rs = getTitlesSqlQuery.executeQuery();
            while (rs.next()) {
                titleNum++;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titleNum;
    }

    // This method establishes a DB connection & returns a boolean status
    public boolean establishDBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(Credentials.URL, Credentials.USERNAME, Credentials.PASSWORD);
            return conn.isValid(5);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}