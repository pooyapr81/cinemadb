import java.sql.*;
public class useDB {

    // اطلاعات اتصال به دیتابیس
    String serverName = "DESKTOP-SCTUB29"; // نام سرور یا آدرس IP
    String databaseName = "cinemaDB"; // نام دیتابیس
    String connectionUrl = "jdbc:sqlserver://" + serverName + ";Database=" + databaseName + ";integratedSecurity=true;encrypt=false;";
//چک کردن اتصال
    public Connection connectToDatabase() {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            System.out.println("WELL DONE!!!");
            return connection;
        } catch (SQLException e) {
            System.out.println("DONT CONNECT!!!");
            e.printStackTrace();
        }
        return null;
    }
//چک کردن username و password کاربر
    public boolean checkuser(String username, String password) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        try {
            // ایجاد اتصال به پایگاه داده
            connection = DriverManager.getConnection(connectionUrl);

            // ایجاد استیتمنت و اجرای کوئری با استفاده از پارامترها
            String sql = "SELECT 1 FROM USERS WHERE username = ? AND upassword = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // اجرای کوئری
            rset = stmt.executeQuery();

            if (rset.next()) {
                return true; // کاربر پیدا شد
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // بستن منابع
            try {
                if (rset != null) rset.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false; // کاربر پیدا نشد
    }
//ثبت نام
    public void signin(String name, String number, int cid, String username, String password) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            // ایجاد اتصال به پایگاه داده
            connection = DriverManager.getConnection(connectionUrl);

            // ایجاد استیتمنت و اجرای کوئری با استفاده از پارامترها
            String sql = "INSERT INTO USERS (uname, number, cid, username, upassword) VALUES (?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, number);
            stmt.setInt(3, cid);
            stmt.setString(4, username);
            stmt.setString(5, password);

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // بستن منابع
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//نمایش فیلم ها
public void showmovie() {
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rset = null;
    try {
        // ایجاد اتصال به پایگاه داده
        connection = DriverManager.getConnection(connectionUrl);

        String sql = "SELECT MOVIE.mid,MOVIE.moname, MOVIE.director, MOVIE.genr, MOVIE.writer FROM MOVIE WHERE MOVIE.ps='T'";
        stmt = connection.prepareStatement(sql);

        System.out.println("number\tname\tdirector\tgenr\twriter\n" +
                "------------------------------------");

        rset = stmt.executeQuery();
        while (rset.next()) {
            System.out.println( rset.getInt("mid") +
                    "\t " + rset.getString("moname") +
                    "\t " + rset.getString("director") +
                    "\t " + rset.getString("genr") +
                    "\t " + rset.getString("writer")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // بستن استیتمنت و رزولت‌ست و اتصال
        try {
            if (rset != null) rset.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
//Display genres.
public void showgenreofmovies(){
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rset = null;
    try {
        // ایجاد اتصال به پایگاه داده
        connection = DriverManager.getConnection(connectionUrl);

        String sql = "SELECT DISTINCT MOVIE.genr FROM MOVIE";
        stmt = connection.prepareStatement(sql);

        System.out.println("genre\n" +
                "------------------------------------");

        rset = stmt.executeQuery();
        while (rset.next()) {
            System.out.println(rset.getString("genr")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // بستن استیتمنت و رزولت‌ست و اتصال
        try {
            if (rset != null) rset.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
//Display movies categorized by genre.
    public void showmoviesofgenre(String genre){
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        try {
            // ایجاد اتصال به پایگاه داده
            connection = DriverManager.getConnection(connectionUrl);

            String sql = "SELECT MOVIE.mid,MOVIE.moname, MOVIE.director, MOVIE.genr, MOVIE.writer FROM MOVIE WHERE MOVIE.genr=?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, genre);
            System.out.println("number\tname\tdirector\tgenre\twriter\n" +
                    "---------------------------------------------");

            rset = stmt.executeQuery();
            while (rset.next()) {
                System.out.println( rset.getInt("mid") +
                        "\t " + rset.getString("moname") +
                        "\t " + rset.getString("director") +
                        "\t " + rset.getString("genr") +
                        "\t " + rset.getString("writer")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // بستن استیتمنت و رزولت‌ست و اتصال
            try {
                if (rset != null) rset.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//   "Display all halls available in the user's city."
    public void showHalls(String username, String password) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        try {
            // ایجاد اتصال به پایگاه داده
            connection = DriverManager.getConnection(connectionUrl);

            String sql = "SELECT HALL.hid, HALL.hname FROM USERS INNER JOIN CITY ON USERS.cid = CITY.cid INNER JOIN HALL ON CITY.cid = HALL.cid WHERE USERS.username = ? AND USERS.upassword = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            System.out.println("hid\tname\n" +
                    "---------------------------------------------");

            rset = stmt.executeQuery();
            while (rset.next()) {
                System.out.println( rset.getInt("hid") +
                        "\t " + rset.getString("hname")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // بستن استیتمنت و رزولت‌ست و اتصال
            try {
                if (rset != null) rset.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
