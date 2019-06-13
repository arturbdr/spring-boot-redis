package com.poc.springbootredis.gateway.http.converter;

import com.poc.springbootredis.domain.User;
import com.poc.springbootredis.domain.UserWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class UserToUserWrapper implements Converter<User, UserWrapper> {

    private final HttpServletRequest request;

    @Override
    public UserWrapper convert(final User source) {

        final String cacheKey = new StringBuilder()
                .append(request.getRequestURI())
                .append(request.getQueryString())
                .toString();

        return UserWrapper.builder()
                .user(source)
                .cacheKey(cacheKey)
                .build();
    }
}
