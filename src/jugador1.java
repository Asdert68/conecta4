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
    
    private int profunditat=6;    
    private Integer InfinitPositiu = Integer.MAX_VALUE;
    
    jugador1(int i) {
        profunditat = i;
        
    }
    //Consisteix en comprobar la valoracio total del taulell un cop posada un fitxa al taulell, 
    //comprobant totes les columnes del taulell tant per al contrari com per a mi mateix, aixi 
    //sabre on posara la fitxa el meu contrari, y podre preveure si on ha posat la fitxa el pot fer guanyar
    //en cas de que posar la fitxa a una columna el faci guanyar retornare un valor molt desfavorable per tal de que
    //aquesta branca de decisions no sigui presa per la funcio moviment. 
    @Override
    public int moviment(Tauler t, int color){

        int coloca=0;
        int actualM=-InfinitPositiu;
        int ElMejor=0;
        
        for(int i=0;i<t.getMida();++i)
        {
            if(t.movpossible(i)){
                Tauler aux= new Tauler(t);
                aux.afegeix(i,color);
                if(aux.solucio(i,color)){
                    return i;
                }
                ElMejor=minim(aux,-InfinitPositiu,InfinitPositiu,profunditat,color);
                if(ElMejor>actualM || !t.movpossible(coloca)){
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
    private int minim(Tauler t, int alfa, int beta, int profunditat, int jugador){
        if(profunditat == 0 || !t.espotmoure()) return SumaT(t, jugador);
        for(int i = 0; i < t.getMida(); i++){
            if(t.movpossible(i)){
                Tauler aux = new Tauler(t);
                aux.afegeix(i, -jugador);
                if (aux.solucio(i, -jugador)) {
                    return -InfinitPositiu;
                }
                beta = Math.min(beta, maxim(aux, alfa, beta, profunditat-1, jugador));
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
    private int maxim(Tauler t, int alfa, int beta, int profunditat, int jugador){
        if(profunditat == 0 || !t.espotmoure()) return SumaT(t,jugador);
        for(int i = 0 ;i < t.getMida(); i++){
            if(t.movpossible(i)){
                Tauler aux = new Tauler (t);
                aux.afegeix(i, jugador);
                if(aux.solucio(i,jugador)) {
                    return InfinitPositiu;
                }
                alfa = Math.max(alfa, minim(aux, alfa, beta, profunditat - 1, jugador));
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
    private int SumaT(Tauler t, int jugador) {

        int suma = 0;
        for (int i = 0; i < t.getMida(); i++) {
            for (int j = 0; j < t.getMida(); j++) {
                suma += EvalPos(t, i, j, jugador);
            }
        }

        return suma;
    }
    
    private int EvalPos(Tauler t, int fil, int col, int Cjug) {

        int jugador = t.getColor(fil, col);
        int color=-1;
        if(Cjug==jugador)color=1;
        int j;
        int contador, contadorBlancs;
        int ValorCasella = 0;

        //Mirar adalt
        contador = 1;
        contadorBlancs = 0;
        for (int i = fil+1; i < t.getMida() && i <= fil+3; i++) {
            if (t.getColor(i, col) == jugador) contador++;
            else{
                break;
            }
        }

        ValorCasella += color*calcularPuntuacio(contador, contadorBlancs);

        //Mirar diagonal adalt dreta
        contador = 1;
        contadorBlancs = 0;
        j = col+1;
        for (int i = fil+1; i < t.getMida() && j < t.getMida() && i <= fil+3; i++) {
            if (t.getColor(i, j++) == jugador) contador++;
            else if (t.getColor(i, col) == 0){
                //mirar los espacios verticales para abajo
                contadorBlancs = espaisRestants(i,col,t);
            }
        }
        ValorCasella += color*calcularPuntuacio(contador, contadorBlancs);

        //Mirar dreta
        contador = 1;
        contadorBlancs = 0;
        for (int i = col+1; i < t.getMida() && i <= col+3; i++) {
            if (t.getColor(fil, i) == jugador) contador++;
            else if (t.getColor(i, col) == 0){
                //mirar los espacios verticales para abajo
                contadorBlancs = espaisRestants(i,col,t);
            }
        }

        ValorCasella += color*calcularPuntuacio(contador, contadorBlancs);

        //Mirar diagonal adalt esquerra
        contador = 1;
        contadorBlancs = 0;
        j = col-1;
        for (int i = fil+1; i < t.getMida() && j >= 0 && i <= fil+3; i++) {
            if (t.getColor(i, j--) == jugador) contador++;
            else if (t.getColor(i, col) == 0){
                //mirar los espacios verticales para abajo
                contadorBlancs = espaisRestants(i,col,t);
            }
        }

        ValorCasella += color*calcularPuntuacio(contador, contadorBlancs);

        return ValorCasella;
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
