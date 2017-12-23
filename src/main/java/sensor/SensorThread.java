package sensor;

import filter.Filter1;
import filter.Filter2;
import filter.IFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;

public class SensorThread implements Runnable {
    private Sensor sensor;
    private List<IFilter> filters;
    private ConcurrentMap<Short, String> concurrentMap;
    private short id;

    public SensorThread(Sensor sensor, String filter, ConcurrentMap<Short, String> concurrentMap) {
        this.setSensor(sensor);
        this.setFilters(new ArrayList<>());
        if (filter.contains("1"))
            this.getFilters().add(new Filter1());
        else if (filter.contains("2"))
            this.getFilters().add(new Filter2());
        this.setConcurrentMap(concurrentMap);
        this.setId(sensor.getID());
    }


    public void run() {
        System.out.println("Start Sensor Thread with ID: " + this.getId());
        int runningTime = getSensor().getUpdateFrequency();
        boolean loop = true;
        long start, end;
        Random random = new Random();
        while (loop) {
            start = System.currentTimeMillis() / 1000;
            this.getSensor().setDetect(random.nextBoolean());
            String result = getSensor().toString();
            for (IFilter filter:
                    getFilters()) {
                result = filter.processData(result);
            }
            this.getConcurrentMap().putIfAbsent(this.getId(), result);
            end = System.currentTimeMillis() / 1000;
            try {
                Thread.sleep(runningTime - (end - start));
            } catch (InterruptedException e) {
               System.out.println("Stopping Sensor Thread with ID: " + this.getId());
                loop = false;
            }
        }
    }

    private Sensor getSensor() {
        return sensor;
    }

    private void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    private List<IFilter> getFilters() {
        return filters;
    }

    private void setFilters(List<IFilter> filters) {
        this.filters = filters;
    }

    private ConcurrentMap<Short, String> getConcurrentMap() {
        return concurrentMap;
    }

    private void setConcurrentMap(ConcurrentMap<Short, String> concurrentMap) {
        this.concurrentMap = concurrentMap;
    }

    private short getId() {
        return id;
    }

    private void setId(short id) {
        this.id = id;
    }
}
