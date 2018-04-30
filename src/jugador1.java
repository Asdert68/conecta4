/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alber
 */
public class jugador1 implements Jugador{
     /**
     * Implementa el moviment del jugador amb un tauler donat i un color de peça.
     * @param t  Tauler actual de joc
     * @param color  Color de la peça que possarà
     * @return Columna on fer el moviment
     */
    private int profunditat=6;
    private int colorP=0;
    
    private Integer InfinitPositiu = Integer.MAX_VALUE;
    
    jugador1(int i) {
        profunditat = i;
        
    }
    
    @Override
    public int moviment(Tauler t, int color){
        colorP=color;
        int coloca=0;
        int actualM=0;
        int ElMejor=0;
        
        for(int i=0;i<t.getMida();++i)
        {
            if(t.movpossible(i)){
                Tauler aux= new Tauler(t);
                aux.afegeix(i,color);
                if(aux.solucio(i,color)){
                    return i;
                }
                ElMejor=minimitzar(aux,-InfinitPositiu,InfinitPositiu,-color,profunditat);
                if(actualM<ElMejor || !t.movpossible(coloca)){
                    coloca=i;
                    actualM=ElMejor;
                }
            }
        

        }

        return coloca;
        
    }
    
    /**
     * Retorna el nom del jugador que es dona al crear-lo
     * @return Nom del jugador
     */
    public String nom(){
        
        return null;

    }

       /**
     * Minimitza el valor de la funcio alfabeta
     * @param t
     * @param alfa
     * @param beta
     * @param profunditat
     * @param jugador
     * @return el valor corresponent a minimitzar
     */
    private int minimitzar(Tauler t, int alfa, int beta, int profunditat, int jugador){
        if(profunditat == 0 || !t.espotmoure()) return evaluarTauler(t, jugador);
        for(int i = 0; i < t.getMida(); i++){
            if(t.movpossible(i)){
                Tauler aux = new Tauler(t);
                aux.afegeix(i, -jugador);
                if (aux.solucio(i, -jugador)) {
                    return -InfinitPositiu;
                }
                beta = Math.min(beta, maximitzar(aux, alfa, beta, profunditat-1, jugador));
                if(beta <= alfa) return beta;
            }
        }
        return beta;
    }

    /**
     * Maximitza el valor de la funcio alfabeta
     * @param t
     * @param alfa
     * @param beta
     * @param profunditat
     * @param jugador
     * @return el valor corresponent a minimitzar
     */
    private int maximitzar(Tauler t, int alfa, int beta, int profunditat, int jugador){
        if(profunditat == 0 || !t.espotmoure()) return evaluarTauler(t,jugador);
        for(int i = 0 ;i < t.getMida(); i++){
            if(t.movpossible(i)){
                Tauler aux = new Tauler (t);
                aux.afegeix(i, jugador);
                if(aux.solucio(i,jugador)) {
                    return InfinitPositiu;
                }
                alfa = Math.max(alfa, minimitzar(aux, alfa, beta, profunditat - 1, jugador));
                if(alfa >= beta) return alfa;
            }
        }
        return alfa;
    }


    /**
     * Evalua cada posicio del tauler i computa una heuristica
     * @param t
     * @param jugador
     * @return la heuristica
     */
    private int evaluarTauler(Tauler t, int jugador) {

        int heuristica = 0;
        for (int i = 0; i < t.getMida(); i++) {
            for (int j = 0; j < t.getMida(); j++) {
                heuristica += puntuarCasella(t, i, j, jugador);
            }
        }

        return heuristica;
    }
    
    private int puntuarCasella(Tauler t, int fil, int col, int jug) {
        int maximMenor = -1;
        int maximMajor = t.getMida();

        int jugador = t.getColor(fil, col);
        int color=-1;
        if(jug==jugador)color=1;
        int j;
        int contador, contadorBlancs;
        int heuristicaCasella = 0;

        //Mirar adalt -> Tests OK
        contador = 1;
        contadorBlancs = 0;
        for (int i = fil+1; i < maximMajor && i <= fil+3; i++) {
            if (t.getColor(i, col) == jugador) contador++;
            else{
                break;
            }
        }

        heuristicaCasella += color*calcularPuntuacio(contador, contadorBlancs);

        //Mirar diagonal adalt dreta -> Tests OK
        contador = 1;
        contadorBlancs = 0;
        j = col+1;
        for (int i = fil+1; i < maximMajor && j < maximMajor && i <= fil+3; i++) {
            if (t.getColor(i, j++) == jugador) contador++;
            else if (t.getColor(i, col) == 0){
                //mirar los espacios verticales para abajo
                contadorBlancs = espaisRestants(i,col,t);
            }
        }
        heuristicaCasella += color*calcularPuntuacio(contador, contadorBlancs);

        //Mirar dreta -> Tests OK
        contador = 1;
        contadorBlancs = 0;
        for (int i = col+1; i < maximMajor && i <= col+3; i++) {
            if (t.getColor(fil, i) == jugador) contador++;
            else if (t.getColor(i, col) == 0){
                //mirar los espacios verticales para abajo
                contadorBlancs = espaisRestants(i,col,t);
            }
        }

        heuristicaCasella += color*calcularPuntuacio(contador, contadorBlancs);

        //Mirar diagonal adalt esquerra -> Tests OK
        contador = 1;
        contadorBlancs = 0;
        j = col-1;
        for (int i = fil+1; i < maximMajor && j > maximMenor && i <= fil+3; i++) {
            if (t.getColor(i, j--) == jugador) contador++;
            else if (t.getColor(i, col) == 0){
                //mirar los espacios verticales para abajo
                contadorBlancs = espaisRestants(i,col,t);
            }
        }

        heuristicaCasella += color*calcularPuntuacio(contador, contadorBlancs);

        return heuristicaCasella;
    }

    
    
    
    int calcularPuntuacio(int puntuacio, int moviments){
        int puntuacioMoviments = 4 - moviments;
        if(puntuacio==0) return 0;
        else if(puntuacio==1) return 1*puntuacioMoviments;
        else if(puntuacio==2) return 10*puntuacioMoviments;
        else if(puntuacio==3) return 100*puntuacioMoviments;
        else return 1000;
    }
    
    private int espaisRestants(int fila, int col, Tauler t){
        int espais = 0;
        for(int i = 0; i < 4 && fila >= 0; ++i){
            if(t.getColor(fila,col) == 0){
                ++espais;
            } else break;

            fila -= i;
        }
        return espais;
    }
   
    
    
}
