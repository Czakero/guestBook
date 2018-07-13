package com.codecool.guestBook.DAO;

import com.codecool.guestBook.Model.DBConnection;
import com.codecool.guestBook.Model.Entry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class EntryDAO implements EntryDAOInterface {
    private static final int NAME = 1;
    private static final int MESSAGE = 2;
    private static final int DATE = 3;
    private DBConnection db = new DBConnection();


    @Override
    public void addEntry(String name, String message) throws SQLException {

        PreparedStatement stmt = db.connect().prepareStatement("SELECT addEntry(?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, message);
        stmt.executeQuery();
        db.close();

    }

    @Override
    public ArrayList<Entry> getEntries() throws SQLException {


        PreparedStatement stmt = db.connect().prepareStatement("SELECT * FROM getEntries()");
        ResultSet rs = stmt.executeQuery();
        ArrayList<Entry> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new Entry(rs.getString(NAME), rs.getString(MESSAGE), rs.getString(DATE)));
        }
        db.close();

        return result;
    }
}
