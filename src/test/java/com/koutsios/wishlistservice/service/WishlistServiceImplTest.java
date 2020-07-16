package com.koutsios.wishlistservice.service;

import static com.koutsios.wishlistservice.fixture.WishlistFixture.aNewWishlist;
import static com.koutsios.wishlistservice.fixture.WishlistFixture.aWishlist;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.koutsios.wishlistservice.domain.Wishlist;
import com.koutsios.wishlistservice.exception.WishlistNotFoundException;
import com.koutsios.wishlistservice.repository.WishlistRepository;
import java.util.Optional;
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

  @Test
  void deleteWishlsit_sucess() {
    when(repository.existsById(anyString())).thenReturn(true);
    doNothing().when(repository).deleteById(anyString());

    wishlistService.deleteWishlist("wishlistId");

    verify(repository).existsById(anyString());
    verify(repository).deleteById(anyString());
  }

  @Test
  void deleteWishlsit_InvalidWishlistId() {
    when(repository.existsById(anyString())).thenReturn(false);

    assertThrows(WishlistNotFoundException.class, () -> wishlistService.deleteWishlist("wishlistId"));
    verify(repository).existsById(anyString());
  }

  @Test
  void getWishlist_success() {
    Wishlist expectedWishlist = aWishlist();
    when(repository.findById(anyString())).thenReturn(of(expectedWishlist));

    Wishlist actualWishlist = wishlistService.getWishlist("generatedId");

    assertNotNull(actualWishlist);
    assertSame(expectedWishlist, actualWishlist);
    verify(repository).findById(anyString());
  }
}