package com.codecool.guestBook.DAO;

import com.codecool.guestBook.Model.Entry;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EntryDAOInterface {
    void addEntry(String name, String meessage) throws SQLException;
    ArrayList<Entry> getEntries() throws SQLException;
}
