package com.koutsios.wishlistservice.service;

import com.koutsios.wishlistservice.domain.Wishlist;
import com.koutsios.wishlistservice.dto.UpdateWishlist;

public interface WishlistService {
  Wishlist createWishlist(String userId, String wishlistName);

  void deleteWishlist(String wishlistId);

  Wishlist getWishlist(String wishlistId);

  Wishlist updateWishlist(String wishlistId, UpdateWishlist updateWishlist);
}
