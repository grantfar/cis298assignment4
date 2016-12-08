package edu.kvcc.cis298.cis298assignment4.data;

/**
 * Created by gfarnsworth on 12/6/16.
 */

public final class BeverageSchema {
    public static final class BeverageTable{
        public static final String NAME = "beverages";
        public static final class Cols{
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String PRICE = "price";
            public static final String PACK = "pack";
            public static final String ACTIVE = "active";
        }
    }
}
