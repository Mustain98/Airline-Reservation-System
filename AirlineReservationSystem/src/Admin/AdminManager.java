package Admin;

import java.util.HashMap;

public class AdminManager {
    private final String defaultAdminName="root";
    private final String defaultAdminPass="root";
    private HashMap<String,String>adminUserNameAndPassword;
    public AdminManager(){
        adminUserNameAndPassword=new HashMap<>();
        adminUserNameAndPassword.put(defaultAdminName,defaultAdminPass);
    }
    HashMap<String,String> getAdmins(){
        return new HashMap<>(adminUserNameAndPassword);
    }
    public void addNewAdmin(String adminName,String adminPass){
        adminUserNameAndPassword.put(adminName,adminPass);
    }
    public int isPrivilegedUserOrNot(String username, String password) {
        int isFound=-1;
        for(String name:adminUserNameAndPassword.keySet()){
            if(username.equals(name)&&password.equals(adminUserNameAndPassword.get(username))){
                boolean DefaultUserNameAndPass = username.equals("root") && adminUserNameAndPassword.get(username).equals("root");
                if(DefaultUserNameAndPass){
                    isFound=0;
                }
                else{
                    isFound=1;
                }
            }
        }
        return isFound;
    }
}
