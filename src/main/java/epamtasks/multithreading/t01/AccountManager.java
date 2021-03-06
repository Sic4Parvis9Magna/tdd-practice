package epamtasks.multithreading.t01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class AccountManager extends Thread{
    private static final Logger log = LogManager.getLogger(AccountManager.class);
    private BankAccount bankAccount;
    private BankAccount bankAccount2;
    private final int managerId;

    private static int managerCounter ;


    public AccountManager(BankAccount bankAccount,BankAccount bankAccount2){
        super();
        this.bankAccount = bankAccount;
        this.bankAccount2 = bankAccount2;
        managerId = managerCounter++;
        setName("Manager#"+managerId);
    }

    public int getManagerId() {
        return managerId;
    }

    @Override
    public void run(){

            for (int i = 0; i < 10; i++) {

                getTransAction();
                getWithdraw();
                getDeposit();

            }


    }

    private AccountManager getTransAction(){
        Random random = new Random();
        boolean reult=false;
        double amount = random.nextDouble()*500+10;
        if((Math.random()*10+1)>5){
            log.info("Manager#{} trying make transaction at {}$ from account {} to {} .",
                    managerId,
                    amount,
                    bankAccount.getHolder(),
                    bankAccount2.getHolder());

          reult= bankAccount.transAction(bankAccount2, amount);

            log.info("Manager#{} {} transaction at {}$ from account {} to {} .",
                    managerId,
                    reult,
                    amount,
                    bankAccount.getHolder(),
                    bankAccount2.getHolder());
        }else {
            log.info("Manager#{} trying to make transaction at {}$ from account {} to {} .",
                    managerId,
                    amount,
                    bankAccount2.getHolder(),
                    bankAccount.getHolder());

           reult= bankAccount2.transAction(bankAccount, amount);

            log.info("Manager#{} {} made transaction at {}$ from account {} to {} .",
                    managerId,
                    reult,
                    amount,
                    bankAccount2.getHolder(),
                    bankAccount.getHolder());
        }
        return this;
    }

    private AccountManager getWithdraw(){
        Random random = new Random();

        if(random.nextInt(100)%2 !=0){
            bankAccount.withdraw(random.nextDouble()*200);
        }else{
            bankAccount2.withdraw(random.nextDouble()*200);
        }
        return this;
    }

    private AccountManager getDeposit(){
        Random random = new Random();

        if(random.nextInt(100)%2 !=0){
            bankAccount.deposit(random.nextDouble()*200);
        }else{
            bankAccount2.deposit(random.nextDouble()*200);
        }
        return this;
    }

}
