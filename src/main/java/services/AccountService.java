package services;

import java.util.HashMap;
import java.util.Map;


public class AccountService {

    Map<Long, User> users=new HashMap<Long, User>();

    public void createUser(String login, String password){

        User user=new User(login, password);
        users.put((long) 1, user);
    }

    public User getUser(String login, String password){
        User user=null;

        for (final Map.Entry<Long, User> entry : users.entrySet()) {
            User tempUser=entry.getValue();
            if ((tempUser.getLogin().equals(login))&&(tempUser.getPassword().equals(password)))
            {
                user=tempUser;
                break;
            }
        }
        return user;
    }
}
