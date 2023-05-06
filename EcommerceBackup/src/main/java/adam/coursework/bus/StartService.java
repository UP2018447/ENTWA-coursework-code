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

    public void addBooks() {
        for (int i = 0; i < authors.size(); i++) {
            Books b = new Books();
            b.setTitle(titles.get(i));
            b.setAuthor(authors.get(i));
            b.setPublisher(publishers.get(i));
            b.setEdition(editions.get(i));
            b.setYearOfPublication(years.get(i));
            b.setPrice(prices.get(i));
            b.setCopies(copies.get(i));
            bf.create(b);
        }
    }
    
    public List<Books> findBooks(String title) {
        Books b = new Books();
        String title2 = b.getTitle();
        List<Books> bs = retrieveBooks();
        List<Books> books;
        books = new ArrayList<>();
        for (int i = 0; i < bs.size(); i++) {
            b = bs.get(i);
            if (title2.equals(title)) {
                books.add(b);
            }
        }
        return books;
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
        List<Basket> currentBasket = retrieveBasket();
        if(currentBasket.isEmpty()){
            bs.setId(1L);
        } else{
            bs.setId(currentBasket.get(currentBasket.size()-1).getId()+1);
        }
        List<Books> allBooks = retrieveBooks();
        for(int i = 0; i < allBooks.size(); i++){
            if(bs.getTitle().equals(allBooks.get(i).getTitle())){
                bs.setAuthor(allBooks.get(i).getAuthor());
                bs.setEdition(allBooks.get(i).getEdition());
                bs.setPrice(allBooks.get(i).getPrice());
            }
        }
        Basket b = new Basket();
        b.setTitle(bs.getTitle());
        b.setAuthor(bs.getAuthor());
        b.setEdition(bs.getEdition());
        b.setPrice(bs.getPrice());
        b.setCopies(bs.getCopies());
        baf.create(bs);
    }

    public void cancelOrder(Orders o) {
        for (int i = 0; i < retrieveOrders().size(); i++) {
            Orders currentOrder = retrieveOrders().get(i);
            if (retrieveOrders().get(i).getId().equals(o.getId())) {
                if (!retrieveOrders().get(i).getStatus().equals("Dispatched")) {
                    o.setStatus("Cancelled");
                    o.setAccountName(currentOrder.getAccountName());
                    o.setTitle(currentOrder.getTitle());
                    o.setAuthor(currentOrder.getAuthor());
                    o.setCopies(currentOrder.getCopies());
                    o.setEdition(currentOrder.getEdition());
                    o.setPrice(currentOrder.getPrice());
                    of.edit(o);
                }
            }
        }
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

    public Administrator retrieveAdmin() {
        Administrator a = new Administrator();
        a.setId(851L);
        a = adf.find(a);
        return a;
    }
    
    private List<List<Basket>> itemLists;

    public List<List<Basket>> getItemLists() {
        return itemLists;
    }

    public void setItemLists(List<List<Basket>> itemLists) {
        this.itemLists = itemLists;
    }
    
    public void placeOrder(Orders o, String accountName, long selectedPaymentMethod){
        List<Basket> order = retrieveBasket();
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
        setOrder(o, items, titles, integerCopies, selectedPaymentMethod);
    }

    public void setOrder(Orders o, List<Basket> items, List<String> titles, List<Integer> integerCopies, long method) {
        List<Orders> currentOrders = retrieveOrders();
        if(currentOrders.isEmpty()){
            o.setId(1L);
        } else{
            o.setId(currentOrders.get(currentOrders.size()-1).getId()+1);
        }
        int fin = 0;
        for(int i = 0; i < retrievePayment().size(); i++){
            if(method == retrievePayment().get(i).getId()){
                fin = i;
                break;
            }
        }
        PaymentMethod pay = retrievePayment().get(fin);
        o.setPayment(retrievePayment().get(fin));
        o.setDeliveryAddress(retrieveAddress().get(0));
        of.create(o);
        List<Basket> contents = baf.findAll();
        for (int i = 0; i < contents.size(); i++) {
            Basket item = contents.get(i);
            baf.remove(item);
        }
        
//        itemLists.add(items);
        List<Books> booksToReduce = retrieveBooks();
        String booksTitle = booksToReduce.get(0).getTitle();
        Books specificBook = new Books();
        for(int i = 0; i < titles.size(); i++){
            String title = titles.get(i);
            int copies = integerCopies.get(i);
            for(int j = 0; j < booksToReduce.size(); j++){
                String targetTitle = booksToReduce.get(j).getTitle();
                if(title.equals(booksToReduce.get(j).getTitle())){
                    
                    int remainingCopies = booksToReduce.get(j).getCopies();
                    remainingCopies = remainingCopies - copies;
                    Books newBook = booksToReduce.get(j);
                    newBook.setCopies(remainingCopies);
//                    long bookID = booksToReduce.get(j).getId();
//                    specificBook.setId(booksToReduce.get(j).getId());
//                    specificBook.setTitle(title);
//                    String bookAuthor = booksToReduce.get(j).getAuthor();
//                    specificBook.setAuthor(booksToReduce.get(j).getAuthor());
//                    String edition = booksToReduce.get(j).getEdition();
//                    specificBook.setEdition(booksToReduce.get(j).getEdition());
//                    String price = booksToReduce.get(j).getPrice();
//                    specificBook.setPrice(booksToReduce.get(j).getPrice());
//                    specificBook.setPublisher(booksToReduce.get(j).getPublisher());
//                    specificBook.setYearOfPublication(booksToReduce.get(j).getYearOfPublication());
//                    specificBook.setCopies(remainingCopies);
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
