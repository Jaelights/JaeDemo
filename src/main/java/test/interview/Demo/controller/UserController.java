package test.interview.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import test.interview.demo.domain.BillingRecord;
import test.interview.demo.domain.Invoice;
import test.interview.demo.domain.User;
import test.interview.demo.repository.BillingRecordRepo;
import test.interview.demo.repository.InvoiceRepo;
import test.interview.demo.repository.UserRepo;

import java.util.List;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    InvoiceRepo invoiceRepo;

    @Autowired
    BillingRecordRepo billingRecordRepo;

    @GetMapping("/{id}/invoices")
    public List<Invoice> getUserInvoices(@PathVariable String id) {
        User user = userRepo.getUserRecordByID(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        List<Invoice> invoices = invoiceRepo.getAllInvoicesForID(Integer.parseInt(id));
        if(invoices.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User has no invoices");
        }
        return invoices;
    }

    @GetMapping("/{id}/billing_records")
    public List<BillingRecord> getUserBillingRecords(@PathVariable String id) {
        User user = userRepo.getUserRecordByID(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        List<BillingRecord> billingRecords = billingRecordRepo.getAllBillingRecordsForID(Integer.parseInt(id));
        if(billingRecords.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User has no billing records");
        }
        return billingRecords;
    }
}
