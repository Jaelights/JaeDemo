package test.interview.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.interview.demo.domain.BillingRecord;
import test.interview.demo.domain.User;

import java.util.List;

@Service  // not using @Repository because it isn't a real repository
@RequiredArgsConstructor
public class UserRepo {

    // Added to pull a single user from the list of users.
    public User getUserRecordByID(String id) {
        List<User> allUsers = FakeDB.allUsers;
        for (User user : allUsers) {
            if (user.getCustomerNumber() == Integer.parseInt(id)){
                return user;
            }
        }
        return null; //Probably better to have full fledged error and logging in production, but as this is a demo...
    }

}