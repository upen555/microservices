package com.lcwd.user.service.repositories;

import com.lcwd.user.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReporistory extends JpaRepository <User,String> {
    //to implement custom method
}
