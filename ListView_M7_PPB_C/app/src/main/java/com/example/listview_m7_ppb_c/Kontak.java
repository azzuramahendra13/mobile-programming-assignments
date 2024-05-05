package com.example.listview_m7_ppb_c;

import androidx.annotation.Nullable;

public class Kontak implements Comparable<Kontak> {
    private String nama;
    private String noHP;

    public Kontak(String nama, String noHP) {
        this.nama = nama;
        this.noHP = noHP;
    }

    @Override
    public int compareTo(Kontak kontak) {
        return nama.compareTo(kontak.nama);
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public String getNama() { return nama; }

    public String getNoHP() {
        return noHP;
    }

    @Override
    public int hashCode() {
        return nama.hashCode() + noHP.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Kontak k = (Kontak) obj;

        if (k == null || !(obj instanceof Kontak)) {
            return false;
        }

        return k.nama.equals(nama) && k.noHP.equals(noHP);
    }
}
