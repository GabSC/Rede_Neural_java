/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redeneural;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Alunos
 */
public class Neuronio {
    
    private double vet[] = new double[12];
    private double bias = -1;
    private double eta = 0.3;
    private double deltaEta = 0.000005;
    private double erroMaximoAceitavel = 0.00001;
    private int maxEpocas = 100000;
    private boolean wRandon = true;

    private double entradas[][] = {{0, 0, 0}, 
                                  {0, 1, 0},
                                  {1, 0, 0}, 
                                  {1, 1, 1}};
    
    
    private double yDs[] = {1, 1, 1, 1};
    private double ws[] = {0.2, -0.3, 0.5};
    private double wBias = 0.5;

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public double getEta() {
        return eta;
    }

    public void setEta(double eta) {
        this.eta = eta;
    }

    public double getDeltaEta() {
        return deltaEta;
    }

    public void setDeltaEta(double deltaEta) {
        this.deltaEta = deltaEta;
    }

    public double getErroMaximoAceitavel() {
        return erroMaximoAceitavel;
    }

    public void setErroMaximoAceitavel(double erroMaximoAceitavel) {
        this.erroMaximoAceitavel = erroMaximoAceitavel;
    }

    public int getMaxEpocas() {
        return maxEpocas;
    }

    public void setMaxEpocas(int maxEpocas) {
        this.maxEpocas = maxEpocas;
    }

    public boolean iswRandon() {
        return wRandon;
    }

    public void setwRandon(boolean wRandon) {
        this.wRandon = wRandon;
    }

    public double[][] getEntradas() {
        return entradas;
    }

    public void setEntradas(double[][] entradas) {
        
        int lin,col;
        int ind = 0;
        
        for(lin = 0;lin < 4;lin++){
        
            for(col = 0;col < 3;col ++){
            
            
                vet[ind] = entradas[lin][col];
            
                ind++;
            }
        
        }
        
        
        
    }

    public double[] getyDs() {
        return yDs;
    }

    public void setyDs(double[] yDs) {
        this.yDs = yDs;
    }

    public double[] getWs() {
        return ws;
    }

    public void setWs(double[] ws) {
        this.ws = ws;
    }

    public double getwBias() {
        return wBias;
    }

    public void setwBias(double wBias) {
        this.wBias = wBias;
    }

    
    public double classificar() {
        Scanner sc = new Scanner(System.in);
      //  double entrada[] = new double[ws.length];
        //Ler entradas
//        System.out.println("Entre com as " + vet.length + " entradas:");
//        for (int i = 0; i < vet.length; i++) {
//            vet[i] = Double.parseDouble(sc.nextLine());
//        }
        // Calcular o v
        double v = (wBias * bias);
        for (int i = 0; i < vet.length; i++) {
            v += ws[i] * vet[i];
        }
        // Calcular o y
        double y = calcularY(v,1);
        return y;
        
    }
    
    public void treinar() {
        double erroQuadratico = 1; // Erro da Época
        int epoca = 0;
        // Inicializando pesos
        if (wRandon) {
            Random r = new Random();
           
            ws = new double[vet.length];
            for (int j = 0; j < ws.length; j++) {
                ws[j] = r.nextDouble();
                if (r.nextInt(2) != 0) {
                    ws[j] *= -1;
                }
            }
            wBias = r.nextDouble();
            if (r.nextInt(2) != 0) {
                wBias *= -1;
            }
        }
        // Treinamento
        while (erroQuadratico > erroMaximoAceitavel && epoca < maxEpocas) {
            // Epoca
            erroQuadratico = 0;
            int i2 = 0;
            System.out.println("Época: " + (epoca + 1));
            System.out.println("Eta: " + eta);
            for (int i = 0; i < vet.length; i++) {
                // Iteração
                // Calcular o v
                double v = (wBias * bias);
                for (int j = 0; j < vet.length; j++) {
                    v += ws[j] * vet[j];
                }
                // Calcular o y
                double y = calcularY(v,1); // o parametro 2 eh apra escolher o tipo de função
                // Calcular o erro da iteração
                if(i2 >= 4){
                i2 = 0;
                }
                double erroInstantaneo = yDs[i2] - y;
                
                i2++;
                
                // Ajustar os pesos
                wBias += (bias * erroInstantaneo * eta);
                for (int j = 0; j < vet.length; j++) {
                    ws[j] += (vet[j] * erroInstantaneo * eta);
                }
                // Incrementa o erro da epoca
                erroQuadratico += (erroInstantaneo * erroInstantaneo);

                for (int j = 0; j < vet.length; j++) {
                    System.out.print(vet[j] + " | ");
                }
                
                if(i2 >= 4){
                i2 = 0;
                }
                System.out.print(yDs[i2] + " -> ");
                System.out.print(y + " (" + erroInstantaneo + ")");
                System.out.println("");
            }
            erroQuadratico = erroQuadratico / vet.length;
            System.out.println("Erro Quadratico: " + erroQuadratico);
            epoca++;
            eta *= (1 - deltaEta);
        }
    }
    
    
    private double funcaoDegrau(double v) {
        double y = 0;
        if (v >= 0) {
            y = 1;
        }
        return y;
    }

    // Saidas entre -1 e 1
    private double funcaoTanHiperbolica(double v) {
        return StrictMath.tanh(v);
    }

    // Saidas entre 0 e 1
    private double funcaoSigmoidal(double v) {
        double beta = 0.5;
        double e = Math.E;
        return 1 / (1 + Math.pow(e, -(beta) * v));
    }
    
    public double calcularY(double v,int op){
        
        double retorno;
        
        if(op == 1 ){
        
            retorno = this.funcaoDegrau(v);
        
        }else if (op == 2){
        
            retorno = this.funcaoSigmoidal(v);
            
            
            
        }else if (op ==3){
        
        
            retorno = this.funcaoTanHiperbolica(v);
            
        }else {
        
            System.out.println("opção invalida");
            retorno = 0.0;
        }
        
        return retorno;
    }
    
    
}
