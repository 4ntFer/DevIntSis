package Server.MachineMonitor;

import com.sun.jdi.LongValue;
import com.sun.management.OperatingSystemMXBean;

import java.util.ArrayList;

public class MachineResoucesMonitor extends Thread{

    private ArrayList<Double> cpuUsage = new ArrayList<>();
    private ArrayList<Long> memUsage = new ArrayList<>();
    
    private boolean isRunning = true;
    private OperatingSystemMXBean systemMXBean;

    /**
     * @param systemMXBean ManagementFactory.getPlataformMXBean of java Sun
     */
    public MachineResoucesMonitor(OperatingSystemMXBean systemMXBean){
        this.systemMXBean = systemMXBean;
    }

    @Override
    public void run(){

        Long maxHeapSize = Runtime.getRuntime().maxMemory();
        while(isRunning){
            cpuUsage.add(systemMXBean.getCpuLoad());
            memUsage.add(
                    (maxHeapSize - Runtime.getRuntime().freeMemory())
            );
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        
    }

    /**
     * Termina e execução da thread.
     */
    public void stopRunning(){
        isRunning = false;
    }

    private synchronized void setRunning(boolean val){
        isRunning = val;
    }

    /**
     * @return Memória total do sistema.
     */
    public int getTotalMachineMemory(){
        return (int) systemMXBean.getTotalMemorySize();
    }


    /**
     * @return  Lista do uso de CPU durante todo o tempo de vida de
     * excução da thread coletados a cada 1 segundo em uma escala de [0.0, 1.0]/
     * @throws MonitorStillActiveExcpetion
     */
    public ArrayList<Double> getCpuUsage() throws MonitorStillActiveExcpetion {
        if(isRunning){
            throw new MonitorStillActiveExcpetion();
        }
        return cpuUsage;
    }

    /**
     * @return Lista do uso de memória em bytes durante todo o tempo de vida
     * da thread coletados a cada segundo.
     * @throws MonitorStillActiveExcpetion
     */
    public ArrayList<Long> getMemoryUsage() throws MonitorStillActiveExcpetion {
        if(isRunning){
            throw new MonitorStillActiveExcpetion();
        }
        return memUsage;
    }

    /**
     * @return Uso médio de cpu ao longo da execução da thread.
     * @throws MonitorStillActiveExcpetion
     */
    public double getAvarageCpuUsage() throws MonitorStillActiveExcpetion{
        if(isRunning){
            throw new MonitorStillActiveExcpetion();
        }

        double totalCpuUsage = 0;

        for(int i = 0; i<cpuUsage.size() ; i++)
            totalCpuUsage += cpuUsage.get(i);


        double avarageCpuUsage = totalCpuUsage / cpuUsage.size();
        
        return avarageCpuUsage;
    }

    /**
     * @return uso médio de memória do sistema ao longo da execução da thread.
     * @throws MonitorStillActiveExcpetion
     */
    public double getAvarageMemoryUsage() throws MonitorStillActiveExcpetion{
        if(isRunning){
            throw new MonitorStillActiveExcpetion();
        }

        Long maxHeapSize = Runtime.getRuntime().maxMemory();

        long totalMemUsage = 0;

        for(int i = 0 ; i < memUsage.size() ; i++)
            totalMemUsage += memUsage.get(i);

        //Usando tamanho do heap da jvm!
        double avarageMemUsage = ((double) totalMemUsage / memUsage.size()) / maxHeapSize;
        
        return avarageMemUsage;
    }

    /**
     * @return se a thread está ou não ativa.
     */
    public boolean isRunning() {
        return isRunning;
    }
}
