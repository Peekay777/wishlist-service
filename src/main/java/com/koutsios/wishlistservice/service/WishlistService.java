package com.koutsios.wishlistservice.service;

import com.koutsios.wishlistservice.domain.Wishlist;

public interface WishlistService {
  Wishlist createWishlist(String userId, String wishlistName);

  void deleteWishlist(String wishlistId);

  Wishlist getWishlist(String wishlistId);
}
