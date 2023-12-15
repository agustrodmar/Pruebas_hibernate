import jakarta.persistence.*

@Entity
data class Animal(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long?=null,
    val name:String,
    val age:Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id")
    var papa: Person? = null
)