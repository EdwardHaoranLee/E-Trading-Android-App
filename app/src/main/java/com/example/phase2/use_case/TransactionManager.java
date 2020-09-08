//package com.example.phase2.use_case;
//import com.example.phase2.entity.TransactionRequest;
//
//public class TransactionManager {
//    public static int thresholdMaxEditing = 6;
//    private int editing = 0;
//    public static int thresholdLendingMinusBorrowing = 1;
//
//    public void editTransactionRequest(TransactionRequest re, String location,
//                                       int year, int month, int date, int hours, int minute) {
//        if(editing <= thresholdMaxEditing){
//            EditTransactions editTransactions = new EditTransactions();
//            editTransactions.setCurrentUser(re.getOwner());
//            editTransactions.removeTransactionRequest(re);
//            editTransactions.setCurrentUser(re.getTheOtherUser());
//            editTransactions.addTransactionRequest(re);
//            re.modifyRequest(location, year, month, date, hours, minute);
//            editing += 1;
//        }
//
//    }
//}
