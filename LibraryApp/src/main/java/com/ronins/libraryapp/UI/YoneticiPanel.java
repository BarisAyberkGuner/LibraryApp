package com.ronins.libraryapp.UI;

import com.ronins.libraryapp.Backend.DB;
import com.ronins.libraryapp.Backend.OCR;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.NumberRenderer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Title("Yönetici Panel")
@SpringUI(path = "/yoneticipanel")
@Theme("apptheme")
@UIScope
public class YoneticiPanel extends UI {

    Logger log = Logger.getLogger(YoneticiPanel.class.getName());
    VerticalLayout root;
    String text = "";

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setContent(root);

        TextArea isim = new TextArea("Kitap İsmi");
        isim.setWidth("25%");
        isim.setHeight(45, Unit.PIXELS);
        TextArea ISBN = new TextArea("ISBN");
        ISBN.setWidth("25%");
        ISBN.setHeight(45, Unit.PIXELS);
        TextArea ISBNUrl = new TextArea("ISBN URL");
        ISBNUrl.setWidth("25%");
        ISBNUrl.setHeight(45, Unit.PIXELS);
        Button btnUpload = new Button("ISBN yükle");
        Button kitapYukle = new Button("Kitap Yükle");
        TextArea tarihAtla = new TextArea("Atlanacak Gün");
        tarihAtla.setWidth("25%");
        tarihAtla.setHeight(45, Unit.PIXELS);
        Button btnAtla = new Button("Tarih Atla");
        Button listUser = new Button("Kullanıcıları Listele");


        root.addComponents(isim, ISBN, ISBNUrl, btnUpload, kitapYukle, tarihAtla, btnAtla, listUser);

        btnUpload.addClickListener(clickEvent -> {
            log.info("Buton tıklandı");
            String url = ISBNUrl.getValue();
            text = OCR.ocrr(url);
            ISBN.setValue(text);
            log.info("İşlem tamamlandı");
        });
        btnAtla.addClickListener(clicEvent -> {
            log.info("Zaman Atlandı.");
            String gunSayisi = tarihAtla.getValue();
            int i = Integer.parseInt(gunSayisi);
            System.out.println(i);
            int tGun;

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
                        tGun = Integer.parseInt(rs.getString("kalangun"));
                        String tISBN = rs.getString("kisbn");
                        int yeniGun = tGun - i;
                        String ssql = "UPDATE sahiplik SET kalangun=(?) WHERE kisbn=(?)";
                        try {
                            PreparedStatement pstmt1 = conn.prepareStatement(ssql);
                            pstmt1.setInt(1, yeniGun);
                            pstmt1.setString(2, tISBN);
                            pstmt1.executeUpdate();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        kitapYukle.addClickListener(clickEvent -> {
            DB.addBook(ISBN.getValue(), isim.getValue());
        });
        listUser.addClickListener(clickEvent -> {
            String dbPath = "jdbc:sqlite:DBFile\\LibraryDB.db";
            Connection conn = null;
            try {
                // Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(dbPath);
                System.out.println("sqlite connected");
                String sql = "SELECT * FROM sahiplik";
                System.out.println("id      sifre");
                try (

                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(sql)) {
                    while (rs.next()) {
                        Label kullanicilar = new Label("ID: " + rs.getString("id") + " Sahip oldugu kitap: " + rs.getString("kismi") + " Kalan Gün: " + rs.getInt("kalangun"));
                        root.addComponent(kullanicilar);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        GridLayout gl = new GridLayout();
        root.addComponent(gl);
    }

}
