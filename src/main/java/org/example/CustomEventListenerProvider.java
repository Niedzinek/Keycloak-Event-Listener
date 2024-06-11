package org.example;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomEventListenerProvider implements EventListenerProvider {
    private static final Logger log = LoggerFactory.getLogger(CustomEventListenerProvider.class);

    @Override
    public void onEvent(Event event) {
        if (event.getType().equals(EventType.REGISTER)) {
            log.info("User registration event received: {}", event);

            String userId = event.getUserId();
            String email = event.getDetails().get("email");
            String username = event.getDetails().get("username");
            String firstName = event.getDetails().get("firstName");
            String lastName = event.getDetails().get("lastName");

            if (userId == null || email == null || username == null) {
                log.warn("Missing mandatory user information: userId={}, email={}, username={}", userId, email, username);
                return;
            }

            Customer customer = new Customer();
            customer.setUserId(userId);
            customer.setEmail(email);
            customer.setLogin(username);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);

            try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
                 Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.persist(customer);
                transaction.commit();
                log.info("User {} successfully saved to the database.", userId);
            } catch (Exception e) {
                log.error("Failed to save user to the database: {}", e.getMessage(), e);
            }
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
        // Implementation for handling admin events if needed
    }

    @Override
    public void close() {
        // No need to close anything here
    }
}
