package ru.koshelev.commons.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "number_of_sold")
    private int numberOfSold;
    @Column(name = "price_purchase")
    private int pricePurchase;
    @Column(name = "price_demand")
    private int priceDemand;
    @Column(name = "date_demand")
    private Date dateDemand;

    public Sale() {}

    public Sale(Product product, int numberOfSold, int pricePurchase, int priceDemand, Date dateDemand) {
        this.product = product;
        this.numberOfSold = numberOfSold;
        this.pricePurchase = pricePurchase;
        this.priceDemand = priceDemand;
        this.dateDemand = dateDemand;
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNumberOfSold() {
        return numberOfSold;
    }

    public void setNumberOfSold(int numberOfSold) {
        this.numberOfSold = numberOfSold;
    }

    public int getPricePurchase() {
        return pricePurchase;
    }

    public void setPricePurchase(int pricePurchase) {
        this.pricePurchase = pricePurchase;
    }

    public int getPriceDemand() {
        return priceDemand;
    }

    public void setPriceDemand(int priceDemand) {
        this.priceDemand = priceDemand;
    }

    public Date getDateDemand() {
        return dateDemand;
    }

    public void setDateDemand(Date dateDemand) {
        this.dateDemand = dateDemand;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", productName=" + product.getName() +
                ", numberOfSold=" + numberOfSold +
                ", pricePurchase=" + pricePurchase +
                ", priceDemand=" + priceDemand +
                ", dateDemand=" + dateDemand +
                '}';
    }
}
