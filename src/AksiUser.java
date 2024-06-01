import java.util.Map;
import java.util.Scanner;

public class AksiUser extends Aksi {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void tampilanAksi() {
        System.out.println("Aksi User:");
        System.out.println("1. Pesan Film");
        System.out.println("2. Lihat List Film");
        System.out.println("3. Lihat Pesanan");
        System.out.println("4. Lihat Saldo");
        System.out.println("5. Logout");
        System.out.println("6. Tutup Aplikasi");
    }

    @Override
    public void keluar() {
        Akun.logout();
        System.out.println("Anda telah logout.");
    }

    @Override
    public void tutupAplikasi() {
        System.out.println("Aplikasi ditutup.");
        System.exit(0);
    }

    @Override
    public void lihatListFilm() {
        Map<String, Film> films = Film.getFilms();
        if (films.isEmpty()) {
            System.out.println("Tidak ada film yang tersedia.");
        } else {
            films.forEach((name, film) -> {
                System.out.println("Film: " + film.getName() + " - Deskripsi: " + film.getDescription() +
                                   " - Harga: " + film.getPrice() + " - Stok: " + film.getStock());
            });
        }
    }

    public void lihatSaldo() {
        User user = Akun.getCurrentUser();
        System.out.println("Saldo anda: " + user.getSaldo());
    }

    public void pesanFilm() {
        System.out.print("Nama Film yang ingin dipesan: ");
        String filmName = scanner.next();
        Film film = Film.getFilms().get(filmName);
        if (film == null) {
            System.out.println("Film yang dicari tidak ditemukan.");
            return;
        }
        System.out.print("Jumlah tiket yang ingin dipesan: ");
        int kuantitas = scanner.nextInt();
        double totalHarga = film.getPrice() * kuantitas;

        if (kuantitas > film.getStock()) {
            System.out.println("Stok tiket tidak mencukupi.");
        } else if (totalHarga > Akun.getCurrentUser().getSaldo()) {
            System.out.println("Saldo tidak mencukupi, saldo yang dimiliki " + Akun.getCurrentUser().getSaldo() + ".");
        } else {
            film.setStock(film.getStock() - kuantitas);
            Akun.getCurrentUser().setSaldo(Akun.getCurrentUser().getSaldo() - totalHarga);
            Akun.getCurrentUser().addPesanan(film, kuantitas);
            System.out.println("Tiket berhasil dipesan.");
        }
    }

    public void lihatPesanan() {
        User user = Akun.getCurrentUser();
        Map<String, Pesanan> pesanan = user.getPesanan();
        if (pesanan.isEmpty()) {
            System.out.println("Kamu belum pernah melakukan pemesanan.");
        } else {
            pesanan.forEach((name, order) -> {
                System.out.println("Film: " + order.getFilm().getName() + " - Jumlah: " + order.getKuantitas() +
                                   " - Total Harga: " + (order.getKuantitas() * order.getFilm().getPrice()));
            });
        }
    }
}