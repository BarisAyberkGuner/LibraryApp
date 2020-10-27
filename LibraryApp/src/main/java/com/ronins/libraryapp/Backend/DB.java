package com.ronins.libraryapp.Backend;

import java.sql.*;


public class DB {
    static String tid = "";

    public static void addBook(String ISBN, String isim) {
        String dbPath = "jdbc:sqlite:DBFile\\LibraryDB.db";
        Connection conn = null;
        try {
            // Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbPath);
            System.out.println("sqlite connected");
            String sql = "INSERT INTO kitap (isbn,isim) values (?,?)";
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, ISBN);
                pstmt.setString(2, isim);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void listUser() {
        String dbPath = "jdbc:sqlite:DBFile\\LibraryDB.db";
        Connection conn = null;
        try {
            // Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbPath);
            System.out.println("sqlite connected");

            String sql = "SELECT * FROM kullanici";
            System.out.println("id      sifre");
            try (
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    System.out.println(rs.getString("id") + "   " + rs.getString("sifre"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setID(String s) {
        tid = s;

    }

    public static String getID() {
        return tid;
    }

    public static boolean checkBook(String logonID) {
        boolean result = true;
        int i = 0;
        String dbPath = "jdbc:sqlite:DBFile\\LibraryDB.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbPath);
            System.out.println("sqlite connected");
            String sql = "SELECT * FROM sahiplik";
            try (
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    if (logonID.equals(rs.getString("id"))) {
                        i++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (i >= 3) {
            result = false;
        }
        return result;
    }

    public static boolean checkBook2(String logonID) {
        boolean result = true;
        int i = 0;
        String dbPath = "jdbc:sqlite:DBFile\\LibraryDB.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbPath);
            System.out.println("sqlite connected");
            String sql = "SELECT * FROM sahiplik";
            try (
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    if (logonID.equals(rs.getString("id")) && rs.getInt("kalangun") < 1) {
                        result = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}