package serezliev.BankWallet.model;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import serezliev.BankWallet.repositories.UserRepository;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username;

    @Column(nullable = false)
    private String email;

    @Column
    private String password;

    @Column
    private double balance;

    @Column
    private int notificationsCount;

    @ElementCollection
    @CollectionTable(name = "action_history", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "action")
    private List<String> actionHistory;

    @ElementCollection(fetch = FetchType.EAGER) // Хибернейт зарежда всеки път контактите заедно с UserEntity (за да се избегне Lazy)
    @CollectionTable(name = "contact_list", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "contacts")
    private List<String> contactList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BalanceHistoryEntity> balanceHistory;


    public UserEntity() {

    }


    public Long getId() {
        return id;
    }

    public UserEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public UserEntity setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public List<String> getActionHistory() {
        Hibernate.initialize(actionHistory);
        return actionHistory;
    }

    @Transactional
    public void addActionToHistory(String action) {
        if (actionHistory == null) {
            actionHistory = new ArrayList<>();
        }
        actionHistory.add(0, action);
    }

    public List<String> getContactList() {
        return contactList;
    }



    @Transactional
    public void addNewContact(String action) {
        if (contactList == null) {
            contactList = new ArrayList<>();
        }
        contactList.add(0, action);
    }

    @Transactional
    public void addBalanceHistory(Double balanceAmount, String dateTime) {
        BalanceHistoryEntity entry = new BalanceHistoryEntity();
        entry.setBalanceAmount(balanceAmount);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        entry.setDateAndTime(instant);

        entry.setUser(this);

        if (balanceHistory == null) {
            balanceHistory = new ArrayList<>();
        }
        balanceHistory.add(entry);
    }


    public UserEntity setActionHistory(List<String> actionHistory) {
        this.actionHistory = actionHistory;
        return this;
    }

    public UserEntity setContactList(List<String> contactList) {
        this.contactList = contactList;
        return this;
    }

    @Transactional
    public List<BalanceHistoryEntity> getBalanceHistory() {
        Hibernate.initialize(balanceHistory);
        return balanceHistory;
    }

    public UserEntity setBalanceHistory(List<BalanceHistoryEntity> balanceHistory) {
        this.balanceHistory = balanceHistory;
        return this;
    }

    public int getNotificationsCount() {
        return notificationsCount;
    }

    public UserEntity setNotificationsCount(int notificationsCount) {
        this.notificationsCount = notificationsCount;
        return this;
    }
}
