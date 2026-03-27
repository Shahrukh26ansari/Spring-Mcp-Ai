package com.queriai.service;

import org.springframework.stereotype.Service;

/**
 * Provides a hardcoded schema description for the e-commerce DB.
 *
 * For a production app you could dynamically query INFORMATION_SCHEMA,
 * but hardcoding gives Claude the cleanest, most consistent context.
 */
@Service
public class SchemaService {

    public String getSchemaDescription() {
        return """
                Database: employee (MySQL)

                Tables:

                1. employee
                   - emp_no      INT            PK
                   - birth_date  DATE           NOT NULL
                   - first_name  VARCHAR(14)    NOT NULL
                   - last_name   VARCHAR(16)    NOT NULL
                   - gender      ENUM('M','F')  NOT NULL
                   - hire_date   DATE           NOT NULL

                2. department
                   - dept_no    CHAR(4)        PK
                   - dept_name  VARCHAR(40)    UNIQUE

                3. dept_emp
                   - emp_no     INT            PK, FK -> employee.emp_no
                   - dept_no    CHAR(4)        PK, FK -> department.dept_no
                   - from_date  DATE           NOT NULL
                   - to_date    DATE           NOT NULL

                4. dept_manager
                   - emp_no     INT            PK, FK -> employee.emp_no
                   - dept_no    CHAR(4)        PK, FK -> department.dept_no
                   - from_date  DATE           NOT NULL
                   - to_date    DATE           NOT NULL

                5. salary
                   - emp_no     INT            PK, FK -> employee.emp_no
                   - from_date  DATE           PK
                   - amount     INT            NOT NULL
                   - to_date    DATE           NOT NULL

                6. title
                   - emp_no     INT            PK, FK -> employee.emp_no
                   - title      VARCHAR(50)    PK
                   - from_date  DATE           PK
                   - to_date    DATE           NULL

                Relationships:
                - One employee can have multiple department assignments (dept_emp)
                - One department can have many employees
                - One employee can manage multiple departments over time (dept_manager)
                - One employee can have multiple salary records over time
                - One employee can have multiple titles over time
                - dept_emp and dept_manager act as junction tables between employee and department
                """;
    }
}