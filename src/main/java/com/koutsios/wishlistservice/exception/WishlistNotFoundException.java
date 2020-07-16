package com.koutsios.wishlistservice.exception;

public class WishlistNotFoundException extends RuntimeException {

  public WishlistNotFoundException(String wishlistId) {
    super("Could find Wishlist " + wishlistId);
  }
}
