package com.codbex.theia.ui.tests;

import org.eclipse.dirigible.tests.UserInterfaceIntegrationTest;
import org.springframework.context.annotation.Import;

@Import(TestConfigurations.class)
public abstract class TheiaIntegrationTest extends UserInterfaceIntegrationTest {
}
