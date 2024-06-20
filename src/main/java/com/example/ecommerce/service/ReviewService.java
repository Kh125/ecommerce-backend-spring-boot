package com.example.ecommerce.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.example.ecommerce.dto.ReviewDto;
import com.example.ecommerce.model.Review;
import com.example.ecommerce.repository.ReviewRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public ReviewService(
        ReviewRepository reviewRepository,
        ModelMapper modelMapper
    ) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    public List<ReviewDto> getAllReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        if(reviews == null) {
            return Collections.emptyList();
        }
        
        return reviews
                    .stream()
                    .map(review -> modelMapper.map(review, ReviewDto.class))
                    .collect(Collectors.toList());
    }

    public ReviewDto getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .orElse(null);
    }

    public Review createReview(ReviewDto reviewDto) {
        Review review = modelMapper.map(reviewDto, Review.class);

        review.setReviewDate(LocalDateTime.now());
        return reviewRepository.save(review);
    }   

    public Review updateReview(Long reviewId, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewId).orElse(null);

        Optional.ofNullable(reviewDto.getContent()).ifPresent(review::setContent);
        Optional.ofNullable(reviewDto.getRating()).ifPresent(review::setRating);
        review.setReviewDate(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
