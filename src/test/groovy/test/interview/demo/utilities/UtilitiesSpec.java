package test.interview.demo.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import test.interview.demo.domain.BillingRecord;
import test.interview.demo.utility.Utilities;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UtilitiesSpec {

    @Test
    public void removeDupesRemovesDupes() throws Exception {
        BillingRecord billingRecord1 = BillingRecord.builder().id(UUID.fromString("11111111-1111-1111-1111-111111111111")).build();
        BillingRecord billingRecord2 = BillingRecord.builder().id(UUID.fromString("11111111-1111-1111-1111-111111111112")).build();
        BillingRecord billingRecord3 = BillingRecord.builder().id(UUID.fromString("11111111-1111-1111-1111-111111111111")).build();


        List<BillingRecord> records = Arrays.asList(billingRecord1, billingRecord2, billingRecord3);

        List<BillingRecord> resultRecords = Utilities.removeDupes(records);
        Assertions.assertEquals(2, resultRecords.size());
    }
}
