package com.codecool.guestBook.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBConnectionInterface {
    String url = "jdbc:postgresql://localhost:5432/guest_book";
    String login = "czakero";
    String password = "misiejkoland18";

    Connection connect() throws SQLException;
    void close() throws SQLException;
}