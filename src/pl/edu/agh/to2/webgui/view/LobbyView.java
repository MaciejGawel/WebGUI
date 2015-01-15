package pl.edu.agh.to2.webgui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import pl.edu.agh.to2.webgui.presenter.LobbyPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej on 2014-11-28.
 * Edited by Lukasz on 2014-12-01
 */
public class LobbyView extends CustomComponent
    implements ILobbyView, View,Button.ClickListener {
    public static final String NAME = "lobby";
    public static final String LEAVE_TEXT = "Leave lobby";
    public static final String SIT_DOWN_TEXT = "Sit down";
    public static final String STAND_UP_TEXT = "Stand up";

    List<LobbyViewListener> listeners = new ArrayList<LobbyViewListener>();
    Table users = new Table("Users in lobby");
    Panel panel = new Panel();
    Label info = new Label();
    Button leave = new Button(LEAVE_TEXT,this);
    Button sitdown = new Button(SIT_DOWN_TEXT, this);
    GridLayout panelLayout = new GridLayout(1,5);

    public LobbyView() {
//        new LobbyPresenter(this);

        setSizeFull();
        users.addContainerProperty("User", String.class, null);
        users.setPageLength(users.size());
        panelLayout.setWidth("100%");
        panelLayout.addComponent(info, 0, 0);
        panelLayout.addComponent(users,0,1);
        panelLayout.addComponent(sitdown,0,2);
        panelLayout.addComponent(leave,0,3);
        panel.setContent(panelLayout);
        setCompositionRoot(panel);
    }

    @Override
    public void showNotification(String message) {
        Notification notification = new Notification(message);
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.show(Page.getCurrent());
    }

    public void setPlayersList(List<String> playersList) {
        users.removeAllItems();
        for(String playerName : playersList) {
            users.addItem(new Object[] {playerName}, null);
        }
    }

    public void sitDown() {
        sitdown.setCaption(STAND_UP_TEXT);
    }

    public void standUp() {
        sitdown.setCaption(SIT_DOWN_TEXT);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        for(LobbyViewListener listener : listeners) {
            listener.buttonClick(clickEvent.getButton().getCaption());
        }
    }

    @Override
    public void addListener(LobbyViewListener listener) {
        listeners.add(listener);
    }
}
