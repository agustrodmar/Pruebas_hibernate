import jakarta.persistence.*

@Entity
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    val age: Int,

    @OneToMany(mappedBy = "papa", cascade = [CascadeType.ALL])
    var animals: MutableList<Animal> = mutableListOf()
)