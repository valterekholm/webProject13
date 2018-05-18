package liber_application.object_representation;

public class UserRepresentation {
	
    private String name;
    private String email;
    
    public UserRepresentation() {}
    
    public UserRepresentation(String name) {
    	this.name = name;
    }
    
    public UserRepresentation(String name, String email) {
    	this.name = name;
    	this.email = email;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    

}
