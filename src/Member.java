
public class Member {
    String name;
    int id;
    String batch;
    String dept;
    String mail;
    String handle;

    public Member(String name, int id, String batch, String dept,String mail,String handle) {
        this.name = name;
        this.id = id;
        this.batch = batch;
        this.dept = dept;
        this.mail=mail;
        this.handle=handle;
    }
    
    public int getId(){
        return id;
        
    }
    
    public String getName(){
        return name;
        
    }
    public String getBatch(){
        return batch;
        
    }
    
    public String getDept(){
        return dept;
        
    }
    public String getMail(){
        return mail;
        
    }
    
     public String gethandle(){
        return handle;
        
    }
    
    
    
    
}
