package pl.edu.agh.to2.webgui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import pl.edu.agh.to2.webgui.presenter.MainPresenter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Maciej on 2014-12-01.
 */
public class MainView extends CustomComponent
        implements IMainView, View, Button.ClickListener, MenuBar.Command {
    public static final String NAME = "";
    public static final String LOGOUT_TEXT = "Logout";
    public static final String CREATE_TEXT = "Create game";

    private MenuBar menu = new MenuBar();

    public MainView() {
        MainPresenter mainPresenter = new MainPresenter(this);
        GridLayout panelLayout = new GridLayout(1, 4);
        Label selected = new Label("Selected: ");

        setSizeFull();
        Button join = new Button("Join now!", this);

        panelLayout.setWidth("100%");
        panelLayout.addComponent(join, 0, 3);
        panelLayout.addComponent(selected, 0, 2);
        panelLayout.addComponent(buildGamesList(), 0, 1);
        panelLayout.addComponent(buildMenu(), 0, 0);
        setCompositionRoot(panelLayout);
    }

    private Component buildGamesList() {
        Table servers = new Table("List of servers");

        servers.setSelectable(true);
        servers.setImmediate(true);
        servers.addContainerProperty("Address", String.class, null);
        servers.addContainerProperty("Game type", String.class, null);
        servers.addContainerProperty("Players", Integer.class, null);
        servers.setPageLength(servers.size());

        //mockup items
        servers.addItem(new Object[] {"193.193.80.0", "N+", 10}, null);
        servers.addItem(new Object[] {"193.193.80.1", "N*", 5}, null);
        servers.addItem(new Object[] {"193.193.80.2", "Poker", 20}, null);

        return servers;
    }

    private Component buildMenu() {
        menu.setWidth("100%");
        MenuBar.MenuItem createGame = menu.addItem(CREATE_TEXT, FontAwesome.PLUS_SQUARE, this);
        MenuBar.MenuItem refresh = menu.addItem("Refresh", FontAwesome.REFRESH, this);


        return menu;
    }

    List<MainViewListener> listeners = new ArrayList<MainViewListener>();

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        for (MainViewListener listener : listeners) {
            listener.buttonClick(clickEvent.getButton().toString());
        }
    }

    @Override
    public void menuSelected(MenuBar.MenuItem menuItem) {
        for (MainViewListener listener : listeners) {
            listener.menuSelected(menuItem.getText());
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        MenuBar.MenuItem currentUser = menu.addItem(String.valueOf(getSession().getAttribute("user")), FontAwesome.USER, null);
        MenuBar.MenuItem logout = currentUser.addItem(LOGOUT_TEXT, FontAwesome.SIGN_OUT, this);
        //logout.setCommand(this);
    }

    @Override
    public void addListener(MainViewListener listener) {
        listeners.add(listener);
    }
}