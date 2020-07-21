package com.koutsios.wishlistservice.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWishlist {

  private List<String> groupIds;

  @NotBlank(message = "Wishlist name must have a value")
  private String name;

}
