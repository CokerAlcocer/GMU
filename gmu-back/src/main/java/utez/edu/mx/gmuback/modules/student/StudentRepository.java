package utez.edu.mx.gmuback.modules.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<T, ID>
// T = Al tipo de entidad que manejara el repo, ID = Al tipo de dato de la llave primaria de la entidad
public interface StudentRepository extends JpaRepository<Student, Long> {
}
