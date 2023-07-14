package org.mashurova;

import java.time.LocalDateTime;

import static org.mashurova.ThreadUtils.*;

//Створити два додаткових потоки різними способами (успадкувати Thread або Runnable або за допомогою лямбди)
//        В першому потоці вивести тільки парні числа в діапазоні від 0 до 100_000
//        В другому потоці вивести тільки непарні числа в тому ж діапазоні
//        Питання: чи виводяться всі числа по черзі парні - не парні?
public class Main {
    public static void main(String[] args) {
        printStartInfo();
        Thread workingThread = new EvenThread();
        workingThread.start();
        Thread sleppyThread = new Thread(new OddThread(), "Sleppy Thread");
        sleppyThread.start();
        Thread usingLambdaThread = new Thread(() -> {
            printStartInfo();
            String threadName = Thread.currentThread().getName();
            for (int i = 0; i < 10; i++) {
                System.out.println(threadName + " " + i);
            }
            printEndInfo();
        }, "Thread With Lambda");
        usingLambdaThread.start();
        printEndInfo();
    }
}

class ThreadUtils {
    public static void printStartInfo() {
        String threadName = Thread.currentThread().getName();
        System.out.println("Thread <" + threadName + "> started at " + LocalDateTime.now());
    }

    public static void printInterruptExceptionInfo(InterruptedException ex) {
        String threadName = Thread.currentThread().getName();
        System.out.println("Thread <" + threadName + "> catch InterruptedException exception at " + LocalDateTime.now());
    }

    public static void printEndInfo() {
        String threadName = Thread.currentThread().getName();
        System.out.println("Thread <" + threadName + "> ended at " + LocalDateTime.now());
    }
}

class OddThread implements Runnable {

    @Override
    public void run() {
        printStartInfo();
        for (int i = 0; i < 100_000; i++) {
            if (i % 2 != 0) {
                System.out.println(i);
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                printInterruptExceptionInfo(e);
            }
        }
        printEndInfo();
    }
}

class EvenThread extends Thread {

    public EvenThread() {
        super("Even Thread");
    }

    @Override
    public void run() {
        // write code here which run in separate thread
        printStartInfo();
        for (int i = 0; i < 100_000; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                printInterruptExceptionInfo(e);
            }
        }
        printEndInfo();
    }
}
