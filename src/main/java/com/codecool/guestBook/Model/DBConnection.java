package com.codecool.guestBook.Model;

import java.sql.*;

public final class DBConnection implements DBConnectionInterface {

    private static Connection db = null;

    @Override
    public void connect() throws SQLException {
        if (db == null) {
            db = DriverManager.getConnection(url, login, password);
        } else if (db.isClosed()) {
            db = DriverManager.getConnection(url, login, password);
        }
    }

    @Override
    public void close() throws SQLException {
        if (db != null && !db.isClosed()) {
            db.close();
        }
    }

    @Override
    public ResultSet executeQuery(String query) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        return rs;
    }
}