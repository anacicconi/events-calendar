package com.cicconi.events.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

@Entity(tableName = "event")
public class Event implements Serializable {

    @NonNull
    @PrimaryKey()
    private String apiId;
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
    @ColumnInfo(name = "date_end")
    private Long dateEnd;
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
    @ColumnInfo(name = "contact_name")
    private String contactName;
    @ColumnInfo(name = "access_phone")
    private String accessPhone;
    private String transport;
    public Boolean favorite;

    public Event(@NotNull String apiId, String accessType, String addressStreet, Double latitude, Double longitude,
        String category, String title, String coverAlt, Long dateStart, Long dateEnd, String addressName, String description,
        String contactMail, String leadText, String coverUrl, String addressCity, String priceType, String url,
        String dateDescription, String addressZipcode, String priceDetail, String contactPhone, String contactUrl,
        String accessMail, String contactFacebook, String contactName, String accessPhone, String transport, Boolean favorite) {
        this.apiId = apiId;
        this.accessType = accessType;
        this.addressStreet = addressStreet;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.title = title;
        this.coverAlt = coverAlt;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
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
        this.contactName = contactName;
        this.accessPhone = accessPhone;
        this.transport = transport;
        this.favorite = favorite;
    }

    public String getApiId() {
        return apiId;
    }

    public String getAccessType() {
        return accessType;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
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

    public String getCoverAlt() {
        return coverAlt;
    }

    public Long getDateStart() {
        return dateStart;
    }

    public Long getDateEnd() {
        return dateEnd;
    }

    public String getAddressName() {
        return addressName;
    }

    public String getDescription() {
        return description;
    }

    public String getContactMail() {
        return contactMail;
    }

    public String getLeadText() {
        return leadText;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public String getPriceType() {
        return this.priceType;
    }

    public EventPriceType getEventPriceType() {
        if(priceType.equals("gratuit")) {
            return EventPriceType.FREE;
        }

        if(priceType.equals("conso")) {
            return EventPriceType.BEVERAGE;
        }

        if(priceType.equals("payant")) {
            return EventPriceType.ENTRANCE;
        }

        return EventPriceType.ENTRANCE;
    }

    public String getUrl() {
        return url;
    }

    public String getDateDescription() {
        return dateDescription;
    }

    public String getAddressZipcode() {
        return addressZipcode;
    }

    public String getPriceDetail() {
        return priceDetail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public String getAccessMail() {
        return accessMail;
    }

    public String getContactFacebook() {
        return contactFacebook;
    }

    public String getContactName() {
        return contactName;
    }

    public String getAccessPhone() {
        return accessPhone;
    }

    public String getTransport() {
        return transport;
    }
}
