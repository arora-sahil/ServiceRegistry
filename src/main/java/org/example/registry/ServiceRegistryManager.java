package org.example.registry;

import org.example.entity.Service;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.*;

public class ServiceRegistryManager {

    private final ServiceRegistry serviceRegistry;
    private final ExecutorService executorService;

    private static ServiceRegistryManager instance;


    private ServiceRegistryManager(ServiceRegistry serviceRegistry, ExecutorService executorService) {
        this.serviceRegistry = serviceRegistry;
        this.executorService = executorService;
    }

    public static synchronized ServiceRegistryManager getInstance(ServiceRegistry serviceRegistry, ExecutorService executorService) {
        if (instance == null) {
            instance = new ServiceRegistryManager(serviceRegistry, executorService);
        }
        return instance;
    }


    public void registerService(Service service) {
        executorService.submit(() -> serviceRegistry.registerService(service));
    }

    public void deregisterService(Service service) {
        executorService.submit(() -> serviceRegistry.deRegisterService(service));
    }

    public Set<String> discoverServices(String serviceName) {
        Future<Set<String>> result = executorService.submit(() -> serviceRegistry.discoverServices(serviceName));

        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }



}
