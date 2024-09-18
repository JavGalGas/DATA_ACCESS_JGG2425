package Exercise2;

import java.util.HashSet;

public class Company {
    //
    private String name;
    private HashSet<Employee> template = new HashSet<>();
    private HashSet<Client> clientPortfolio = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalClients(){
        return clientPortfolio.size();
    }
    public int getTotalEmployees(){
        return template.size();
    }
}
