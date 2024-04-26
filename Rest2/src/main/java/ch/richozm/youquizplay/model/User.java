package ch.richozm.youquizplay.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_User")
    private Integer pkUser;
    
    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getPKUser() {
        return pkUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
       this.password = password;
    }

    public void setPKUser(Integer pk) {
        this.pkUser = pk;
    }
}