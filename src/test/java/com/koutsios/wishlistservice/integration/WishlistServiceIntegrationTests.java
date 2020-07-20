package com.koutsios.wishlistservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koutsios.wishlistservice.AbstractIntegrationTestWithMongoDb;
import com.koutsios.wishlistservice.domain.Wishlist;
import com.koutsios.wishlistservice.repository.WishlistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration Tests")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class WishlistServiceIntegrationTests extends AbstractIntegrationTestWithMongoDb {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WishlistRepository repository;

	@Test
	@DisplayName("Create new wishlist")
	void createNewWishlist() throws Exception {
		String userId = "userIdExample";
		String wishlistName = "wishlistNameExample";

		String response = mockMvc.perform(post("/wishlist/{userId}/{wishlistName}", userId, wishlistName))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		Wishlist wishlistResponse = objectMapper.readValue(response, Wishlist.class);

		assertNotNull(wishlistResponse);
		assertEquals(userId, wishlistResponse.getUserId());
		assertEquals(wishlistName, wishlistResponse.getName());
		Wishlist wishlist = repository.findById(wishlistResponse.getId()).orElseThrow();
		assertEquals(userId, wishlist.getUserId());
		assertEquals(wishlistName, wishlist.getName());
	}
}
