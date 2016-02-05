package dbService;

import dbService.dataSets.UsersDataSet;

import java.sql.Connection;


public interface DBService {
    public UsersDataSet getUser(long id) throws DBException;
    public long addUser(String name, String pass) throws DBException;
    public void cleanUp() throws DBException;
    public void printConnectInfo();
    public Connection getH2Connection();
}
