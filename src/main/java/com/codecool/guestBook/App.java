package com.codecool.guestBook;

import com.codecool.guestBook.Controller.Controller;

import java.sql.SQLException;

public class App 
{
    public static void main( String[] args ) {

        try {
            new Controller().start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
