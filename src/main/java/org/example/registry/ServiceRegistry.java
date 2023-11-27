package org.example.registry;

import org.example.entity.Service;

import java.util.Set;

public interface ServiceRegistry {

    void registerService(Service service);

    void deRegisterService(Service service);

    Set<String> discoverServices(String serviceName);
}
