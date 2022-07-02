package org.wakuluk.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RentalAgreement {
  @JsonProperty("Tool Code")
  private String toolCode;

  @JsonProperty("Tool Type")
  private String toolType;

  @JsonProperty("Tool Brand")
  private String toolBrand;

  @JsonProperty("Rental Days")
  private int rentalDays;

  @JsonProperty("Checkout Date")
  private String checkoutDate;

  @JsonProperty("Due Date")
  private String dueDate;

  @JsonProperty("Daily Rental Charge")
  private String dailyRentalCharge;

  @JsonProperty("Charge Days")
  private int chargeDays;

  @JsonProperty("Pre-discount charge")
  private String preDiscountCharge;

  @JsonProperty("Discount percent")
  private String discountPercent;

  @JsonProperty("Discount amount")
  private String discountAmount;

  @JsonProperty("Final Charge")
  private String finalCharge;
}
