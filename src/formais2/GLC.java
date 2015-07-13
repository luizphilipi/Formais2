/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formais2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author gaulix
 */
public class GLC {

    private final String simboloInicial;
    private final Set<String> simbolosNaoTerminais = new HashSet<String>();
    private final Set<String> simbolosTerminais = new HashSet<String>();
    private final Map<String, List<String>> producoes = new HashMap<String, List<String>>();

    public Set<String> getSimbolosNaoTerminais() {
        return simbolosNaoTerminais;
    }

    public Set<String> getSimbolosTerminais() {
        return simbolosTerminais;
    }

    public String getSimboloInicial() {
        return simboloInicial;
    }

    public Set<String> calcFirstProd(String prod) {
        Map<String, Set<String>> conjuntosFirst = obterConjuntosFirst();
        Set<String> retorno = new HashSet<String>();
        String[] splited = prod.split(" ");
        for (String string : splited) {
            Set<String> get = conjuntosFirst.get(string);
            if (get != null) {
                retorno.addAll(get);
                retorno.remove("&");
                if (!get.contains("&")) {
                    break;
                }
            } else {
                break;
            }
        }
        return retorno;
    }

    public int getNTerminaisCount() {
        return simbolosNaoTerminais.size();
    }

    public int getTerminaisCount() {
        return simbolosTerminais.size();
    }

    public GLC(String glc) {
        String[] linhas = glc.split("\n");
        simboloInicial = linhas[0].split("->")[0].trim();
        for (String linha : linhas) {
            String simbolo = linha.split("->")[0].trim();
            String ladoDireito = linha.split("->")[1].trim();
            List<String> producoesAUX = new ArrayList<String>();
            for (String producao : ladoDireito.split("\\|")) {
                producoesAUX.add(producao.trim());
                for (String l : producao.split(" ")) {
                    if (isSimboloTerminal(l)) {
                        simbolosTerminais.add(l);
                    }
                }
            }
            simbolosNaoTerminais.add(simbolo);
            producoes.put(simbolo, producoesAUX);
        }
    }

    public boolean possuiRecursaoAEsquerda() {
        return possuiRecursaoAEsquerda(new HashSet<String>(), simboloInicial, obterConjuntosFirst(), new ArrayList<String>(producoes.keySet()));
    }

    private boolean possuiRecursaoAEsquerda(Set<String> simbolosVisitados, String simbolo, Map<String, Set<String>> conjuntosFirst, List<String> restante) {
        List<String> producoes = this.producoes.get(simbolo);

        restante.remove(simbolo);

        for (String producao : producoes) {
            String[] simbolos = producao.split(" ");
            int i = 0;
            boolean contemSimbolo = true;

            if (simbolo.equals(simbolos[0])) {
                System.out.println("Possui recursão a esquerda direta com o símbolo " + simbolo);
                return true;
            }

            while (contemSimbolo && i < simbolos.length && !isSimboloTerminal(simbolos[i])) {

                if (simbolosVisitados.contains(simbolos[i])) {
                    System.out.println("Recursao indireta na produção " + simbolo + " -> " + producao + " com o símbolo " + simbolos[i]);
                    return true;
                }

                simbolosVisitados.add(simbolos[i]);

                if (possuiRecursaoAEsquerda(simbolosVisitados, simbolos[i], conjuntosFirst, restante)) {
                    return true;
                }
                contemSimbolo = conjuntosFirst.get(simbolos[i]).contains("&");
                i++;
            }
        }
        simbolosVisitados.remove(simbolo);

        if (!restante.isEmpty()) {
            return possuiRecursaoAEsquerda(simbolosVisitados, restante.get(0), conjuntosFirst, restante);
        }

        return false;
    }

    private boolean isSimboloTerminal(String simbolo) {
        if (simbolo == null || simbolo.length() == 0) {
            return false;
        }
        return simbolo.equals("&") || !(Character.isLetter(simbolo.charAt(0)) && Character.isUpperCase(simbolo.charAt(0)));
    }

    public Map<String, Set<String>> obterConjuntosFirst() {

        Map<String, Set<String>> conjuntosFirst = new HashMap<String, Set<String>>();

        obterFirstDosSimbolosTerminais(conjuntosFirst);

        adicionarSimbolosDiretos(conjuntosFirst);

        adicionarSimbolosIndiretos(conjuntosFirst);

        return conjuntosFirst;
    }

    private void obterFirstDosSimbolosTerminais(Map<String, Set<String>> conjuntosFirst) {
        for (String symbol : simbolosTerminais) {
            Set<String> conjuntoFirstDoSimbolo = new HashSet<String>();
            conjuntoFirstDoSimbolo.add(symbol);
            conjuntosFirst.put(symbol, conjuntoFirstDoSimbolo);
        }
    }

    private void adicionarSimbolosDiretos(Map<String, Set<String>> conjuntosFirst) {
        for (String key : simbolosNaoTerminais) {
            conjuntosFirst.put(key, new HashSet<String>());
        }

        for (String simbolo : producoes.keySet()) {
            for (String producao : producoes.get(simbolo)) {
                String primeiroSimbolo = producao.split(" ")[0];
                if (isSimboloTerminal(primeiroSimbolo)) {
                    conjuntosFirst.get(simbolo).add(primeiroSimbolo);
                }
            }
        }
    }

