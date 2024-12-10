import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

class ParcelManagementSystem {
    private final CopyOnWriteArrayList<Parcel> parcels = new CopyOnWriteArrayList<>();
    private final Semaphore semaphore = new Semaphore(1);

    public void registerParcel(Parcel parcel) {
        try {
            semaphore.acquire();
            parcels.add(parcel);
            System.out.println("Colis enregistré: " + parcel.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public void deliverParcel(int parcelId) {
        try {
            semaphore.acquire();
            for (Parcel parcel : parcels) {
                if (parcel.getId() == parcelId && parcel.getStatus().equals("En attente")) {
                    parcel.setStatus("Livré");
                    System.out.println("Colis livré: " + parcelId);
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public CopyOnWriteArrayList<Parcel> getParcels() {
        return parcels;
    }
}
