package accounts;

import dbService.DBService;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;
    private DBService dbServiceImpl;

    public AccountService(DBService dbServiceImpl) {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
        this.dbServiceImpl = dbServiceImpl;
    }

    public void addNewUser(UserProfile userProfile) {
        try {
            dbServiceImpl.addUser(userProfile.getLogin(), userProfile.getPass());
        }
        catch (Exception ex){
            Logger.getGlobal().info("Exception:"+ex.getMessage());
        }

       // loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
