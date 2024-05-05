package com.example.listview_m7_ppb_c;

import android.provider.BaseColumns;

public final class KontakContract {
    private KontakContract() {}

    public static class KontakList implements BaseColumns {
        public static final String TABLE_NAME = "kontak_list";
        public static final String COLUMN_NAMA = "nama";
        public static final String COLUMN_NO_HP = "no_hp";
    }
}
