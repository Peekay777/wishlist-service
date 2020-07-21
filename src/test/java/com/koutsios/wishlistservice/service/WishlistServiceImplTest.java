package com.koutsios.wishlistservice.service;

import static com.koutsios.wishlistservice.fixture.WishlistFixture.aNewWishlist;
import static com.koutsios.wishlistservice.fixture.WishlistFixture.aWishlist;
import static com.koutsios.wishlistservice.fixture.WishlistFixture.anUpdateWishlist;
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
import com.koutsios.wishlistservice.dto.UpdateWishlist;
import com.koutsios.wishlistservice.exception.WishlistNotFoundException;
import com.koutsios.wishlistservice.repository.WishlistRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@DisplayName("Wishlist Service method test")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class WishlistServiceImplTest {

  @Autowired
  private WishlistService wishlistService;

  @MockBean
  private WishlistRepository repository;

  @Test
  @DisplayName("newWishlist - Create new wishlist successfully")
  void newWishlist_success() {
    when(repository.save(any(Wishlist.class))).thenReturn(aNewWishlist());

    Wishlist actualWishlist = wishlistService.createWishlist("userid","wishlistname");

    assertNotNull(actualWishlist.getId());
    assertEquals("userid", actualWishlist.getUserId());
    assertEquals("wishlistname", actualWishlist.getName());
    assertTrue(actualWishlist.getWanted().isEmpty());
    verify(repository).save(any(Wishlist.class));
  }

  @Test
  @DisplayName("getWishlist - Retrieve wishlist successfully")
  void getWishlist_success() {
    Wishlist expectedWishlist = aWishlist();
    when(repository.findById(anyString())).thenReturn(of(expectedWishlist));

    Wishlist actualWishlist = wishlistService.getWishlist("generatedId");

    assertNotNull(actualWishlist);
    assertSame(expectedWishlist, actualWishlist);
    verify(repository).findById(anyString());
  }

  @Test
  @DisplayName("getWishlist - Invalid wishlistId - WIshlistNotFoundException")
  void getWishlist_InvalidWishlistId() {
    when(repository.findById(anyString())).thenReturn(Optional.empty());

    assertThrows(WishlistNotFoundException.class, () -> wishlistService.getWishlist("wishlistId"));

    verify(repository).findById(anyString());
  }

  @Test
  @DisplayName("updateWishlist - Update wishlist successfully")
  void updateWishlist_success() {
    UpdateWishlist updateWishlist = anUpdateWishlist();
    Wishlist wishlist = aWishlist();
    Wishlist expectedWishlist = aWishlist();
    expectedWishlist.setName(updateWishlist.getName());
    expectedWishlist.setGroupIds(updateWishlist.getGroupIds());
    when(repository.findById(anyString())).thenReturn(of(wishlist));
    when(repository.save(any(Wishlist.class))).thenReturn(expectedWishlist);

    Wishlist actualWishlist = wishlistService.updateWishlist("generatedId", updateWishlist);

    assertNotNull(actualWishlist);
    assertSame(expectedWishlist, actualWishlist);
    verify(repository).findById(anyString());
    verify(repository).save(any(Wishlist.class));
  }

  @Test
  @DisplayName("updateWishlist - Invalid wishlistId - WIshlistNotFoundException")
  void updateWishlist_InvalidWishlistId() {
    UpdateWishlist updateWishlist = anUpdateWishlist();
    when(repository.findById(anyString())).thenReturn(Optional.empty());

    assertThrows(WishlistNotFoundException.class, () -> wishlistService.updateWishlist("wishlistId", updateWishlist));

    verify(repository).findById(anyString());
  }

  @Test
  @DisplayName("deleteWishlist - Delete wishlist successfully")
  void deleteWishlist_success() {
    when(repository.existsById(anyString())).thenReturn(true);
    doNothing().when(repository).deleteById(anyString());

    wishlistService.deleteWishlist("wishlistId");

    verify(repository).existsById(anyString());
    verify(repository).deleteById(anyString());
  }

  @Test
  @DisplayName("deleteWishlist - Invalid wishlistId - WishlistNotFoundException")
  void deleteWishlist_InvalidWishlistId() {
    when(repository.existsById(anyString())).thenReturn(false);

    assertThrows(WishlistNotFoundException.class, () -> wishlistService.deleteWishlist("wishlistId"));
    verify(repository).existsById(anyString());
  }
}