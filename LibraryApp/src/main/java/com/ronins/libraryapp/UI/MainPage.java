package com.ronins.libraryapp.UI;


import com.ronins.libraryapp.Backend.OCR;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.ronins.libraryapp.Backend.DB;

@Title("Anasayfa")
@SpringUI
@Theme("apptheme")
public class MainPage extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout root = new VerticalLayout();


        root.setDefaultComponentAlignment(Alignment.TOP_CENTER);

        VerticalLayout buttons = new VerticalLayout();
        buttons.setStyleName("mainPage");
        buttons.setWidth(30, Unit.PERCENTAGE);
        buttons.setDefaultComponentAlignment(Alignment.TOP_CENTER);

        Button btnAsama1 = new Button("Yönetici Girişi", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                getUI().getPage().open("/yonetici", "Yönetici Girişi", false);
            }
        });

        Button btnAsama2 = new Button("Kullanici Girişi", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().getPage().open("/kullanici", "Kullanıcı Girişi", false);
            }
        });
        buttons.addComponents(btnAsama1, btnAsama2);
        root.addComponent(buttons);
        setContent(root);

    }
}
