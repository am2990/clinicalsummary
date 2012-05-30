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

package org.openmrs.module.reportingsummary.api.io.util;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.reportingsummary.api.io.InputOutputConstants;
import org.openmrs.util.OpenmrsConstants;

public class InputOutputUtils {

    private static final Log log = LogFactory.getLog(InputOutputUtils.class);

    /**
     * Method to execute a command inside the shell. The command is usually the mysqldump or mysql command.
     *
     * @param commands the command array to be executed
     * @throws Exception when the task execution is interrupted
     */
    public static void executeCommand(final File workingDirectory, final String[] commands) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process process;
        if (OpenmrsConstants.UNIX_BASED_OPERATING_SYSTEM)
            process = runtime.exec(commands, null, workingDirectory);
        else
            process = runtime.exec(commands);

        StreamHandler errorHandler = new StreamHandler(process.getErrorStream(), "ERROR");
        StreamHandler outputHandler = new StreamHandler(process.getInputStream(), "OUTPUT");

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(errorHandler);
        executorService.execute(outputHandler);

        int exitValue = process.waitFor();
        log.info("Process execution completed with exit value: " + exitValue + " ...");

        executorService.shutdown();
    }

    /**
     * List of all table names that should go to remote sites. The list includes patient to allow us to print based on
     * the patient identifier, user to allow user with the appropriate privileges print the summaries.
     *
     * @return the table names
     */
    public static String[] enumerateDatabase() {
        return  new String[] {"location", "patient_identifier_type", "person", "patient", "patient_identifier",
                "person_name", "person_address", "privilege", "role", "role_privilege", "role_role",
                "user_property", "user_role", "users"};
    }

    /**
     * Method to extract the runtime properties of OpenMRS. The properties will be used to extract the inverted index
     * table along some of the necessary tables.
     *
     * @return the runtime properties configuration
     */
    public static Properties prepareDatabaseProperties() {
        Properties properties = new Properties();
        Properties runtimeProperties = Context.getRuntimeProperties();

        String databaseUser = runtimeProperties.getProperty("database.username");
        if (StringUtils.isBlank(databaseUser))
            databaseUser = runtimeProperties.getProperty("connection.username", "test");
        properties.setProperty(InputOutputConstants.DATABASE_USERNAME, databaseUser);

        String databasePassword = runtimeProperties.getProperty("database.password");
        if (StringUtils.isBlank(databasePassword))
            databasePassword = runtimeProperties.getProperty("connection.password", "test");
        properties.setProperty(InputOutputConstants.DATABASE_PASSWORD, databasePassword);

        String databaseName = "openmrs";
        String databaseHost = "localhost";
        String databasePort = "3306";
        String connectionString = runtimeProperties.getProperty("connection.url");
        if (!StringUtils.isBlank(connectionString)) {
            connectionString = runtimeProperties.getProperty("connection.url");
            int questionMark = connectionString.lastIndexOf("?");
            int slashDatabase = StringUtils.ordinalIndexOf(connectionString, "/", 3);
            databaseName = connectionString.substring(slashDatabase + 1, questionMark);
            // get the host
            int slashHost = StringUtils.ordinalIndexOf(connectionString, "/", 2);
            String databasePath = connectionString.substring(slashHost + 1, slashDatabase);
            int colonMarker = databasePath.indexOf(":");
            databaseHost = databasePath.substring(0, colonMarker);
            databasePort = databasePath.substring(colonMarker + 1);
        }

        properties.setProperty(InputOutputConstants.DATABASE_NAME, databaseName);
        properties.setProperty(InputOutputConstants.DATABASE_HOSTNAME, databaseHost);
        properties.setProperty(InputOutputConstants.DATABASE_PORT, databasePort);

        return properties;
    }
}
