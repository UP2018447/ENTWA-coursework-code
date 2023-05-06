/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package adam.coursework.ctrl;

import adam.coursework.bus.StartService;
import adam.coursework.ents.Address;
import adam.coursework.ents.Administrator;
import adam.coursework.ents.Basket;
import adam.coursework.ents.Books;
import adam.coursework.ents.Orders;
import adam.coursework.ents.PaymentMethod;
import adam.coursework.ents.Person;
import adam.coursework.ents.bookList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author adamt
 */
@Named(value = "start")
@SessionScoped
public class start implements Serializable {

    /**
     * Creates a new instance of start
     */
    public start() {
    }

    private String name;
    private String surname;
    private String emailAddress;
    private String password;
    private String role;

    private String loginName;
    private String loginSurname;
    private String loginEmail;
    private String loginPassword;

    private String title;
    private String author;
    private String edition;
    private String price;
    private String copy;

    private String yearOfPublication;
    private String priceRange;
    private String newStatus;

    private Basket sb = new Basket();

    public Basket getSb() {
        return sb;
    }

    public void setSb(Basket sb) {
        this.sb = sb;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginSurname() {
        return loginSurname;
    }

    public void setLoginSurname(String loginSurname) {
        this.loginSurname = loginSurname;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String Author) {
        this.author = Author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @EJB
    private StartService ss;

    public void action() {
//        name = name + "!";
        ss.startStarting(name, surname, emailAddress, password);
    }

    public String loginToSystem() {
        emailAddress = emailAddress + emailAddress;
        password = password + "...";
        return "";
    }
    
    private boolean browseFlag = false;

    public boolean isBrowseFlag() {
        return browseFlag;
    }

    public void setBrowseFlag(boolean browseFlag) {
        this.browseFlag = browseFlag;
    }
    
    public void showBookBrowser(){
        browseFlag = true;
    }
    
    private List<Books> allBooks;

    public List<Books> getAllBooks() {
        allBooks = ss.retrieveBooks();
        return allBooks;
    }

    public void setAllBooks(List<Books> allBooks) {
        this.allBooks = allBooks;
    }

    private Books listOfBooks;

    public Books getListOfBooks() {
        listOfBooks = findBooks();
        return listOfBooks;
    }

    public void setListOfBooks(Books listOfBooks) {
        this.listOfBooks = listOfBooks;
    }

    public Books findBooks() {
        List<Books> books = ss.retrieveBooks();
        Books newBook;
        for (int i = 0; i < books.size(); i++) {
            newBook = books.get(i);
            if (newBook.getTitle().equals(title)) {
                listOfBooks = newBook;
            }
        }
        return listOfBooks;
    }

    private Administrator admin = new Administrator();

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    private boolean adminRender;

    public boolean isAdminRender() {
        return adminRender;
    }

    public void setAdminRender(boolean adminRender) {
        this.adminRender = adminRender;
    }

    public void checkAdminCredentials() {
        if (admin.getUsername().equals("admin") && admin.getPassword().equals("admin")) {
            adminRender = true;
        }
    }

    public void placeOrder() {
        List<Basket> order = ss.retrieveBasket();
        int total = 0;
        double totalCost = 0;
        for(int i = 0; i < order.size(); i++){
            total += order.get(i).getCopies();
        }
        List<String> titles = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        List<String> editions = new ArrayList<>();
        List<String> prices = new ArrayList<>();
        List<String> copies = new ArrayList<>();
        Basket item;
        List<Basket> items = new ArrayList<>();
        List<Integer> integerCopies = new ArrayList<>();
        for (int i = 0; i < order.size(); i++) {
            item = order.get(i);
            items.add(item);
            titles.add(item.getTitle());
            authors.add(item.getAuthor());
            editions.add(item.getEdition());
            double indivPrice = Double.parseDouble(item.getPrice());
            prices.add(item.getPrice());
            int copyNo = item.getCopies();
            double typePrice = indivPrice * copyNo;
            totalCost += typePrice;
            integerCopies.add(copyNo);
            String copyWord = Integer.toString(copyNo);
            copies.add(copyWord);
        }
        String orderTitle = titles.toString();
        String orderAuthor = authors.toString();
        String orderEdition = editions.toString();
        String orderPrice = prices.toString();
        String orderCopy = copies.toString();
        o.setTitle(orderTitle);
        o.setAuthor(orderAuthor);
        o.setEdition(orderEdition);
        o.setPrice(orderPrice);
        o.setCopies(orderCopy);
        o.setAccountName(accountName);
        o.setStatus("Ordered");
        o.setPaymentID(selectedPaymentMethod);
        o.setAllCopies(total);
        o.setTotalPrice(totalCost);
        ss.setOrder(o, items, titles, integerCopies, selectedPaymentMethod);
    }
    
    private List<Orders> transactions;

    public List<Orders> getTransactions() {
        transactions = ss.retrieveOrders();
        return transactions;
    }

    public void setTransactions(List<Orders> transactions) {
        this.transactions = transactions;
    }

    private Orders o = new Orders();

    public Orders getO() {
        return o;
    }

    public void setO(Orders o) {
        this.o = o;
    }

    public void cancelOrder() {
        ss.cancelOrder(o);
    }

//    @PostConstruct
    public void addBooks() {
//        ss.addAdmin();
        ss.addBooks();

    }
    
    public void updatedOrderStatus(){
        String status = newStatus;
        ss.updatedStatus(o, status);
    }

    private String newEmailAddress;

    public String getNewEmailAddress() {
        return newEmailAddress;
    }

    public void setNewEmailAddress(String newEmailAddress) {
        this.newEmailAddress = newEmailAddress;
    }

    public void findEmailAddress() {
        newEmailAddress = ss.findEmailAddress(name, surname);
    }

    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void findPassword() {
        newPassword = ss.findPassword(emailAddress);

    }

    private boolean standardOrder = true;

    public boolean isStandardOrder() {
        return standardOrder;
    }

    public void setStandardOrder(boolean standardOrder) {
        this.standardOrder = standardOrder;
    }

    private boolean filterFlag;

    public boolean isFilterFlag() {
        return filterFlag;
    }

    public void setFilterFlag(boolean filterFlag) {
        this.filterFlag = filterFlag;
    }
    
    public List<Orders> accountOrders(){
//        orderList = new ArrayList<>();
        String nameAcc = getAccountName();
        List<Orders> orders = ss.retrieveOrders();
        List<Orders> filter2 = new ArrayList<>();
        for(int i = 0; i < orders.size(); i++){
            Orders orderNew = orders.get(i);
            String name = orderNew.getAccountName();
            if(orderNew.getAccountName() == null ? nameAcc == null : orderNew.getAccountName().equals(nameAcc)){
                filter2.add(orderNew);
            }
        }
//        for (Iterator<Orders> iterator = orders.iterator(); iterator.hasNext();) {
//            if (!iterator.next().getAccountName().contains("adam thornton")) {
//                iterator.remove();
//            }
//        }
//        orderList = filter2;
        return filter2;
    }

    public void filterOrders() {
//        final List<Orders> fixedOrders = ss.retrieveOrders();
        List<Orders> orders = ss.retrieveOrders();
//        for(int i = 0; i < fixedOrders.size(); i++){
//            if(!orders.get(i).getTitle().contains(orderFilter)){
//                orders.remove(i);
//            }
//        }
        for (Iterator<Orders> iterator = orders.iterator(); iterator.hasNext();) {
            if (!iterator.next().getTitle().contains(orderFilter)) {
                iterator.remove();
            }
        }

        filteredOrder = orders;
        standardOrder = false;
        filterFlag = true;
    }

    public void reset() {
        standardOrder = true;
        filterFlag = false;
    }

    private List<Orders> filteredOrder;

    public List<Orders> getFilteredOrder() {
        return filteredOrder;
    }

    public void setFilteredOrder(List<Orders> filteredOrder) {
        this.filteredOrder = filteredOrder;
    }

    private String orderFilter;

    public String getOrderFilter() {
        return orderFilter;
    }

    public void setOrderFilter(String orderFilter) {
        this.orderFilter = orderFilter;
    }

    private List<Orders> orderList;

    public List<Orders> getOrderList() {
        orderList = new ArrayList<>();
        List<Orders> orderListUpdate = accountOrders();
        for(int i = 0; i < orderListUpdate.size(); i++){
            orderList.add(orderListUpdate.get(i));
        }
        return orderList;
    }

    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }
    
    private boolean selectFlag = false;

    public boolean isSelectFlag() {
        return selectFlag;
    }

    public void setSelectFlag(boolean selectFlag) {
        this.selectFlag = selectFlag;
    }
    
    public void showOrder(){
        selectFlag = true;
    }
    
    public void hide(){
        selectFlag = false;
    }
    
    private List<Orders> adminOrders;

    public List<Orders> getAdminOrders() {
        adminOrders = ss.retrieveOrders();
        return adminOrders;
    }

    public void setAdminOrders(List<Orders> adminOrders) {
        this.adminOrders = adminOrders;
    }
    
    private Orders detailedTransaction;

    public Orders getDetailedTransaction() {
        detailedTransaction = selectReport();
        return detailedTransaction;
    }

    public void setDetailedTransaction(Orders detailedTransaction) {
        this.detailedTransaction = detailedTransaction;
    }
    
    public List<Orders> transactionReport(){
        List<Orders> transactionList = ss.retrieveOrders();
        List<Orders> oneTransaction = new ArrayList<>();
        for(int i = 0; i < transactionList.size(); i++){
            if(transactionID == transactionList.get(i).getId()){
                transactionList.add(transactionList.get(i));
            }
        }
        return oneTransaction;
    }
    
    private boolean transactionFlag = false;

    public boolean isTransactionFlag() {
        return transactionFlag;
    }

    public void setTransactionFlag(boolean transactionFlag) {
        this.transactionFlag = transactionFlag;
    }
    
    public void showTransaction(){
        transactionFlag = true;
    }
    
    public Orders selectReport(){
        List<Orders> oneTransaction = ss.retrieveOrders();
        Orders od;
        for(int i = 0; i < oneTransaction.size(); i++){
            od = oneTransaction.get(i);
            long enteredID = transactionID;
            if(od.getId().equals(enteredID)){
                detailedTransaction = od;
            }
        }
        return detailedTransaction;
    }
    
    private long transactionID;

    public long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }
    
