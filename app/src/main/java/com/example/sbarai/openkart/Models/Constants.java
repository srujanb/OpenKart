package com.example.sbarai.openkart.Models;

/**
 * Created by sbarai on 1/19/18.
 */

public class Constants {

    class CollaborationItem {

        public static final int UNITTYPE_COUNT = 0;
        public static final int UNITTYPE_GRAM = 1;
        public static final int UNITTYPE_KILOGRAM = 2;
        public static final int UNITTYPE_MILLILITER = 3;
        public static final int UNITTYPE_LITRE = 4;

    }

    class ProspectOrder{

        private final int STATUS_ACTIVE = 0;
        private final int STATUS_CANCELED = 1;
        private final int STATUS_ORDERED = 2;
        private final int STATUS_PARTIALLY_DELIVERED = 3;
        private final int STATUS_DELIVERED = 4;

    }

}
