package edu.jsu.mcis.tas_sp20;


public class Badge {
    private int id;
    private String F_name;
    private String L_name;
    private String M_init;
    
    public Badge(int id){
        this.id = id;
    }
    
    public String getF_name(){
        return F_name;
    }
    public String getL_name(){
        return L_name;
    }
    public String getM_init(){
        return M_init;
    }
    
    
    @Override
    public String toString(){
        return "#" + id + "(" + L_name + ", " + F_name + M_init + ")";
    }
}
