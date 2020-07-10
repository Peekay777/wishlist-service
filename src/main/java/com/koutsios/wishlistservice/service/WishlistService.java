package com.koutsios.wishlistservice.service;

import com.koutsios.wishlistservice.domain.Wishlist;

public interface WishlistService {
  Wishlist newWishlist(String userId, String wishlistName);
}
