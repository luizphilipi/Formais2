/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formais2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaulix
 */
public class Formais2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // com recursao a esquerda
        String gramatica = "E -> E + T | E - T | T\n"
                + "T -> T * F | T / F | F\n"
                + "F -> ( E ) | id";
        String gramatica2 = "S -> A b | A B c\n"
                + "B -> b B | A d | &\n"
                + "A -> a A | &";
        // sem recursao a esquerda
        String gramatica3 = "S -> A B C\n"
                + "A -> a A | &\n"
                + "B -> b B | A C d\n"
                + "C -> c C | &";
        // sem recursao a esquerda e nao fatorada (indiretamente)
        String gramatica4 = "S -> A B | B C\n"
                + "A -> a A | &\n"
                + "B -> b B | d\n"
                + "C -> c C1\n"
                + "C1 -> C | &";
        // sem recursao a esquerda e fatorada
        String gramatica5 = "S -> a A B | B S1\n"
                + "S1 -> C | &\n"
                + "A -> a A | &\n"
                + "B -> b B | d\n"
                + "C -> c C1\n"
                + "C1 -> C | &";
        // possui recursao a esquerda indireta
        String gramatica6 = "S -> A a | a\n"
                + "A -> S | b";
        String gramatica7 = "S -> a S | a B | d S\n"
                + "B -> b B | b";
        GLC glc = new GLC(gramatica7);

        String gramatica8 = "S -> B a B | id S\n"
                + "B -> a";

        String gramatica9 = "E -> T E1\n"
                + "E1 -> + T E1 | &\n"
                + "T -> F T1\n"
                + "T1 -> * F T1 | &\n"
                + "F -> ( E ) | id";
        AnalizadorSintático a = new AnalizadorSintático(gramatica9);

        System.out.println("First: " + glc.obterConjuntosFirst());
        System.out.println("Follow: " + glc.obterConjuntosFollow());
        try {
            System.out.println("Possui rec. esquerda: " + glc.possuiRecursaoAEsquerda());
        } catch (Exception ex) {
            Logger.getLogger(Formais2.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Fatorada: " + glc.isFatorada());
    }

}
