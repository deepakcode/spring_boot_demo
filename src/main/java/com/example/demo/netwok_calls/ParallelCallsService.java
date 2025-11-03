package com.example.demo.netwok_calls;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class ParallelCallsService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Async
    public CompletableFuture<String> serviceOne() throws InterruptedException {
        CompletableFuture<String> result = CompletableFuture.supplyAsync(() -> {

            String response = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/1", String.class);
            System.out.println("Response:get : " + response);
            return response;

        });
        return result;
    }

    @Async
    public CompletableFuture<String> serviceTwo() throws InterruptedException {
        CompletableFuture<String> result = CompletableFuture.supplyAsync(() -> {

            String postBody = "{\"title\":\"DeepakVK\",\"body\":\"bar\",\"userId\":100}";
            String response = restTemplate.postForObject("https://jsonplaceholder.typicode.com/posts", postBody, String.class);
            System.out.println("Response:post : " + response);

            return response;
        });
        return result;
    }

    public CompletableFuture<String> parallel_calls() throws InterruptedException {
        CompletableFuture<String> future1 = serviceOne();
        CompletableFuture<String> future2 = serviceTwo();
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2);
        CompletableFuture<String> result = allFutures.thenApply(v -> {
            return future1.join() + future2.join();
        });
        return result;
    }
}
