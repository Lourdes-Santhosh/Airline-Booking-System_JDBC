class Passenger {
    private int pid;
    private String pName;
    private String sex;
    private int flightId;
    private String address;

    public Passenger(int pid, String pName, String sex, int flightId, String address) {
        this.pid = pid;
        this.pName = pName;
        this.sex = sex;
        this.flightId = flightId;
        this.address = address;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Method to display passenger details
    public void display() {
        System.out.println("Passenger ID: " + pid);
        System.out.println("Passenger Name: " + pName);
        System.out.println("Sex: " + sex);
        System.out.println("Flight ID: " + flightId);
        System.out.println("Address: " + address);
    }
}