    private void adicionarSimbolosIndiretos(Map<String, Set<String>> conjuntosFirst) {
        for (String simbolo : producoes.keySet()) {
            for (String producao : producoes.get(simbolo)) {
                if (!isSimboloTerminal(producao.split(" ")[0])) {
                    int index = 0;
                    boolean continua = true;
                    String[] simbolos = producao.split(" ");
                    while (continua) {
                        if (simbolos.length <= index) {
                            conjuntosFirst.get(simbolo).add("&");
                            continua = false;
                        } else {
                            Set<String> firstDireita = conjuntosFirst.get(simbolos[index]);
                            if (firstDireita.contains("&")) {
                                Set<String> copia = new HashSet<String>(firstDireita);
                                copia.remove("&");
                                conjuntosFirst.get(simbolo).addAll(copia);
                                continua = true;
                                index++;
                            } else {
                                conjuntosFirst.get(simbolo).addAll(firstDireita);
                                continua = false;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isFatorada() {
        Map<String, Set<String>> conjuntosFirst = obterConjuntosFirst();

        for (String naoTerminal : simbolosNaoTerminais) {
            HashSet<String> simbolosGerados = new HashSet<String>();

            for (String producao : producoes.get(naoTerminal)) {
                String[] simbolos = producao.split(" ");
                HashSet<String> conjuntoFirstDaProducao = new HashSet<String>(Arrays.asList("&"));
                int i = 0;

                while (i < simbolos.length && conjuntoFirstDaProducao.contains("&")) {
                    if (!simbolos[i].equals("&")) {
                        conjuntoFirstDaProducao.remove("&");
                        conjuntoFirstDaProducao.addAll(conjuntosFirst.get(simbolos[i]));
                    }
                    i++;
                }

                if (!interseccaoVazia(simbolosGerados, conjuntoFirstDaProducao)) {
                    return false;
                }

                simbolosGerados.addAll(conjuntoFirstDaProducao);
            }
        }

        return true;
    }

    private boolean interseccaoVazia(Set<String> simbolosGerados, Set<String> conjuntoFirstDaProducao) {
        Set<String> interseccao = new HashSet<String>(conjuntoFirstDaProducao);
        interseccao.retainAll(simbolosGerados);
        return interseccao.isEmpty();
    }

    public Map<String, Set<String>> obterConjuntosFollow() {
        Map<String, Set<String>> conjuntosFirst = obterConjuntosFirst();
        Map<String, Set<String>> conjuntosFollow = new HashMap<String, Set<String>>();

        for (String key : simbolosNaoTerminais) {
            conjuntosFollow.put(key, new HashSet<String>());
        }

        conjuntosFollow.get(simboloInicial).add("$");

        adicionarFollowsDiretos(conjuntosFollow, conjuntosFirst);

        adicionarFollowsIndiretos(conjuntosFollow, conjuntosFirst);

        return conjuntosFollow;
    }

    private void adicionarFollowsDiretos(Map<String, Set<String>> conjuntosFollow, Map<String, Set<String>> conjuntosFirst) {
        for (String simbolo : producoes.keySet()) {
            for (String producao : producoes.get(simbolo)) {
                String[] simbolos = producao.split(" ");

                for (int i = 0; i < simbolos.length - 1; i++) {

                    int j = i + 1;
                    Set<String> firstDeJ;

                    if (!isSimboloTerminal(simbolos[i])) {
                        do {
                            Set<String> followDeI = conjuntosFollow.get(simbolos[i]);
                            firstDeJ = conjuntosFirst.get(simbolos[j]);
                            followDeI.addAll(firstDeJ);
                            followDeI.remove("&");
                            j++;
                        } while (firstDeJ.contains("&") && j < simbolos.length);
                    }
                }
            }
        }
    }

    private void adicionarFollowsIndiretos(Map<String, Set<String>> conjuntosFollow, Map<String, Set<String>> conjuntosFirst) {
        Map<String, Set<String>> conjuntosFollowAnterior = conjuntosFollow;
        boolean conjuntosIguais = false;

        while (!conjuntosIguais) {

            for (String simbolo : producoes.keySet()) {
                for (String producao : producoes.get(simbolo)) {
                    String[] simbolos = producao.split(" ");

                    int i = simbolos.length - 1;
                    boolean contemEpson = true;

                    while (i >= 0 && !isSimboloTerminal(simbolos[i]) && contemEpson) {
                        if (conjuntosFollow.containsKey(simbolos[i])) {
                            conjuntosFollow.get(simbolos[i]).addAll(conjuntosFollow.get(simbolo));
                            contemEpson = conjuntosFirst.get(simbolos[i]).contains("&");
                        }
                        i--;
                    }
                }
            }
            conjuntosIguais = conjuntosFollowAnterior.equals(conjuntosFollow);
            conjuntosFollowAnterior = conjuntosFollow;
        }
    }

    public boolean interseccaoEntreFirstEFollowVazia() {
        Map<String, Set<String>> conjuntosFirst = obterConjuntosFirst();
        Map<String, Set<String>> conjuntosFollow = obterConjuntosFollow();

        for (String naoTerminal : simbolosNaoTerminais) {
            Set<String> firstAuxiliar = conjuntosFirst.get(naoTerminal);
            Set<String> followAuxiliar = conjuntosFollow.get(naoTerminal);
            followAuxiliar.retainAll(firstAuxiliar);

            if (firstAuxiliar.contains("&") && !followAuxiliar.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private boolean verificarSeLL1() {
        return isFatorada() && !possuiRecursaoAEsquerda(new HashSet<String>(), simboloInicial, obterConjuntosFirst(), new ArrayList<String>(simbolosNaoTerminais)) && interseccaoEntreFirstEFollowVazia();
    }

}
