/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author usuari
 */
public class jugador1v2 {
     /**
     * Implementa el moviment del jugador amb un tauler donat i un color de peça.
     * @param t  Tauler actual de joc
     * @param color  Color de la peça que possarà
     * @return Columna on fer el moviment
     */
    static int profunditat;
    static boolean acaba;
    static int coloca;
    
    
    public int moviment(Tauler t, int color){
        /*int max=0;
        if(color==1){
            for(int i=0;i<t.getMida();++i)
            {
                if(t.solucio(i,1)){
                    coloca=i;
                    acaba=true;   
                }
                if(t.solucio(i,-1)) max=999/profunditat;
                
            }
            
        }
        else{
             for(int i=0;i<t.getMida();++i)
            {
                if(t.solucio(i,1))max=-1000/profunditat;
                if(t.solucio(i,-1) && max<999) max=-999/profunditat;
                
            }
        }
        return max;
        */
        int columna;
        float mov;
        int mida = t.getMida();
        float millor_mov = -1000; 
        for(int i = 0; i < mida; ++i){
            
            if(t.movpossible(i)){
                Tauler t2 = new Tauler(mida);
                t2.afegeix(i,color);
                mov = alfabeta(t2, -1000, 1000, this.profunditat, true, color);
                if(mov > millor_mov){
                    millor_mov = mov;
                    columna = i;
                }
            }
            else mov = -1000;
        }
        return columna;
    }
    
    private float alfabeta(Tauler t, float alfa, float beta, int profunditat, boolean torn, int color){
        
    }
    
    /**
     * Retorna el nom del jugador que es dona al crear-lo
     * @return Nom del jugador
     */
    public String nom(){
        
        return null;

    }
}
