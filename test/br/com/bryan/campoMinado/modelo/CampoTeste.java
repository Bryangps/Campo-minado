package br.com.bryan.campoMinado.modelo;

import br.com.bryan.campoMinado.excecao.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CampoTeste {

    private Campo campo;

    @BeforeEach
    void iniciarCampo() {
        campo = new Campo(3, 3);
    }

    @Test
    public void testeVizinhoDistancia1Esquerda(){
        Campo vizinho = new Campo(3, 2);

        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }


    @Test
    public void testeVizinhoDistancia1Direita(){
        Campo vizinho = new Campo(3, 4);

        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistancia1EmCima(){
        Campo vizinho = new Campo(2, 3);

        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistancia1EmBaixo(){
        Campo vizinho = new Campo(4, 3);

        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistancia2(){
        Campo vizinho = new Campo(2, 2);

        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    public void testeNaoVizinho(){
        Campo vizinho = new Campo(1, 1);

        boolean resultado = campo.adicionarVizinho(vizinho);
        assertFalse(resultado);
    }

    @Test
    void testeValorPadraoAtributoMarcado(){

        assertFalse(campo.isMarcado());
    }


    @Test
    void testeAlternarMarcacao(){
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacaoDuasChamadas(){
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAbrirNaoMinadoNaoMarcado(){

        assertTrue(campo.abrir());
    }

    @Test
    void testeAbrirNaoMinadoMarcado(){

        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirMinadoMarcado(){
        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirMinadoNaoMarcado(){
        campo.minar();
        assertThrows(ExplosaoException.class, () -> campo.abrir());
    }

    @Test
    void testeAbrirComVizinhos1(){

        Campo campo11 = new Campo(1, 1);
        Campo campo22 = new Campo(2,2);

        campo22.adicionarVizinho(campo11);

        campo.adicionarVizinho(campo22);

        campo.abrir();
        assertTrue(campo11.isAberto() && campo22.isAberto());
    }

    @Test
    void testeAbrirComVizinhos2(){

        Campo campo11 = new Campo(1, 1);
        Campo campo12 = new Campo(1, 2);
        campo12.minar();

        Campo campo22 = new Campo(2,2);
        campo22.adicionarVizinho(campo11);
        campo22.adicionarVizinho(campo12);

        campo.adicionarVizinho(campo22);

        campo.abrir();
        assertTrue(campo22.isAberto() && campo11.isFechado());
    }

    @Test
    void testeVizinhancaSegura(){
        Campo campo1 = new Campo(2,2);
        Campo campo2 = new Campo(2,3);
        Campo campo3 = new Campo(2,4);

        campo.adicionarVizinho(campo1);
        campo.adicionarVizinho(campo2);
        campo.adicionarVizinho(campo3);

        assertTrue(campo.vizinhancaSegura());
    }

    @Test
    void testeVizinhoMinado(){
        Campo campo1 = new Campo(2,2);
        Campo campo2 = new Campo(2,3);
        Campo campo3 = new Campo(2,4);

        campo.adicionarVizinho(campo1);
        campo.adicionarVizinho(campo2);
        campo.adicionarVizinho(campo3);

        campo2.minar();

        assertFalse(campo.vizinhancaSegura());
    }

    @Test
    void testeMinar(){
        Campo campo1 = new Campo(2,2);
        campo1.minar();
        assertTrue(campo1.isMinado());
    }

    @Test
    void testeSetAberto(){
        Campo campo1 = new Campo(2,2);
        campo1.setAberto(true);

        assertTrue(campo1.isAberto());
    }

    @Test
    void testeGetLinha(){
        Campo campo1 = new Campo(2,2);

        assertEquals(2, campo1.getLinha());
    }

    @Test
    void testeGetColuna(){
        Campo campo1 = new Campo(2,2);
        assertEquals(2, campo1.getColuna());
    }

    @Test
    void testeObjetivoAlcancadoDesvendado(){
        Campo campo1 = new Campo(2,2);
        campo1.setAberto(true);
        assertTrue(campo1.objetivoAlcancado());
    }

    @Test
    void testeObjetivoAlcancadoProtegido(){
        Campo campo1 = new Campo(2,2);

        campo1.minar();
        campo1.alternarMarcacao();
        assertTrue(campo1.objetivoAlcancado());
    }

    @Test
    void testeObjetivoNaoAlcancado(){
        Campo campo1 = new Campo(2,2);

        campo1.minar();
        assertFalse(campo1.objetivoAlcancado());
    }

    @Test
    void testeMinasNaVizinhanca(){
        Campo campo1 = new Campo(2,2);
        Campo campo2 = new Campo(2,3);
        Campo campo3 = new Campo(2,4);
        Campo campo4 = new Campo(4,3);

        campo1.minar();
        campo3.minar();

        campo.adicionarVizinho(campo1);
        campo.adicionarVizinho(campo2);
        campo.adicionarVizinho(campo3);
        campo.adicionarVizinho(campo4);

        assertEquals(2, campo.minasNaVizinhanca());
    }

    @Test
    void testeIniciar(){
        campo.reiniciar();
        assertFalse(campo.isAberto() && campo.isMinado() &&
                campo.isMarcado());
    }

    @Test
    void testeToStringMarcado(){
        campo.alternarMarcacao();
        assertEquals("x", campo.toString());
    }

    @Test
    void testeToStringAbertoMinado(){
        campo.setAberto(true);
        campo.minar();

        assertEquals("*", campo.toString());
    }

    @Test
    void testeToStringAbertoMinasNaVizinhanca(){
        Campo campo1 = new Campo(2,2);
        Campo campo2 = new Campo(2,3);
        Campo campo3 = new Campo(2,4);

        campo3.minar();
        campo1.minar();
        campo.setAberto(true);
        campo.adicionarVizinho(campo1);
        campo.adicionarVizinho(campo2);
        campo.adicionarVizinho(campo3);

        assertEquals("2", campo.toString());
    }

    @Test
    void testeToStringAberto(){
        campo.setAberto(true);

        assertEquals(" ", campo.toString());
    }

    @Test
    void testeToStringFechado(){
        assertEquals("?", campo.toString());
    }
}
