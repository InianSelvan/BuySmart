package com.buysmart.api.pojo;

/**
 * 
 * @author Iniyan
 * 
 * AmazonPOJO is used to construct the main object from the
 * API call. This will be further used by the products factory but
 * a refined POJO object.
 *
 */
public class AmazonPOJO {
	
	private String freeShipping;

    private String department;

    private String image;

    private String shippingCost;

    private String kind;

    private String thumbnailImage;

    private String sku;

    private String upc;

    private String customerReviewAverage;

    private String url;

    private String onlineAvailability;

    private String source;

    private String shortDescription;

    private String customerReviewCount;

    private String manufacturer;

    private String name;

    private String subclass;

    private String longDescription;

    private String addToCartUrl;

    private String salePrice;

    private String regularPrice;
	
	private String inStoreAvailability;

    public String getInStoreAvailability() {
		return inStoreAvailability;
	}

	public void setInStoreAvailability(String inStoreAvailability) {
		this.inStoreAvailability = inStoreAvailability;
	}

	public String getFreeShipping() {
		return freeShipping;
	}

	public void setFreeShipping(String freeShipping) {
		this.freeShipping = freeShipping;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(String shippingCost) {
		this.shippingCost = shippingCost;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getThumbnailImage() {
		return thumbnailImage;
	}

	public void setThumbnailImage(String thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getCustomerReviewAverage() {
		return customerReviewAverage.substring(0, 2);
	}

	public void setCustomerReviewAverage(String customerReviewAverage) {
		this.customerReviewAverage = customerReviewAverage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOnlineAvailability() {
		return onlineAvailability;
	}

	public void setOnlineAvailability(String onlineAvailability) {
		this.onlineAvailability = onlineAvailability;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getCustomerReviewCount() {
		return customerReviewCount;
	}

	public void setCustomerReviewCount(String customerReviewCount) {
		this.customerReviewCount = customerReviewCount;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubclass() {
		return subclass;
	}

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getAddToCartUrl() {
		return addToCartUrl;
	}

	public void setAddToCartUrl(String addToCartUrl) {
		this.addToCartUrl = addToCartUrl;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(String regularPrice) {
		this.regularPrice = regularPrice;
	}

	

}
