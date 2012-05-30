/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.reportingsummary.api.io;

public final class InputOutputConstants {

    public static final String FILE_TYPE_ZIP = "zip";

    // encryption parameters
    public static final String SECRET_KEY_FACTORY = "PBKDF2WithHmacSHA1";

    public static final String CIPHER_CONFIGURATION = "AES/CBC/PKCS5Padding";

    public static final String KEY_SPEC = "AES";

    // downloading parameters
    public static final int BUFFER_SIZE = 4096;

    // inverted index table dump file
    public static final String INDEX_CONFIGURATION_SQL_FILE = "inverted-index.sql";

    // database properties constants
    public static final String DATABASE_USERNAME = "database.username";

    public static final String DATABASE_PASSWORD = "database.password";

    public static final String DATABASE_HOSTNAME = "database.hostname";

    public static final String DATABASE_PORT = "database.port";

    public static final String DATABASE_NAME = "database.name";
}
