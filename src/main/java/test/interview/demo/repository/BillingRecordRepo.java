package test.interview.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.interview.demo.domain.BillingRecord;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingRecordRepo {

    public List<BillingRecord> getAllBillingRecords() {
        return FakeDB.allBillingRecords;
    }

    // Figured this logic was better here than in the controller...again.  IRL, it'd just be a more direct query.
    public List<BillingRecord> getAllBillingRecordsForID(int id) {
        return FakeDB.allBillingRecords.stream()
                .filter(br -> br.getUser().getCustomerNumber() == id)
                .collect(Collectors.toList());
    }

}