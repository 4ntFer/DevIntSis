package Server.MachineMonitor;

/**
 * Lançada caso seja solicitado um recurso à MachineResoucesMonitor que ele não pode forncer
 * enquanto ainda está ativo!
 */
public class MonitorStillActiveExcpetion extends Exception{
    public MonitorStillActiveExcpetion(){
        super("Monitor thread is running!");
    }
}
