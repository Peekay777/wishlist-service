package com.koutsios.wishlistservice.fixture;

import com.koutsios.wishlistservice.domain.Wishlist;
import java.util.HashMap;

public class WishlistFixture {

  public static Wishlist aNewWishlist() {
    return Wishlist.builder()
        .id("generatedId")
        .userId("userid")
        .name("wishlistname")
        .wanted(new HashMap<>())
        .build();
  }
}
