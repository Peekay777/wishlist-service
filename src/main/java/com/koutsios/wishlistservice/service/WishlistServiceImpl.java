package com.koutsios.wishlistservice.service;

import com.koutsios.wishlistservice.domain.Wishlist;
import com.koutsios.wishlistservice.repository.WishlistRepository;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {

  @Autowired
  private WishlistRepository repository;

  @Override
  public Wishlist newWishlist(String userId, String wishlistName) {
    Wishlist newWishlist = Wishlist.builder()
        .userId(userId)
        .name(wishlistName)
        .wanted(new HashMap<>())
        .build();
    return repository.save(newWishlist);
  }

}