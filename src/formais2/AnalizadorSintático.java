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
        String programa = "public class z {\n"
                + "    \n"
                + "    public static String alex(String a){\n"
                + "        return a.substring(1, a.length());\n"
                + "    }\n"
                + "public static void main(String[] args){\n"
                + "        String x = args[0]+\"$\";\n"
                + "        x = z.S(x);\n"
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
                programa += gerarCodigoProd(prods[j].trim(), 0);

            }
            programa += "return \"\";\n}\n";
        }
        programa += "}\n";
        return programa;
    }

    public String gerarCodigoNTerm(String nTerm) {
        return "public static String " + nTerm + "(String x){\n"
                + "String y = x;\n";
    }

    public String gerarCodigoProd(String producao2, int i) {
        String retorno = "";
        if (i >= producao2.length() || producao2.equals("&")) {
            return retorno;
        }
        String producao = producao2.split(" ")[i];
        if (glc.getSimbolosTerminais().contains(producao)) {
             retorno += "if(x.equals(" + producao + ")){ \n"
                    + "    y=z.alex(x); \n";
            for(int j = 1; i < producao2.split(" ").length;j++)   
        } else {
            Set<String> first = glc.calcFirstProd(producao);
            retorno += "if (";
            String or = "";
            for (String s : first) {
                retorno += or + "x.equals(" + s + ")";
                or = " || ";
            }
            retorno+="){\n" + gerarCodigoProd(producao2, i+1)+ "\n}";
            return retorno;
        }
    }
}
