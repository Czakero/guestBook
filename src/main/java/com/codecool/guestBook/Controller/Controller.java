package com.codecool.guestBook.Controller;


import com.codecool.guestBook.Model.DBConnection;

import java.sql.SQLException;
import java.util.Scanner;

public class Controller {
    private Scanner sc = new Scanner(System.in);
    private boolean isRunning = true;
    private static final String first_query = "SELECT addEntry('maciej', 'z klanu');";
    private static final String second_query = "SELECT * FROM getEntries();";

    private DBConnection c = new DBConnection();

    public void start() throws SQLException {
        while (isRunning) {
            int option = getIntInput();
            switch (option) {
                case 1:
                    try {
                        c.connect();
                        c.executeQuery(first_query);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        c.close();
                    }
                    break;
                case 2:
                    try {
                        c.connect();
                        c.executeQuery(second_query);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        c.close();
                    }
                    break;
                case 0:
                    isRunning = false;
                default:
                    System.out.println("There is no such option!");
            }

        }

    }
    private int getIntInput() {
        int number;
        do {
            System.out.print("Choose option: ");
            while (!sc.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.print("Choose option: ");
                sc.next();
            }
            number = sc.nextInt();
        } while (number <= 0);

        return number;
    }
}
