package pl.edu.agh.to2.webgui.view;

/**
 * Created by lukasz on 01.12.14.
 */
public interface IGameView {
    interface GameViewListener {
        void buttonClick(String operation);
    }

    public void addListener(GameViewListener listener);
}