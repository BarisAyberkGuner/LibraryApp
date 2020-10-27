package com.ronins.libraryapp.UI;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;


@Title("Yönetici Giriş")
@Theme("apptheme")
@SpringUI(path = "/yonetici")
@UIScope
public class YoneticiGiris extends UI {

    VerticalLayout root;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        root = new VerticalLayout();
        setContent(root);
        root.setDefaultComponentAlignment(Alignment.TOP_CENTER);

        String adminI = "admin";
        String adminP = "admin";


        TextArea id = new TextArea();
        id.setCaption("Kullanici Adi:");
        id.setWidth("20%");
        id.setHeight(40, Unit.PIXELS);
        TextArea pass = new TextArea();
        pass.setCaption("Şifre");
        pass.setWidth("20%");
        pass.setHeight(40, Unit.PIXELS);
        Label wrongPass = new Label("Yanlış Şifre");


        root.addComponents(id, pass);

        Button btnSearch = new Button("Ara");
        btnSearch.setWidth("50%");

        Button girisBtn = new Button("Giriş", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String adminID = id.getValue();
                String adminPass = pass.getValue();
                if (adminID.equals(adminI) && adminPass.equals(adminP)) {
                    getUI().getPage().open("/yoneticipanel", "Yönetici Panel", false);
                } else {
                    root.addComponent(wrongPass);

                }
            }
        });
        root.addComponent(girisBtn);


    }
}
