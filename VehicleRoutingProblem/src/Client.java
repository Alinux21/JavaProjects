import java.time.LocalTime;

public class Client {
    private String name;
    private LocalTime minTime;
    private LocalTime maxTime;
    private ClientType type;

    public Client() {

    }

    public Client(String name) {
        this(name, null, null);
    }

    public Client(String name, LocalTime minTime, LocalTime maxTime) {
        this.name = name;
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public Client(String name, ClientType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinTime(LocalTime minTime) {
        this.minTime = minTime;
    }

    public LocalTime getMinTime() {
        return this.minTime;
    }

    public void setMaxTime(LocalTime maxTime) {
        this.maxTime = maxTime;
    }

    public LocalTime getMaxTime() {
        return this.maxTime;
    }

    public ClientType getClientType() {
        return this.type;
    }

    public void setClientType(ClientType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Type: " + type + ", Visiting hours: " + minTime + "-" + maxTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Client)) {
            return false;
        }
        Client other = (Client) obj;
        return name.equals(other.name);
    }

}
