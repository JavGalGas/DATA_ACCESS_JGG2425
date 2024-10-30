package com.jgg.Unit4;

public class crudCaller {
    private static boolean isRunning = true;
    private static String columnOption;
    public static void run(){
        while (isRunning){
            int option = UI.getOption();
            if(option >=1 && option <= 4){
                columnOption = UI.getColumnOption();
            }
            switch (option){
                case 1:
                    crudHandler.executeInsert(columnOption);
                    break;
                case 2:
                    crudHandler.executeSelect(columnOption);
                    break;
                case 3:
                    crudHandler.executeUpdate(columnOption);
                    break;
                case 4:
                    crudHandler.executeDelete(columnOption);
                    break;
                case 5:
                    UI.close();
                    isRunning = false;
                    break;
                default:
                    UI.getErrorMessage(0);
                    break;
            }
        }
    }
}
