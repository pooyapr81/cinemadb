import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        String username = "";
        String password = "";
        useDB db = new useDB();
        // ورود کاربر به اکانت خود یا ثبت نام و ساخت اکانت
        Scanner scanner = new Scanner(System.in);
        System.out.println(" WELCOME TO CINEMA TICKET !! ");
        System.out.println("please log in to your account or sign up \n 1.log in\n 2.sign up");
        int enter = scanner.nextInt();
        if (enter == 1) {
            System.out.println("enter username :");
            username = scanner.next();
            System.out.println("enter password :");
            password = scanner.next();
            boolean ck = db.checkuser(username, password);
            if (ck) {
                System.out.println("done");
            } else {
                System.out.println("not done");
            }
        } else if (enter == 2) {
            System.out.println("enter name :");
            String name = scanner.next();
            System.out.println("enter number :");
            String number = scanner.next();
            System.out.println("enter cid :");
            int cid = scanner.nextInt();
            System.out.println("enter username :");
            username = scanner.next();
            System.out.println("enter password :");
            password = scanner.next();
            db.signin(name, number, cid, username, password);
        }
        int userid = db.userid(username, password);

        // نمایش فیلم ها
        db.showmovie();
        while (true) {                                                           //نمایش فیلم ها بر اساس ژانر دلخواه
            System.out.println("do you search for specific genre :(yes or no)");
            String decision = scanner.next();
            if (decision.equals("yes")) {
                db.showgenreofmovies();
                System.out.println("if you want to see movies title based on genre write the genre :");
                String genre = scanner.next();
                db.showmoviesofgenre(genre);
            } else {
                break;
            }
        }

        System.out.println("please enter the number of movie:");           // انتخاب فیلم و نمایش سالن های سینما
        int movienum = scanner.nextInt();
        db.showHalls(userid, movienum);
        System.out.println("choose the hall:");
        int hallnum = scanner.nextInt();
        System.out.println("choose date and time:");
        db.dateandtime(hallnum,movienum);
        while (true) {
            db.showchairs(hallnum);
            System.out.println("choose the hall:");
            int chairid=scanner.nextInt();
            System.out.println("do you want to choose another chair?(yes or no)");
            String chairch=scanner.next();
            if(chairch.equals("yes")){
                continue;
            }else{
                break;
            }
        }

    }

    public static String convert(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return date.format(formatter);
    }

    public static String convertTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.format(formatter);
    }

}
