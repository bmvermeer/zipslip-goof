package io.snyk.demo.repo;

import io.snyk.demo.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Long> {

}
