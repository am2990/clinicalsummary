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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class to handle the stream output of executing a command in the shell or command line.
 */
public class StreamHandler implements Runnable {

	private static final Log log = LogFactory.getLog(StreamHandler.class);

	private final InputStream stream;

	private final String source;

	public StreamHandler(final InputStream stream, final String source) {
		this.stream = stream;
		this.source = source;
	}

	/**
	 * @see Runnable#run()
	 */
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = reader.readLine()) != null)
				log.info(source + ": " + line);
			reader.close();
		} catch (IOException e) {
			log.error("Handling stream from runtime exec failed ...", e);
		}
	}
}
