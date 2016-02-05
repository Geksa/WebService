package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import accounts.AccountService;
import servlets.SessionsServlet;
import servlets.SignUpServlet;
import servlets.SignInServlet;
import dbService.DBService;
import dbService.DBServiceImpl;

import websockets.WebSocketChatServlet;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbServiceImpl = new DBServiceImpl();
        dbServiceImpl.printConnectInfo();

        AccountService accountService = new AccountService(dbServiceImpl);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");

        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");

        context.addServlet(new ServletHolder(new SessionsServlet(accountService)), "/api/v1/sessions");

        context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();

        Logger.getGlobal().info("Server started");
        server.join();

        //dbServiceImpl.cleanUp();


    }
}
