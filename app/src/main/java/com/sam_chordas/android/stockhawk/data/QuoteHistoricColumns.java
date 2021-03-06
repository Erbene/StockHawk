package com.sam_chordas.android.stockhawk.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by maia on 29/06/16.
 */
public class QuoteHistoricColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement
    public static final String _ID = "_id";
    @DataType(DataType.Type.TEXT) @NotNull
    public static final String SYMBOL = "symbol";
    @DataType(DataType.Type.TEXT) @NotNull
    public static final String DATE = "date";
    @DataType(DataType.Type.TEXT) @NotNull
    public static final String OPEN = "open";
    @DataType(DataType.Type.TEXT) @NotNull
    public static final String HIGH = "high";
    @DataType(DataType.Type.TEXT)
    public static final String LOW = "low";
    @DataType(DataType.Type.TEXT)
    public static final String CLOSE = "close";
}
