package ru.koshelev.commons.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @Column
    private int amount;
    @Column
    private int price;
    @Column
    private Date date;
    @Column
    private int remained;

    public Purchase(){}

    public Purchase(int amount, int price, Date date, int remained) {
        this.amount = amount;
        this.price = price;
        this.date = date;
        this.remained = remained;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRemained() {
        return remained;
    }

    public void setRemained(int remained) {
        this.remained = remained;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", productName=" + product.getName() +
                ", amount=" + amount +
                ", price=" + price +
                ", date=" + date +
                ", remained=" + remained +
                '}';
    }
}
