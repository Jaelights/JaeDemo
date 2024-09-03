package test.interview.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import test.interview.demo.domain.Invoice;
import test.interview.demo.repository.InvoiceRepo;
import test.interview.demo.utility.Utilities;

import java.util.UUID;

@RestController()
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceRepo invoiceRepo;

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable String id) {
        Invoice invoice = invoiceRepo.getById(UUID.fromString(id));

        // Abort if nothing was found
        if(invoice == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found");
        }

        invoice.setBillingRecords(Utilities.removeDupes(invoice.getBillingRecords()));
        return invoice;
    }
}
