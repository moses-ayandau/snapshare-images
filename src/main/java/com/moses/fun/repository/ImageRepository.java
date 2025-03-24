package com.moses.fun.repository;

import com.moses.fun.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.key = :key")
    void deleteByKey(String key);
}
