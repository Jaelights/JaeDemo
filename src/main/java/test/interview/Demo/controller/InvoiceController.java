package test.interview.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.interview.demo.domain.Invoice;
import test.interview.demo.repository.InvoiceRepo;

import java.util.UUID;

@RestController()
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceRepo invoiceRepo;

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable String id){
        return invoiceRepo.getById(UUID.fromString(id));
    }


}
