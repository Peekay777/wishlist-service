package com.koutsios.wishlistservice.controller;

import static com.koutsios.wishlistservice.fixture.WishlistFixture.aNewWishlist;
import static com.koutsios.wishlistservice.fixture.WishlistFixture.aWishlist;
import static com.koutsios.wishlistservice.fixture.WishlistFixture.anUpdateWishlist;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koutsios.wishlistservice.dto.UpdateWishlist;
import com.koutsios.wishlistservice.exception.WishlistNotFoundException;
import com.koutsios.wishlistservice.service.WishlistService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = WishlistController.class)
@DisplayName("Wishlist Controller method tests")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class WishlistControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private WishlistService wishlistService;

  @Test
  @DisplayName("POST Wishlist - 201 Created")
  void createNewWishlist_success() throws Exception {
    when(wishlistService.createWishlist(anyString(), anyString())).thenReturn(aNewWishlist());

    mockMvc.perform(post("/wishlist/userid/wishlistname"))
        .andExpect(status().isCreated());

    verify(wishlistService).createWishlist(anyString(), anyString());
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
  @DisplayName("PUT Wishlist - 201 Created")
  void putWishlist_success() throws Exception {
    when(wishlistService.updateWishlist(anyString(), any(UpdateWishlist.class))).thenReturn(aWishlist());

    mockMvc.perform(put("/wishlist/wishlistId")
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(anUpdateWishlist())))
        .andExpect(status().isCreated());

    verify(wishlistService).updateWishlist(anyString(), any(UpdateWishlist.class));
  }

  @Test
  @DisplayName("PUT Wishlist - Given invalid wishlist id - 404 Not Found")
  void putWishlist_InvalidWishlistId_return404() throws Exception {
    when(wishlistService.updateWishlist(anyString(), any(UpdateWishlist.class))).thenThrow(new WishlistNotFoundException("wishlistid"));

    mockMvc.perform(put("/wishlist/wishlistId")
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(anUpdateWishlist())))
        .andExpect(status().isNotFound());

    verify(wishlistService).updateWishlist(anyString(), any(UpdateWishlist.class));
  }

  @Test
  @DisplayName("DELETE Wishlist - 204 No Content ")
  void deleteWishlist_success() throws Exception {
    mockMvc.perform(delete("/wishlist/wishlistid"))
        .andExpect(status().isNoContent());

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