package ayp.aug.litecontacts.model;

import java.util.UUID;

/**
 * Created by Tanaphon on 8/9/2016.
 */
public class Contact {
    private UUID uuid;
    private String name;
    private String number;
    private String email;



    public Contact(){
        this(UUID.randomUUID());
    }

    public Contact(UUID uuid){
        this.setUuid(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    private void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoFileName(){
        return "IMG_" + this.getUuid().toString() + ".jpg";
    }
}
