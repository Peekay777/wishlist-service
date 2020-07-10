package com.koutsios.wishlistservice.service;

import static com.koutsios.wishlistservice.fixture.WishlistFixture.aNewWishlist;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.koutsios.wishlistservice.domain.Wishlist;
import com.koutsios.wishlistservice.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class WishlistServiceImplTest {

  @Autowired
  private WishlistService wishlistService;

  @MockBean
  private WishlistRepository repository;

  @Test
  void newWishlist_success() {
    when(repository.save(any(Wishlist.class))).thenReturn(aNewWishlist());

    Wishlist actualWishlist = wishlistService.newWishlist("userid","wishlistname");

    assertNotNull(actualWishlist.getId());
    assertEquals("userid", actualWishlist.getUserId());
    assertEquals("wishlistname", actualWishlist.getName());
    assertTrue(actualWishlist.getWanted().isEmpty());
    verify(repository).save(any(Wishlist.class));
  }
}