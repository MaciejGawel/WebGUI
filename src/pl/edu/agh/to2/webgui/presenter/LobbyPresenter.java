package pl.edu.agh.to2.webgui.presenter;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import pl.edu.agh.to2.webgui.MessageListener;
import pl.edu.agh.to2.webgui.WebGUI;
import pl.edu.agh.to2.webgui.view.GameView;
import pl.edu.agh.to2.webgui.view.ILobbyView;
import pl.edu.agh.to2.webgui.view.LobbyView;
import pl.edu.agh.to2.webgui.view.MainView;
import to2.dice.game.GameState;
import to2.dice.game.Player;
import to2.dice.messaging.LocalConnectionProxy;
import to2.dice.messaging.Response;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Created by Maciej on 2014-11-28.
 * Edited by Lukasz on 2014-12-01
 */
public class LobbyPresenter implements ILobbyView.LobbyViewListener {
    private LobbyView view;
    private LocalConnectionProxy lcp;

    public LobbyPresenter(LobbyView lobbyView){
        this.view = lobbyView;
        lobbyView.addListener(this);
//        this.lcp = WebGUI.lcp;
        this.lcp = (LocalConnectionProxy) VaadinSession.getCurrent().getAttribute("lcp");
        ((MessageListener) VaadinSession.getCurrent().getAttribute("listener")).setLobbyPresenter(this);
    }
    public void buttonClick(String operation) {
        if(operation.equalsIgnoreCase(LobbyView.LEAVE_TEXT)) {
            Response response = null;
            response = lcp.leaveRoom();
            if (response.isSuccess()) {
                view.getUI().getNavigator().navigateTo(MainView.NAME);
            } else {
                view.showNotification(response.message);
            }
        }
        else if(operation.equalsIgnoreCase(LobbyView.SIT_DOWN_TEXT)){
            Response response = null;
            response = lcp.sitDown();
            if(response.isSuccess()) {
                view.showNotification("You've sat down");
                view.sitDown();
            }
            else {
                view.showNotification(response.message);
            }
        }
        else if(operation.equalsIgnoreCase(LobbyView.STAND_UP_TEXT)){
            Response response = null;
            response = lcp.standUp();
            if(response.isSuccess()) {
                view.showNotification("You've stood up");
                view.standUp();
            }
            else {
                view.showNotification(response.message);
            }
        }

    }

    public void updateGameState(GameState gameState) {
        List<Player> players = gameState.getPlayers();
        List<String> playersNames = new ArrayList<String>();
        for(Player player : players) {
            playersNames.add(player.getName());
        }
        view.setPlayersList(playersNames);
    }

    public void startGame() {
        view.getUI().getNavigator().navigateTo(GameView.NAME);
    }
}
