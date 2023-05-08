/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package adam.coursework.bus;

import adam.coursework.ents.Address;
import adam.coursework.ents.Administrator;
import adam.coursework.ents.Basket;
import adam.coursework.ents.Books;
import adam.coursework.ents.Orders;
import adam.coursework.ents.PaymentMethod;
import adam.coursework.ents.Person;
import adam.coursework.ents.bookList;
import adam.coursework.pers.AddressFacade;
import adam.coursework.pers.AdministratorFacade;
import adam.coursework.pers.BasketFacade;
import adam.coursework.pers.BooksFacade;
import adam.coursework.pers.OrdersFacade;
import adam.coursework.pers.PaymentMethodFacade;
import adam.coursework.pers.PersonFacade;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author adamt
 */
@Stateless
public class StartService {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private PersonFacade pf;

    @EJB
    private OrdersFacade of;

    @EJB
    private AdministratorFacade adf;

    @EJB
    private BooksFacade bf;

    @EJB
    private BasketFacade baf;

    @EJB
    private PaymentMethodFacade pmf;

    @EJB
    private AddressFacade af;

    public void startStarting(String name, String surname, String email, String password) {
        Person p = new Person();
        p.setName(name);
        p.setSurname(surname);
        p.setEmailAddress(email);
        p.setPassword(password);
        pf.create(p);
    }
    
    public void registerAdmin(String username, String password){
        Administrator a = new Administrator();
        a.setUsername(username);
        a.setPassword(password);
        adf.create(a);
    }

    public void addBooks() {
        List<Books> currentBooks = retrieveBooks();
        for(int i = 0; i < currentBooks.size(); i++){
            currentBooks.get(i).setCopies(50);
            bf.edit(currentBooks.get(i));
        }
    }
    
    public void recordBook(Books book){
        bf.create(book);
    }

    public String findPassword(String emailAddress) {
        List<Person> users = pf.findAll();
        String passwordFound = "";
        for (int i = 0; i < users.size(); i++) {
            Person per = users.get(i);

            if (per.getEmailAddress().equals(emailAddress)) {
                passwordFound = per.getPassword();
            }
        }
        return passwordFound;
    }

    public String findEmailAddress(String forename, String surname) {
        List<Person> users = pf.findAll();
        String emailAddressFound = "";
        for (int i = 0; i < users.size(); i++) {
            Person per = users.get(i);

            if (per.getName().equals(forename) && per.getSurname().equals(surname)) {
                emailAddressFound = per.getEmailAddress();
            }
        }
        return emailAddressFound;
    }

    public void addBookToBasket(Basket bs) {
        Books book = bf.find(bs.getId());
        Basket b = bs;
        b.setTitle(book.getTitle());
        b.setAuthor(book.getAuthor());
        b.setEdition(book.getEdition());
        b.setPrice(book.getPrice());
        b.setCopies(bs.getCopies());
        baf.create(bs);
    }

    public void cancelOrder(Orders o, long deleteID) {
        Orders cancel = of.find(deleteID);
        cancel.setStatus("Cancelled");
        of.edit(cancel);
    }

    public void addPaymentMethod(PaymentMethod pay, String loginEmail, String loginPassword) {
        List<Person> customers = pf.findAll();
        for (int i = 0; i < customers.size(); i++) {
            Person customer = customers.get(i);
            if (customer.getEmailAddress().equals(loginEmail) && customer.getPassword().equals(loginPassword)) {
                Person currentCustomer = customer;

                List<Person> currentCustomers = new ArrayList<>();
                currentCustomers.add(currentCustomer);
                pay.setPersons(currentCustomers);

                pmf.create(pay);
                List<PaymentMethod> customerMethod = pmf.findAll();
                currentCustomer.setPayments(customerMethod);
            }
        }
    }
    
    public String retrieveAccountName(String loginName, String loginSurname, String loginEmail, String loginPassword) {
        List<Person> users = retrieveAccounts();
        String accountName = "";
        for (int i = 0; i < users.size(); i++) {
            Person p = users.get(i);
            String email = p.getEmailAddress();
            if (p.getEmailAddress().equals(loginEmail)) {
                loginName = p.getName();
                loginSurname = p.getSurname();
                accountName = loginName + " " + loginSurname;
            }
        }
        return accountName;
    }
    
    public List<Books> findBooks(List<Books> listOfBooks, String author, String title, String yearOfPublication, String priceRange) {
        listOfBooks = retrieveBooks();
        for (Iterator<Books> iterator = listOfBooks.iterator(); iterator.hasNext();) {
            if (author.isEmpty()) {
                if (!iterator.next().getTitle().contains(title)) {
                    iterator.remove();
                }
            } else {
                if (!iterator.next().getAuthor().contains(author)) {
                    iterator.remove();
                }
            }
        }
        if(!yearOfPublication.isEmpty())
        {
            for (Iterator<Books> iterator = listOfBooks.iterator(); iterator.hasNext();) {
                if (!iterator.next().getYearOfPublication().equals(yearOfPublication)) {
                    iterator.remove();
                }
            }
        }
        if(!priceRange.isEmpty()){
            String[] prices = priceRange.split("-");
            double lowBound = Double.parseDouble(prices[0]);
            double highBound = Double.parseDouble(prices[1]);
            for (Iterator<Books> iterator = listOfBooks.iterator(); iterator.hasNext();) {
                double price = Double.parseDouble(iterator.next().getPrice());
                if (price < lowBound || price > highBound) {
                    iterator.remove();
                }
            }
        }
        return listOfBooks;
    }
    
