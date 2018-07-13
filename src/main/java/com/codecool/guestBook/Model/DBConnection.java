package com.codecool.guestBook.Model;

import java.sql.*;

public final class DBConnection implements DBConnectionInterface {

    private static Connection db = null;

    @Override
    public Connection connect() throws SQLException {
        if (db == null) {
            db = DriverManager.getConnection(url, login, password);
        } else if (db.isClosed()) {
            db = DriverManager.getConnection(url, login, password);
        }
        return db;
    }

    @Override
    public void close() throws SQLException {
        if (db != null && !db.isClosed()) {
            db.close();
        }
    }
}