package com.buysmart.api.pojo;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * 
 * @author Iniyan
 * 
 * EbayPOJO is used to construct the main object from the
 * API call. This will be further used by the products factory but
 * a refined POJO object.
 *
 */
public final class EbayPOJO {
    public String getTimestamp() {
		return timestamp;
	}

	public String getAck() {
		return ack;
	}

	public String getBuild() {
		return build;
	}

	public String getVersion() {
		return version;
	}

	public Item getItem() {
		return item;
	}

	public final String timestamp;
    public final String ack;
    public final String build;
    public final String version;
    public final Item item;

    @JsonCreator
    public EbayPOJO(@JsonProperty("Timestamp") String timestamp, @JsonProperty("Ack") String ack, @JsonProperty("Build") String build, @JsonProperty("Version") String version, @JsonProperty("Item") Item item){
        this.timestamp = timestamp;
        this.ack = ack;
        this.build = build;
        this.version = version;
        this.item = item;
    }

    public static final class Item {
        public boolean isBestOfferEnabled() {
			return bestOfferEnabled;
		}

		public String getItemID() {
			return itemID;
		}

		public String getEndTime() {
			return endTime;
		}

		public String getStartTime() {
			return startTime;
		}

		public String getViewItemURLForNaturalSearch() {
			return viewItemURLForNaturalSearch;
		}

		public String getListingType() {
			return listingType;
		}

		public String getLocation() {
			return location;
		}

		public String[] getPaymentMethods() {
			return paymentMethods;
		}

		public String getGalleryURL() {
			return galleryURL;
		}

		public String[] getPictureURL() {
			return pictureURL;
		}

		public String getPostalCode() {
			return postalCode;
		}

		public String getPrimaryCategoryID() {
			return primaryCategoryID;
		}

		public String getPrimaryCategoryName() {
			return primaryCategoryName;
		}

		public long getQuantity() {
			return quantity;
		}

		public Seller getSeller() {
			return seller;
		}

		public long getBidCount() {
			return bidCount;
		}

		public ConvertedCurrentPrice getConvertedCurrentPrice() {
			return convertedCurrentPrice;
		}

		public CurrentPrice getCurrentPrice() {
			return currentPrice;
		}

		public String getListingStatus() {
			return listingStatus;
		}

		public long getQuantitySold() {
			return quantitySold;
		}

		public String[] getShipToLocations() {
			return shipToLocations;
		}

		public String getSite() {
			return site;
		}

		public String getTimeLeft() {
			return timeLeft;
		}

		public String getTitle() {
			return title;
		}

		public long getHitCount() {
			return hitCount;
		}

		public String getPrimaryCategoryIDPath() {
			return primaryCategoryIDPath;
		}

		public Storefront getStorefront() {
			return storefront;
		}

		public String getCountry() {
			return country;
		}

		public ReturnPolicy getReturnPolicy() {
			return returnPolicy;
		}

		public ProductID getProductID() {
			return productID;
		}

		public boolean isAutoPay() {
			return autoPay;
		}

		public PaymentAllowedSite[] getPaymentAllowedSite() {
			return paymentAllowedSite;
		}

		public boolean isIntegratedMerchantCreditCardEnabled() {
			return integratedMerchantCreditCardEnabled;
		}

		public long getHandlingTime() {
			return handlingTime;
		}

		public String getQuantityAvailableHint() {
			return quantityAvailableHint;
		}

		public long getQuantityThreshold() {
			return quantityThreshold;
		}

		public String[] getExcludeShipToLocation() {
			return excludeShipToLocation;
		}

		public boolean isGlobalShipping() {
			return globalShipping;
		}

		public long getQuantitySoldByPickupInStore() {
			return quantitySoldByPickupInStore;
		}

		public String getsKU() {
			return sKU;
		}

		public boolean isNewBestOffer() {
			return newBestOffer;
		}

