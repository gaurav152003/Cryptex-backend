package com.gaurav.service;

import com.gaurav.model.PaymentDetails;
import com.gaurav.model.User;
import com.gaurav.repository.PaymentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Override
    public PaymentDetails addOrUpdatePaymentDetails(
            String accountNumber,
            String accountHolderName,
            String ifsc,
            String bankName,
            User user
    ) {

        // ✅ Fetch existing OR create new
        PaymentDetails paymentDetails =
                paymentDetailsRepository.findByUserId(user.getId())
                        .orElse(new PaymentDetails());

        // ✅ Set user only once
        paymentDetails.setUser(user);

        // ✅ Update fields
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setBankName(bankName);

        // ✅ INSERT (first time) or UPDATE (later)
        return paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {
        return paymentDetailsRepository
                .findByUserId(user.getId())
                .orElse(null);
    }
}

//@Service
//public class PaymentDetailsServiceImpl implements PaymentDetailsService{
//
//    @Autowired
//    private PaymentDetailsRepository paymentDetailsRepository;
//
//    @Override
//    public PaymentDetails addPaymentDetails(String accountNumber,
//                                            String accountHolderName,
//                                            String ifsc,
//                                            String bankName,
//                                            User user
//    ) {
//        PaymentDetails paymentDetails = new PaymentDetails();
//        paymentDetails.setAccountNumber(accountNumber);
//        paymentDetails.setAccountHolderName(accountHolderName);
//        paymentDetails.setIfsc(ifsc);
//        paymentDetails.setBankName(bankName);
//        paymentDetails.setUser(user);
//        return paymentDetailsRepository.save(paymentDetails);
//    }
//
//    @Override
//    public PaymentDetails getUsersPaymentDetails(User user) {
//        return paymentDetailsRepository.getPaymentDetailsByUserId(user.getId());
//    }
//
//    @Override
//    public PaymentDetails updatePaymentDetails(Long paymentDetailsId, String accountNumber, String accountHolderName, String ifsc, String bankName, User user) {
//        PaymentDetails paymentDetails = paymentDetailsRepository
//                .findById(paymentDetailsId)
//                .orElseThrow(() -> new RuntimeException("Payment details not found"));
//
//        // 🔒 Security check
//        if (!paymentDetails.getUser().getId().equals(user.getId())) {
//            throw new RuntimeException("Unauthorized to update payment details");
//        }
//
//        paymentDetails.setAccountNumber(accountNumber);
//        paymentDetails.setAccountHolderName(accountHolderName);
//        paymentDetails.setIfsc(ifsc);
//        paymentDetails.setBankName(bankName);
//
//        return paymentDetailsRepository.save(paymentDetails);
//    }
//}
