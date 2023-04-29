package es.ceu.gisi.modcomp.cyk_algorithm.algorithm;

import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.exceptions.CYKAlgorithmException;
import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.interfaces.CYKAlgorithmInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.lang.Character;
import java.util.HashSet;
import java.util.Set;
import java.util.*;
import java.lang.Iterable;





/**
 * Esta clase contiene la implementación de la interfaz CYKAlgorithmInterface
 * que establece los métodos necesarios para el correcto funcionamiento del
 * proyecto de programación de la asignatura Modelos de Computación.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class CYKAlgorithm implements CYKAlgorithmInterface {

private List<Character> letrasNTerminales = new ArrayList<>(); /* Para que puede ser utilizada en todos los métodos. */
private List<Character> letrasTerminales  = new ArrayList<>();
private Map<Character, List<String>> producciones = new HashMap<>();
private char eInicial;
char terminal;
char nonterminal;
   
@Override
    /**
     * Método que añade los elementos no terminales de la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     * @throws CYKAlgorithmException Si el elemento no es una letra mayúscula.
     */
    public void addNonTerminal(char nonterminal) throws CYKAlgorithmException {
        //List<Character> letrasNTerminales = new ArrayList<>(); // Lista que almacena las letras existentes
          

if (letrasNTerminales.contains(nonterminal)) {
    System.out.println("La letra " + nonterminal + " ya existe");
}else{
    letrasNTerminales.add(nonterminal);
    
}
        if((nonterminal>='A') && (nonterminal<='Z')){ // el valor de nonterminal es una letra mayúscula del alfabeto inglés
          
        }else{
           
        //throw new CYKAlgorithmException("No es un elemento no terminal.");
        throw new CYKAlgorithmException();
    }
    }

    @Override
    /**
     * Método que añade los elementos terminales de la gramática.
     *
     * @param terminal Por ejemplo, 'a'
     * @throws CYKAlgorithmException Si el elemento no es una letra minúscula.
     */
    
    public void addTerminal(char terminal) throws CYKAlgorithmException {
    if (!Character.isLowerCase(terminal)) {
        throw new CYKAlgorithmException();
    }
    if (letrasTerminales.contains(terminal)) {
        throw new CYKAlgorithmException();
    }
    letrasTerminales.add(terminal);
}


     

    @Override
    /**
     * Método que indica, de los elementos no terminales, cuál es el axioma de
     * la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     * @throws CYKAlgorithmException Si el elemento insertado no forma parte del
     * conjunto de elementos no terminales.
     */
    public void setStartSymbol(char nonterminal) throws CYKAlgorithmException {
    //char eInicial;
       // if ((letrasNTerminales.isEmpty) && (letrasNTerminales.add())){
      
    if (letrasNTerminales.contains(nonterminal)){
             
    this.eInicial = nonterminal; //al elemento inicial actual lo inicializa como no terminal
     
        if (!letrasNTerminales.isEmpty()) {
        this.eInicial = letrasNTerminales.get(0);
        }
    }else{
        throw new CYKAlgorithmException();
    }
    }   

    @Override
    /**
     * Método utilizado para construir la gramática. Admite producciones en FNC,
     * es decir de tipo A::=BC o A::=a
     *
     * @param nonterminal A
     * @param production "BC" o "a"
     * @throws CYKAlgorithmException Si la producción no se ajusta a FNC o está
     * compuesta por elementos (terminales o no terminales) no definidos
     * previamente.
     */
    
    /*public void addProduction(char nonterminal, String production) throws CYKAlgorithmException {
    if (!letrasNTerminales.contains(nonterminal)) {
        throw new CYKAlgorithmException();
    }
    Production p = new Production(nonterminal, production);
    if (production.containsKey(nonterminal)) {
        List<Production> nonterminalProductions = productions.get(nonterminal);
        nonterminalProductions.add(p);
    } else {
        List<Production> nonterminalProductions = new ArrayList<>();
        nonterminalProductions.add(p);
        productions.put(nonterminal, nonterminalProductions);
    }
    */


    public void addProduction(char nonterminal, String production) throws CYKAlgorithmException {
    
      //Map<Character, List<String>> producciones = new HashMap<>();// Character almacena la cabeza de la producción

     if (producciones.containsKey(nonterminal)) { 
    List<String> produccionesNT = producciones.get(nonterminal);
    produccionesNT.add(production);
} else {
    List<String> produccionesNT = new ArrayList<>();
    produccionesNT.add(production); //se añaden las derivaciones
    producciones.put(nonterminal, produccionesNT); //le asocia al no terminal  su producción
     }
        
     
    
    
    
    
    /* public void addProduction(char nonterminal, String production) throws CYKAlgorithmException {
    Map<Character, Set<String>> producciones = new HashMap<>();
    
    if (producciones.containsKey(nonterminal)) { 
        Set<String> produccionesNT = producciones.get(nonterminal);
        produccionesNT.add(production);
    } else {
        Set<String> produccionesNT = new HashSet<>();
        produccionesNT.add(production);
        producciones.put(nonterminal, produccionesNT);
    }
    */
    // Resto del código


    
   /* if (production.length() == 1 && Character.isLowerCase(production.charAt(0))) {
        // Si la producción es una letra minúscula
  
        
    } else if (production.length() == 2 && Character.isUpperCase(production.charAt(0)) && Character.isUpperCase(production.charAt(1))) {
        // Si la producción son dos letras mayúsculas
        
   
    } else {
        // Si la producción no cumple con las restricciones
        throw new CYKAlgorithmException();
    } */

  
        throw new CYKAlgorithmException();
    }

    @Override
    /**
     * Método que indica si una palabra pertenece al lenguaje generado por la
     * gramática que se ha introducido.
     *
     * @param word La palabra a verificar, tiene que estar formada sólo por
     * elementos no terminales.
     * @return TRUE si la palabra pertenece, FALSE en caso contrario
     * @throws CYKAlgorithmException Si la palabra proporcionada no está formada
     * sólo por terminales, si está formada por terminales que no pertenecen al
     * conjunto de terminales definido para la gramática introducida, si la
     * gramática es vacía o si el autómata carece de axioma.
     */
    public boolean isDerived(String word) throws CYKAlgorithmException {
        for (int i = 0; i < word.length(); i++) {
        if (Character.isUpperCase(word.charAt(i))) {
            
        return true;
        
        }else{
        return false;
    }
    }
        
        throw new CYKAlgorithmException();
    }
  
    /*public boolean isDerived(String word) throws CYKAlgorithmException {
    // Verificar que la palabra está formada sólo por elementos no terminales
    for (char c : word.toCharArray()) {
        if (!producciones.containsKey(c)) {
            throw new CYKAlgorithmException("La palabra no está formada sólo por elementos no terminales");
        }
    }
    
    while (word.contains(nonterminal)) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (producciones.containsKey(c)) {
                Set<String> produccionesNT = producciones.get(c);
                for (String prod : produccionesNT) {
                    String newWord = word.substring(0, i) + prod + word.substring(i + 1);
                    if (newWord.length() == 1) {
                        return producciones.containsKey(newWord.charAt(0));
                    }
                    if (isDerived(newWord)) {
                        return true;
                    }
                }
            }
        }
        break;
    }
    return false;
}
*/


    @Override
    /**
     * Método que, para una palabra, devuelve un String que contiene todas las
     * celdas calculadas por el algoritmo (la visualización debe ser similar al
     * ejemplo proporcionado en el PDF que contiene el paso a paso del
     * algoritmo).
     *
     * @param word La palabra a verificar, tiene que estar formada sólo por
     * elementos no terminales.
     * @return Un String donde se vea la tabla calculada de manera completa,
     * todas las celdas que ha calculado el algoritmo.
     * @throws CYKAlgorithmException Si la palabra proporcionada no está formada
     * sólo por terminales, si está formada por terminales que no pertenecen al
     * conjunto de terminales definido para la gramática introducida, si la
     * gramática es vacía o si el autómata carece de axioma.
     */
