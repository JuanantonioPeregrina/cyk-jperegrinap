package es.ceu.gisi.modcomp.cyk_algorithm.algorithm;

import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.exceptions.CYKAlgorithmException;
import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.interfaces.CYKAlgorithmInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



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
private char eInicial;
   
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
       //List<Character> letrasTerminales  = new ArrayList<>();
         if (terminal >= 'a' && terminal <= 'z') {
             
        letrasTerminales.add(terminal);
} else {
    //throw new CYKAlgorithmException("Símbolo inválido: " + terminal);
    throw new CYKAlgorithmException();
}
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
    
     /* Map<Character, List<String>> producciones = new HashMap<>();// Character almacena la cabeza de la producción

     if (producciones.containsKey(nonterminal)) { 
    List<String> produccionesNT = producciones.get(nonterminal);
    produccionesNT.add(production);
} else {
    List<String> produccionesNT = new ArrayList<>();
    produccionesNT.add(production); //se añaden las derivaciones
    producciones.put(nonterminal, produccionesNT); //traspasa el no terminal */
}
     
     public void addProduction(char nonterminal, String production) throws CYKAlgorithmException {
    Map<Character, Set<String>> producciones = new HashMap<>();
    
    if (producciones.containsKey(nonterminal)) { 
        Set<String> produccionesNT = producciones.get(nonterminal);
        produccionesNT.add(production);
    } else {
        Set<String> produccionesNT = new HashSet<>();
        produccionesNT.add(production);
        producciones.put(nonterminal, produccionesNT);
    }
    
    // Resto del código
}

    
   /* if (production.length() == 1 && Character.isLowerCase(production.charAt(0))) {
        // Si la producción es una letra minúscula
  
        
    } else if (production.length() == 2 && Character.isUpperCase(production.charAt(0)) && Character.isUpperCase(production.charAt(1))) {
        // Si la producción son dos letras mayúsculas
        
   
    } else {
        // Si la producción no cumple con las restricciones
        throw new CYKAlgorithmException();
    } */
}
  
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
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    /**
     * Elimina todos los elementos que se han introducido hasta el momento en la
     * gramática (elementos terminales, no terminales, axioma y producciones),
     * dejando el algoritmo listo para volver a insertar una gramática nueva.
     */
    public void removeGrammar() {
        throw new UnsupportedOperationException("Not supported yet.");
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
