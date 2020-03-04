package edu.jsu.mcis.tas_sp20;


public class Badge {
    private String id;
    private String F_name;
    private String L_name;
    private String M_init;
    
    public Badge(String id){
        this.id = id;
    }
    
    public String getid(){
        return id;
    }
    
    public String getF_name(){
        return F_name;
    }
    public void setF_name(String f){
        this.F_name = f;
    }
    
    public String getL_name(){
        return L_name;
    }
    public void setL_name(String l){
        this.L_name = l;
    }
    
    public String getM_init(){
        return M_init;
    }
    public void setM_init(String m){
        this.M_init = m;
    }
    
    @Override
    public String toString(){
        return "#" + id + "(" + L_name + ", " + F_name + M_init + ")";
    }
}
