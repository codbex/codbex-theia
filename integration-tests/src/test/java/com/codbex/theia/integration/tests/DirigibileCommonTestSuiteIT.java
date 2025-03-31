package com.codbex.theia.integration.tests;

import org.eclipse.dirigible.integration.tests.api.SecurityIT;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({//
        SecurityIT.class, //
})
public class DirigibileCommonTestSuiteIT {
}
