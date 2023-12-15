import jakarta.persistence.*

@Entity
data class Direccion (
    @Id
    @Column(name = "ID_DIRECCION")
    private val iddireccion:Int,
    @Column(name = "DIRECCION")
    private val nombredireccion:String,
    @Column(name = "LOCALIDAD")
    private val localidad:String,

    @OneToOne(mappedBy = "direccion", fetch = FetchType.LAZY)
    var empleado:Empleado?=null
){
    override fun toString(): String {
        return "Direccion(iddireccion=$iddireccion, nombredireccion='$nombredireccion', localidad='$localidad', empleado=${empleado?.codigo})"
    }
}
