package com.koutsios.wishlistservice.controller;

import static com.koutsios.wishlistservice.fixture.WishlistFixture.aNewWishlist;
import static com.koutsios.wishlistservice.fixture.WishlistFixture.aWishlist;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.koutsios.wishlistservice.exception.WishlistNotFoundException;
import com.koutsios.wishlistservice.service.WishlistService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = WishlistController.class)
@DisplayName("Wishlist Controller method tests")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class WishlistControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private WishlistService wishlistService;

  @Test
  @DisplayName("POST Wishlist - 200 ok")
  void createNewWishlist_success() throws Exception {
    when(wishlistService.newWishlist(anyString(), anyString())).thenReturn(aNewWishlist());

    mockMvc.perform(post("/wishlist/userid/wishlistname"))
        .andExpect(status().isOk());

    verify(wishlistService).newWishlist(anyString(), anyString());
  }

  @Test
  @DisplayName("GET Wishlist - 200 ok")
  void getWishlist_success() throws Exception {
    when(wishlistService.getWishlist(anyString())).thenReturn(aWishlist());

    mockMvc.perform(get("/wishlist/wishlistId"))
        .andExpect(status().isOk());

    verify(wishlistService).getWishlist(anyString());
  }

  @Test
  @DisplayName("GET Wishlist - Given Invalid WishlistId - 404 Not Found")
  void getWishlist_invalidWishlistId_return404() throws Exception {
    when(wishlistService.getWishlist(anyString())).thenThrow(new WishlistNotFoundException("wishlistid"));

    mockMvc.perform(get("/wishlist/wishlistid"))
        .andExpect(status().isNotFound());

    verify(wishlistService).getWishlist(anyString());
  }

  @Test
  @DisplayName("DELETE Wishlist - 200 ok ")
  void deleteWishlist_success() throws Exception {
    mockMvc.perform(delete("/wishlist/wishlistid"))
        .andExpect(status().isOk());

    verify(wishlistService).deleteWishlist(anyString());
  }

  @Test
  @DisplayName("DELETE Wishlist - Given invalid wishlist id - 404 Not Found")
  void deleteWishlist_InvalidWishlistId_return404() throws Exception {
    doThrow(new WishlistNotFoundException("wishlistid")).when(wishlistService).deleteWishlist(anyString());

    mockMvc.perform(delete("/wishlist/wishlistid"))
        .andExpect(status().isNotFound());

    verify(wishlistService).deleteWishlist(anyString());
  }



}