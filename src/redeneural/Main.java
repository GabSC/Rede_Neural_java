package redeneural;

public class Main {

    
    public static void main(String[] args) {
      
        
        Neuronio n1 = new Neuronio();
        
        
         double a[][] = {{0, 1, 0}, 
                        {1, 0, 1},
                        {1, 1, 1}, 
                         {1, 0, 1}};
         
         double A[][] = {{1, 1, 1}, 
                         {1, 0, 1},
                         {1, 1, 1}, 
                         {1, 0, 1}};
         
         double L[][] = {{1, 1, 1}, 
                         {1, 0, 0},
                         {1, 0, 0}, 
                         {1, 1, 1}};
         
         
         
         double yDsejado[] = {1,1,1,1};
         double yDesejado2[] = {0,0,0,0};
        
         n1.setEntradas(a);
         n1.setyDs(yDsejado);
         n1.treinar();
        double saida = n1.classificar();
        
        
        Neuronio n2 = new Neuronio();
        n2.setEntradas(L);
        n2.setyDs(yDesejado2);
        n2.treinar();
        double saida2 = n2.classificar();
        
        
        if(saida == 1){
        
            System.out.println("Letra A");
            
        }
        
        if (saida2 == 0){
        
            System.out.println("Letra L");
        
        }
         
        
        
        
    }

      
    
}
