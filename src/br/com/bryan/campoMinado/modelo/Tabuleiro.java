package br.com.bryan.campoMinado.modelo;

import br.com.bryan.campoMinado.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<Campo>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void abrir(int linha, int coluna) {
       try {
            campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst().ifPresent(Campo::abrir);
       } catch (ExplosaoException e) {
           campos.forEach(c -> c.setAberto(true));
           throw e;
       }
    }

    public void alternarMarcacao(int linha, int coluna) {
        campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst().ifPresent(Campo::alternarMarcacao);
    }

    private void gerarCampos() {
        for (int linha = 0 ; linha < linhas ; linha++ ) {
            for (int coluna = 0 ; coluna < colunas ; coluna++ ) {
                campos.add(new Campo(linha, coluna));
            }
        }
    }

    public List<Campo> getCampos() {
        return campos;
    }

     private void associarVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = c -> c.isMinado();
        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();

        } while (minasArmadas < minas);
    }

    public boolean objetivoAlcancado(){
        return campos.stream().allMatch(Campo::objetivoAlcancado);
    }

    public void reiniciar(){
        campos.forEach(Campo::reiniciar);
        sortearMinas();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int coluna = 0; coluna < colunas; coluna++) {
            sb.append(" ");
            sb.append(coluna);
            sb.append(" ");
        }
        sb.append("\n");

        int i = 0;
        for (int linha = 0 ; linha < linhas ; linha++ ) {
            sb.append(linha);
            for (int coluna = 0 ; coluna < colunas ; coluna++ ) {
                sb.append(" ");
                sb.append(campos.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
