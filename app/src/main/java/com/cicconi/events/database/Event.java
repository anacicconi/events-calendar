package com.cicconi.events.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "event")
public class Event implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Integer id;
    @ColumnInfo(name = "api_id")
    public String apiId;
    @ColumnInfo(name = "access_type")
    private String accessType;
    @ColumnInfo(name ="address_street")
    private String addressStreet;
    private Double latitude;
    private Double longitude;
    private String category;
    private String title;
    @ColumnInfo(name = "cover_alt")
    private String coverAlt;
    @ColumnInfo(name = "date_start")
    private Long dateStart;
    @ColumnInfo(name = "address_name")
    private String addressName;
    private String description;
    @ColumnInfo(name = "contact_mail")
    private String contactMail;
    @ColumnInfo(name = "lead_text")
    private String leadText;
    @ColumnInfo(name = "cover_url")
    private String coverUrl;
    @ColumnInfo(name = "address_city")
    private String addressCity;
    @ColumnInfo(name = "price_type")
    private String priceType;
    private String url;
    @ColumnInfo(name = "date_description")
    private String dateDescription;
    @ColumnInfo(name = "address_zipcode")
    private String addressZipcode;
    @ColumnInfo(name = "price_detail")
    private String priceDetail;
    @ColumnInfo(name = "contact_phone")
    private String contactPhone;
    @ColumnInfo(name = "contact_url")
    private String contactUrl;
    @ColumnInfo(name = "access_mail")
    private String accessMail;
    @ColumnInfo(name = "contact_facebook")
    private String contactFacebook;
    @ColumnInfo(name = "access_phone")
    private String accessPhone;
    public Boolean favorite;

    @Ignore
    public Event(String apiId, String accessType, String addressStreet, Double latitude, Double longitude,
        String category, String title, String coverAlt, Long dateStart, String addressName, String description,
        String contactMail, String leadText, String coverUrl, String addressCity, String priceType, String url,
        String dateDescription, String addressZipcode, String priceDetail, String contactPhone, String contactUrl,
        String accessMail, String contactFacebook, String accessPhone, Boolean favorite) {
        this.apiId = apiId;
        this.accessType = accessType;
        this.addressStreet = addressStreet;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.title = title;
        this.coverAlt = coverAlt;
        this.dateStart = dateStart;
        this.addressName = addressName;
        this.description = description;
        this.contactMail = contactMail;
        this.leadText = leadText;
        this.coverUrl = coverUrl;
        this.addressCity = addressCity;
        this.priceType = priceType;
        this.url = url;
        this.dateDescription = dateDescription;
        this.addressZipcode = addressZipcode;
        this.priceDetail = priceDetail;
        this.contactPhone = contactPhone;
        this.contactUrl = contactUrl;
        this.accessMail = accessMail;
        this.contactFacebook = contactFacebook;
        this.accessPhone = accessPhone;
        this.favorite = favorite;
    }

    public Event(int id, String apiId, String accessType, String addressStreet, Double latitude, Double longitude,
        String category, String title, String coverAlt, Long dateStart, String addressName, String description,
        String contactMail, String leadText, String coverUrl, String addressCity, String priceType, String url,
        String dateDescription, String addressZipcode, String priceDetail, String contactPhone, String contactUrl,
        String accessMail, String contactFacebook, String accessPhone, Boolean favorite) {
        this.id = id;
        this.apiId = apiId;
        this.accessType = accessType;
        this.addressStreet = addressStreet;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.title = title;
        this.coverAlt = coverAlt;
        this.dateStart = dateStart;
        this.addressName = addressName;
        this.description = description;
        this.contactMail = contactMail;
        this.leadText = leadText;
        this.coverUrl = coverUrl;
        this.addressCity = addressCity;
        this.priceType = priceType;
        this.url = url;
        this.dateDescription = dateDescription;
        this.addressZipcode = addressZipcode;
        this.priceDetail = priceDetail;
        this.contactPhone = contactPhone;
        this.contactUrl = contactUrl;
        this.accessMail = accessMail;
        this.contactFacebook = contactFacebook;
        this.accessPhone = accessPhone;
        this.favorite = favorite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverAlt() {
        return coverAlt;
    }

    public void setCoverAlt(String coverAlt) {
        this.coverAlt = coverAlt;
    }

    public Long getDateStart() {
        return dateStart;
    }

    public void setDateStart(Long dateStart) {
        this.dateStart = dateStart;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public String getLeadText() {
        return leadText;
    }

    public void setLeadText(String leadText) {
        this.leadText = leadText;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDateDescription() {
        return dateDescription;
    }

    public void setDateDescription(String dateDescription) {
        this.dateDescription = dateDescription;
    }

    public String getAddressZipcode() {
        return addressZipcode;
    }

    public void setAddressZipcode(String addressZipcode) {
        this.addressZipcode = addressZipcode;
    }

    public String getPriceDetail() {
        return priceDetail;
    }

    public void setPriceDetail(String priceDetail) {
        this.priceDetail = priceDetail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getAccessMail() {
        return accessMail;
    }

    public void setAccessMail(String accessMail) {
        this.accessMail = accessMail;
    }

    public String getContactFacebook() {
        return contactFacebook;
    }

    public void setContactFacebook(String contactFacebook) {
        this.contactFacebook = contactFacebook;
    }

    public String getAccessPhone() {
        return accessPhone;
    }

    public void setAccessPhone(String accessPhone) {
        this.accessPhone = accessPhone;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
