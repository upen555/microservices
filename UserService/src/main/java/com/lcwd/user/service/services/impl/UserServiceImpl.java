package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.repositories.UserReporistory;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserReporistory userReporistory;
    @Autowired
    private RestTemplate restTemplate;
     private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userReporistory.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userReporistory.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userReporistory.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given Id Not exist : "+userId)) ;
        //fetch user rating from RATING SERVICE
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{} ",ratingsOfUser);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        List<Rating> ratingList =  ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(),Hotel.class);
           Hotel hotel = forEntity.getBody();
           logger.info(" Response status code ",forEntity.getStatusCode());

            //set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect((Collectors.toList()) );

        user.setRatings(ratingList);
        return  user;
    }
}
