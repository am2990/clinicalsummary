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
package org.openmrs.module.reportingsummary.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openmrs.module.reportingsummary.api.io.util.ExcelTemplateRendererHardcode;
import org.openmrs.notification.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The main controller.
 */
@Controller
public class  ExcelRendererController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/reportingsummary/summaryrenderer.form", method = RequestMethod.GET)
		public void manage(
				@RequestParam(value = "type", required=false) String type,
				ModelMap model,HttpServletRequest request, HttpServletResponse response) {
		//model.addAttribute("user", Context.getAuthenticatedUser());
		System.out.println("CONTROL START ----------------------------------------");

		response.setContentType("application/download");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		response.setHeader("Content-Disposition", "attachment; filename=\""
			+ "Summary-" + formatter.format(new Date()) + "."+type + "\"");
		Message message=new Message();		
		message.setContent("Response initialised-------------");
		System.out.println("Response initialised ----------------------------------------");
		log.info(type);
		try {
			
			ExcelTemplateRendererHardcode.RenderToExcel(response.getOutputStream(), type);
			message.setContent("Rendered--------------------");
			System.out.println("Rendered ----------------------------------------");
			log.info(message);
			response.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
