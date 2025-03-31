/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.theia.integration.tests;

import org.eclipse.dirigible.tests.framework.HtmlAttribute;
import org.eclipse.dirigible.tests.framework.HtmlElementType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Import({TerminalRestConfig.class, TestConfigurations.class})
public class TheiaTerminalIT extends TheiaIntegrationTest {

    @LocalServerPort
    private int localServerPort;

    @Test
    void testTerminalWorks() {
        ide.openHomePage();

        String testRest = "http://localhost:" + localServerPort + TerminalRestConfig.TerminalTestRest.TEST_PATH;
        enterCommand("wget -qO- " + testRest);

        await().pollInterval(1, TimeUnit.SECONDS)
               .atMost(30, TimeUnit.SECONDS)
               .until(() -> TerminalRestConfig.TerminalTestRest.isCalled());
    }

    private void enterCommand(String command) {
        this.browser.clickOnElementByAttributePattern(HtmlElementType.CANVAS, HtmlAttribute.CLASS, "xterm-link-layer");
        this.browser.type(command);
        this.browser.pressEnter();
    }

}