		public final boolean bestOfferEnabled;
        public final String itemID;
        public final String endTime;
        public final String startTime;
        public final String viewItemURLForNaturalSearch;
        public final String listingType;
        public final String location;
        public final String[] paymentMethods;
        public final String galleryURL;
        public final String[] pictureURL;
        public final String postalCode;
        public final String primaryCategoryID;
        public final String primaryCategoryName;
        public final long quantity;
        public final Seller seller;
        public final long bidCount;
        public final ConvertedCurrentPrice convertedCurrentPrice;
        public final CurrentPrice currentPrice;
        public final String listingStatus;
        public final long quantitySold;
        public final String[] shipToLocations;
        public final String site;
        public final String timeLeft;
        public final String title;
        public final long hitCount;
        public final String primaryCategoryIDPath;
        public final Storefront storefront;
        public final String country;
        public final ReturnPolicy returnPolicy;
        public final ProductID productID;
        public final boolean autoPay;
        public final PaymentAllowedSite paymentAllowedSite[];
        public final boolean integratedMerchantCreditCardEnabled;
        public final long handlingTime;
        public final String quantityAvailableHint;
        public final long quantityThreshold;
        public final String[] excludeShipToLocation;
        public final boolean globalShipping;
        public final long quantitySoldByPickupInStore;
        public final String sKU;
        public final boolean newBestOffer;

        @JsonCreator
        public Item(@JsonProperty("BestOfferEnabled") boolean bestOfferEnabled, @JsonProperty("ItemID") String itemID, @JsonProperty("EndTime") String endTime, @JsonProperty("StartTime") String startTime, @JsonProperty("ViewItemURLForNaturalSearch") String viewItemURLForNaturalSearch, @JsonProperty("ListingType") String listingType, @JsonProperty("Location") String location, @JsonProperty("PaymentMethods") String[] paymentMethods, 
        		@JsonProperty("GalleryURL") String galleryURL, @JsonProperty("PictureURL") String[] pictureURL, @JsonProperty("PostalCode") String postalCode, @JsonProperty("PrimaryCategoryID") String primaryCategoryID, @JsonProperty("PrimaryCategoryName") String primaryCategoryName, @JsonProperty("Quantity") long quantity, @JsonProperty("Seller") Seller seller, @JsonProperty("BidCount") long bidCount, @JsonProperty("ConvertedCurrentPrice") ConvertedCurrentPrice convertedCurrentPrice, 
        		@JsonProperty("CurrentPrice") CurrentPrice currentPrice, @JsonProperty("ListingStatus") String listingStatus, @JsonProperty("QuantitySold") long quantitySold, @JsonProperty("ShipToLocations") String[] shipToLocations, @JsonProperty("Site") String site, @JsonProperty("TimeLeft") String timeLeft, @JsonProperty("Title") String title,
        		@JsonProperty("HitCount") long hitCount, @JsonProperty("PrimaryCategoryIDPath") String primaryCategoryIDPath,
        		@JsonProperty("Storefront") Storefront storefront, @JsonProperty("Country") String country, @JsonProperty("ReturnPolicy") ReturnPolicy returnPolicy, 
        		@JsonProperty("ProductID") ProductID productID, @JsonProperty("AutoPay") boolean autoPay, @JsonProperty("PaymentAllowedSite") PaymentAllowedSite[] paymentAllowedSite,
        		@JsonProperty("IntegratedMerchantCreditCardEnabled") boolean integratedMerchantCreditCardEnabled, @JsonProperty("HandlingTime") long handlingTime, 
        		@JsonProperty("QuantityAvailableHint") String quantityAvailableHint, @JsonProperty("QuantityThreshold") long quantityThreshold,
        		@JsonProperty("ExcludeShipToLocation") String[] excludeShipToLocation, 
        		@JsonProperty("GlobalShipping") boolean globalShipping, @JsonProperty("QuantitySoldByPickupInStore") long quantitySoldByPickupInStore, @JsonProperty("SKU") String sKU, @JsonProperty("NewBestOffer") boolean newBestOffer){
            this.bestOfferEnabled = bestOfferEnabled;
            this.itemID = itemID;
            this.endTime = endTime;
            this.startTime = startTime;
            this.viewItemURLForNaturalSearch = viewItemURLForNaturalSearch;
            this.listingType = listingType;
            this.location = location;
            this.paymentMethods = paymentMethods;
            this.galleryURL = galleryURL;
            this.pictureURL = pictureURL;
            this.postalCode = postalCode;
            this.primaryCategoryID = primaryCategoryID;
            this.primaryCategoryName = primaryCategoryName;
            this.quantity = quantity;
            this.seller = seller;
            this.bidCount = bidCount;
            this.convertedCurrentPrice = convertedCurrentPrice;
            this.currentPrice = currentPrice;
            this.listingStatus = listingStatus;
            this.quantitySold = quantitySold;
            this.shipToLocations = shipToLocations;
            this.site = site;
            this.timeLeft = timeLeft;
            this.title = title;
            this.hitCount = hitCount;
            this.primaryCategoryIDPath = primaryCategoryIDPath;
            this.storefront = storefront;
            this.country = country;
            this.returnPolicy = returnPolicy;
            this.productID = productID;
            this.autoPay = autoPay;
            this.paymentAllowedSite = paymentAllowedSite;
            this.integratedMerchantCreditCardEnabled = integratedMerchantCreditCardEnabled;
            this.handlingTime = handlingTime;
            this.quantityAvailableHint = quantityAvailableHint;
            this.quantityThreshold = quantityThreshold;
            this.excludeShipToLocation = excludeShipToLocation;
            this.globalShipping = globalShipping;
            this.quantitySoldByPickupInStore = quantitySoldByPickupInStore;
            this.sKU = sKU;
            this.newBestOffer = newBestOffer;
        }

