package com.koutsios.wishlistservice.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koutsios.wishlistservice.domain.Wishlist;
import com.koutsios.wishlistservice.dto.UpdateWishlist;
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
	@DisplayName("Given a user Id and a wishlist name then create new wishlist")
	void createNewWishlistSuccess() throws Exception {
		String userId = "userIdExample";
		String wishlistName = "wishlistNameExample";

		Wishlist wishlistResponse = createWishlist(userId, wishlistName, mockMvc, objectMapper);

		Wishlist wishlist = repository.findById(wishlistResponse.getId()).orElseThrow();
		assertEquals(wishlistResponse.getUserId(), wishlist.getUserId());
		assertEquals(wishlistResponse.getName(), wishlist.getName());
	}

	@Test
	@DisplayName("Given a wishlist Id then return wishlist object")
	void getWishlist_success() throws Exception {
		String userId = "userIdExample";
		String wishlistName = "wishlistNameExample";
		Wishlist createResponse = createWishlist(userId, wishlistName, mockMvc, objectMapper);
		String wishlistId = createResponse.getId();

		String response = mockMvc.perform(get("/wishlist/{wishlistId}", wishlistId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userId", is(userId)))
				.andExpect(jsonPath("$.name", is(wishlistName)))
				.andReturn()
				.getResponse()
				.getContentAsString();
		Wishlist wishlistResponse = objectMapper.readValue(response, Wishlist.class);

		Wishlist wishlistDb = repository.findById(wishlistId).orElseThrow();
		assertEquals(wishlistResponse.getUserId(), wishlistDb.getUserId());
		assertEquals(wishlistResponse.getName(), wishlistDb.getName());
		assertThat(wishlistDb.getWanted()).isEqualTo(wishlistResponse.getWanted());
	}

	@Test
	@DisplayName("Given an invalid wishlist Id then return status 404 NotFound")
	void getWishlist_invalidWishlistId_404NotFound() throws Exception {
		String wishlistId = "InvalidId";
		mockMvc.perform(get("/wishlist/{wishlistId}", wishlistId))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Could find Wishlist " + wishlistId));
	}

	@Test
	@DisplayName("Given an updated wishlist the amend wishlist")
	void updateWishlist_successful() throws Exception {
		String userId = "userIdExample";
		String wishlistName = "wishlistNameExample";
		Wishlist createResponse = createWishlist(userId, wishlistName, mockMvc, objectMapper);
		String wishlistId = createResponse.getId();
		UpdateWishlist updateWishlist = UpdateWishlist.builder()
				.groupIds(null)
				.name("NewWishlistName")
				.build();

		String response = mockMvc.perform(put("/wishlist/{wishlistId}", wishlistId)
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateWishlist)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.userId", is(createResponse.getUserId())))
				.andExpect(jsonPath("$.name", is(updateWishlist.getName())))
				.andExpect(jsonPath("$.groupIds", is(createResponse.getGroupIds())))
				.andReturn()
				.getResponse()
				.getContentAsString();
		Wishlist wishlistResponse = objectMapper.readValue(response, Wishlist.class);

		Wishlist wishlistDb = repository.findById(wishlistId).orElseThrow();
		assertEquals(wishlistResponse.getUserId(), wishlistDb.getUserId());
		assertEquals(wishlistResponse.getName(), updateWishlist.getName());
		assertThat(wishlistDb.getWanted()).isEqualTo(wishlistResponse.getWanted());
	}

	@Test
	@DisplayName("Given a wishlist Id when deleting then delete wishlist")
	void deleteWishlist_success() throws Exception {
		String userId = "userIdExample";
		String wishlistName = "wishlistNameExample";
		Wishlist createResponse = createWishlist(userId, wishlistName, mockMvc, objectMapper);
		String wishlistId = createResponse.getId();

		mockMvc.perform(delete("/wishlist/{wishlistId}", wishlistId))
				.andExpect(status().isNoContent());

		Wishlist wishlistDb = repository.findById(wishlistId).orElse(null);
		assertNull(wishlistDb);
	}

	@Test
	@DisplayName("Given an invalid wishlist Id when deleting then return status 404 NotFound")
	void deleteWishlist_invalidWishlistId_404NotFound() throws Exception {
		String wishlistId = "InvalidId";
		mockMvc.perform(delete("/wishlist/{wishlistId}", wishlistId))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Could find Wishlist " + wishlistId));
	}

	private static Wishlist createWishlist(String userId, String wishlistName, MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
		String response = mockMvc.perform(post("/wishlist/{userId}/{wishlistName}", userId, wishlistName))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.userId", is(userId)))
				.andExpect(jsonPath("$.name", is(wishlistName)))
				.andExpect(jsonPath("$.wanted").isEmpty())
				.andReturn()
				.getResponse()
				.getContentAsString();
		return objectMapper.readValue(response, Wishlist.class);
	}
}
