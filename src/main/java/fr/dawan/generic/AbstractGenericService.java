package fr.dawan.generic;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractGenericService<
        E extends BaseEntity,
        D,
        R extends JpaRepository<E, Long>,
        M extends GenericMapper<D, E>
        > implements GenericService<D> {
    
    protected final R repository;
    protected final M mapper;
    protected final ApplicationEventPublisher publisher;
    
    @Override
    public Page<D> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }
    
    @Override
    public Optional<D> findById(long id) {
        return repository.findById(id).map(mapper::toDto);
    }
    
    @Override
    public D saveOrUpdate(D dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }
    
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
