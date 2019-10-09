package GUI;

import Logica.Gato;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class MenuPrincipal extends Canvas {

    private Image splash,  select,  level;
    private int selector,  posy,  posx,  pos,  limite;
    private GatoVisual ca;
    private acercaDe acerca;
    private Display di;
    private boolean escojerNivel;
    MIDlet midlet;

    public MenuPrincipal(Display d, MIDlet m) {
        try {
            midlet = m;
            di = d;
            ca = new GatoVisual(this);
            acerca = new acercaDe(this);
            splash = Image.createImage("/img/splash.png");
            select = Image.createImage("/img/selector.png");
            level = Image.createImage("/img/level.png");
            selector = 0;
            posy = 60;
            posx = 8;
            pos = Graphics.TOP | Graphics.HCENTER;
            escojerNivel = false;
            limite = 3;
            this.setFullScreenMode(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSelector() {
        return selector;
    }

    public Display getDisplay() {
        return di;
    }

    public boolean isEscojerNivel() {
        return escojerNivel;
    }

    public void paint(Graphics g) {
        g.setColor(0, 0, 0);

        g.drawImage(splash, 87, 0, pos);
        if (escojerNivel) {
            g.drawImage(level, 50, 0, pos);
            g.drawImage(select, posx + 50, posy + (selector * 20) + 58, pos);
            return;
        }
        g.drawImage(select, posx, posy + (selector * 20), pos);
    }

    public void setEscojerNivel(boolean escojerNivel) {
        this.escojerNivel = escojerNivel;
    }

    protected void keyPressed(int keyCode) {
        int ingame=getGameAction(keyCode);
        try {
            if (ingame == Canvas.UP||keyCode == Canvas.KEY_NUM2) {
                selector -= (selector != 0) ? 1 : 0;
            } else if (ingame == Canvas.DOWN||keyCode == Canvas.KEY_NUM8) {
                selector += (selector != limite) ? 1 : 0;
            } else if (ingame == Canvas.FIRE||keyCode == Canvas.KEY_NUM5) {
                gestionarNivel();
            }        
            repaint();
        } catch (Exception e) {
        }
    }

    public void gestionarNivel() {
        try {
            if (escojerNivel) {
                switch (selector) {
                    case 0:
                        ca.nuevoPvC(Gato.O, Gato.X, Gato.FACIL);
                        di.setCurrent(ca);
                        break;
                    case 1:
                        ca.nuevoPvC(Gato.O, Gato.X, Gato.MEDIO);
                        di.setCurrent(ca);
                        break;
                    case 2:
                        ca.nuevoPvC(Gato.O, Gato.X, Gato.DIFICIL);
                        di.setCurrent(ca);
                        break;
                    case 3:
                        escojerNivel = false;
                        selector = 0;
                        this.repaint();
                        break;
                }
                return;
            }
              switch (selector) {
                case 0:
                    escojerNivel = true;
                    break;
                case 1:
                    ca.nuevoPvP();
                    di.setCurrent(ca);
                    break;
                case 2:
                    di.setCurrent(acerca);
                    break;
                case 3:
                    midlet.notifyDestroyed();
                    break;

            }
        } catch (Exception e) {
        }
    }
}