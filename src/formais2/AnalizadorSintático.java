/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formais2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class AnalizadorSintático {

    public String programa;
    private String gramatica;
    private GLC glc;
    Map<String, Set<String>> firsts;

    public AnalizadorSintático(String gramatica) {
        this.gramatica = gramatica;
        glc = new GLC(gramatica);
        firsts = glc.obterConjuntosFirst();
        programa = gerarParserDescendenteRecusivo();
        FileWriter arquivo;

        try {
            arquivo = new FileWriter(new File("z.java"));
            arquivo.write(programa);
            arquivo.close();
            Runtime.getRuntime().exec("javac z.java");
            Runtime.getRuntime().exec("jar - cf z.jar z.java");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reconhecer(String aReconhecer) {
        try {
            Runtime.getRuntime().exec("java z " + aReconhecer);
        } catch (IOException ex) {
            Logger.getLogger(AnalizadorSintático.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String gerarParserDescendenteRecusivo() {
        String programa = "public class z {\n\n"
                + "    public static String sentenca;\n"
                + "    \n"
                + "        public static String alex(String a) {\n"
                + "	if(a.length() != 0) {\n"
                + "	    sentenca = sentenca.substring(sentenca.indexOf(a + \" \") + a.length() + 1);\n"
                + "	}\n"
                + "	return sentenca.split(\" \")[0];\n"
                + "    }\n\n"
                + "public static void main(String[] args){\n"
                + "        z.sentenca = args[0]+\" $\";\n"
                + "        String x = z.alex(\"\");"
                + "        x = z." + glc.getSimboloInicial() + "(x);\n"
                + "        if(x.equals(\"$\")){\n"
                + "            System.out.println(\"reconhecida\");\n"
                + "        }else{\n"
                + "            System.err.println(\"errou\");\n"
                + "        }\n"
                + "    }\n";
        String[] Nterminais = gramatica.split("\n");
        for (int i = 0; i < Nterminais.length; i++) {
            String terminal = Nterminais[i].split("->")[0].trim();
            programa += gerarCodigoNTerm(terminal);
            String[] prods = Nterminais[i].split("->")[1].split("\\|");
            for (int j = 0; j < prods.length; j++) {
                if (j != 0) {
                    programa += "else ";
                }
                programa += gerarCodigoProd(prods[j].trim());
            }
            programa += "return x;\n}\n";
        }
        programa += "}\n";
        return programa;
    }

    public String gerarCodigoNTerm(String nTerm) {
        return "public static String " + nTerm + "(String x){\n";
    }

    public String gerarCodigoProd(String producao2) {
        String retorno = "";
        if (glc.getSimbolosTerminais().contains(producao2.split(" ")[0])) {
            return geraRecursivoTerminal(producao2, 0);
        } else {
            Set<String> first = glc.calcFirstProd(producao2);
            retorno += "// first de " + producao2 + "\nif (";
            String or = "";
            for (String s : first) {
                retorno += or + "x.equals(\"" + s + "\")";
                or = " || ";
            }
            retorno += ") {\n" + geraRecursivoTerminal(producao2, 0) + "\n}";
            return retorno;
        }
    }

    public String geraRecursivoTerminal(String producao, int i) {
        String[] splited = producao.split(" ");
        String retorno = "";
        if (splited.length > i) {
            if (splited[i].equals("&")) {
                return retorno;
            }
            if (glc.getSimbolosTerminais().contains(splited[i])) {
                retorno += "if (x.equals(\"" + splited[i] + "\")) {\n";
                retorno += "x = z.alex(x);";
                retorno += geraRecursivoTerminal(producao, i + 1);
                retorno += "\nreturn x;}\n";
            } else {
                retorno += "\nx = z." + splited[i] + "(x);\n";
                retorno += geraRecursivoTerminal(producao, i + 1);
            }
        }
        return retorno;
    }
}
