package com.koutsios.wishlistservice.domain;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "wishlists")
public class Wishlist {

  @Id
  private String id;

  @NotBlank(message = "Wishlist must belong to a valid user")
  private String userId;

  private List<String> groupIds;

  @NotBlank(message = "Wishlist name must have a value")
  private String name;

  private Map<String, Product> wanted;

}
