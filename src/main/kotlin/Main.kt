// src/main/kotlin/com/ejemplo/Main.kt

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence
import java.time.LocalDate


fun main() {
    //Parte ORM
    val entityManagerFactory: EntityManagerFactory = Persistence.createEntityManagerFactory("ejemplo")
    val entityManager: EntityManager = entityManagerFactory.createEntityManager()

    //SE CREAN LOS OBJETOS

    // Objeto de la clase Dirección
    val nuevadireccion = Direccion(4,"San Juan","San Fernando")

    // Objeto de la clase Empleado
    val empleado =  Empleado(apellidos = "Rodriguez", nombre = "Agustín", fechaNacimiento = LocalDate.now())

    val mipersona = Person(1, "Luis", 36)

    // Objetos de la clase Animal para OneToMany
    val animal1 = Animal(name = "Fredo", age = 4)
    val animal2 = Animal(name = "Michael", age = 2)

    // establezco la relación entre animal y persona
    animal1.papa = mipersona
    animal2.papa = mipersona


    nuevadireccion.empleado = empleado
    empleado.direccion = nuevadireccion

    // Persistencia de los objetos
    entityManager.transaction.begin()
    entityManager.persist(empleado)
    entityManager.transaction.commit()
    entityManager.transaction.begin()
    entityManager.persist(mipersona)
    entityManager.persist(animal1)
    entityManager.persist(animal2)
    entityManager.transaction.commit()


    entityManager.close()
    entityManagerFactory.close()
}