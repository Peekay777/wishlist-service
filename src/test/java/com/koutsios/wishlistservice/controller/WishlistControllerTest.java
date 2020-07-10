package com.koutsios.wishlistservice.controller;

import static com.koutsios.wishlistservice.fixture.WishlistFixture.aNewWishlist;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.koutsios.wishlistservice.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = WishlistController.class)
class WishlistControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private WishlistService wishlistService;

  @Test
  void createNewWishlist_success() throws Exception {
    when(wishlistService.newWishlist(anyString(), anyString())).thenReturn(aNewWishlist());

    mockMvc.perform(post("/wishlist/userid/wishlistname"))
        .andExpect(status().isOk());

    verify(wishlistService).newWishlist(anyString(), anyString());
  }

}