# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

`codbex-theia` is a stripped-down distribution of [Eclipse Dirigible](https://github.com/eclipse/dirigible) packaged as a Spring Boot application. Despite the name, it does **not** bundle the Eclipse Theia IDE — it ships only the **Terminal-over-HTTP** standard components (and supporting IDE/git/console UI). The intended use is browser-based shell access into a target environment such as a Kubernetes pod.

The application is essentially a thin assembly layer: nearly all functionality comes from `org.eclipse.dirigible:dirigible-components-*` dependencies declared in `application/pom.xml`. There is very little hand-written Java here — understand the codebase by understanding *which Dirigible components are included* and *how Spring profiles wire them up*, not by reading local source.

### Product scope

codbex-theia is a codbex product (see https://www.codbex.com/products/): a Dirigible-based distribution scoped to a single use case — minimal, terminal-only shell access. It strips the platform down to that, which is why `dirigible.properties` sets `DIRIGIBLE_PRODUCT_TYPE=terminal`. When deciding what belongs in this repo, keep that scope in mind — broader IDE/engine features are out of scope here.

## Module layout

Maven multi-module project under parent `com.codbex.platform:codbex-platform-parent` (the parent defines build profiles, plugin config, and dependency versions — it is external, not in this repo):

- `application/` — the Spring Boot app. `TheiaApplication` is the entry point; it scans `org.eclipse.dirigible` and excludes the default JDBC/JPA autoconfiguration (Dirigible manages datasources itself). Also contains the `Dockerfile`.
- `branding/` — Dirigible branding resources (logo, favicon, `branding.js`) served under `/services/web/ide-branding/`. Packaged as a jar and consumed by `application`.
- `integration-tests/` — Selenium/browser-driven integration tests built on `dirigible-tests-framework` / `dirigible-tests-base`. No production code.

## Build & run

All commands run from the repo root. Maven profiles (`quick-build`, `unit-tests`, `integration-tests`, `tests`, `format`) come from the platform parent.

```shell
mvn -T 1C clean install -P quick-build   # build the jar, skip tests
mvn clean install -P unit-tests          # unit tests
mvn clean install -P integration-tests   # integration tests (browser-driven)
mvn clean install -P tests               # all tests
mvn verify -P format                     # format the code — run before committing
```

Run the built jar (note the required `--add-opens` flags — the app will misbehave without them):

```shell
java --add-opens=java.base/java.lang=ALL-UNNAMED \
     --add-opens=java.base/java.lang.reflect=ALL-UNNAMED \
     --add-opens=java.base/java.nio=ALL-UNNAMED \
     -jar application/target/codbex-theia-*.jar
```

Docker (build the jar first):

```shell
cd application && docker build . --tag ghcr.io/codbex/codbex-theia:latest
docker run --name theia -p 80:80 ghcr.io/codbex/codbex-theia:latest
```

The Docker image is based on `amazoncorretto:21-alpine` and installs `ttyd`, `nodejs`/`npm`, `esbuild`, `typescript`, and `git` — these are runtime requirements of the terminal and JavaScript engine components.

### Running a single test

Standard Maven, but bind to the right profile so the surrounding config (datasource, etc.) is active:

```shell
mvn test -P unit-tests -pl application -Dtest=TheiaApplicationTest
mvn verify -P integration-tests -pl integration-tests -Dit.test=TheiaTerminalIT
```

## Access

- App: http://localhost:80 — default login `admin` / `admin`
- Swagger UI: http://localhost/swagger-ui/index.html

## Spring profiles (important)

The app is driven by Spring profiles, not by the small amount of local code. `application.properties` activates `common,app-default` by default. To enable a specific Dirigible security/deployment mode, add its profile explicitly alongside `common` and `app-default`, e.g.:

```
SPRING_PROFILES_ACTIVE=common,snowflake,app-default
```

Security backends are pluggable via the included components: `basic` (default), `keycloak`, `cognito`, `snowflake`, `client-registration`. Activate the matching profile to switch.

## Configuration

- `application/src/main/resources/dirigible.properties` — Dirigible product config. Notable: `DIRIGIBLE_MULTI_TENANT_MODE=false`, `DIRIGIBLE_PRODUCT_TYPE=terminal`, `DIRIGIBLE_HOME_URL=services/web/ide-terminal/` (the terminal is the landing page). `${project.version}` / `${git.commit.id}` are filled at build time via resource filtering.
- `application-app-default.properties` — sets `server.port=80`.

## Conventions

- Java source files carry the EPL-2.0 license header (see any existing `.java` file). New files should reuse it verbatim.
- This repo is released on a version-bump cadence: commits like "version set to X for release" / "version set to Y-SNAPSHOT for development" are made by the release pipeline (`.github/workflows/release.yaml`) — do not hand-edit versions for normal work.
