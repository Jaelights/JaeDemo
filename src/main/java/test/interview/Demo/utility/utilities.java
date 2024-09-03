package test.interview.demo.utility;

import test.interview.demo.domain.BillingRecord;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    /*
        For the record, the duplicates feels like a data issue that should be fixed in the db.  But assuming,
        for a moment, that we simply have a problem with dupes in the DB and the DB team can't get to them
        anytime soon...for some reason.  Let's just only allow one through.

        Also, I worry about the timestamps in the dupes, I don't know if older or newer timestamps are important
        to things like warantees and subscriptions.  It's something I'd want to clarify with business.  Here we will
        just use first come first serve.

        Worried about the implications static might have on threading and scalability, but it's a utility
        so it feels like this should be fine.
         */
    public static List<BillingRecord> removeDupes(List<BillingRecord> initialBillingRecords){
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
