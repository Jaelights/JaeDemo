package test.interview.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.interview.demo.domain.Invoice;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service  // not using @Repository because it isn't a real repository
@RequiredArgsConstructor
public class InvoiceRepo {

    public Invoice getById(UUID id) {
        return FakeDB.idToInvoiceMap.get(id);
    }

    // Figured this logic was better here than in the controller.  IRL, it'd just be a more direct query.
    public List<Invoice> getAllInvoicesForID(int id) {
        return FakeDB.idToInvoiceMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(invoice -> invoice.getUser().getCustomerNumber() == id)
                .collect(Collectors.toList());
    }
}