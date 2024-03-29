package com.senla.service;

import com.senla.annotation.Logging;
import com.senla.api.dao.IRatingDao;
import com.senla.api.dao.IUserProfileDao;
import com.senla.api.service.IRatingService;
import com.senla.api.service.IUserAuthenticationService;
import com.senla.model.Rating;
import com.senla.model.UserProfile;
import com.senla.model.dto.RatingDto;
import com.senla.model.dto.filter.RatingFilter;
import com.senla.modelMapperMethods.ExtendedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class RatingService implements IRatingService {
    @Autowired
    private IUserProfileDao userProfileDao;
    @Autowired
    private IRatingDao ratingDao;
    @Autowired
    private ExtendedModelMapper modelMapper;
    @Autowired
    private IUserAuthenticationService userAuthenticationService;

    @Override
    @Logging
    public void addMarkToUser(RatingDto ratingDto) {
        if (ratingDto.getRating() < 0 || ratingDto.getRating() > 5) {
            throw new RuntimeException("Недопустимая отметка");
        }
        UserProfile sender = modelMapper.map(userAuthenticationService.getCurrentUserProfile(), UserProfile.class);
        Rating rating = modelMapper.map(ratingDto, Rating.class);
        rating.setSender(sender);
        if (rating.getSender().getId().equals(rating.getReceiver().getId())) {
            throw new RuntimeException("Вы не можете поставить себе оценку.");
        }
        RatingFilter ratingFilter = new RatingFilter();
        ratingFilter.setReceiver(rating.getReceiver().getId());
        ratingFilter.setSender(rating.getSender().getId());
        if (ObjectUtils.isEmpty(ratingDao.getByFilter(ratingFilter))) {
            rating.setCreationDate(LocalDate.now());
            ratingDao.save(rating);
            RatingFilter ratingFilter2 = new RatingFilter();
            ratingFilter2.setReceiver(rating.getReceiver().getId());
            UserProfile receiver = userProfileDao.get(rating.getReceiver().getId());
            List<Rating> ratings = ratingDao.getByFilter(ratingFilter2);
            Integer sum = 0;
            for (Rating value : ratings) {
                sum += value.getRating();
            }
            Double avgRating = (double) (sum / ratings.size());
            receiver.setAvgRating(avgRating);
            userProfileDao.update(receiver);
        } else if (!ObjectUtils.isEmpty(ratingDao.getByFilter(ratingFilter))) {
            throw new RuntimeException("Пользователь уже получил от Вас оценку.");
        }
    }
}