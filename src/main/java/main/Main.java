package main;

import sensor.Sensor;
import sensor.SensorThread;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Main {

    public static void main(String[] args){


        ConcurrentMap<Short, String> concurrentMap = new ConcurrentHashMap<>(3);

        //Sensors
        Sensor s1 = new Sensor((short)1, 180, 10, "Sensor 1");
        Sensor s2 = new Sensor((short)2, 200, 20, "Sensor 2");
        Sensor s3 = new Sensor((short)3, 335, 30, "Sensor 3");

        //Sensor Threads
        SensorThread st1 = new SensorThread(s1, "", concurrentMap);
        SensorThread st2 = new SensorThread(s2, "1", concurrentMap);
        SensorThread st3 = new SensorThread(s3, "1 2", concurrentMap);


        //Threads
        ThreadGroup tg = new ThreadGroup("Sensor Threads Group");
        Thread t1 = new Thread(tg,st1, "Thread 1");
        Thread t2 = new Thread(tg,st2, "Thread 2");
        Thread t3 = new Thread(tg, st3, "Thread 3");

        WebServer server = null;
        try {
            server = new WebServer(concurrentMap);
            server.getServer().start();
            System.out.println("WebServer started");

        } catch (IOException e) {
            e.printStackTrace();

        }
        t1.start();
        t2.start();
        t3.start();


        WebServer finalServer = server;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(finalServer != null){
                finalServer.getServer().stop(0);
                System.out.println("WebServer stopped");
            }
            tg.interrupt();
        }));


    }
}
