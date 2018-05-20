package hu.elte.f40b2i.data;

import hu.elte.f40b2i.data.Tudor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface TudorRepository extends JpaRepository<Tudor,Integer> { }
