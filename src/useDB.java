import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
//SIGN IN
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

    //GET USERID
    public int userid(String username, String password) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        int userId = -1; // مقدار پیش‌فرض در صورتی که هیچ usid یافت نشود

        try {
            // ایجاد اتصال به پایگاه داده
            connection = DriverManager.getConnection(connectionUrl);

            String sql = "SELECT usid FROM USERS WHERE username = ? AND upassword = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            rset = stmt.executeQuery();
            if (rset.next()) { // فقط اولین نتیجه را پردازش می‌کنیم
                userId = rset.getInt("usid");
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

        return userId;
    }
//DISPLAY MOVIES
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
    public void showHalls(int usid) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        try {
            // ایجاد اتصال به پایگاه داده
            connection = DriverManager.getConnection(connectionUrl);

            String sql = "SELECT HALL.hid, HALL.hname FROM USERS INNER JOIN CITY ON USERS.cid = CITY.cid INNER JOIN HALL ON CITY.cid = HALL.cid WHERE USERS.usid = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, usid);
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
    //GET TID
    public int gettid(int hid, int mid, String mdate, String mtime) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        int TId = -1; // مقدار پیش‌فرض در صورتی که هیچ usid یافت نشود

        try {
            // ایجاد اتصال به پایگاه داده
            connection = DriverManager.getConnection(connectionUrl);

            // استفاده از تبدیل نوع داده در SQL
            String sql = "SELECT numbertk FROM numberofticket WHERE hid = ? AND movieid = ? AND ndate = ? AND ntime = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, hid);
            stmt.setInt(2, mid);
            stmt.setString(3, mdate);
            stmt.setString(4, mtime);
            rset = stmt.executeQuery();
            if (rset.next()) { // فقط اولین نتیجه را پردازش می‌کنیم
                TId = rset.getInt("numbertk");
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

        return TId;
    }

    //register ticket
public void createticket(int tid,int usid,LocalDate mdate,LocalTime mtime,int hid,int mid,int fee,int seatid){
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rset = null;
    try {
        // ایجاد اتصال به پایگاه داده
        connection = DriverManager.getConnection(connectionUrl);

        String sql = "INSERT INTO TICKET (tid, usid, mdate, mtime, hid, mid, fee, seatid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        stmt = connection.prepareStatement(sql);
        stmt.setInt(1, tid);
        stmt.setInt(2, usid);
        stmt.setDate(3, java.sql.Date.valueOf(mdate));
        stmt.setTime(4, Time.valueOf(mtime));
        stmt.setInt(5, hid);
        stmt.setInt(6, mid);
        stmt.setInt(7, fee);
        stmt.setInt(8, seatid);

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("The ticket was registered successfully.");
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
//DISPLAY CHAIR
 public void showchairs(int hid) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        try {
            // ایجاد اتصال به پایگاه داده
            connection = DriverManager.getConnection(connectionUrl);

            String sql = "SELECT CHAIR.chid,CHAIR.crow,CHAIR.ccolumn FROM CHAIR WHERE CHAIR.hid = ? AND CHAIR.cstatus='T'";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, hid);
            System.out.println("chairid\trow\tcolumn\n" +
                    "---------------------------------------------");

            rset = stmt.executeQuery();
            while (rset.next()) {
                System.out.println( rset.getInt("chid") +
                        "\t " + rset.getInt("crow") +
                        "\t " + rset.getInt("ccolumn")
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
//    int z=db.userid("pooya","parva");
//    LocalTime time = LocalTime.of(14, 30, 0);
//    LocalDate date = LocalDate.of(2024, 3, 3);
//        db.createticket(1,z,date,time,1000,102,85000,2);
//
//LocalTime time = LocalTime.of(14, 00, 00);
//    String t=convertTime(time);
//    LocalDate date = LocalDate.of(2024, 06, 22);
//    String s=convert(date);
//    int z= db.gettid(1000,102,s,t);
//   System.out.println(z);
}
