package edu.eci.arsw.moneylaundering;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionAnalyzerThread extends Thread{

    private  AtomicInteger procesados;
    private  TransactionReader lector;
    private  TransactionAnalyzer analizador;
    private  List<File> archivos;
    private  int a,b;
    private AtomicBoolean trabajando;

    public TransactionAnalyzerThread(int a, int b, List<File> archivos, TransactionReader lector, TransactionAnalyzer analizador, AtomicInteger procesados){
        this.a = a;
        this.b = b;
        this.archivos =archivos;
        this.analizador = analizador;
        this.lector= lector;
        this.procesados = procesados;
        this.trabajando = new AtomicBoolean(true);
    }

    @Override
    public void run() {

        while (true){
            System.out.println("....................");
            for (int i = a; i < b; i++)
            {
                List<Transaction> transactions = lector.readTransactionsFromFile(archivos.get(i));
                for(Transaction transaction : transactions)
                {
                    while (!trabajando.get()){
                        synchronized (this){
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    analizador.addTransaction(transaction);
                }
                procesados.getAndIncrement();
            }
            break;
        }
    }

    public AtomicBoolean getTrabajando() {
        return trabajando;
    }

    public void dormir() {
        this.trabajando.getAndSet(false);
    }
    public synchronized void despertar(){
        this.trabajando.getAndSet(true);
        notify();
    }
}
