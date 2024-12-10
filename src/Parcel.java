class Parcel {
    private final int id;
    private String status;
    private final String destination;

    public Parcel(int id, String destination) {
        this.id = id;
        this.destination = destination;
        this.status = "En attente";
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getDestination() {
        return destination;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}