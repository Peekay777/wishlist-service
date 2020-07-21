package com.koutsios.wishlistservice.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.koutsios.wishlistservice.domain.Wishlist;
import com.koutsios.wishlistservice.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

  @Autowired
  private WishlistService wishlistService;

  @PostMapping("/{userId}/{wishlistName}")
  @ResponseStatus(CREATED)
  public Wishlist createWishlist(@PathVariable String userId,
                                 @PathVariable String wishlistName) {
    return wishlistService.createWishlist(userId, wishlistName);
  }

  @GetMapping("/{wishlistId}")
  @ResponseStatus(OK)
  public Wishlist getWishlist(@PathVariable String wishlistId) {
    return wishlistService.getWishlist(wishlistId);
  }

  @DeleteMapping("/{wishlistId}")
  @ResponseStatus(NO_CONTENT)
  public void deleteWishlist(@PathVariable String wishlistId) {
    wishlistService.deleteWishlist(wishlistId);
  }

}
