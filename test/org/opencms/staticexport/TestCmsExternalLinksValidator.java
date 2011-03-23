/*
 * File   : $Source: /alkacon/cvs/opencms/test/org/opencms/staticexport/TestCmsExternalLinksValidator.java,v $
 * Date   : $Date: 2011/03/23 09:34:43 $
 * Version: $Revision: 1.4 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (C) 2005 Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.staticexport;

import org.opencms.file.CmsObject;
import org.opencms.relations.CmsExternalLinksValidator;
import org.opencms.test.OpenCmsTestCase;
import org.opencms.util.CmsUriSplitter;

import java.util.ArrayList;
import java.util.List;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

/** 
 * 
 * @version $Revision: 1.4 $
 * 
 * @since 7.0.4
 */
public class TestCmsExternalLinksValidator extends OpenCmsTestCase {

    /**
     * Default JUnit constructor.<p>
     * 
     * @param arg0 JUnit parameters
     */
    public TestCmsExternalLinksValidator(String arg0) {

        super(arg0);
    }

    /**
     * Test suite for this test class.<p>
     * 
     * @return the test suite
     */
    public static Test suite() {

        TestSuite suite = new TestSuite();
        suite.setName(TestCmsExternalLinksValidator.class.getName());
        suite.addTest(new TestCmsExternalLinksValidator("testExternalLinksOutside"));

        TestSetup wrapper = new TestSetup(suite) {

            @Override
            protected void setUp() {

                setupOpenCms("simpletest", "/sites/default/");
            }

            @Override
            protected void tearDown() {

                removeOpenCms();
            }

        };

        return wrapper;
    }

    /**
     * This test can go wrong if the external sites in the WWW not exists anymore or 
     * no connect to the WWW exists.<p>
     * 
     * Please remove sites, which not exists anymore.<p>
     * 
     * @throws Exception if test fails
     */
    public void testExternalLinksOutside() throws Exception {

        CmsObject cms = getCmsObject();

        List list = new ArrayList();
        list.add("http://www.deloitte.com/dtt/section_node/0,1042,sid%253D62862,00.html");
        list.add("http://www.dsb.dk/servlet/Satellite?pagename=Millenium/Page/StandardForside&c=Page&cid=1002806878464");
        list.add("http://www.nbi.dk/%7Enatphil/hug/hug.intro.html");
        list.add("http://www.si-folkesundhed.dk/Forskning/Sygdomme og tilskadekomst/Ulykker/Nyhedsbrev.aspx");
        list.add("http://www.mim.dk/Udgivelser/Milj%F8Danmark/");
        list.add("http://www.ug.dk/Videnscenter for vejledning/Forside/Virtuelt tidsskrift.aspx");

        // checks the list of external links
        for (int i = 0; i < list.size(); i++) {
            String url = (String)list.get(i);
            System.out.println("Checking external link: " + url);
            System.out.println("  Extenal link encoded: " + new CmsUriSplitter(url, true).toURI().toURL());
            assertTrue("External link check failed:" + url, CmsExternalLinksValidator.checkUrl(cms, url));
        }
    }
}
