package com.ronins.libraryapp.UI;

import com.ronins.libraryapp.Backend.DB;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.ronins.libraryapp.UI.KullaniciGiris;
import java.sql.*;
import java.util.logging.Logger;
import com.ronins.libraryapp.Backend.DB.*;


@Title("Kullanıcı Girişi")
@SpringUI(path = "/kullanici")
@Theme("apptheme")
@UIScope
public class KullaniciGiris extends UI {

    Logger log;

    VerticalLayout root;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setContent(root);

        TextArea id = new TextArea();
        id.setCaption("Kullanici Adi:");
        id.setWidth("20%");
        id.setHeight(40, Unit.PIXELS);
        TextArea pass = new TextArea();
        pass.setCaption("Şifre");
        pass.setWidth("20%");
        pass.setHeight(40, Unit.PIXELS);
        Button girisBtn = new Button("Giriş");
        root.addComponent(girisBtn);
        root.addComponents(id, pass, girisBtn);
        Label wrongID = new Label("Yanlış Kullanıcı Adı");

        girisBtn.addClickListener(clickEvent ->
        {

            String dbPath = "jdbc:sqlite:DBFile\\LibraryDB.db";
            Connection conn = null;
            int c = 0;
            try {
                conn = DriverManager.getConnection(dbPath);
                System.out.println("sqlite connected");
                String sql = "SELECT * FROM kullanici";
                try (
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(sql)) {
                    while (rs.next()) {
                        String tId = rs.getString("id");
                        String tPass = rs.getString("sifre");
                        System.out.println(rs.getString("id"));
                        if (tId.equals(id.getValue()) && tPass.equals(pass.getValue())) {
                            getUI().getPage().open("/kullanicipanel", "Kullanıcı Panel", false);
                            c = 1;
                            DB.setID(tId);
                        }
                    }
                    if (c == 0) {
                        Label wrongPass = new Label("Yanlış Kullanıcı Adı veya Şifre");
                        root.addComponent(wrongPass);
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
