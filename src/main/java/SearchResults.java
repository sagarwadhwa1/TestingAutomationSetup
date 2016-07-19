package main.java;

public class SearchResults implements Comparable{
    String name,url;
    long price;
    
    public SearchResults(long price, String name, String url){
	this.price = price;
	this.name = name;
	this.url = url;
    }
    
    @Override
    public String toString(){
	return price + " | " + name + " | " + url;
    }
    
    public long getPrice() {
	return price;
    }

    @Override
    public int compareTo(Object result) {
        long comparePrice=((SearchResults)result).getPrice();
        /* For Ascending order*/
        return (int) (this.price-comparePrice);

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }

}
