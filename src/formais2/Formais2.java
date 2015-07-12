/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formais2;

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
                + "B -> b B | A C d | &\n"
                + "C -> c C | &";
        // sem recursao a esquerda e nao fatorada
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
        GLC glc = new GLC(gramatica5);
        System.out.println(glc.possuiRecursaoAEsquerda());
        System.out.println(glc.fatorada());
    }

}
