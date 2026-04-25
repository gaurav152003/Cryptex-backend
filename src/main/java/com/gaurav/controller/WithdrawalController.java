package com.gaurav.controller;

import com.gaurav.domain.WalletTransactionType;
import com.gaurav.model.User;
import com.gaurav.model.Wallet;
import com.gaurav.model.WalletTransaction;
import com.gaurav.model.Withdrawal;
import com.gaurav.service.UserService;
import com.gaurav.service.WalletService;
import com.gaurav.service.WalletTransactionService;
import com.gaurav.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@RestController
//public class WithdrawalController {
//
//    @Autowired
//    private WithdrawalService withdrawalService;
//
//    @Autowired
//    private WalletService walletService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private WalletTransactionService walletTransactionService;
//
//    // USER WITHDRAWAL (AUTO APPROVED)
//    @PostMapping("/api/withdrawal/{amount}")
//    public ResponseEntity<?> withdrawalRequest(
//            @PathVariable Long amount,
//            @RequestHeader("Authorization") String jwt) throws Exception {
//
//        User user = userService.findUserProfileByJwt(jwt);
//        Wallet userWallet = walletService.getUserWallet(user);
//
//        // Create withdrawal as COMPLETED
//        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
//
//        // Deduct balance immediately
//        walletService.addBalanceToWallet(userWallet, -amount);
//
//        // Save wallet transaction
//        walletTransactionService.createTransaction(
//                userWallet,
//                WalletTransactionType.WITHDRAWAL,
//                null,
//                "Withdrawal to bank account",
//                amount
//        );
//
//        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
//    }
//
//    // USER WITHDRAWAL HISTORY
//    @GetMapping("/api/withdrawal")
//    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
//            @RequestHeader("Authorization") String jwt) throws Exception {
//
//        User user = userService.findUserProfileByJwt(jwt);
//        List<Withdrawal> withdrawals =
//                withdrawalService.getUsersWithdrawalHistory(user);
//
//        return new ResponseEntity<>(withdrawals, HttpStatus.OK);
//    }
//}


@RestController
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletTransactionService walletTransactionService;

    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Wallet userWallet=walletService.getUserWallet(user);

        Withdrawal withdrawal=withdrawalService.requestWithdrawal(amount,user);
        walletService.addBalanceToWallet(userWallet, -withdrawal.getAmount());

        WalletTransaction walletTransaction = walletTransactionService.createTransaction(
                userWallet,
                WalletTransactionType.WITHDRAWAL,null,
                "bank account withdrawal",
                withdrawal.getAmount()
        );

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(
            @PathVariable Long id,
            @PathVariable boolean accept,
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        Withdrawal withdrawal=withdrawalService.procedWithdrawal(id,accept);

        Wallet userWallet=walletService.getUserWallet(user);
        if(!accept){
            walletService.addBalanceToWallet(userWallet, withdrawal.getAmount());
        }

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(

            @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        List<Withdrawal> withdrawal=withdrawalService.getUsersWithdrawalHistory(user);

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(

            @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        List<Withdrawal> withdrawal=withdrawalService.getAllWithdrawalRequest();

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }
}
