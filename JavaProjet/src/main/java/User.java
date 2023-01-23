public class User {
    public String pseudo;
    public int uniqueID;
    //history
     public User(String pseudo, int uniqueID){
         this.pseudo=pseudo;
         this.uniqueID=uniqueID;
     }

    public String getPseudo() {
        return pseudo;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }


}
