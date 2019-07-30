package ru.restaurant.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.voting.model.DayMenu;

@Repository
@Transactional(readOnly = true)
public interface DayMenuRepository extends JpaRepository<DayMenu, Integer> {

    @Transactional
    @Override
    void deleteById(Integer id);

    @Transactional
    @Override
    DayMenu save(DayMenu dayMenu);
}