class Flight {
    private int id;
    private String name;
    private int seats;

    public Flight(int id, String name, int seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    // Method to display flight details
    public void display() {
        System.out.println("Flight ID: " + id);
        System.out.println("Flight Name: " + name);
        System.out.println("Available Seats: " + seats);
    }
}