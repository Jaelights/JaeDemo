package test.interview.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.interview.demo.domain.BillingRecord;
import test.interview.demo.domain.Invoice;
import test.interview.demo.repository.InvoiceRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceRepo invoiceRepo;

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable String id){
        Invoice invoice = invoiceRepo.getById(UUID.fromString(id));
        invoice.setBillingRecords(removeDupes(invoice.getBillingRecords()));
        return invoice;
    }

    /*
    For the record, this feels like a data issue that should be fixed in the logs.  But assuming,
    for a moment, that we simply have a problem with dupes in the DB and the DB team can't get to them
    anytime soon...for some reason.  Let's just only allow one through.

    Also, I worry about the timestamps in the dupes, I don't know if older or newer timestamps are important
    to things like warantees and subscriptions.  It's something I'd want to clarify with business.  Here we will
    just use first come first serve.

    Worried about the implications static might have on threading and scalability, but it's a utility
    so it feels like this should be fine.
     */
    static List<BillingRecord> removeDupes(List<BillingRecord> initialBillingRecords){
        ArrayList<String> existingIDs = new ArrayList<>();
        ArrayList<BillingRecord> cleanedBillingRecords = new ArrayList<>();
        for (BillingRecord br : initialBillingRecords){
            String id = br.getId().toString();
            if (existingIDs.contains(id)) {
                continue;
            } else {
                existingIDs.add(id);
                cleanedBillingRecords.add(br);
            }
        }
        return cleanedBillingRecords;
    }
}
