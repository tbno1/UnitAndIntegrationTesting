package tutorial.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class EntityX {
    @Id
    @GeneratedValue
    private int id;
    @Column(length = 20, nullable = false)
    private String someProperty;
    @Column(length = 100)
    private String customerEmail;
    @Column(precision = 15, scale = 2)
    private BigDecimal total;

    public EntityX() {
    }

    public EntityX(int type, String code, String customerEmail) {
        this.customerEmail = customerEmail;
        someProperty = "abc";
    }

    public int getId() {
        return id;
    }

    public String getSomeProperty() {
        return someProperty;
    }

    private void setSomeProperty(String someProperty) {
        this.someProperty = someProperty;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}