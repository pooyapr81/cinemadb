import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        String username = "";
        String password = "";
        final int fee = 80000;
        useDB db = new useDB();
        // ورود کاربر به اکانت خود یا ثبت نام و ساخت اکانت
        Scanner scanner = new Scanner(System.in);
        System.out.println(" WELCOME TO CINEMA TICKET !! ");
        System.out.println("please log in to your account or sign up \n 1.log in\n 2.sign up");
        int enter = scanner.nextInt();
        if (enter == 1) {   //ورود کاربر
            System.out.println("enter username :");
            username = scanner.next();
            System.out.println("enter password :");
            password = scanner.next();
            boolean ck = db.checkuser(username, password);
            if (ck) {
                System.out.println("wellcom!! ");
            } else {
                System.out.println("username or password was incorrect");
            }
        } else if (enter == 2) {   // ثبت نام کاربر
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


        System.out.println("BUY TICKET(yes or no)");
        String buy = scanner.next();
        if (buy.equals("yes")) {  //خرید بلیط یا نمایش و حذف آنها
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
            int movienum = scanner.nextInt();  // انتخاب فیلم
            db.showHalls(userid, movienum);
            System.out.println("choose the hall:");
            int hallnum = scanner.nextInt();   // انتخاب سالن نمایش
            System.out.println("choose date and time:");
            db.dateandtime(hallnum, movienum);
            int showtime = scanner.nextInt();

            //ثبت بلیط
            int numberofticket = 0;
            while (true) {
                db.showchairs(hallnum, showtime);
                System.out.println("choose the chair:");
                int chairid = scanner.nextInt();   //انتخاب شماره صندلی
                int tid = db.gettid(showtime) + 1;
                db.createticket(tid, userid, showtime, fee, chairid);
                numberofticket++;
                System.out.println("Do you want to choose another seat?(yes or no)");
                String chairch = scanner.next();
                if (chairch.equals("yes")) {
                    if (db.checkhall(hallnum, showtime)) {
                        continue;
                    } else {
                        System.out.println("no empty chair!!!");
                        break;
                    }
                } else {
                    break;
                }
            }
            System.out.println("total amount:" + numberofticket * fee);  // مجموع مبلغ

        } else { //نمایش بلیط های کاربر
            System.out.println("1.wallet\n2.my ticket");
            int wm = scanner.nextInt();
            if (wm == 1) {

            } else if (wm == 2) {
                db.showmyticket(userid);
                System.out.println("delete ticket?(yes or no)");
                String dt = scanner.next();

                if (dt.equals("yes")) {   //حذف بلیط
                    System.out.println("enter ticket number that you want to delete it:");
                    int deletet = scanner.nextInt();
                    db.deletetk(deletet, userid);
                }
            } else {
                System.out.println("good by!!");
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