    private long searchID;

    public long getSearchID() {
        return searchID;
    }

    public void setSearchID(long searchID) {
        this.searchID = searchID;
    }
    
    private List<PaymentMethod> paymentMethods;

    public List<PaymentMethod> getPaymentMethods() {
        paymentMethods = ss.retrievePayment();
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
    
    public Orders selectOrder(){
        List<Orders> allOrders = ss.retrieveOrders();
        Orders od;
        for(int i = 0; i < allOrders.size(); i++){
            od = allOrders.get(i);
            long enteredID = searchID;
            long findID = od.getId();
            if(od.getId().equals(enteredID)){
                selectedOrder = od;
            }
        }
        return selectedOrder;
    }
    
    private Orders selectedOrder;

    public Orders getSelectedOrder() {
        selectedOrder = selectOrder();
        return selectedOrder;
    }

    public void setSelectedOrder(Orders selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public void addToBasket() {
        ss.addBookToBasket(sb);
//        sb.setId(sb.getId()+1);
//        order = new ArrayList<>();
//        List<Basket> orderResult = ss.retrieveBasket();
//        for (int i = 0; i < orderResult.size(); i++) {
//            Basket entry = orderResult.get(i);
//            order.add(entry);
//        }
//        order.add(sb);
    }

    public void shoppingBasket() {
        List<Basket> orderResult = ss.retrieveBasket();
        order = new ArrayList<>();
        for (int i = 0; i < orderResult.size(); i++) {
            Basket entry = orderResult.get(i);
            order.add(entry);
        }
    }

    private PaymentMethod pay = new PaymentMethod();

    public PaymentMethod getPay() {
        return pay;
    }

    public void setPay(PaymentMethod pay) {
        this.pay = pay;
    }

    Address a = new Address();

    public Address getA() {
        return a;
    }

    public void setA(Address a) {
        this.a = a;
    }
    
    private long selectedPaymentMethod;

    public long getSelectedPaymentMethod() {
        return selectedPaymentMethod;
    }

    public void setSelectedPaymentMethod(long selectedPaymentMethod) {
        this.selectedPaymentMethod = selectedPaymentMethod;
    }

    public void addPaymentMethod() {
        ss.addPaymentMethod(pay, loginEmail, loginPassword);
    }

    public void addDeliveryAddress() {
        ss.addDeliveryAddress(a, loginEmail, loginPassword);
    }

    private List<Basket> order = new ArrayList<>();

    public List<Basket> getOrder() {
        order = new ArrayList<>();
        List<Basket> basketList = ss.retrieveBasket();
        for(int i = 0; i < basketList.size(); i++){
            order.add(basketList.get(i));
        }
        return order;
    }

    public void setOrder(List<Basket> order) {
        this.order = order;
    }

    private String accountName;

    public String getAccountName() {
        List<Person> users = ss.retrieveAccounts();
        for (int i = 0; i < users.size(); i++) {
            Person p = users.get(i);
            if (p.getEmailAddress().equals(loginEmail) && p.getPassword().equals(loginPassword)) {
                loginName = p.getName();
                loginSurname = p.getSurname();
                accountName = loginName + " " + loginSurname;
            }
        }
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    private boolean render = false;

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }
    
    private boolean customerFlag = false;

    public boolean isCustomerFlag() {
        return customerFlag;
    }

    public void setCustomerFlag(boolean customerFlag) {
        this.customerFlag = customerFlag;
    }
    
    private boolean administratorFlag = false;

    public boolean isAdministratorFlag() {
        return administratorFlag;
    }

    public void setAdministratorFlag(boolean administratorFlag) {
        this.administratorFlag = administratorFlag;
    }
    
    public void checkRole() {
        if(role.equals("Customer")){
            customerFlag = true;
            administratorFlag = false;
        }
        else if(role.equals("Administrator")){
            customerFlag = false;
            administratorFlag = true;
        }
        else{
            customerFlag = false;
            administratorFlag = false;
        }
    }

    public void checkCredentials() {
        List<Person> users = ss.retrieveAccounts();
        if (loginEmail.equals("admin") && loginPassword.equals("admin")) {
            adminRender = true;
            render = false;
        }
        for (int i = 0; i < users.size(); i++) {
            Person p = users.get(i);
            if (p.getEmailAddress().equals(loginEmail) && p.getPassword().equals(loginPassword)) {
                render = true;
                adminRender = false;
                break;
            }
        }
    }
}
