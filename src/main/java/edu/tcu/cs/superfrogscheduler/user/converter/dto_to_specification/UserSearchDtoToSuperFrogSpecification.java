package edu.tcu.cs.superfrogscheduler.user.converter.dto_to_specification;

import edu.tcu.cs.superfrogscheduler.user.entity.utils.SuperFrogUserSpecification;
import edu.tcu.cs.superfrogscheduler.user.dto.UserSearchDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserSearchDtoToSuperFrogSpecification
        implements Converter<UserSearchDto, SuperFrogUserSpecification> {

    @Override
    public SuperFrogUserSpecification convert(UserSearchDto source) {

        return new SuperFrogUserSpecification(
                source.getFirstName(),
                source.getLastName(),
                source.getPhoneNumber(),
                source.getEmail());

    }

}
