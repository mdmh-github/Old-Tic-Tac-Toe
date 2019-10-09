package GUI;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

public class GatoDeMario extends MIDlet {

    private Display display;
    private MenuPrincipal main;
    private Command salir,  ok;

    public GatoDeMario() {
        display = Display.getDisplay(this);
        main = new MenuPrincipal(display, this);
        display.setCurrent(main);
        salir = new Command("Salir", Command.EXIT, 1);
        ok = new Command("ok", Command.OK, 1);
        main.addCommand(salir);
        main.addCommand(ok);
    }

    public void startApp() {
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

}

