package com.codbex.theia.integration.tests;

import org.eclipse.dirigible.integration.tests.api.SecurityIT;
import org.eclipse.dirigible.integration.tests.ui.tests.TerminalIT;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({//
        SecurityIT.class, //
        TerminalIT.class//
})
public class DirigibileCommonTestSuiteIT {
}
