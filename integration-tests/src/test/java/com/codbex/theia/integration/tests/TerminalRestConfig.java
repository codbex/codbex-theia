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

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@TestConfiguration
class TerminalRestConfig {

    @RestController
    static class TerminalTestRest {
        public static final String TEST_PATH = "/services/core/version/terminal/api/it";
        private static boolean called = false;

        static boolean isCalled() {
            return called;
        }

        @GetMapping(TEST_PATH)
        String test() {
            called = true;
            return "Hello";
        }
    }
}