        public static final class Seller {
            public String getUserID() {
				return userID;
			}

			public String getFeedbackRatingStar() {
				return feedbackRatingStar;
			}

			public long getFeedbackScore() {
				return feedbackScore;
			}

			public long getPositiveFeedbackPercent() {
				return positiveFeedbackPercent;
			}

			public final String userID;
            public final String feedbackRatingStar;
            public final long feedbackScore;
            public final long positiveFeedbackPercent;
    
            @JsonCreator
            public Seller(@JsonProperty("UserID") String userID, @JsonProperty("FeedbackRatingStar") String feedbackRatingStar, @JsonProperty("FeedbackScore") long feedbackScore, @JsonProperty("PositiveFeedbackPercent") long positiveFeedbackPercent){
                this.userID = userID;
                this.feedbackRatingStar = feedbackRatingStar;
                this.feedbackScore = feedbackScore;
                this.positiveFeedbackPercent = positiveFeedbackPercent;
            }
        }

        public static final class ConvertedCurrentPrice {
            public final double value;
            public double getValue() {
				return value;
			}

			public String getCurrencyID() {
				return currencyID;
			}

			public final String currencyID;
    
            @JsonCreator
            public ConvertedCurrentPrice(@JsonProperty("Value") double value, @JsonProperty("CurrencyID") String currencyID){
                this.value = value;
                this.currencyID = currencyID;
            }
        }

        public static final class CurrentPrice {
            public final double value;
            public double getValue() {
				return value;
			}

			public String getCurrencyID() {
				return currencyID;
			}

			public final String currencyID;
    
            @JsonCreator
            public CurrentPrice(@JsonProperty("Value") double value, @JsonProperty("CurrencyID") String currencyID){
                this.value = value;
                this.currencyID = currencyID;
            }
        }

        public static final class Storefront {
            public final String storeURL;
            public String getStoreURL() {
				return storeURL;
			}

			public String getStoreName() {
				return storeName;
			}

			public final String storeName;
    
            @JsonCreator
            public Storefront(@JsonProperty("StoreURL") String storeURL, @JsonProperty("StoreName") String storeName){
                this.storeURL = storeURL;
                this.storeName = storeName;
            }
        }

        public static final class ReturnPolicy {
            public String getRefund() {
				return refund;
			}

			public String getReturnsWithin() {
				return returnsWithin;
			}

			public String getReturnsAccepted() {
				return returnsAccepted;
			}

			public String getDescription() {
				return description;
			}

			public String getShippingCostPaidBy() {
				return shippingCostPaidBy;
			}

			public final String refund;
            public final String returnsWithin;
            public final String returnsAccepted;
            public final String description;
            public final String shippingCostPaidBy;
    
            @JsonCreator
            public ReturnPolicy(@JsonProperty("Refund") String refund, @JsonProperty("ReturnsWithin") String returnsWithin, @JsonProperty("ReturnsAccepted") String returnsAccepted, @JsonProperty("Description") String description, @JsonProperty("ShippingCostPaidBy") String shippingCostPaidBy){
                this.refund = refund;
                this.returnsWithin = returnsWithin;
                this.returnsAccepted = returnsAccepted;
                this.description = description;
                this.shippingCostPaidBy = shippingCostPaidBy;
            }
        }

        public static final class ProductID {
            public final String value;
            public final String type;
    
            @JsonCreator
            public ProductID(@JsonProperty("Value") String value, @JsonProperty("Type") String type){
                this.value = value;
                this.type = type;
            }
        }

        public static final class PaymentAllowedSite {
    
            @JsonCreator
            public PaymentAllowedSite(){
            }
        }
    }
}