package com.aeguinazu.bookstore.models.bookentities;

import com.aeguinazu.bookstore.models.orderentities.OrderItems;
import com.aeguinazu.bookstore.models.shoppingentities.ShoppingCart;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition="TEXT")
    private String description;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private Integer price;

    private Double rating;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "book_genre", joinColumns = {@JoinColumn(name = "book_id")}, inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private Set<Genre> genres = new HashSet<>();

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonManagedReference(value = "author_book_ref")
    private BookAuthor author;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    @JsonIgnore
    private BookDiscount discount;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonBackReference(value = "publisher_book_ref")
    private BookPublisher publisher;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    @JsonIgnore
    private BookInventory inventory;

    @OneToOne(mappedBy = "book")
    @JsonIgnore
    private ShoppingCart shoppingCart;

    @OneToOne(mappedBy = "book")
    @JsonIgnore
    private OrderItems orderItems;

    @Column(nullable = false)
    private String format;

    private String coverImg;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Book() {
    }

    public Book(String title, String description, String isbn, Integer price, String format, String coverImg) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.price = price;
        this.format = format;
        this.coverImg = coverImg;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public OrderItems getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItems orderItems) {
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public BookAuthor getAuthor() {
        return author;
    }

    public void setAuthor(BookAuthor author) {
        this.author = author;
    }

    public BookDiscount getDiscount() {
        return discount;
    }

    public void setDiscount(BookDiscount discount) {
        this.discount = discount;
    }

    public BookPublisher getPublisher() {
        return publisher;
    }

    public void setPublisher(BookPublisher publisher) {
        this.publisher = publisher;
    }

    public BookInventory getInventory() {
        return inventory;
    }

    public void setInventory(BookInventory inventory) {
        this.inventory = inventory;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

}
