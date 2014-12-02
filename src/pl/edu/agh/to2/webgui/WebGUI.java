package pl.edu.agh.to2.webgui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.server.VaadinRequest;
import pl.edu.agh.to2.webgui.presenter.MainPresenter;
import pl.edu.agh.to2.webgui.view.CreateGameView;
import pl.edu.agh.to2.webgui.presenter.GamePresenter;
import pl.edu.agh.to2.webgui.presenter.LobbyPresenter;
import pl.edu.agh.to2.webgui.view.GameView;
import pl.edu.agh.to2.webgui.view.LobbyView;
import pl.edu.agh.to2.webgui.view.LoginView;
import pl.edu.agh.to2.webgui.view.MainView;
import pl.edu.agh.to2.webgui.presenter.LoginPresenter;
import to2.dice.game.GameState;
import to2.dice.messaging.LocalConnectionProxy;
import to2.dice.server.Server;
import to2.dice.server.ServerMessageListener;

/**
 * Created by Maciej on 2014-11-28.
 */
public class WebGUI extends UI {
    @Override
    public void init(VaadinRequest request) {
        Server server = new Server();
        ServerMessageListener listener = new ServerMessageListener() {
            @Override
            public void onGameStateChange(GameState gameState) {
                return;
            }
        };
        LocalConnectionProxy lcp = new LocalConnectionProxy(server, listener);


        new Navigator(this, this);
        LoginView loginView = new LoginView();
        LoginPresenter loginPresenter = new LoginPresenter(loginView, lcp);
        
        LobbyView lobbyView = new LobbyView();
        new LobbyPresenter(lobbyView);
        
        GameView gameView = new GameView();
        new GamePresenter(gameView);

//        MainView mainView = new MainView();
//        MainPresenter mainPresenter = new MainPresenter(mainView);

//        getNavigator().addView(MainView.NAME, mainView);
        getNavigator().addView(MainView.NAME, MainView.class);
        getNavigator().addView(LoginView.NAME, loginView);
        getNavigator().addView(CreateGameView.NAME, CreateGameView.class);
        getNavigator().addView(GameView.NAME, gameView);
        getNavigator().addView(LobbyView.NAME, lobbyView);
        
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
