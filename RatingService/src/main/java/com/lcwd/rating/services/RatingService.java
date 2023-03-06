package com.lcwd.rating.services;

import com.lcwd.rating.entities.Rating;

import java.util.List;

public interface RatingService {
    //create
    public Rating create(Rating rating);


    //get All Ratings
    List<Rating>getRatings();

    //get rating by user id
    List<Rating>getRatingByUserId(String UserId);

    //get rating by hotel Id
    List<Rating>getRatingByHotelId(String hotelId);
}
