package com.koutsios.wishlistservice.controller;

import com.koutsios.wishlistservice.domain.Wishlist;
import com.koutsios.wishlistservice.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

  @Autowired
  private WishlistService wishlistService;

  @PostMapping("/{userId}/{wishlistName}")
  public Wishlist create(@PathVariable String userId,
                         @PathVariable String wishlistName) {
    return wishlistService.newWishlist(userId, wishlistName);
  }

}