public String algorithmStateToString(String word) throws CYKAlgorithmException {
    // Comprobar que la gramática ha sido definida
    if (producciones.isEmpty() || eInicial == '\0') { //valor por defecto de un char
        throw new CYKAlgorithmException();
    }

    int n = word.length();

    // Crear la matriz de valores
    Set<String>[][] values = new Set[n][n];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            values[i][j] = new HashSet<>();
        }
    }

    // Llenar la diagonal principal con los símbolos terminales correspondientes
    for (int i = 0; i < n; i++) {
        char c = word.charAt(i);
        List<String> produccionesC = producciones.get(c);
        if (produccionesC != null) {
            values[i][i].addAll(produccionesC);
        }
    }

    // Calcular los valores para las demás celdas
    for (int l = 2; l <= n; l++) { // Tamaño de la subcadena actual
        for (int i = 0; i <= n - l; i++) { // Posición inicial de la subcadena actual
            int j = i + l - 1; // Posición final de la subcadena actual
            for (int k = i; k < j; k++) { // División de la subcadena actual en dos partes
                Set<String> leftValues = values[i][k];
                Set<String> rightValues = values[k+1][j];
                for (String leftValue : leftValues) {
                    for (String rightValue : rightValues) {
                        String concatValue = leftValue + rightValue;
                        List<String> produccionesConcat = producciones.get(concatValue);
                        if (produccionesConcat != null) {
                            values[i][j].addAll(produccionesConcat);
                        }
                    }
                }
            }
        }
    }

    // Construir la representación en cadena de la matriz de valores
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n - i; j++) {
            sb.append("(").append(i).append(",").append(i+j).append("): ");
            Set<String> cellValues = values[i][i+j];
            if (cellValues.isEmpty()) {
                sb.append("-\n");
            } else {
                sb.append(String.join(", ", cellValues)).append("\n");
            }
        }
    }
    return sb.toString();
}


    /*public String algorithmStateToString(String word) throws CYKAlgorithmException {
    try {  
        // Verificar que la palabra sea válida
        for (int i = 0; i < word.length(); i++) {
            if (Character.isLowerCase(word.charAt(i))) {
                throw new CYKAlgorithmException();
            }
        }

        // Inicializar la tabla
        Map<Map.Entry<Integer, Integer>, Set<String>> table = new HashMap<>(); // par i,j que producen un conjunto
        for (int i = 0; i < word.length(); i++) {
            table.put(Map.entry(i, i), new HashSet<>());
        }

        // Calcular los valores de cada celda en la tabla
        for (int j = 1; j < word.length(); j++) {
            for (int i = 0; i < word.length() - j; i++) {
                Set<String> cellValues = new HashSet<>();
                for (int k = i; k < i + j; k++) {
                    Set<String> leftValues = table.get(Map.entry(i, k));
                    Set<String> rightValues = table.get(Map.entry(k + 1, i + j));
                    for (String leftValue : leftValues) {
                        for (String rightValue : rightValues) {
                            //for (Production production : grammar.getProductions()) {
                                if (production.getRight().equals(leftValue + rightValue)) {
                                    cellValues.add(production.getLeft());
                                }
                            }
                        }
                    }
                }
                table.put(Map.entry(i, i + j), cellValues);
            }
        

        // Construir el String que muestra la tabla calculada
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < word.length(); j++) {
            for (int i = 0; i < word.length() - j; i++) {
                Set<String> cellValues = table.get(Map.entry(i, i + j));
                sb.append(String.format("[%d,%d]: ", i, i + j));
                if (cellValues.isEmpty()) {
                    sb.append("{}");
                } else {
                    sb.append(String.join(",", cellValues));
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    } catch (CYKAlgorithmException e) {
        throw new CYKAlgorithmException();
    }
}
*/
    @Override
    /**
     * Elimina todos los elementos que se han introducido hasta el momento en la
     * gramática (elementos terminales, no terminales, axioma y producciones),
     * dejando el algoritmo listo para volver a insertar una gramática nueva.
     */
    public void removeGrammar() {
   
    //terminal.clear(); Si las paso a string si podría usarlo
    //nonterminal.clear();
    //productions.clear();
    terminal = '\0';
   // axioms.clear();
   //eInicial.clear();
    
   nonterminal = '\0';
   eInicial = '\0';


        //throw new CYKAlgorithmException();
    }

    @Override
    /**
     * Devuelve un String que representa todas las producciones que han sido
     * agregadas a un elemento no terminal.
     *
     * @param nonterminal
     * @return Devuelve un String donde se indica, el elemento no terminal, el
     * símbolo de producción "::=" y las producciones agregadas separadas, única
     * y exclusivamente por una barra '|' (no incluya ningún espacio). Por
     * ejemplo, si se piden las producciones del elemento 'S', el String de
     * salida podría ser: "S::=AB|BC".
     */
    public String getProductions(char nonterminal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    /**
     * Devuelve un String con la gramática completa.
     *
     * @return Devuelve el agregado de hacer getProductions sobre todos los
     * elementos no terminales.
     */
    public String getGrammar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
