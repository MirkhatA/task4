package com.sevice.serviceImpl;

import com.sevice.Service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceImpl extends UnicastRemoteObject implements Service {
    private final BlockingQueue<Integer> queue;
    ArrayList<Integer> primeNumbers = new ArrayList<Integer>();
    static long startTime = 0, endTime = 0;
    boolean firstProcessStarted = false;

    public ServiceImpl() throws RemoteException {
        super();
        this.queue = new LinkedBlockingQueue<Integer>();
    }

    @Override
    public Integer getMessage() throws RemoteException {
        if (!firstProcessStarted) {
            startTime = System.nanoTime();
        }
        firstProcessStarted = true;
        return this.queue.poll();
    }

    @Override
    public void sendMessage(int num) throws RemoteException {
        this.queue.add(num);
    }

    @Override
    public void saveMessages(int num) throws RemoteException {
        System.out.println("The nums: " + queue);
        primeNumbers.add(num);
        if (queue.isEmpty()) {
            try {
                Thread.sleep((long) 11.11);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finalOutput(primeNumbers);
        }
    }

    public static void finalOutput(ArrayList<Integer> numberList) {
        int sum = 0;
        for (int numbers : numberList) {
            sum += numbers;
        }
        System.out.println("Total sum: " + sum);
        endTime = System.nanoTime();
        System.out.println("Taken time:" + (endTime - startTime));
    }

}