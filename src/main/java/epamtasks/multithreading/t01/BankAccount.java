package epamtasks.multithreading.t01;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Optional;
import java.util.Scanner;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class BankAccount {

    private static final Logger log = LogManager.getLogger(BankAccount.class);
    private String holder;
    private String filename;
    private final Object lock = new Object();
    private static final String ERROR_FORMAT ;


    static {

        ERROR_FORMAT ="error:{},\n appears from:{} " ;
    }


    private BankAccount(String holder, String filename) {
        this.holder = holder;
        this.filename = filename;

    }

    public static Optional<BankAccount> buildAccount(String holder, String fileName)  {

      try(BufferedWriter writer = new BufferedWriter( new FileWriter(fileName))) {
          writer.write(String.format("Account holder: \"%s\"%nCurrent amount: %.2f $",holder,0.0));
          return   of(new BankAccount(holder,fileName));
      } catch (IOException e) {
          log.error(ERROR_FORMAT,e.getMessage(),e.getStackTrace());
            return empty();
      }

    }



    public String getHolder() {
        return holder;
    }
    public String getFilename() {
        return filename;
    }

    public double getBalanse(){
        double am = 0;
        synchronized (lock){
            log.info("Thread {} lock {} account for getBalanse",
                    Thread.currentThread().getName(),getHolder());
           am = readAmount();
            log.info("Thread {} unlock {} account for getBalanse",
                    Thread.currentThread().getName(),getHolder());
        }

        return am;
    }

    private double readAmount(){
        double result =0;
        try(BufferedReader br = new BufferedReader(
                new FileReader(filename))) {
            String prevLine="";
            String newLine="";
            while ((newLine = br.readLine()) != null){
                prevLine=newLine;
            }

            String [] content = prevLine.split(" ");
            if(!(content.length<3)){
                result = parseDouble(content[content.length-2].trim());
            }
        } catch (IOException e1) {
            log.error(ERROR_FORMAT,e1.getMessage(),e1.getStackTrace());
        }
        return result;
    }

    private static double parseDouble(String string){
        double result =0;
        try(Scanner scanner = new Scanner(string) ) {
            if (scanner.hasNextDouble()){
                result = scanner.nextDouble();
            }
        }
        return result;
    }

    public boolean deposit(double deposit){
        if(deposit <=0)return false;
        boolean result =false;
        synchronized (lock){
            log.info("Thread {} lock {} account for deposit {}",
                    Thread.currentThread().getName(),getHolder(),deposit);
            Double amount = deposit + getBalanse();
            result = writeDeposit(deposit,amount);
            log.info("Thread {} unlock {} account for deposit {}",
                    Thread.currentThread().getName(),getHolder(),deposit);
        }

        return result;
    }

    private  boolean writeDeposit(double deposit, double amount){
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filename,true))) {
            String message = String.format("%nDeposit %+.2f $%nCurrent amount: %.2f $",
                    deposit,amount);

            writer.write(message);
            return true;

        } catch (IOException e2) {
            log.error(ERROR_FORMAT,e2.getMessage(),e2.getStackTrace());
            return false;
        }
    }

    public boolean withdraw(double amount){
        boolean result=false;
        synchronized (lock){
            log.info("Thread {} lock {} account for withdraw {}",
                    Thread.currentThread().getName(),getHolder(),amount);
            Double balanse =  getBalanse();
          if(amount > balanse){
              return false;
          }
              balanse -= amount;

            result = writeWithdraw(amount, balanse);

            log.info("Thread {} unlock {} account for withdraw {}",
                    Thread.currentThread().getName(),getHolder(),amount);
        }

        return result;

    }
    private  boolean writeWithdraw(double withdraw, double amount){
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filename,true))) {
            String message = String.format("%nWithdraw -%.2f $%nCurrent amount: %.2f $",
                    withdraw,amount);

            writer.write(message);
            return true;

        } catch (IOException e2) {
            log.error(ERROR_FORMAT,e2.getMessage(),e2.getStackTrace());
            return false;
        }
    }

    public boolean transAction(BankAccount destination, double amount){
        if(this == destination) return false;
        synchronized (this) {

                if (!this.withdraw(amount)) {
                    return false;
                } else if (!destination.deposit(amount)) {
                    this.deposit(amount);
                    return false;
                }
                return true;

        }

    }

}
