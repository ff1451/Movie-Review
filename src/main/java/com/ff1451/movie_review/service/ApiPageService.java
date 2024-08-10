package com.ff1451.movie_review.service;

import com.ff1451.movie_review.entity.ApiPage;
import com.ff1451.movie_review.repository.ApiPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiPageService {
    private final ApiPageRepository apiPageRepository;

    public int getLastPage(){
        return apiPageRepository.findById(1L)
            .map(ApiPage::getLastPage)
            .orElse(1);
    }

    public void updateLastPage(int lastPage) {
        ApiPage page = apiPageRepository.findById(1L)
            .orElse(new ApiPage());
        page.setLastPage(lastPage);
        apiPageRepository.save(page);
    }

    public void resetLastPage() {
        updateLastPage(1);
    }

}
