package com.koutsios.wishlistservice.repository;

import com.koutsios.wishlistservice.domain.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WishlistRepository extends MongoRepository<Wishlist, String> {

}
