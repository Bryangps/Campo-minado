package br.com.bryan.campoMinado;

import br.com.bryan.campoMinado.modelo.Tabuleiro;
import br.com.bryan.campoMinado.visao.TabuleiroConsole;

public class Aplicacao {
    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 3);

        new TabuleiroConsole(tabuleiro);


    }
}
