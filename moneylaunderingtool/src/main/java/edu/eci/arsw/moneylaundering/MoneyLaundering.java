package edu.eci.arsw.moneylaundering;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoneyLaundering
{
    private TransactionAnalyzer transactionAnalyzer;
    private TransactionReader transactionReader;
    private int amountOfFilesTotal;
    private AtomicInteger amountOfFilesProcessed;
    private TransactionAnalyzerThread[] hilos;

    public MoneyLaundering()
    {
        transactionAnalyzer = new TransactionAnalyzer();
        transactionReader = new TransactionReader();
        amountOfFilesProcessed = new AtomicInteger();
    }

    public void processTransactionData(int numHilos)
    {
        amountOfFilesProcessed.set(0);
        List<File> transactionFiles = getTransactionFileList();
        amountOfFilesTotal = transactionFiles.size();
        int archivosPorHilo =  amountOfFilesTotal/numHilos;
        int aux = 0;
        boolean hiloAdicional = (archivosPorHilo*numHilos) != amountOfFilesTotal;
        int hiloAdd = hiloAdicional ?1:0;
        hilos = new  TransactionAnalyzerThread[numHilos + hiloAdd];
        for (int i = 0; i < numHilos; i++) {
            TransactionAnalyzerThread hilo = new TransactionAnalyzerThread(aux,aux+archivosPorHilo-1,transactionFiles,transactionReader,transactionAnalyzer,amountOfFilesProcessed);
            aux+=archivosPorHilo;
            hilos[i] = hilo;

        }
        if(hiloAdicional){

            TransactionAnalyzerThread hilo = new TransactionAnalyzerThread(aux,amountOfFilesTotal,transactionFiles,transactionReader,transactionAnalyzer,amountOfFilesProcessed);
            hilos[numHilos] = hilo;
        }

        for (TransactionAnalyzerThread h: hilos) {
            h.start();
        }



    }

    public List<String> getOffendingAccounts()
    {
        return transactionAnalyzer.listOffendingAccounts();
    }

    private List<File> getTransactionFileList()
    {
        List<File> csvFiles = new ArrayList<>();
        try (Stream<Path> csvFilePaths = Files.walk(Paths.get("src/main/resources/")).filter(path -> path.getFileName().toString().endsWith(".csv"))) {
            csvFiles = csvFilePaths.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFiles;
    }

    public static void main(String[] args)
    {
        MoneyLaundering moneyLaundering = new MoneyLaundering();
        moneyLaundering.processTransactionData(20);
        boolean guarda = true;
        while(guarda)
        {
            guarda = false;
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if(line.contains("exit"))
                break;
            for (TransactionAnalyzerThread h: moneyLaundering.hilos) {
                h.dormir();
                
            }
            String message = "Processed %d out of %d files.\nFound %d suspect accounts:\n%s";
            
            List<String> offendingAccounts = moneyLaundering.getOffendingAccounts();
            String suspectAccounts = offendingAccounts.stream().reduce("", (s1, s2)-> s1 + "\n"+s2);
            message = String.format(message, moneyLaundering.amountOfFilesProcessed.get(), moneyLaundering.amountOfFilesTotal, offendingAccounts.size(), suspectAccounts);
            System.out.println(message);
            System.out.println("Hilos dormidos");
            scanner = new Scanner(System.in);
            line = scanner.nextLine();

            for (TransactionAnalyzerThread h: moneyLaundering.hilos) {
                h.despertar();
            }
            System.out.println("Working...");
            System.out.println("Hilos despiertos");
            for (TransactionAnalyzerThread p: moneyLaundering.hilos) {
                if (p.isAlive()){

                    guarda = true;
                    break;
                }

            }
        }
        System.out.println("Finished");

    }


}
