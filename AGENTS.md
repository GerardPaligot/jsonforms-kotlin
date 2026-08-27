# Project Information & Agent Guide

## Overview
`jsonforms-kotlin` is a Kotlin Multiplatform implementation of the [JSONForms](https://jsonforms.io/) standard from the Eclipse Foundation. It leverages [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) to render dynamic forms based on JSON Schemas and UI Schemas across Android, iOS, and JVM/Desktop.

## Tool Execution Priority Policy
> [!IMPORTANT]
> **IDE Tools Precedence**: In all agent execution workflows, **IDE tools take precedence over local CLI commands**. 
> Always prioritize using the integrated IDE tools (`build_project`, `get_file_problems`, `lint_files`, `search_symbol`, `rename_refactoring`, `open_file_in_editor`, etc.) for building, compiling, diagnosing, inspecting, searching, and refactoring code.
> **Only fall back to local CLI commands** (e.g., terminal Gradle commands) if the required action or intent is not supported or available within the IDE toolset.

## Requirements & Environment
- **Java / JDK**: Java 21 is required (`sourceCompatibility`, `targetCompatibility`, and JVM bytecode target are set to `VERSION_21`).
- **Gradle**: 8.14.5 (configured in `gradle/wrapper/gradle-wrapper.properties`).
- **Kotlin**: 2.1.21 with Compose Multiplatform 1.8.1.
- **Android Gradle Plugin (AGP)**: 8.10.1 (Compile SDK: 35, Min SDK: 26).

---

## IDE MCP Tools Reference (`idea` server)

When interacting with the project through the IDE MCP server (`idea`), the following toolsets are available:

### 1. Project & Build Management
- `build_project`: Build the project within the IDE.
- `get_project_modules`: List all modules configured in the IDE workspace.
- `get_project_dependencies`: Retrieve project dependencies for a specified module.
- `get_run_configurations`: List all available run/debug configurations in the IDE.
- `execute_run_configuration`: Execute a specific run/debug configuration.

### 2. Code Inspection, Diagnostics & Analysis
- `get_file_problems`: Retrieve current file compilation errors, warnings, and highlighting issues.
- `lint_files`: Run IDE inspections/linters against selected files.
- `analyze_calls`: Trace call hierarchies and find incoming/outgoing usages of symbols.
- `generate_psi_tree`: Inspect the Program Structure Interface (PSI) tree of a file.
- `run_inspection_kts` / `validate_inspection_kts`: Execute or validate custom Kotlin inspection scripts.

### 3. Search, Navigation & Refactoring
- `search_symbol`: Search for symbol definitions across the project.
- `get_symbol_info`: Retrieve detailed semantic info and documentation for a symbol.
- `search_text` / `search_regex` / `search_file`: Search by string, regex, or filename in the project index.
- `rename_refactoring`: Perform semantic, IDE-aware renaming of symbols across the project.
- `reformat_file`: Reformat code according to IDE code style settings.

### 4. Editor & File Operations
- `open_file_in_editor`: Open a file in the active IDE editor tab.
- `get_all_open_file_paths`: List all currently open files in editor tabs.
- `read_file`: Read a file's content directly from the IDE.
- `create_new_file`: Create a new file within the project tree.
- `apply_patch`: Apply diff patches to project files.
- `list_directory_tree`: Browse the project directory hierarchy.

### 5. Debugging (Xdebug & Session Control)
- `xdebug_start_debugger_session` / `xdebug_control_session`: Start and control debug sessions (pause, resume, step over/into/out).
- `xdebug_set_breakpoint` / `xdebug_list_breakpoints` / `xdebug_remove_breakpoint`: Manage breakpoints.
- `xdebug_get_stack` / `xdebug_get_threads` / `xdebug_get_frame_values`: Inspect stack frames, threads, and local variables.
- `xdebug_get_value_by_path` / `xdebug_set_variable`: Inspect and modify variable values at runtime.
- `xdebug_evaluate_expression`: Evaluate arbitrary expressions at the current breakpoint.
- `xdebug_run_to_line`: Continue execution until a specified line is reached.
- `xdebug_get_debugger_status`: Check the current status of the debugger session.

### 6. Version Control & Terminal
- `git_status`: Query Git VCS status from the IDE.
- `get_repositories`: List Git repositories tied to the workspace.
- `execute_terminal_command`: Execute shell commands in the IDE terminal.

### 7. Database & SQL Operations
- `list_database_connections` / `create_database_connection` / `edit_database_connection` / `test_database_connection`: Manage database connections.
- `list_database_schemas` / `introspect_schema` / `list_schema_objects` / `list_schema_object_kinds` / `get_database_object_description`: Database schema inspection.
- `execute_sql_query` / `fetch_query_result` / `cancel_sql_query` / `preview_table_data` / `list_recent_sql_queries`: SQL query execution and result handling.

### 8. Jupyter Notebooks & Python
- `create_notebook` / `read_notebook` / `edit_notebook`: Manage notebook files.
- `read_notebook_cell` / `run_notebook_cell` / `wait_cell_execution`: Execute and inspect notebook cells.
- `get_notebook_state` / `interrupt_notebook` / `kill_notebook`: Kernel lifecycle control.
- `configure_python_interpreter` / `get_python_environment`: Manage Python interpreter settings.

---

## Fallback CLI Commands
Use these commands only when the corresponding intent cannot be satisfied via IDE tools:
- Build project: `./gradlew build`
- Run desktop app: `./gradlew :composeApp:run`
- Run tests: `./gradlew test`
- Check code style: `./gradlew ktlintCheck`
- Format code: `./gradlew ktlintFormat`
