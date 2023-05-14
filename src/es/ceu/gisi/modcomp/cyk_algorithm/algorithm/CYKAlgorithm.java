package es.ceu.gisi.modcomp.cyk_algorithm.algorithm;
import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.exceptions.CYKAlgorithmException;
import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.interfaces.CYKAlgorithmInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Esta clase contiene la implementación de la interfaz CYKAlgorithmInterface
 * que establece los métodos necesarios para el correcto funcionamiento del
 * proyecto de programación de la asignatura Modelos de Computación.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */

public class CYKAlgorithm implements CYKAlgorithmInterface {

private final List<Character> letrasNTerminales = new ArrayList<>(); /* Para que puede ser utilizada en todos los métodos. */
private final List<Character> letrasTerminales  = new ArrayList<>();
//Almacena todas las producciones del lenguaje
private final Map<Character, List<String>> producciones = new HashMap<>();
private char eInicial; //axioma

   
    @Override
    /**
     * Método que añade los elementos no terminales de la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     * @throws CYKAlgorithmException Si el elemento no es una letra mayúscula o
     * si ya está en el conjunto.
     */
     
    public void addNonTerminal(char nonterminal) throws CYKAlgorithmException {
        
        //Si es una letra mayúscula del alfabeto inglés
        if((nonterminal>='A') && (nonterminal<='Z')){ 
          if(!letrasNTerminales.contains(nonterminal)){ //si la lista de NT no contiene elementos  
              letrasNTerminales.add(nonterminal); // añade en la lista
          
            }else{ 
                throw new CYKAlgorithmException(); //lanza excepción
            }
        }else{ //sino es mayúscula 
                
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
    
    if (!Character.isLowerCase(terminal)) { //Cuando no es minúscula la letra 
        throw new CYKAlgorithmException(); //lanza excepción
    }
    
    // Si aún no se ha agregado anteriormente salta excepción
    if (letrasTerminales.contains(terminal)) { 
        throw new CYKAlgorithmException();
    }
    letrasTerminales.add(terminal); //se añade si se cumplen las condiciones
    
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
      
    if (letrasNTerminales.contains(nonterminal)){ // si existen NT en la lista
             
    this.eInicial = nonterminal; //el elem inicial(axioma) se inicializa como NT
     
        if (!letrasNTerminales.isEmpty()) {  //si la lista no está vacía
        
        //le asigna el valor de la primera posición 
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
        char symbol = production.charAt(i); //asigna el char del índice
       
        // Si no hay símbolos en las listas se produce una excepción
        if (!letrasTerminales.contains(symbol) && !letrasNTerminales.contains(symbol)) {
            throw new CYKAlgorithmException();
        }
    }
    
    //Si la lista de NT no contiene elem de ese tipo se lanza excepción
    if (!letrasNTerminales.contains(nonterminal)) { 
        throw new CYKAlgorithmException();
    }
    
    //  Si ya hay una una producción creada
    if (producciones.containsKey(nonterminal) && producciones.get(nonterminal).contains(production)) {
        throw new CYKAlgorithmException(); 
     
    }
    
    //Si su longitud corresponde a 1 y no hay un elemento en la primera posición
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
    // Sino hay entrada para la clave le asigna una lista vacía
    producciones.putIfAbsent(nonterminal, new ArrayList<>()); 
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
   
    if (eInicial == '\0') {  //si el axioma tiene su valor por defecto (nulo)
        throw new CYKAlgorithmException();
    }

    if (word == null || word.isEmpty()) { //Si la pal es vacía o no es válida
        throw new CYKAlgorithmException();
    }   
    
    if (word.contains(" ")) { //Si existen espacios en blanco
        throw new CYKAlgorithmException();
    }

    for (char c : word.toCharArray()) { //mapea el String(word) en un array char
       
    //Asegurar elementos terminales en la palabra y que no haya espacios en mitad
        if (!letrasTerminales.contains(c) && !Character.isWhitespace(c)) { 
            throw new CYKAlgorithmException();
        }
    }
   
    if (producciones.isEmpty()) {
        throw new CYKAlgorithmException();
    }

    int n = word.length();

    // Crea una matriz NxN a través de una lista dentro de otra de conjuntos 
    List<List<Set<Character>>> matriz = new ArrayList<>();
    for (int i = 0; i < n; i++) {  //n filas hasta long de la palabra
        matriz.add(new ArrayList<>()); //añade una lista lista vacía
        for (int j = 0; j < n; j++) { //columnas hasta n
            matriz.get(i).add(new HashSet<>()); // para ese i le añade un conj
        }
    }

    // Inicializa la diagonal de la matriz con los no terminales que generan cada símbolo de la palabra
    for (int i = 0; i < n; i++) {
        char c = word.charAt(i);
        for (char nt : letrasNTerminales) {
        // Lista temporal para almacenar las producciones de un nt del mapa
            List<String> prodList = producciones.get(nt);
            for (String prod : prodList) {
                //  si el único caracter que la compone es igual a c
                if (prod.length() == 1 && prod.charAt(0) == c) {
                    matriz.get(i).get(i).add(nt); //en la diagonal se añade si se deriva 
                }
            }
        }
    }

    // Calculamos las celdas de la matriz utilizando las celdas anteriores
    for (int p = 2; p <= n; p++) { //subcadenas de tamaño 2 menores que el tam de la palabra
        for (int i = 0; i <= n - p; i++) { // inicio de la subcadena
            int j = i + p - 1; // final de la subcadena
            for (int k = i; k < j; k++) { // se divide la subcadena en 2 partes 
               
    //Conjunto de NT que pueden generar la 1ra parte de la subcadena desde 
                Set<Character> conj1 = matriz.get(i).get(k); //i hasta k
               
    // Genera la segunda parte de la subcadena
                Set<Character> conj2 = matriz.get(k + 1).get(j); 
               
                for (Character nt : letrasNTerminales) {
                    List<String> prodList = producciones.get(nt);
                    for (String prod : prodList) {
    //Si la cadena es de long 2 tiene un simbolo de conj1 seguido por uno de conj2
                        if (prod.length() == 2 && conj1.contains(prod.charAt(0)) && conj2.contains(prod.charAt(1))) {
                            matriz.get(i).get(j).add(nt); //añade si se cumple
                    }
                    }
                }
            }
        }
    }

    // Comprueba si la cadena está generada por el axioma
    for (int i = 0; i < n; i++) {
        if (matriz.get(0).get(i).contains(eInicial)) {
            return true;
        }
    }
    
    return false;
} 
 
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
       
    // Si la tabla está vacía o el axioma es nulo
    if (producciones.isEmpty() || eInicial == '\0') { //valor estándar de 1 char
        throw new CYKAlgorithmException();  //lanza excepción
    }

    int n = word.length();

   // Crea una matriz de valores
    Set<String>[][] valores = new Set[n][n]; //n filas y n columnas
    
    for (int i = 0; i < n; i++) { //fila hasta el número de letras de la palabra
        for (int j = 0; j < n; j++) { //columna
            valores[i][j] = new HashSet<>(); // se crea new objeto en cada celda
        }                                       // para que tenga {}
    }

    // Calcular los valores para las celdas debajo de la diagonal
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < n - i; j++) {
            int fila = i + j; // indicar la última fila 
            for (int k = j; k < fila; k++) {
    
    //Se divide en 2 subcadenas, izquierda y derecha y luego se concatenan
                Set<String> valIzq = valores[j][k];
                Set<String> valDer = valores[k+1][fila]; 
               
                for (String leftValue : valIzq) {
                    for (String rightValue : valDer) {
                        String concatValue = leftValue + rightValue;
                        //Lista temporal para concatenar las subcadenas 
                        List<String> produccionesConcat = producciones.get(concatValue);
                        if (produccionesConcat != null) {
                            valores[j][fila].addAll(produccionesConcat);
                        }
                    }
                }
            }
        }
    }

