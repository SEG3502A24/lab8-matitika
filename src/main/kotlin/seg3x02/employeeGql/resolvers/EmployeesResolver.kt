package seg3x02.employeeGql.resolvers

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.*

@Controller
class EmployeesResolver (
    private val employeesRepo: EmployeesRepository,
    private val mongoOperations: MongoOperations
){
    @QueryMapping
    fun employees(): List<Employee> {
        return employeesRepo.findAll()
    }

    @MutationMapping
    fun addEmployee(@Argument("createEmployeeInput") input: CreateEmployeeInput): Employee {
        if (input.name != null &&
            input.dateOfBirth != null &&
            input.city != null &&
            input.salary != null) {
                val employee = Employee(input.name, input.dateOfBirth, input.city, input.salary, input.gender, input.email)
                employee.id = UUID.randomUUID().toString()
                employeesRepo.save(employee)
                return employee
            } else {
                throw Exception("Invalid Input")
            }
    }
}
