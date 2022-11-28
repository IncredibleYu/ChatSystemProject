package Test;
import Thread.*;
public class UDPTest {
    ThreadUDPReceiver receiver;
    public void setup(){
        new ThreadUDPSender().();
    }
}
