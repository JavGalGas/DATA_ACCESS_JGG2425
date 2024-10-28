package org.example;

public class InsertTranCaller {
   static boolean isRunning = true;
    public static void run(){
        while(isRunning){
            int option = UI.getOption();
            switch(option){
                case 1:
                    InsertTranHandler.runTran();
                    break;
                case 2:
                    isRunning = false;
                    UI.close();
                    break;
                default:
                    UI.showErrorMessage();
                    break;
            }
        }
    }
}
