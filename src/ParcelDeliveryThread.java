class ParcelDeliveryThread extends Thread {
    private final ParcelManagementSystem system;
    private final Parcel parcel;

    public ParcelDeliveryThread(ParcelManagementSystem system, Parcel parcel) {
        this.system = system;
        this.parcel = parcel;
    }

    @Override
    public void run() {
        system.registerParcel(parcel);
        try {
            Thread.sleep(2000);
            system.deliverParcel(parcel.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
