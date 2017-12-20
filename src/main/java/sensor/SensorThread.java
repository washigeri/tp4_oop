package sensor;

public class SensorThread implements Runnable {

    private Sensor sensor;

    public SensorThread(short id, int workingRange, int updateFrequency, String OPT) {
        this.sensor = new Sensor(id, workingRange, updateFrequency, OPT);
    }


    public void run() {

    }
}
