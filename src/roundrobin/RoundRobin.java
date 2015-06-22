/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roundrobin;

import java.util.Map;
import java.util.Timer;
import java.util.TreeMap;

/**
 *
 * @author barbara.lopes
 */
public class RoundRobin {

    Timer timer;
    
    public void run(TreeMap<String,Integer> processos, TreeMap<String,Integer> recursos){
        int countProcessos,q;
        int countTerminados = 0;
        int value;       
        TreeMap<String,Integer> processosTemp = new TreeMap<>();
        
        countProcessos = processos.size();
        
        for(Map.Entry<String,Integer> entry: recursos.entrySet()){
        
            q = entry.getValue();
            System.out.println("Recurso em uso: "+entry.getKey()+" - Tempo de processamento: "+q+"s");
            
            //Restaura valores dos processos
            for(Map.Entry<String,Integer> e: processos.entrySet())
            {
                processosTemp.put(e.getKey(), e.getValue());
            }
            
            do
            {
                System.out.println("	Processo  	Tempo Antes	Tempo Depois");
                for(Map.Entry<String,Integer> entryP: processosTemp.entrySet())
                {
                    value = entryP.getValue();
                    if(value!=0)
                    {
                        System.out.print("	   "+entryP.getKey()+"	            "+entryP.getValue()+"s             ");
                        
                        if(value >= q){
                            try {
                                    Thread.sleep(q*1000);
                            } catch(InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                            processosTemp.put(entryP.getKey(), entryP.getValue() - q);
                        }
                        else{
                            try {
                                    Thread.sleep(value*1000);
                            } catch(InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                            processosTemp.put(entryP.getKey(), 0);
                        }
                        
                        System.out.println(entryP.getValue()+"s");
                        if(entryP.getValue() == 0){
                            countTerminados++;
                        }
                    } 
                }
            }while(countTerminados < countProcessos);
            
            countTerminados = 0;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        TreeMap<String,Integer> processos = new TreeMap<>(); 
        processos.put("P1",50);
        processos.put("P2",60);
        processos.put("P3",70);
        processos.put("P4",80);
        processos.put("P5",90);
                
        TreeMap<String,Integer> recursos = new TreeMap<>(); 
        recursos.put("R1",5);
        recursos.put("R2",10);
        recursos.put("R3",15);
        recursos.put("R4",20);
        
        RoundRobin rr = new RoundRobin();
        rr.run(processos, recursos);
    }
}
