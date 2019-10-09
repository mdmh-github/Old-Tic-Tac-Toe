package GUI;

import javax.microedition.lcdui.*;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;

public class acercaDe extends Canvas {

    private Image fondo;
    private MenuPrincipal menu;
    private int var;
    private Player sonido;

    public acercaDe(MenuPrincipal m) {
        menu = m;
        var = 1;
        this.setFullScreenMode(true);
        try {
            fondo = Image.createImage("/img/acercade.png");
            sonido = Manager.createPlayer(getClass().getResourceAsStream("/musicaOculta/porAmor.mid"), "audio/midi");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        g.drawImage(fondo, 65, 0, Graphics.TOP | Graphics.HCENTER);
    }

    protected void keyPressed(int keyCode) {
        switch (keyCode) {
            case Canvas.KEY_NUM6:
                if (var == 1 || var == 4) {
                    var = var << 1;
                    return;
                }
                regresar();

            case Canvas.KEY_NUM3:
                if (var == 2) {
                    var = var << 1;
                    return;
                }
                regresar();

            case Canvas.KEY_NUM4:
                if (var == 8) {
                    try {
                        sonido.start();
                    } catch (Exception e) {
                    }
                    return;
                }
                regresar();
            default: {
                regresar();
            }
        }
    }

    private void regresar() {
        try {
            var = 1;
            sonido.stop();
        } catch (Exception e) {
        }
        menu.getDisplay().setCurrent(menu);
    }
}