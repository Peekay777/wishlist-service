package com.koutsios.wishlistservice.domain;

import com.koutsios.wishlistservice.constant.ProductStatus;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  @NotBlank(message = "Product must have name")
  private String name;

  @URL(message = "Invalid link to product details")
  private String productDetailsLink;

  @NotBlank(message = "Product must have a valid status")
  private ProductStatus status;

}
