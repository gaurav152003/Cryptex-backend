package com.gaurav.service;

import com.gaurav.model.PaymentDetails;
import com.gaurav.model.User;

public interface PaymentDetailsService {
    public PaymentDetails addOrUpdatePaymentDetails( String accountNumber,
                                             String accountHolderName,
                                             String ifsc,
                                             String bankName,
                                             User user
    );

    public PaymentDetails getUsersPaymentDetails(User user);



}
