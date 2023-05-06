/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adam.coursework.ents;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adamt
 */
public class bookList {
    
    private final List<String> titles = new ArrayList<>();
    private final List<String> authors = new ArrayList<>();
    private final List<String> publishers = new ArrayList<>();
    private final List<String> editions = new ArrayList<>();
    private final List<String> years = new ArrayList<>();
    private final List<String> price = new ArrayList<>();
    private final List<Integer> copies = new ArrayList<>();

    public bookList() {
        initTitle();
        initAuthor();
        initPublishers();
        initEdition();
        initYears();
        initPrice();
        initCopies();
    }
    
    private void initTitle(){
        titles.add("Title 1");
        titles.add("Death of the giant baboon");
        titles.add("Crimson Moon");
        titles.add("The Stolen Token");
    }
    
    private void initAuthor(){
        authors.add("Author 1");
        authors.add("Jim");
        authors.add("Bayek");
        authors.add("Arno");
    }
    
    private void initPublishers(){
        publishers.add("Publisher 1");
        publishers.add("Publisher 2");
        publishers.add("Publisher 3");
        publishers.add("Publisher 4");
    }
    
    private void initEdition(){
        editions.add("1st");
        editions.add("2nd");
        editions.add("3rd");
        editions.add("4th");
    }
    
    private void initYears(){
        years.add("2005");
        years.add("2011");
        years.add("2017");
        years.add("2023");
    }
    
    private void initPrice(){
        price.add("4.99");
        price.add("5.49");
        price.add("5.99");
        price.add("4.49");
    }
    
    private void initCopies(){
        copies.add(50);
        copies.add(50);
        copies.add(50);
        copies.add(50);
    }

    public List<String> getTitles() {
        return titles;
    }
    
    public String getTitle(int i){
        return titles.get(i);
    }

    public List<String> getAuthors() {
        return authors;
    }
    
    public String getAuthor(int i){
        return authors.get(i);
    }

    public List<String> getPublishers() {
        return publishers;
    }
    
    public String getPublisher(int i){
        return publishers.get(i);
    }

    public List<String> getEditions() {
        return editions;
    }
    
    public String getEdition(int i){
        return editions.get(i);
    }

    public List<String> getYears() {
        return years;
    }
    
    public String getYear(int i){
        return years.get(i);
    }

    public List<String> getPrice() {
        return price;
    }
    
    public String getOnePrice(int i){
        return price.get(i);
    }

    public List<Integer> getCopies() {
        return copies;
    }
    
    public Integer getCopy(int i){
        return copies.get(i);
    }
    
}
