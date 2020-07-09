package com.cicconi.events.model;

import java.util.Date;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FieldsResponse {

    @SerializedName("blind")
    @Expose
    private Integer blind;
    @SerializedName("pmr")
    @Expose
    private Integer pmr;
    @SerializedName("date_end")
    @Expose
    private Date dateEnd;
    @SerializedName("deaf")
    @Expose
    private Integer deaf;
    @SerializedName("updated_at")
    @Expose
    private Date updatedAt;
    @SerializedName("access_type")
    @Expose
    private String accessType;
    @SerializedName("occurrences")
    @Expose
    private String occurrences;
    @SerializedName("address_street")
    @Expose
    private String addressStreet;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lat_lon")
    @Expose
    private List<Double> latLon = null;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("cover_alt")
    @Expose
    private String coverAlt;
    @SerializedName("date_start")
    @Expose
    private Date dateStart;
    @SerializedName("address_name")
    @Expose
    private String addressName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("contact_mail")
    @Expose
    private String contactMail;
    @SerializedName("lead_text")
    @Expose
    private String leadText;
    @SerializedName("cover_url")
    @Expose
    private String coverUrl;
    @SerializedName("cover_credit")
    @Expose
    private String coverCredit;
    @SerializedName("address_city")
    @Expose
    private String addressCity;
    @SerializedName("programs")
    @Expose
    private String programs;
    @SerializedName("price_type")
    @Expose
    private String priceType;
    @SerializedName("cover")
    @Expose
    private CoverResponse cover;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("date_description")
    @Expose
    private String dateDescription;
    @SerializedName("address_zipcode")
    @Expose
    private String addressZipcode;
    @SerializedName("contact_name")
    @Expose
    private String contactName;
    @SerializedName("price_detail")
    @Expose
    private String priceDetail;
    @SerializedName("contact_phone")
    @Expose
    private String contactPhone;
    @SerializedName("access_link")
    @Expose
    private String accessLink;
    @SerializedName("contact_url")
    @Expose
    private String contactUrl;
    @SerializedName("access_mail")
    @Expose
    private String accessMail;
    @SerializedName("contact_facebook")
    @Expose
    private String contactFacebook;
    @SerializedName("access_phone")
    @Expose
    private String accessPhone;
    @SerializedName("transport")
    @Expose
    private String transport;

    public Integer getBlind() {
        return blind;
    }

    public void setBlind(Integer blind) {
        this.blind = blind;
    }

    public Integer getPmr() {
        return pmr;
    }

    public void setPmr(Integer pmr) {
        this.pmr = pmr;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getDeaf() {
        return deaf;
    }

    public void setDeaf(Integer deaf) {
        this.deaf = deaf;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(String occurrences) {
        this.occurrences = occurrences;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Double> getLatLon() {
        return latLon;
    }

    public void setLatLon(List<Double> latLon) {
        this.latLon = latLon;
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public String getCoverCredit() {
        return coverCredit;
    }

    public void setCoverCredit(String coverCredit) {
        this.coverCredit = coverCredit;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getPrograms() {
        return programs;
    }

    public void setPrograms(String programs) {
        this.programs = programs;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public CoverResponse getCover() {
        return cover;
    }

    public void setCover(CoverResponse cover) {
        this.cover = cover;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
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

    public String getAccessLink() {
        return accessLink;
    }

    public void setAccessLink(String accessLink) {
        this.accessLink = accessLink;
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

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }
}
