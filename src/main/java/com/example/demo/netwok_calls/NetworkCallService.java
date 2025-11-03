package com.example.demo.netwok_calls;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NetworkCallService {


    private RestTemplate restTemplate = new RestTemplate();

    /*
    https://jsonplaceholder.typicode.com/posts/1
    {
      "userId": 1,
      "id": 1,
      "title": "sunt a",
      "body": "architecto"
    }
     */
    public Post getData(){
        Post post = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/1", Post.class);
        return post;
    }

}
