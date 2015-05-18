package websockets;

import events.TestEvent;
import events.TestEventQualifier;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alfonso Tamés
 */
@Singleton
@ServerEndpoint("/notifier")
public class Notifier {
    
    
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());    
    
    @OnError
    public void onError(Throwable t) {
        System.out.println("An error ocurred");
    }

    @OnOpen
    public void onOpen(final Session session) {
        try {
            session.getBasicRemote().sendText("Welcome");
            sessions.add(session);
        } catch (Exception ex) {
            Logger.getLogger(Notifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void onCDIEvent(@Observes @TestEventQualifier TestEvent te) {
        System.out.println("The Notifier received a CDI Event!");
        
        Iterator i = sessions.iterator();
        while (i.hasNext()) {
            Session s = (Session)i.next();
            try {
                System.out.println("Sending message to session "+s.getId());
                s.getBasicRemote().sendText(te.getMessage());
            } catch (java.lang.IllegalStateException | IOException ex) {
                Logger.getLogger(Notifier.class.getName()).log(Level.SEVERE, "Carefully removing disconnected client {0}", s.getId());
                i.remove();
            }            
        }
    }
    
    
    
    

}