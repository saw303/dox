/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox.web.admin;

import ch.silviowangler.dox.export.DoxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 15.01.13 07:42
 *        </div>
 */
@Controller
public class RepositoryController {

    @Autowired
    private DoxExporter doxExporter;
    @Autowired
    private Properties mimeTypes;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/admin/export")
    public void getDocument(HttpServletResponse response) {

        try {
            File zipFile = doxExporter.export();

            InputStream in = new FileInputStream(zipFile);

            response.setContentType(this.mimeTypes.getProperty("zip"));
            response.addHeader("Content-Disposition", "attachment; filename=dox-repository.zip");

            byte[] buffer = new byte[1024];
            int bytes;
            while ((bytes = in.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytes);
            }
        } catch (IOException e) {
            logger.error("Unable to send dox repository export", e);
        }
    }
}
