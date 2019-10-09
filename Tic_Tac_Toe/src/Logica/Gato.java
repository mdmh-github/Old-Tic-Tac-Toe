package Logica;

import java.util.Random;

public class Gato {

    private int gato[][],  i,  j,  turnos;
    public static final int O = 1,  X = 2,  NINGUNO = 3,  FACIL = 10,  MEDIO = 20,  DIFICIL = 30;
    private int player1,  player2,  ganador,  ultimoTiro,  nivel,  h[],  v[],  d[];
    private boolean fin,  botActivo;
    private StringBuffer s;
    private Random r;

    public Gato() {
        gato = new int[3][3];
        s = new StringBuffer();
        h = new int[3];
        v = new int[3];
        d = new int[2];
        r = new Random();
    }

    private void nuevo() {
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                gato[i][j] = 0;
            }
        }
    }

    public void nuevoJuegoPvP(int mnemonic1, int mnemonic2) {
        player1 = mnemonic1;
        player2 = mnemonic2;
        turnos = 9;
        botActivo = false;
        nuevo();
        fin = false;
        ganador = NINGUNO;
        ultimoTiro = 0;

    }

    public void nuevoJuegoPvC(int mnemonic1, int mnemonic2, int l) {
     
        player1 = mnemonic1;
        player2 = mnemonic2;
        turnos = 9;
        botActivo = true;
        nuevo();
        fin = false;
        ganador = NINGUNO;
        nivel = l;
        ultimoTiro = 0;
    }

    public boolean isBotActivo() {
        return botActivo;
    }

    public boolean tirarPlayer(int player, int posicion) {
    
        if (fin) {
            return false;
        }

        if (colocar(player, posicion)) {
            turnos--;
            ganadorOfin(player, posicion);
            ultimoTiro = posicion;
            return true;
        }
        return false;
    }

    public int tiroFacil(int player) {
  
        int num;
        if (fin) {
            return -1;
        }
        do {
            num = r.nextInt(9) + 1;
        } while (!colocar(player, num));
        return num;
    }

    public int tiroMedio(int player) {
        if (fin) {
            return -1;
        }
        switch (r.nextInt(2)) {
            case 0:
                return tiroFacil(player);

            case 1:
                return tiroDificil(player);
        }
        return -1;
    }

    public int tiroDificil(int player) {
     
        int r;
        h[0] = verPosicion(1) ^ verPosicion(2) ^ verPosicion(3);
        h[1] = verPosicion(4) ^ verPosicion(5) ^ verPosicion(6);
        h[2] = verPosicion(7) ^ verPosicion(8) ^ verPosicion(9);
        v[0] = verPosicion(1) ^ verPosicion(4) ^ verPosicion(7);
        v[1] = verPosicion(2) ^ verPosicion(5) ^ verPosicion(8);
        v[2] = verPosicion(3) ^ verPosicion(6) ^ verPosicion(9);
        d[0] = verPosicion(1) ^ verPosicion(5) ^ verPosicion(9);
        d[1] = verPosicion(3) ^ verPosicion(5) ^ verPosicion(7);
        r = inteligenciaPorTipo(player2, player);
        if (r != 0) {
            return (r);
        }
        r = inteligenciaPorTipo(player1, player);
        if (r != 0) {
            return (r);
        }
        return tiroFacil(player);
    }

    private int inteligencia(int playerEv, int playerColocar, int p1, int p2, int p3, int hx, int cs) {
        if ((verPosicion(p1) | verPosicion(p2) | verPosicion(p3)) == playerEv && hx == 0) {
            for (i = p1; i <= p3; i += cs) {
                if (colocar(playerColocar, i)) {
                    return (i);
                }
            }
        }
        return 0;
    }

    private int inteligenciaPorTipo(int playerEv, int playerc) {
        int r;
        if ((r = inteligencia(playerEv, playerc, 1, 2, 3, h[0], 1)) != 0) {
            return r;
        }
        if ((r = inteligencia(playerEv, playerc, 4, 5, 6, h[1], 1)) != 0) {
            return r;
        }
        if ((r = inteligencia(playerEv, playerc, 7, 8, 9, h[2], 1)) != 0) {
            return r;
        }
        if ((r = inteligencia(playerEv, playerc, 1, 4, 7, v[0], 3)) != 0) {
            return r;
        }
        if ((r = inteligencia(playerEv, playerc, 2, 5, 8, v[1], 3)) != 0) {
            return r;
        }
        if ((r = inteligencia(playerEv, playerc, 3, 6, 9, v[2], 3)) != 0) {
            return r;
        }
        if ((r = inteligencia(playerEv, playerc, 1, 5, 9, d[0], 4)) != 0) {
            return r;
        }
        if ((r = inteligencia(playerEv, playerc, 3, 5, 7, d[1], 4)) != 0) {
            return r;
        }
        return 0;
    }

    public void tiroGato() {
        if (botActivo && !fin) {
            switch (nivel) {
                case FACIL:
                    ultimoTiro = tiroFacil(player2);
                    break;
                case MEDIO:
                    ultimoTiro = tiroMedio(player2);
                    break;
                case DIFICIL:
                    ultimoTiro = tiroDificil(player2);
                    break;
            }
            if (ultimoTiro != -1) {
                turnos--;
            }
            ganadorOfin(player2, ultimoTiro);
        }
    }

    public void ganadorOfin(int player, int posicion) {
        if (evaluar() == player) {
            fin = true;
            ganador =
                    player;
        }
        if (turnos == 0) {
            fin = true;
        }

    }

    public int getPlayer1() {
        return player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public int getGanador() {
        if (fin) {
            return ganador;
        }

        return NINGUNO;
    }

    public boolean isFin() {
        return fin;
    }

    public int getTurnosRestantes() {
        return turnos;
    }

    public int evaluar() {
        h[0] = (gato[0][0] & gato[0][1] & gato[0][2]);
        h[1] = (gato[1][0] & gato[1][1] & gato[1][2]);
        h[2] = (gato[2][0] & gato[2][1] & gato[2][2]);
        v[0] = (gato[0][0] & gato[1][0] & gato[2][0]);
        v[1] = (gato[0][1] & gato[1][1] & gato[2][1]);
        v[2] = (gato[0][2] & gato[1][2] & gato[2][2]);
        d[0] = (gato[0][0] & gato[1][1] & gato[2][2]);
        d[1] = (gato[0][2] & gato[1][1] & gato[2][0]);
        if (h[0] == player1 || h[1] == player1 || h[2] == player1 || v[0] == player1 || v[1] == player1 || v[2] == player1 || d[0] == player1 || d[1] == player1) {
            return player1;
        } else if (h[0] == player2 || h[1] == player2 || h[2] == player2 || v[0] == player2 || v[1] == player2 || v[2] == player2 || d[0] == player2 || d[1] == player2) {
            return player2;
        }

        return 0;
    }

    public boolean colocar(int player, int posicion) {
        if (verPosicion(posicion) == 0) {
            switch (posicion) {
                case 1:
                    gato[0][0] = player;
                    return true;
                case 2:
                    gato[1][0] = player;
                    return true;
                case 3:
                    gato[2][0] = player;
                    return true;
                case 4:
                    gato[0][1] = player;
                    return true;
                case 5:
                    gato[1][1] = player;
                    return true;
                case 6:
                    gato[2][1] = player;
                    return true;
                case 7:
                    gato[0][2] = player;
                    return true;
                case 8:
                    gato[1][2] = player;
                    return true;
                case 9:
                    gato[2][2] = player;
                    return true;
            }

            if (evaluar() == player) {
                fin = true;
                return true;
            }

            return true;
        }

        return false;
    }

    public int verPosicion(int posicion) {
        switch (posicion) {
            case 1:
                return gato[0][0];
            case 2:
                return gato[1][0];
            case 3:
                return gato[2][0];
            case 4:
                return gato[0][1];
            case 5:
                return gato[1][1];
            case 6:
                return gato[2][1];
            case 7:
                return gato[0][2];
            case 8:
                return gato[1][2];
            case 9:
                return gato[2][2];
            default:
                return -1;
        }

    }

    public int getNivel() {
        return nivel;
    }

    public int getUltimoTiro() {
        return ultimoTiro;
    }

    public String toString() {
        s.delete(0, s.length());
        for (i = 0; i <
                3; i++) {
            for (j = 0; j <
                    3; j++) {
                s.append(gato[j][i]);
            }
            s.append("\n");
        }

        return s.toString();
    }

    public int[][] getArreglo() {
        return gato;
    }
}