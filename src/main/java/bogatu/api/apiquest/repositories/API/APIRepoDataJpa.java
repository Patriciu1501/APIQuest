package bogatu.api.apiquest.repositories.API;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.entities.API;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface APIRepoDataJpa extends JpaRepository<API, Integer> {

    @Transactional(readOnly = true)
    List<APIDto> getAllAPIs();


    @Query("""
            SELECT a FROM API a
            WHERE a.isDefault = true""")
    List<API> getAllDefaults();
}
