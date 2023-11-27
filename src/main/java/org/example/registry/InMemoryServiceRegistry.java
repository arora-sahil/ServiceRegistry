package org.example.registry;

import org.example.entity.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryServiceRegistry implements ServiceRegistry {

    private final Map<String, Set<String>> registry;

    public InMemoryServiceRegistry() {
        this.registry = new ConcurrentHashMap<>();
    }

    @Override
    public void registerService(Service service) {
        String serviceName = service.getServiceName();
        Set<String> serviceUrls = registry.get(service.getServiceUrl());

        if (serviceUrls == null) {
            serviceUrls = ConcurrentHashMap.newKeySet();
            registry.put(serviceName, serviceUrls);
        }

        serviceUrls.add(service.getServiceUrl());

        System.out.println("Service registered - Name: " + service.getServiceName() + ", URL: " +
                service.getServiceUrl());
    }

    @Override
    public void deRegisterService(Service service) {
        String serviceName = service.getServiceName();
        String serviceUrl = service.getServiceUrl();
        Set<String> serviceUrls = registry.get(serviceName);

        if (serviceUrls != null) {
            serviceUrls.remove(serviceUrl);

            if (serviceUrls.isEmpty()) {
                registry.remove(serviceName);
            }
            System.out.println("Service deregistered - Name: " + serviceName + ", URL: " + serviceUrl);
        }
    }

    @Override
    public Set<String> discoverServices(String serviceName) {
        return  registry.getOrDefault(serviceName, ConcurrentHashMap.newKeySet());
    }
}
