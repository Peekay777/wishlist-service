package com.koutsios.wishlistservice.fixture;

import static com.koutsios.wishlistservice.constant.ProductStatus.AVAILABLE;

import com.koutsios.wishlistservice.constant.ProductStatus;
import com.koutsios.wishlistservice.domain.Product;
import com.koutsios.wishlistservice.domain.Wishlist;
import java.util.HashMap;
import java.util.Map;

public class WishlistFixture {

  public static Wishlist aNewWishlist() {
    return Wishlist.builder()
        .id("generatedId")
        .userId("userid")
        .name("wishlistname")
        .wanted(new HashMap<>())
        .build();
  }

  public static Wishlist aWishlist() {
    return Wishlist.builder()
        .id("generatedId")
        .userId("userid")
        .name("wishlistname")
        .wanted(Map.of(
            "item1", Product.builder()
                .name("item1name")
                .productDetailsLink("http://amazon.co.uk/p=1234567890")
                .status(AVAILABLE)
                .build()
        ))
        .build();
  }
}
