package com.vacationplanner.an.vacationguru;

/**
 * Created by An on 8/4/2015.
 */
public class MyPlace {
    private String name;
    private String address;
    private String rating;
    private String imageUrl;
    private String id;
    public MyPlace()
    {

    }
    public String getName(){return this.name;};
    public String getAddress(){return this.address;};
    public String getRating(){return this.rating;};
    public String getImageUrl(){return this.imageUrl;};
    public String getId(){return this.id;};
    public void setName(String name){
        this.name=name;
    }
    public void setAddress(String address){this.address=address;};
    public void setRating(String rating){this.rating=rating;};
    public void setImageUrl(String imageUrl){this.imageUrl=imageUrl;};
    public void setId(String id){this.id=id;};
}
