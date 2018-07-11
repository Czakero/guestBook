package com.codecool.guestBook.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBConnectionInterface {
    String url = "jdbc:postgresql://localhost:5432/guest_book";
    String login = "czakero";
    String password = "misiejkoland18";

    void connect() throws SQLException;
    void close() throws SQLException;
    ResultSet executeQuery(String query) throws SQLException;
}