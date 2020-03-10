package edu.jsu.mcis.tas_sp20;


public class Badge {

    private String id;
    private String description;
    
    public Badge(String id, String d) {
        
        this.id = id;
        this.description = d;
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        
        //"#12565C60 (Chapman, Joshua E)"
        String s = "#";
        s += this.getId() + " (" + this.getDescription() + ")";
        return s;
        
    }
    
   
}