    public List<Orders> accountOrders(String accountName) {
        String nameAcc = accountName;
        List<Orders> orders = retrieveOrders();
        List<Orders> filter2 = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            Orders orderNew = orders.get(i);
            String name = orderNew.getAccountName();
            if (orderNew.getAccountName() == null ? nameAcc == null : orderNew.getAccountName().equals(nameAcc)) {
                filter2.add(orderNew);
            }
        }
        return filter2;
    }
    
    public Orders selectOrder(long searchID, Orders selectedOrder) {
        List<Orders> allOrders = retrieveOrders();
        Orders od;
        for (int i = 0; i < allOrders.size(); i++) {
            od = allOrders.get(i);
            long enteredID = searchID;
            if (od.getId().equals(enteredID)) {
                selectedOrder = od;
            }
        }
        return selectedOrder;
    }
    
    public List<Address> accountAddress(String accountName){
        String nameAcc = accountName;
        List<Address> allAddresses = retrieveAddress();
        List<Address> accountAddress = new ArrayList<>();
        for(int i = 0; i < allAddresses.size(); i++){
            Address singleAddress = allAddresses.get(i);
            String customerName = singleAddress.getCustomer();
            if(customerName.equals(nameAcc)){
                accountAddress.add(singleAddress);
            }
        }
        return accountAddress;
    }
    
    public List<PaymentMethod> accountMethod(String accountName){
        String nameAcc = accountName;
        List<PaymentMethod> payments = retrievePayment();
        List<PaymentMethod> accountPayment = new ArrayList<>();
        for(int i = 0; i < payments.size(); i++){
            PaymentMethod singlePayment = payments.get(i);
            String holderName = singlePayment.getCardHolder();
            if(holderName.equals(nameAcc)){
                accountPayment.add(singlePayment);
            }
        }
        return accountPayment;
    }

    public void addDeliveryAddress(Address a, String loginEmail, String loginPassword) {
        List<Person> occupants = pf.findAll();
        for (int i = 0; i < occupants.size(); i++) {
            Person occupant = occupants.get(i);
            if (occupant.getEmailAddress().equals(loginEmail) && occupant.getPassword().equals(loginPassword)) {
                Person currentOccupant = occupant;

                List<Person> currentOccupants = new ArrayList<>();
                currentOccupants.add(currentOccupant);
                a.setPersons(currentOccupants);
                a.getPersons();
                af.create(a);
                List<Address> occupantAddress = af.findAll();
                currentOccupant.setOccupance(occupantAddress);

            }
        }
    }

    public void addAdmin() {
        Administrator admin = new Administrator();
        adf.create(admin);
    }
    
    public String check(String loginEmail, String loginPassword){
        String type = "None";
        for(int i = 0; i < retrieveAccounts().size(); i++){
            if(retrieveAccounts().get(i).getEmailAddress().equals(loginEmail) && retrieveAccounts().get(i).getPassword().equals(loginPassword)){
                type = "Customer";
            }
        }
        for(int i = 0; i < retrieveAdmin().size(); i++){
            if(retrieveAdmin().get(i).getUsername().equals(loginEmail) && retrieveAdmin().get(i).getPassword().equals(loginPassword)){
                type = "Admin";
            }
        }
        return type;
    }

    public List<Administrator> retrieveAdmin() {
        List<Administrator> admins = adf.findAll();
        return admins;
    }
    
    private List<List<Basket>> itemLists;

    public List<List<Basket>> getItemLists() {
        return itemLists;
    }

    public void setItemLists(List<List<Basket>> itemLists) {
        this.itemLists = itemLists;
    }
    
    public void placeOrder(Orders o){
        List<Basket> order = retrieveBasket();
        int total = 0;
        double totalCost = 0;
        for(int i = 0; i < order.size(); i++){
            total += order.get(i).getCopies();
        }
        List<String> orderTitles = new ArrayList<>();
        List<String> orderAuthors = new ArrayList<>();
        List<String> orderEditions = new ArrayList<>();
        List<String> orderPrices = new ArrayList<>();
        List<String> orderCopies = new ArrayList<>();
        Basket item;
        List<Basket> items = new ArrayList<>();
        List<Integer> integerCopies = new ArrayList<>();
        for (int i = 0; i < order.size(); i++) {
            item = order.get(i);
            items.add(item);
            orderTitles.add(item.getTitle());
            orderAuthors.add(item.getAuthor());
            orderEditions.add(item.getEdition());
            double indivPrice = Double.parseDouble(item.getPrice());
            orderPrices.add(item.getPrice());
            int copyNo = item.getCopies();
            double typePrice = indivPrice * copyNo;
            totalCost += typePrice;
            integerCopies.add(copyNo);
            String copyWord = Integer.toString(copyNo);
            orderCopies.add(copyWord);
        }
        o.setTitle(orderTitles.toString());
        o.setAuthor(orderAuthors.toString());
        o.setEdition(orderEditions.toString());
        o.setPrice(orderPrices.toString());
        o.setCopies(orderCopies.toString());
        o.setAllCopies(total);
        o.setTotalPrice(totalCost);
        
        int index1 = 0;
        for(int i = 0; i < retrievePayment().size(); i++){
            if(o.getPaymentID() == retrievePayment().get(i).getId()){
                index1 = i;
                break;
            }
        }
        
        int index2 = 0;
        for(int i = 0; i < retrieveAddress().size(); i++){
            if(o.getDeliveryID() == retrieveAddress().get(i).getId()){
                index2 = i;
                break;
            }
        }
        o.setDeliveryAddress(retrieveAddress().get(index2));
        o.setPayment(retrievePayment().get(index1));
        List<Orders> currentOrders = retrieveOrders();
        if(currentOrders.isEmpty()){
            o.setId(1L);
        } else{
            o.setId(currentOrders.get(currentOrders.size()-1).getId()+1);
        }
        of.create(o);
        setOrder(o, items, orderTitles, integerCopies);
    }

    public void setOrder(Orders o, List<Basket> items, List<String> orderTitles, List<Integer> integerCopies) {
        List<Basket> contents = baf.findAll();
        for (int i = 0; i < contents.size(); i++) {
            Basket item = contents.get(i);
            baf.remove(item);
        }
        List<Books> booksToReduce = retrieveBooks();
        for(int i = 0; i < orderTitles.size(); i++){
            String title = orderTitles.get(i);
            int copies = integerCopies.get(i);
            for(int j = 0; j < booksToReduce.size(); j++){
                if(title.equals(booksToReduce.get(j).getTitle())){
                    
                    int remainingCopies = booksToReduce.get(j).getCopies();
                    remainingCopies = remainingCopies - copies;
                    Books newBook = booksToReduce.get(j);
                    newBook.setCopies(remainingCopies);
                    bf.edit(newBook);
                }
            }
        }
    }
    
    public void updatedStatus(Orders o, String status){
        List<Orders> allOrders = retrieveOrders();
        for(int i = 0; i < allOrders.size(); i++){
            if(allOrders.get(i).getId().equals(o.getId())){
                o = allOrders.get(i);
                o.setStatus(status);
                of.edit(o);
            }
        }
    }

    public List<Orders> retrieveOrders() {
        List<Orders> orderList = of.findAll();
        return orderList;
    }

    public List<Address> retrieveAddress() {
        List<Address> add = af.findAll();
        return add;
    }

    public List<PaymentMethod> retrievePayment() {
        List<PaymentMethod> pay = pmf.findAll();
        return pay;
    }

    public List<Person> retrieveAccounts() {
        List<Person> per = pf.findAll();
        return per;
    }

    public List<Books> retrieveBooks() {
        List<Books> b = bf.findAll();
        return b;
    }

    public List<Basket> retrieveBasket() {
        List<Basket> ba = baf.findAll();
        return ba;
    }
    
    private List<String> titles;

    public List<String> getTitles() {
        titles = new ArrayList<>();
        titles.add("Title 1");
        titles.add("Death of the giant baboon");
        titles.add("Crimson Moon");
        titles.add("The Stolen Token");
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }
    
    private List<String> authors;

    public List<String> getAuthors() {
        authors = new ArrayList<>();
        authors.add("Author 1");
        authors.add("Jim");
        authors.add("Bayek");
        authors.add("Arno");
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
    
    private List<String> publishers;

    public List<String> getPublishers() {
        publishers = new ArrayList<>();
        publishers.add("Publisher 1");
        publishers.add("Publisher 2");
        publishers.add("Publisher 3");
        publishers.add("Publisher 4");
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }
    
    private List<String> editions;

    public List<String> getEditions() {
        editions = new ArrayList<>();
        editions.add("1st");
        editions.add("2nd");
        editions.add("3rd");
        editions.add("4th");
        return editions;
    }

    public void setEditions(List<String> editions) {
        this.editions = editions;
    }
    
    private List<String> years;

    public List<String> getYears() {
        years = new ArrayList<>();
        years.add("2005");
        years.add("2011");
        years.add("2017");
        years.add("2023");
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }
    
    private List<String> prices;

    public List<String> getPrices() {
        prices = new ArrayList<>();
        prices.add("4.99");
        prices.add("5.49");
        prices.add("5.99");
        prices.add("4.49");
        return prices;
    }

    public void setPrices(List<String> prices) {
        this.prices = prices;
    }
    
    private List<Integer> copies;

    public List<Integer> getCopies() {
        copies = new ArrayList<>();
        copies.add(25);
        copies.add(20);
        copies.add(30);
        copies.add(5);
        return copies;
    }

    public void setCopies(List<Integer> copies) {
        this.copies = copies;
    }
    
    
}
