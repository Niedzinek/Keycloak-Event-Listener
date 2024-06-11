package org.example;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class CustomEventListenerProviderFactory implements EventListenerProviderFactory {
    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new CustomEventListenerProvider();
    }

    @Override
    public void init(Config.Scope scope) {
        // Możesz dodać kod inicjalizacyjny tutaj, jeśli jest potrzebny
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // Możesz dodać kod, który zostanie wykonany po inicjalizacji sesji
    }

    @Override
    public void close() {
        // Możesz dodać kod sprzątający tutaj, jeśli jest potrzebny
    }

    @Override
    public String getId() {
        return "custom-event-listener"; // Zwróć unikalny identyfikator dla twojego listenera
    }
}
