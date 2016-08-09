package ayp.aug.litecontacts.model;

/**
 * Created by Tanaphon on 8/9/2016.
 */
public class ContactDBSchema {
    public static final class ContactTable {
        public static final String NAME = "contact";

        public static final class Columns {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String NUMBER = "number";
            public static final String EMAIL = "email";
        }
    }
}
