package com.ctrlaltthink.ai_resume_parser.service.user.impl;

import com.ctrlaltthink.ai_resume_parser.dto.user.ProfileUpdateRequest;
import com.ctrlaltthink.ai_resume_parser.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public void mergeUserInfo(User user, ProfileUpdateRequest request) {

        if(StringUtils.isNoneBlank(request.getFirstName())
                && !user.getFirstName().equals(request.getFirstName())){
            user.setFirstName(request.getFirstName());
        }

        if(StringUtils.isNoneBlank(request.getLastName())
                && !user.getLastName().equals(request.getLastName())){
            user.setLastName(request.getLastName());
        }
    }
}
