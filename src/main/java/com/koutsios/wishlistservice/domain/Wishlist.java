package com.koutsios.wishlistservice.domain;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wishlist")
public class Wishlist {

  @Id
  @Setter(PRIVATE)
  private String id;

  @NotBlank(message = "Wishlist must belong to a valid user")
  private String userId;

  private List<String> groupIds;

  @NotBlank(message = "Wishlist name must have a value")
  private String name;

  private Map<String, Product> wanted;

}
