package br.com.bryan.campoMinado.modelo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TabuleiroTeste {

    private Tabuleiro tabuleiro;

    @BeforeEach
    void iniciarTabuleiro() {
        tabuleiro = new Tabuleiro(6,6,3);
    }

    @Test
    void testeAbrir(){

        tabuleiro.abrir(3,3);

        Campo campo = tabuleiro.getCampos()
                .stream().filter(c -> c.getLinha() == 3 && c.getColuna() == 3)
                .findFirst()
                .orElseThrow();
        assertTrue(campo.isAberto());
    }

    @Test
    void testeAlternarMarcacao(){
        Campo campo = tabuleiro.getCampos()
                .stream().filter(c -> c.getLinha() == 3 && c.getColuna() == 3)
                .findFirst().orElseThrow();

        tabuleiro.alternarMarcacao(3,3);
        assertTrue(campo.isMarcado());
    }

    @Test
    void testeReinicir(){
        tabuleiro.abrir(3,3);
        tabuleiro.reiniciar();

        Campo campo = tabuleiro.getCampos()
                .stream()
                .filter(c -> c.getLinha() == 3 && c.getColuna() == 3)
                .findFirst().orElseThrow();
        assertFalse(campo.isAberto());
    }
}
