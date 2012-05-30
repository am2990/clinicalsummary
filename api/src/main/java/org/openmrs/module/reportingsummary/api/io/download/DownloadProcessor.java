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

package org.openmrs.module.reportingsummary.api.io.download;

import java.io.File;
import java.io.OutputStream;
import java.security.spec.KeySpec;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.reportingsummary.api.io.InputOutputConstants;
import org.openmrs.module.reportingsummary.api.io.util.InputOutputUtils;

public class DownloadProcessor {

    private static final Log log = LogFactory.getLog(DownloadProcessor.class);

    private byte[] initVector;

    private String password;

    public DownloadProcessor(final String password) {
        this.password = password;
    }

    /**
     * Method to initialize the cipher object with the correct encryption algorithm.
     *
     * @throws Exception
     */
    private Cipher initializeCipher() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(InputOutputConstants.SECRET_KEY_FACTORY);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), password.getBytes(), 1024, 128);
        SecretKey secretKey = factory.generateSecret(spec);

        SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), InputOutputConstants.KEY_SPEC);

        if (log.isDebugEnabled())
            log.debug("Encrypting with: " + secret.getAlgorithm());

        Cipher cipher = Cipher.getInstance(InputOutputConstants.CIPHER_CONFIGURATION);
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        return cipher;
    }

    /**
     * Method to process the inverted index table and generate a dump file that can be distributed to remote sites.
     *
     * The file contains sql statement for the index table in the database. Download process will execute mysqldump
     * to get the content of the database. To execute mysqldump correctly, the database user must have RELOAD privilege
     *
     * @throws Exception if the execution process terminated unexpectedly
     */
    protected final void processIndex() throws Exception {
        // File indexFile = new File(TaskUtils.getSummaryOutputPath(), TaskConstants.INDEX_CONFIGURATION_SQL_FILE);

        Properties databaseProperties = InputOutputUtils.prepareDatabaseProperties();
        // command that need to be executed to get the required tables
        String[] commands = {"mysqldump", "-u" + databaseProperties.get(InputOutputConstants.DATABASE_USERNAME),
                "-p" + databaseProperties.get(InputOutputConstants.DATABASE_PASSWORD),
                "-h" + databaseProperties.get(InputOutputConstants.DATABASE_HOSTNAME),
                "-P" + databaseProperties.get(InputOutputConstants.DATABASE_PORT),
                "-x", "-q", "-e",
    //            "--add-drop-table", "-r", indexFile.getAbsolutePath(),
                String.valueOf(databaseProperties.get(InputOutputConstants.DATABASE_NAME))};

        commands = (String[]) ArrayUtils.addAll(commands, InputOutputUtils.enumerateDatabase());
    //    TaskUtils.executeCommand(TaskUtils.getSummaryOutputPath(), commands);
    }

    public void download(final OutputStream outputStream, final String password) {

    }
}
