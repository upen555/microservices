package com.lcwd.hotel.services;

import com.lcwd.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

  public Hotel create(Hotel hotel);

  public List<Hotel> getAll();

  Hotel get(String id);

}
