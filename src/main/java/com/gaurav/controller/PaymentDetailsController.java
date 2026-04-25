package com.gaurav.controller;

import com.gaurav.exception.UserException;
import com.gaurav.model.PaymentDetails;
import com.gaurav.model.User;
import com.gaurav.service.PaymentDetailsService;
import com.gaurav.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentDetailsController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentDetailsService paymentDetailsService;

    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetails> addOrUpdatePaymentDetails(
            @RequestBody PaymentDetails paymentDetailsRequest,
            @RequestHeader("Authorization") String jwt
    ) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);

        PaymentDetails paymentDetails =
                paymentDetailsService.addOrUpdatePaymentDetails(
                        paymentDetailsRequest.getAccountNumber(),
                        paymentDetailsRequest.getAccountHolderName(),
                        paymentDetailsRequest.getIfsc(),
                        paymentDetailsRequest.getBankName(),
                        user
                );

        return ResponseEntity.ok(paymentDetails);
    }

    @GetMapping("/payment-details")
    public ResponseEntity<PaymentDetails> getUsersPaymentDetails(

            @RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);

        PaymentDetails paymentDetails=paymentDetailsService.getUsersPaymentDetails(user);
        return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
    }

//    @PutMapping("/payment-details/{id}")
//    public ResponseEntity<PaymentDetails> updatePaymentDetails(
//            @PathVariable Long id,
//            @RequestBody PaymentDetails request,
//            @AuthenticationPrincipal User user
//    ) {
//        PaymentDetails updated = paymentDetailsService.updatePaymentDetails(
//                id,
//                request.getAccountNumber(),
//                request.getAccountHolderName(),
//                request.getIfsc(),
//                request.getBankName(),
//                user
//        );
//
//        return ResponseEntity.ok(updated);
//    }

}
