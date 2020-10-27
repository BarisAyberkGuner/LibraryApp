package com.ronins.libraryapp.UI;

import com.ronins.libraryapp.Backend.DB;
import com.ronins.libraryapp.Backend.OCR;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.ronins.libraryapp.UI.KullaniciGiris;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Title("Kullanici Panel")
@SpringUI(path = "/kullanicipanel")
@Theme("apptheme")
@UIScope
public class KullaniciPanel extends UI {

    Logger log = Logger.getLogger(KullaniciPanel.class.getName());
    VerticalLayout root;
    String text = "";
    static int check = 0;

    String LogonID = DB.getID();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setContent(root);
        Image a = new Image();


        TextArea isim = new TextArea("Kitap İsmi");
        isim.setWidth("25%");
        isim.setHeight(45, Unit.PIXELS);
        TextArea ISBN = new TextArea("ISBN");
        ISBN.setWidth("25%");
        ISBN.setHeight(45, Unit.PIXELS);
        Button btnSearch = new Button("Kitap Ara");
        TextArea alISBN = new TextArea("ISBN");
        alISBN.setWidth("25%");
        alISBN.setHeight(45, Unit.PIXELS);
        Button btnAl = new Button("Kitap Al");
        Button btnYukle = new Button("ISBN Yükle");
        TextArea ISBNUrl = new TextArea("ISBN URL");
        ISBNUrl.setWidth("25%");
        ISBNUrl.setHeight(45, Unit.PIXELS);
        TextArea ISBNver = new TextArea("Teslim Edilecek Kitabın ISBN'i");
        ISBNver.setWidth("25%");
        ISBNver.setHeight(45, Unit.PIXELS);
        Button btnVer = new Button("Kitabı İade Et");
        Label kitapİadeEdin = new Label("3 Adetten Fazla Kitap veya Süresi Dolmuş Kitaplarınız Bulunmaktadır \n Lütfen Kitap İade Edin");

        root.addComponents(isim, ISBN, btnSearch, alISBN, btnAl, ISBNUrl, btnYukle);
        btnAl.addClickListener(clickEvent -> {

            String alinacakISBN = alISBN.getValue();
            log.info("alinan isbn: " + alinacakISBN);

            String dbPath = "jdbc:sqlite:DBFile\\LibraryDB.db";
            Connection conn = null;
            if (DB.checkBook(LogonID) && DB.checkBook2(LogonID)) {
                try {
                    conn = DriverManager.getConnection(dbPath);
                    log.info("Sqlite Connected");
                    String sql = "SELECT * FROM kitap";
                    try (
                            Statement st = conn.createStatement();
                            ResultSet rs1 = st.executeQuery(sql)) {

                        while (rs1.next()) {
                            String tIsbn = rs1.getString("isbn");
                            String tIsim = rs1.getString("isim");
                            if (alinacakISBN.equals(rs1.getString("isbn"))) {
                                log.info("Kitap alindi");
                                String sqlAdd = "INSERT INTO sahiplik(kismi,kisbn,id,kalangun) values(?,?,?,?)";
                                try {
                                    PreparedStatement pstmt = conn.prepareStatement(sqlAdd);
                                    pstmt.setString(1, tIsim);
                                    pstmt.setString(2, tIsbn);
                                    pstmt.setString(3, LogonID);
                                    pstmt.setInt(4, 7);
                                    pstmt.executeUpdate();
                                } catch (SQLException e) {
                                    System.out.println(e.getMessage());
                                }
                                try {
                                    String sqlDel = "DELETE FROM kitap WHERE isbn=(?)";
                                    try {
                                        PreparedStatement pstmt1 = conn.prepareStatement(sqlDel);
                                        pstmt1.setString(1, alinacakISBN);
                                        pstmt1.executeUpdate();
                                    } catch (SQLException e) {
                                        System.out.println(e.getMessage());
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                root.addComponent(kitapİadeEdin);
            }
        });
        btnYukle.addClickListener(clickEvent -> {
                    String url = ISBNUrl.getValue();
                    text = OCR.ocrr(url);
                    ISBNver.setValue(text);
                    root.addComponents(ISBNver, btnVer);
                }
        );
        btnVer.addClickListener(clickEvent -> {
            System.out.println("sadad");
            String verilecekISBN = ISBNver.getValue();
            String dbPath = "jdbc:sqlite:DBFile\\LibraryDB.db";
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(dbPath);
                log.info("Sqlite Connected");
                String sql = "SELECT * FROM sahiplik";
                try (
                        Statement st = conn.createStatement();
                        ResultSet rs1 = st.executeQuery(sql)) {
                    while (rs1.next()) {
                        String tIsbn = rs1.getString("kisbn");
                        String tIsim = rs1.getString("kismi");
                        System.out.println(verilecekISBN + "****" + rs1.getString("kisbn"));
                        if (verilecekISBN.equals(rs1.getString("kisbn"))&&LogonID.equals(rs1.getString("id"))) {
                            log.info("Kitap verildi.");
                            String sqlAdd = "INSERT INTO kitap(isim,isbn) values(?,?)";
                            try {
                                PreparedStatement pstmt = conn.prepareStatement(sqlAdd);
                                pstmt.setString(1, tIsim);
                                pstmt.setString(2, tIsbn);
                                pstmt.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                            try {
                                String sqlDel = "DELETE FROM sahiplik WHERE kisbn=(?)";
                                try {
                                    PreparedStatement pstmt1 = conn.prepareStatement(sqlDel);
                                    pstmt1.setString(1, verilecekISBN);
                                    pstmt1.executeUpdate();
                                } catch (SQLException e) {
                                    System.out.println(e.getMessage());
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        btnSearch.addClickListener(clickEvent -> {
            log.info("İşlem tamamlandı");
            String dbPath = "jdbc:sqlite:DBFile\\LibraryDB.db";
            Connection conn = null;
            String tISBN = ISBN.getValue();
            String tIsim = isim.getValue();
            try {
                conn = DriverManager.getConnection(dbPath);
                System.out.println("sqlite connected");
                String sql = "SELECT * FROM kitap";
                try (
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(sql)) {
                    while (rs.next()) {
                        if (tIsim.equals(rs.getString("isim")) || tISBN.equals(rs.getString("isbn"))) {

                            Label kitaplar = new Label("Kitap İsmi: " + rs.getString("isim") + " ISBN: " + rs.getString("ISBN"));
                            root.addComponent(kitaplar);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
