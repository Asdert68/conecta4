/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alber
 */
public class jugador1 {
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
        int max=0;
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
        
    }
    
    /**
     * Retorna el nom del jugador que es dona al crear-lo
     * @return Nom del jugador
     */
    public String nom(){
        
        return null;

    }
}
