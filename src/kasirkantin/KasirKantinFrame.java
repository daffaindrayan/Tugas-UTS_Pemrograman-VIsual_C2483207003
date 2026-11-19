package kasirkantin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class KasirKantinFrame extends JFrame {

    private JTextField txtNamaBarang;
    private JTextField txtHargaSatuan;
    private JTextField txtJumlahBeli;
    private JTextField txtUangBayar;

    private JLabel lblTotalHarga;
    private JLabel lblKembalian;

    private JButton btnHitung;
    private JButton btnBayar;
    private JButton btnReset;

    private Long totalHargaTersimpan = null;
    private final Locale localeID = new Locale("id", "ID");

    public KasirKantinFrame() {
        initComponents();
        resetForm();
    }

    private void initComponents() {
        setTitle("Kasir Kantin Kampus - Pemrograman Visual");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 440);
        setMinimumSize(new Dimension(480, 420));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        mainPanel.setBackground(new Color(245, 247, 250));

        // Panel Judul
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 64, 175));
        headerPanel.setBorder(new EmptyBorder(14, 16, 14, 16));

        JLabel lblJudul = new JLabel("KASIR KANTIN KAMPUS");
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblJudul.setForeground(Color.WHITE);

        JLabel lblSubJudul = new JLabel("Pencatatan Transaksi Makanan & Minuman");
        lblSubJudul.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSubJudul.setForeground(new Color(219, 234, 254));

        headerPanel.add(lblJudul, BorderLayout.NORTH);
        headerPanel.add(lblSubJudul, BorderLayout.SOUTH);

        // Panel Form Input
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                new EmptyBorder(14, 14, 14, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("SansSerif", Font.BOLD, 13);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 13);

        txtNamaBarang = new JTextField();
        txtNamaBarang.setFont(inputFont);

        txtHargaSatuan = new JTextField();
        txtHargaSatuan.setFont(inputFont);

        txtJumlahBeli = new JTextField();
        txtJumlahBeli.setFont(inputFont);

        txtUangBayar = new JTextField();
        txtUangBayar.setFont(inputFont);

        addFormRow(formPanel, gbc, 0, "Nama Makanan / Minuman", txtNamaBarang, labelFont);
        addFormRow(formPanel, gbc, 1, "Harga Satuan (Rp)", txtHargaSatuan, labelFont);
        addFormRow(formPanel, gbc, 2, "Jumlah Pembelian", txtJumlahBeli, labelFont);
        addFormRow(formPanel, gbc, 3, "Uang Pembayaran (Rp)", txtUangBayar, labelFont);

        // Panel Hasil Perhitungan
        JPanel hasilPanel = new JPanel(new GridBagLayout());
        hasilPanel.setBackground(new Color(239, 246, 255));
        hasilPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(147, 197, 253), 1),
                new EmptyBorder(12, 14, 12, 14)
        ));

        GridBagConstraints gbcHasil = new GridBagConstraints();
        gbcHasil.insets = new Insets(4, 6, 4, 6);
        gbcHasil.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitleTotal = new JLabel("Total Harga:");
        lblTitleTotal.setFont(labelFont);
        lblTitleTotal.setForeground(new Color(31, 41, 55));

        lblTotalHarga = new JLabel("Rp0");
        lblTotalHarga.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotalHarga.setForeground(new Color(30, 64, 175));

        JLabel lblTitleKembali = new JLabel("Uang Kembalian:");
        lblTitleKembali.setFont(labelFont);
        lblTitleKembali.setForeground(new Color(31, 41, 55));

        lblKembalian = new JLabel("Rp0");
        lblKembalian.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblKembalian.setForeground(new Color(22, 101, 52));

        gbcHasil.gridx = 0;
        gbcHasil.gridy = 0;
        gbcHasil.weightx = 0.4;
        hasilPanel.add(lblTitleTotal, gbcHasil);

        gbcHasil.gridx = 1;
        gbcHasil.weightx = 0.6;
        hasilPanel.add(lblTotalHarga, gbcHasil);

        gbcHasil.gridx = 0;
        gbcHasil.gridy = 1;
        gbcHasil.weightx = 0.4;
        hasilPanel.add(lblTitleKembali, gbcHasil);

        gbcHasil.gridx = 1;
        gbcHasil.weightx = 0.6;
        hasilPanel.add(lblKembalian, gbcHasil);

        // Gabungkan form dan hasil di bagian tengah
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setBackground(new Color(245, 247, 250));
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.add(hasilPanel, BorderLayout.SOUTH);

        // Panel Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(245, 247, 250));

        btnHitung = new JButton("Hitung");
        btnHitung.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnHitung.setBackground(new Color(37, 99, 235));
        btnHitung.setForeground(Color.WHITE);
        btnHitung.setFocusPainted(false);
        btnHitung.setPreferredSize(new Dimension(100, 36));

        btnBayar = new JButton("Bayar");
        btnBayar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnBayar.setBackground(new Color(22, 163, 74));
        btnBayar.setForeground(Color.WHITE);
        btnBayar.setFocusPainted(false);
        btnBayar.setPreferredSize(new Dimension(100, 36));

        btnReset = new JButton("Reset");
        btnReset.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnReset.setBackground(new Color(220, 38, 38));
        btnReset.setForeground(Color.WHITE);
        btnReset.setFocusPainted(false);
        btnReset.setPreferredSize(new Dimension(100, 36));

        buttonPanel.add(btnHitung);
        buttonPanel.add(btnBayar);
        buttonPanel.add(btnReset);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Event Handling
        btnHitung.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesHitung();
            }
        });

        btnBayar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesBayar();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText,
                            JTextField textField, Font labelFont) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.42;
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(new Color(31, 41, 55));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.58;
        panel.add(textField, gbc);
    }

    /**
     * Membersihkan karakter 'Rp', spasi, dan titik/koma pemisah ribuan
     * agar input seperti "Rp15.000" atau "15000" dapat diparsing menjadi angka.
     */
    private Long parseAngka(String teks, String namaField) {
        if (teks == null || teks.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    namaField + " wajib diisi.",
                    "Input Kosong",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String bersih = teks.trim()
                .replaceAll("(?i)rp", "")
                .replaceAll("\\s+", "")
                .replace(".", "")
                .replace(",", "");

        if (bersih.isEmpty() || !bersih.matches("-?\\d+")) {
            JOptionPane.showMessageDialog(this,
                    namaField + " harus berupa angka yang valid.",
                    "Format Angka Tidak Valid",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            return Long.parseLong(bersih);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Nilai " + namaField + " terlalu besar atau tidak valid.",
                    "Kesalahan Angka",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private boolean validasiInputItem(boolean fokusKeError) {
        String namaBarang = txtNamaBarang.getText().trim();
        if (namaBarang.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nama makanan atau minuman wajib diisi.",
                    "Input Kosong",
                    JOptionPane.WARNING_MESSAGE);
            if (fokusKeError) {
                txtNamaBarang.requestFocusInWindow();
            }
            return false;
        }

        Long hargaSatuan = parseAngka(txtHargaSatuan.getText(), "Harga satuan");
        if (hargaSatuan == null) {
            if (fokusKeError) {
                txtHargaSatuan.requestFocusInWindow();
            }
            return false;
        }
        if (hargaSatuan < 0) {
            JOptionPane.showMessageDialog(this,
                    "Harga satuan tidak boleh bernilai negatif.",
                    "Nilai Tidak Valid",
                    JOptionPane.WARNING_MESSAGE);
            if (fokusKeError) {
                txtHargaSatuan.requestFocusInWindow();
            }
            return false;
        }

        Long jumlahBeli = parseAngka(txtJumlahBeli.getText(), "Jumlah pembelian");
        if (jumlahBeli == null) {
            if (fokusKeError) {
                txtJumlahBeli.requestFocusInWindow();
            }
            return false;
        }
        if (jumlahBeli <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Jumlah pembelian harus lebih dari 0.",
                    "Nilai Tidak Valid",
                    JOptionPane.WARNING_MESSAGE);
            if (fokusKeError) {
                txtJumlahBeli.requestFocusInWindow();
            }
            return false;
        }

        return true;
    }

    private Long hitungTotalDariInput() {
        if (!validasiInputItem(true)) {
            return null;
        }
        long harga = parseAngka(txtHargaSatuan.getText(), "Harga satuan");
        long jumlah = parseAngka(txtJumlahBeli.getText(), "Jumlah pembelian");
        return harga * jumlah;
    }

    private void prosesHitung() {
        Long total = hitungTotalDariInput();
        if (total == null) {
            return;
        }
        totalHargaTersimpan = total;
        lblTotalHarga.setText(formatRupiah(total));
        lblKembalian.setText("Rp0");
        lblKembalian.setForeground(new Color(22, 101, 52));
    }

    private void prosesBayar() {
        Long total = hitungTotalDariInput();
        if (total == null) {
            return;
        }
        totalHargaTersimpan = total;
        lblTotalHarga.setText(formatRupiah(total));

        Long uangBayar = parseAngka(txtUangBayar.getText(), "Uang pembayaran");
        if (uangBayar == null) {
            txtUangBayar.requestFocusInWindow();
            return;
        }

        if (uangBayar < 0) {
            JOptionPane.showMessageDialog(this,
                    "Uang pembayaran tidak boleh bernilai negatif.",
                    "Nilai Tidak Valid",
                    JOptionPane.WARNING_MESSAGE);
            txtUangBayar.requestFocusInWindow();
            return;
        }

        if (uangBayar < total) {
            long kurang = total - uangBayar;
            String pesanKurang = "Pembayaran kurang " + formatRupiah(kurang);
            lblKembalian.setText(pesanKurang);
            lblKembalian.setForeground(new Color(185, 28, 28));
            JOptionPane.showMessageDialog(this,
                    pesanKurang,
                    "Pembayaran Kurang",
                    JOptionPane.WARNING_MESSAGE);
            txtUangBayar.requestFocusInWindow();
            return;
        }

        long kembalian = uangBayar - total;
        lblKembalian.setText(formatRupiah(kembalian));
        lblKembalian.setForeground(new Color(22, 101, 52));

        JOptionPane.showMessageDialog(this,
                "Transaksi: " + txtNamaBarang.getText().trim()
                + "\nTotal Harga: " + formatRupiah(total)
                + "\nUang Bayar: " + formatRupiah(uangBayar)
                + "\nKembalian: " + formatRupiah(kembalian),
                "Pembayaran Berhasil",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetForm() {
        txtNamaBarang.setText("");
        txtHargaSatuan.setText("");
        txtJumlahBeli.setText("");
        txtUangBayar.setText("");
        lblTotalHarga.setText("Rp0");
        lblKembalian.setText("Rp0");
        lblKembalian.setForeground(new Color(22, 101, 52));
        totalHargaTersimpan = null;
        txtNamaBarang.requestFocusInWindow();
    }

    private String formatRupiah(long nilai) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
        formatter.setMaximumFractionDigits(0);
        formatter.setMinimumFractionDigits(0);
        String hasil = formatter.format(nilai).replace("\u00a0", "");
        if (!hasil.startsWith("Rp")) {
            hasil = "Rp" + hasil;
        }
        return hasil;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KasirKantinFrame().setVisible(true);
            }
        });
    }
}