    // Inicializa la diagonal de la matriz con las producciones que genera el sym
    for (int i = 0; i < n; i++) { 
        char c = word.charAt(i); // se recorre la palabra letra x letra
    
        //Si hay producciones para ese char, se añaden al conjunto de valores en la posición 
        List<String> produccionesC = producciones.get(c);
        if (produccionesC != null) {
            valores[i][i].addAll(produccionesC);
        }
    } 

    // Construye la representación en cadena de la matriz de valores
    StringBuilder sb = new StringBuilder(); // crea el objeto StringBuilder
    for (int i = n - 1; i >= 0; i--) {
        for (int j = 0; j < n - i; j++) {
            int fila = j;
            int columna = i + j;
            sb.append("(").append(fila).append(",").append(columna).append("): ");
            
    // Conjunto que se asocia a la matriz de valores 
    Set<String> cellValues = valores[fila][columna]; 
    
    //Para cada celda comprueba si el conjunto de valores es vacío o no
            if (cellValues.isEmpty()) {
                sb.append("-\n");
            } else { //concatena los elementos de la colección en una cadena
                sb.append(String.join(",", cellValues)).append("\n");
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
    
    letrasNTerminales.clear(); //vacía las listas y el mapa llamando ese método
    letrasTerminales.clear();
    producciones.clear();
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
    
    //Cuando para un no terminal no hay asociación
    if (productions == null) {
        return ""; //devuelve cadena vacía
    }
    String productionsString = productions.stream().collect(Collectors.joining("|"));
    
    //Si es cierto no devuelve nada y sino las producciones con su nomenclatura
    return productionsString.isEmpty() ? "" : nonterminal + "::=" + productionsString; 
}
    
    @Override
    /**
     * Devuelve un String con la gramática completa.
     *
     * @return Devuelve el agregado de hacer getProductions sobre todos los
     * elementos no terminales.
     */
    public String getGrammar() {
    
    //Construye una representación de la gramática en formato de cadena
    StringBuilder grammar = new StringBuilder();// 

    for (char nt : letrasNTerminales) {
    
    //El this hace referencia al atributo "producciones" de la clase actual
       List<String> producciones = this.producciones.get(nt); 
        for (String p : producciones) { //agrega una nueva línea después de cada producción
            grammar.append(nt).append(" -> ").append(p).append(System.lineSeparator());
        }
    }

    return grammar.toString();
}
}
