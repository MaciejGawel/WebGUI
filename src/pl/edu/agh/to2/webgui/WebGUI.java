package pl.edu.agh.to2.webgui;

import com.google.gwt.dev.jjs.SourceInfoCorrelation;
import com.vaadin.annotations.Push;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.server.VaadinRequest;
import pl.edu.agh.to2.webgui.presenter.MainPresenter;
import pl.edu.agh.to2.webgui.view.*;
import pl.edu.agh.to2.webgui.presenter.GamePresenter;
import pl.edu.agh.to2.webgui.presenter.LobbyPresenter;
import pl.edu.agh.to2.webgui.presenter.LoginPresenter;
import to2.dice.game.GameState;
import to2.dice.messaging.LocalConnectionProxy;
import to2.dice.server.Server;
import to2.dice.server.ServerMessageListener;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej on 2014-11-28.
 */
@Push
public class WebGUI extends UI {

    @Override
    public void init(VaadinRequest request) {
        Server server = ContextListener.server;
        MessageListener listener = new MessageListener(this);
        LocalConnectionProxy lcp = null;
        try {
            lcp = new LocalConnectionProxy(server, listener);
        } catch (ConnectException e) {
            e.printStackTrace();
        }
//        lcp = ContextListener.lcp;
        getSession().setAttribute("lcp", lcp);
        getSession().setAttribute("listener", listener);

        new Navigator(this, this);
//        LoginView loginView = new LoginView();
//        LoginPresenter loginPresenter = new LoginPresenter(loginView, lcp);
        
//        LobbyView lobbyView = new LobbyView();
//        LobbyPresenter lobbyPresenter = new LobbyPresenter(lobbyView);
        
//        GameView gameView = new GameView();
//        GamePresenter gamePresenter = new GamePresenter(gameView);

//        listener.setGamePresenter(gamePresenter);
//        listener.setLobbyPresenter(lobbyPresenter);

        getNavigator().addView(MainView.NAME, MainView.class);
        getNavigator().addView(LoginView.NAME, LoginView.class);
        getNavigator().addView(CreateGameView.NAME, CreateGameView.class);
        getNavigator().addView(GameView.NAME, GameView.class);
        getNavigator().addView(LobbyView.NAME, LobbyView.class);
        getNavigator().addView(ScoreView.NAME, ScoreView.class);
        
        getNavigator().addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent viewChangeEvent) {
                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = viewChangeEvent.getNewView() instanceof LoginView;

                if(!isLoggedIn && !isLoginView) {
                    getNavigator().navigateTo(LoginView.NAME);
                    return false;
                } else if (isLoggedIn && isLoginView) {
                    return false;
                }
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent viewChangeEvent) {

            }
        });
    }
}
