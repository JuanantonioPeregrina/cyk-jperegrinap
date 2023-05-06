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
import java.util.stream.Collectors;





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

        if((nonterminal>='A') && (nonterminal<='Z')){ // el valor de nonterminal es una letra mayúscula del alfabeto inglés
          if(!letrasNTerminales.contains(nonterminal)){
              letrasNTerminales.add(nonterminal);
          
            }else{
                throw new CYKAlgorithmException();
            }
        }else{
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
    public void addProduction(char nonterminal, String production) throws CYKAlgorithmException {
    for (int i = 0; i < production.length(); i++) {  
        char symbol = production.charAt(i);
        if (!letrasTerminales.contains(symbol) && !letrasNTerminales.contains(symbol)) {
            throw new CYKAlgorithmException();
        }
    }
    if (!letrasNTerminales.contains(nonterminal)) {
        throw new CYKAlgorithmException();
    }
    if (producciones.containsKey(nonterminal)) {
        List<String> currentProductions = producciones.get(nonterminal);
        for (String currentProduction : currentProductions) { //currentProduction= currentProductions.get(i)
            if (currentProduction.equals(production)) {
                throw new CYKAlgorithmException(); // ya existe
            }
            
        }
    }
     
    
    if (production.length() == 1) {
        if (!letrasTerminales.contains(production.charAt(0))) {
            throw new CYKAlgorithmException();
        }
    } else if (production.length() == 2) {
        if (!letrasNTerminales.contains(production.charAt(0)) || !letrasNTerminales.contains(production.charAt(1))) {
            throw new CYKAlgorithmException();
        }
    } else {
        throw new CYKAlgorithmException();
    }
    producciones.putIfAbsent(nonterminal, new ArrayList<>()); // sino hay entrada para la clave
    producciones.get(nonterminal).add(production);
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
    // Comprueba si la palabra es derivada
    if (eInicial == '\0' || producciones.isEmpty()) {
        throw new CYKAlgorithmException();
    }

    if (word == null || word.isEmpty() || word.contains(" ")) {
        throw new CYKAlgorithmException();
    }

    for (char c : word.toCharArray()) {
        if (!letrasTerminales.contains(c)) {
            throw new CYKAlgorithmException();
        }
    }

    int n = word.length();

    List<List<Set<Character>>> matriz = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        matriz.add(new ArrayList<>());
        for (int j = 0; j < n; j++) {
            matriz.get(i).add(new HashSet<>());
        }
    }

    for (int i = 0; i < n; i++) {
        char c = word.charAt(i);
        for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {
            char nt = entry.getKey();
            List<String> prodList = entry.getValue();
            for (String prod : prodList) {
                if (prod.length() == 1 && prod.charAt(0) == c) {
                    matriz.get(i).get(i).add(nt);
                }
            }
        }
    }

    for (int l = 2; l <= n; l++) {
        for (int i = 0; i <= n - l; i++) {
            int j = i + l - 1;
            for (int k = i; k < j; k++) {
                Set<Character> set1 = matriz.get(i).get(k);
                Set<Character> set2 = matriz.get(k + 1).get(j);
                for (Character nt : producciones.keySet()) {
                    List<String> prodList = producciones.get(nt);
                    for (String prod : prodList) {
                        if (prod.length() == 2 && set1.contains(prod.charAt(0)) && set2.contains(prod.charAt(1))) {
                            matriz.get(i).get(j).add(nt);
                        }
                    }
                }
            }
        }
    }

    Set<Character> startSet = matriz.get(0).get(n-1);

    if (startSet == null || startSet.isEmpty()) {
        return false;
    }

    if (!startSet.contains(eInicial)) {
        return false;
    }

    return true;
}


   /* if (eInicial == '\0') {
        throw new CYKAlgorithmException();
    }

    if (word == null || word.isEmpty()) {
        throw new CYKAlgorithmException();
    }

    
if (word.contains(" ")) {
    throw new CYKAlgorithmException();
}

   for (char c : word.toCharArray()) {
     if (!letrasTerminales.contains(c) && !Character.isWhitespace(c)) { //asegurar elementos terminales en la palabra
        throw new CYKAlgorithmException();
    }
}


    if (producciones.isEmpty()) {
        throw new CYKAlgorithmException();
    }

    int n = word.length();

    // Creamos una matriz nxn para el algoritmo CYK
    List<List<Set<Character>>> matriz = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        matriz.add(new ArrayList<>());
        for (int j = 0; j < n; j++) {
            matriz.get(i).add(new HashSet<>());
        }
    }

    // Inicializamos la diagonal de la matriz con los no terminales que generan cada símbolo
    for (int i = 0; i < n; i++) {
        char c = word.charAt(i);
        for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {
            char nt = entry.getKey();
            List<String> prodList = entry.getValue();
            for (String prod : prodList) {
                if (prod.length() == 1 && prod.charAt(0) == c) {
                    matriz.get(i).get(i).add(nt);
                }
            }
        }
    }

    // Calculamos las celdas de la matriz utilizando las celdas anteriores
    for (int l = 2; l <= n; l++) {
        for (int i = 0; i <= n - l; i++) {
            int j = i + l - 1;
            for (int k = i; k < j; k++) {
                Set<Character> set1 = matriz.get(i).get(k);
                Set<Character> set2 = matriz.get(k + 1).get(j);
                for (Character nt : producciones.keySet()) {
                    List<String> prodList = producciones.get(nt);
                    for (String prod : prodList) {
                        if (prod.length() == 2 && set1.contains(prod.charAt(0)) && set2.contains(prod.charAt(1))) {
                            matriz.get(i).get(j).add(nt);
                        }
                    }
                }
            }
        }
    }

    // Comprobamos si la cadena está generada por el no terminal inicial
    for (int i = 0; i < n; i++) {
        if (matriz.get(0).get(i).contains(eInicial)) {
            return true;
        }
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

    @Override
    /**
     * Elimina todos los elementos que se han introducido hasta el momento en la
     * gramática (elementos terminales, no terminales, axioma y producciones),
     * dejando el algoritmo listo para volver a insertar una gramática nueva.
     */
   
public void removeGrammar() {
    if (producciones.isEmpty()) {
        return;
    }

    for (char nonterminal : letrasNTerminales) {
        producciones.remove(nonterminal);
    }

    letrasNTerminales.clear();
    letrasTerminales.clear();
    producciones.clear();
//System.out.println("Producciones después de eliminar todas las producciones: " + producciones);
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
    List<String> productions = producciones.get(nonterminal);
    if (productions == null) {
        return "";
    }
    String productionsString = productions.stream().collect(Collectors.joining("|"));
    return productionsString.isEmpty() ? "" : nonterminal + "::=" + productionsString; //si es cierto nada y sino lo posterior
}
    
    @Override
    /**
     * Devuelve un String con la gramática completa.
     *
     * @return Devuelve el agregado de hacer getProductions sobre todos los
     * elementos no terminales.
     */
    public String getGrammar() {
        /*if (letrasNTerminales.isEmpty() || producciones.isEmpty() || eInicial == '\0') {
        throw new CYKAlgorithmException(); */
    
    StringBuilder grammar = new StringBuilder();

    for (char nt : letrasNTerminales) {
       List<String> producciones = this.producciones.get(nt);
        for (String p : producciones) {
            grammar.append(nt).append(" -> ").append(p).append(System.lineSeparator());
        }
    }

    return grammar.toString();
}
}
