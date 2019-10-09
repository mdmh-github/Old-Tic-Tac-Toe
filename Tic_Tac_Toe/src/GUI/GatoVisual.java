package GUI;

import Logica.Gato;
import java.io.IOException;
import java.util.Random;
import javax.microedition.lcdui.*;

public class GatoVisual extends Canvas {

    private final int pvp = 0,  pvc = 1;
    private int pos = (Graphics.LEFT | Graphics.TOP),  v1,  v2,  h1,  h2,  w,  h,  tipo;
    private Image board,  x,  o,  t,  lienzo,  gameOver;
    private final static Gato gato = new Gato();
    public final static Random r = new Random();
    private Graphics gg;
    private MenuPrincipal menu;

    public GatoVisual(MenuPrincipal m) {
        try {
            menu = m;
            determinarImagenes();
            this.setFullScreenMode(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void determinarImagenes() throws IOException {
        nuevoFondo();
        w = (int) (board.getWidth() / 3);
        h = (int) (board.getHeight() / 3);
        x = Image.createImage("/img/X.png");
        o = Image.createImage("/img/O.png");
        gameOver = Image.createImage("/img/gameOver.png");
        h1 = 4 + w;
        h2 = h1 * 2;
        v1 = h;
        v2 = v1 * 2;
    }

    private void nuevoFondo() throws IOException {
        board = Image.createImage("/img/gato" + r.nextInt(7) + ".png");
        lienzo = Image.createImage(board.getWidth(), board.getHeight());
        gg = lienzo.getGraphics();
        gg.drawImage(board, 0, 0, pos);
    }

    public void nuevoPvP() throws IOException {
        gato.nuevoJuegoPvP(Gato.O, Gato.X);
        tipo = pvp;
        nuevoFondo();
    }

    public void nuevoPvC(int p1, int p2, int l) throws IOException {
        gato.nuevoJuegoPvC(p1, p2, l);
        tipo = pvc;
        nuevoFondo();
    }

    public void paint(Graphics g) {
        g.setColor(255, 255, 255);    
        g.drawImage(lienzo, 0, 0, pos);
        g.setColor(0, 0, 0);
        g.drawString("Turnos: ", 50, 200, pos);
        g.drawString(Integer.toString(gato.getTurnosRestantes()), 90, 200, pos);
    }

    protected void keyPressed(int keyCode) {
        try {
            if (!gato.isFin()) {
                if (gato.isBotActivo()) {
                    t = (gato.getPlayer1() == Gato.O) ? o : x;
                    if (gato.tirarPlayer(gato.getPlayer1(), keyCode - 48)) {
                        this.pintar(gato.getUltimoTiro());
                        if (!gato.isFin()) {
                            gato.tiroGato();
                            t = (gato.getPlayer2() == Gato.O) ? o : x;
                            this.pintar(gato.getUltimoTiro());
                        }
                    }
                } else {
                    if (gato.getTurnosRestantes() % 2 == 1) {
                        t = (gato.getPlayer1() == Gato.O) ? o : x;
                        if (gato.tirarPlayer(gato.getPlayer1(), keyCode - 48)) {
                            this.pintar(gato.getUltimoTiro());
                        }
                    } else {
                        t = (gato.getPlayer2() == Gato.O) ? o : x;
                        if (gato.tirarPlayer(gato.getPlayer2(), keyCode - 48)) {
                            this.pintar(gato.getUltimoTiro());
                        }
                    }
                }
                if (gato.isFin()) {
                    pintarFin();
                }
                repaint();
                return;
            }

            switch (keyCode) {
                case Canvas.KEY_NUM1:
                    switch (tipo) {
                        case pvp:
                            nuevoPvP();
                            break;
                        case pvc:
                            nuevoPvC(gato.getPlayer1(), gato.getPlayer2(), gato.getNivel());
                            break;
                    }
                    break;
                case Canvas.KEY_NUM3:
                    menu.setEscojerNivel(false);
                    menu.getDisplay().setCurrent(menu);
                    break;
            }
            repaint();
        } catch (Exception e) {
        }
    }

    private void pintarFin() throws InterruptedException {
        Thread.sleep(500);
        gg.drawImage(gameOver, 0, 0, pos);
        gg.setColor(0, 0, 0);
        switch (gato.getGanador()) {
            case Gato.O:
                gg.drawImage(o, 0, v1, pos);
                break;
            case Gato.X:
                gg.drawImage(x, 0, v1, pos);
                break;
            case Gato.NINGUNO:
                gg.drawString("Nadie", h1, v1, pos);
                break;
        }
        gg.drawString("Gano! xD!", h1, v1 + 20, pos);
    }

    public boolean isBotActivo() {
        return gato.isBotActivo();
    }

    private void pintar(int cuadro) {
        this.repaint(80, 140, 81, 142);
        switch (cuadro) {
            case 1:
                gg.drawImage(t, 0, 0, pos);
                return;
            case 2:
                gg.drawImage(t, h1, 0, pos);
                return;
            case 3:
                gg.drawImage(t, h2, 0, pos);
                return;
            case 4:
                gg.drawImage(t, 0, v1, pos);
                return;
            case 5:
                gg.drawImage(t, h1, v1, pos);
                return;
            case 6:
                gg.drawImage(t, h2, v1, pos);
                return;
            case 7:
                gg.drawImage(t, 0, v2, pos);
                return;
            case 8:
                gg.drawImage(t, h1, v2, pos);
                return;
            case 9:
                gg.drawImage(t, h2, v2, pos);
                return;
        }
    }
}