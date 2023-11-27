package org.example;

import org.example.entity.Service;
import org.example.registry.InMemoryServiceRegistry;
import org.example.registry.ServiceRegistry;
import org.example.registry.ServiceRegistryManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ServiceRegistry serviceRegistry = new InMemoryServiceRegistry();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ServiceRegistryManager manager = ServiceRegistryManager.getInstance(
                serviceRegistry, executorService);
        manager.registerService(Service.builder().serviceName("AUTH_SERVICE_1").
                serviceUrl("http://auth-service-1").build());
        manager.registerService(Service.builder().serviceName("AUTH_SERVICE_2").
                serviceUrl("http://auth-service-2").build());
        manager.registerService(Service.builder().serviceName("PAYMENT_SERVICE_1").
                serviceUrl("http://payment-service-1").build());
        System.out.println(manager.discoverServices("AUTH_SERVICE_1"));

        manager.deregisterService(Service.builder().serviceName("AUTH_SERVICE_1").
                serviceUrl("http://auth-service-1").build());

        System.out.println(manager.discoverServices("AUTH_SERVICE_1"));
        executorService.shutdown();
    }
}